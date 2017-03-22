package abacus.awt;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import abacus.awt.command.AwtRenderCommand;
import abacus.awt.command.ClearCommand;
import abacus.awt.command.RectCommand;
import abacus.awt.command.SpriteCommand;
import abacus.graphics.Renderer;

/*
 * Basic Java2D implementation of Renderer where sprites are
 * drawn directly onto the canvas
 * 
 * using floating point positions, this can allow sprites to move 
 * more smoothly, but it can also be more ugly, since this allows
 * very thin (1 px) offsets between sprites
 * 
 * You never work directly with this class.
 */
public class AwtCanvasRenderer implements Renderer {

    // canvas graphics
    protected Canvas canvas;
    
    // current layer (z-index) and transparency state
    protected float layer = 0.0f;
    protected float alpha = 1.0f;
    
    // width and height of virtual resolution
    protected int width, height;
    
    // number of draw commands last frame
    protected int drawCommands = 0;
    
    // list of draw commands
    protected List<AwtRenderCommand> commands;
    protected List<AwtRenderCommand> screenCommands;
    
    // ctor, needs a canvas and virtual resolution
    public AwtCanvasRenderer(Canvas c, int width, int height) {
        ImageSprite.renderer = this;
        
        this.width = width;
        this.height = height;
        
        canvas = c;
        commands = new ArrayList<>();
        screenCommands = new ArrayList<>();
    }
    
    // sets the virtual resolution
    public void setVirtualResolution(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    // game engine calls this before rendering
    @Override
    public void begin() {
        commands.clear();
        screenCommands.clear();
    }
    
    // game engine calls this once rendering is done
    // displays the graphics to the screen
    @Override
    public void finish() {
        // sort draw commands by layer
        Collections.sort(commands, new Comparator<AwtRenderCommand>() {
            @Override
            public int compare(AwtRenderCommand a, AwtRenderCommand b) {
                return a.getLayer() > b.getLayer() ? 1 : a.getLayer() < b.getLayer() ? -1 : 0;
            }
        });
        // sort more draw commands by layer
        Collections.sort(screenCommands, new Comparator<AwtRenderCommand>() {
            @Override
            public int compare(AwtRenderCommand a, AwtRenderCommand b) {
                return a.getLayer() > b.getLayer() ? 1 : a.getLayer() < b.getLayer() ? -1 : 0;
            }
        });
        
        try {
            BufferStrategy bs = canvas.getBufferStrategy();
            if (bs == null || bs.contentsLost()) {
                canvas.createBufferStrategy(2);
                return;
            }
            Graphics2D graphics = (Graphics2D)bs.getDrawGraphics();
            
            // set the screen to black
            graphics.setColor(Color.BLACK);
            graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            
            // draw sprites to the canvas
            for (AwtRenderCommand c : commands) {
                c.draw(graphics);
            }
            
            // this whole mess draws black bars onto the canvas
            // so that you are limited to the virtual resolution's ratio
            float ratio = (float)width / height;
            float sRatio = (float)canvas.getWidth() / canvas.getHeight();
            int dx = 0;
            int dy = 0;
            graphics.setColor(Color.BLACK);
            if (ratio > sRatio) {
                ratio = (float)canvas.getWidth() / width;
                dy = (int)((canvas.getHeight() - height * ratio) / 2f);
                
                graphics.fillRect(0, 0, canvas.getWidth(), dy);
                graphics.fillRect(0, canvas.getHeight() - dy, canvas.getWidth(), dy);
            }
            else { 
                ratio = (float)canvas.getHeight() / height;
                dx = (int)((canvas.getWidth() - width * ratio) / 2f);
                
                graphics.fillRect(0, 0, dx, canvas.getHeight());
                graphics.fillRect(canvas.getWidth() - dx, 0, dx, canvas.getHeight());
            }
            
            // draws stuff to the canvas's actual resolution
            for (AwtRenderCommand c : screenCommands) {
                c.draw(graphics);
            }
            
            // dispose of buffer strategy's graphics and swap buffers
            graphics.dispose();
            bs.show();
            
            // track draw commands
            drawCommands = commands.size() + screenCommands.size();
        }
        catch (Exception e) {
            // should do something...
            e.printStackTrace();
            System.out.println("\n\n\n");
        }
//        System.out.println("render");
    }
    
    // number of draw commands last frame
    @Override
    public int drawCommands() {
        return drawCommands;
    }
    
    // returns virtual resolution's width
    @Override
    public int getWidth() {
        return width;
    }
    
    // returns virtual resolution's height
    @Override
    public int getHeight() {
        return height;
    }
    
    // returns canvas's width
    @Override
    public int getRealWidth() {
        return canvas.getWidth();
    }
    
    // returns canvas's height
    @Override
    public int getRealHeight() {
        return canvas.getHeight();
    }

    // clears the screen to a specific color
    @Override
    public void clearScreen(int r, int g, int b) {
        commands.add(new ClearCommand(new Color(r << 16 | g << 8 | b), canvas.getWidth(), canvas.getHeight()));
    }

    // sets the state of transparency
    public void setAlpha(float alpha) {
        this.alpha = Math.min(1.0f, Math.max(0.0f, alpha));
    }

    // sets the state of layer
    public void setLayer(float layer) {
        this.layer = layer;
    }

    // draws an image using the virtual resolution
    // [flip] flips the sprite horizontally
    public void drawImage(Image image, float x, float y, float w, float h, boolean flip) {
        if (image == null) return;
        
        float ratio = (float)width / height;
        float sRatio = (float)canvas.getWidth() / canvas.getHeight();
        float dx = 0f;
        float dy = 0f;
        
        if (ratio > sRatio) {
            ratio = (float)canvas.getWidth() / width;
            dy = (canvas.getHeight() - height * ratio) / 2f;
        }
        else { 
            ratio = (float)canvas.getHeight() / height;
            dx = (canvas.getWidth() - width * ratio) / 2f;
        }
        
        x *= ratio;
        x += dx;
        y *= ratio;
        y += dy;
        w *= ratio;
        h *= ratio;
        
        commands.add(new SpriteCommand(image,
                (int)Math.floor(x), 
                canvas.getHeight() - (int)Math.floor(y + h), 
                (int)Math.ceil(w), 
                (int)Math.ceil(h), 
                alpha, layer, flip));
    }
    
    // draws an image using the canvas's real resolution
    // [flip] flips the sprite horizontally
    public void drawImageReal(Image image, float x, float y, float w, float h, boolean flip) {
        if (image == null) return;
        
        screenCommands.add(new SpriteCommand(image,
                (int)Math.floor(x), 
                canvas.getHeight() - (int)Math.floor(y + h), 
                (int)Math.ceil(w), 
                (int)Math.ceil(h), 
                alpha, layer, flip));
    }

    @Override
    public void drawRect(int col, float x, float y, float w, float h, float layer) {
        float ratio = (float)width / height;
        float sRatio = (float)canvas.getWidth() / canvas.getHeight();
        float dx = 0f;
        float dy = 0f;
        
        if (ratio > sRatio) {
            ratio = (float)canvas.getWidth() / width;
            dy = (canvas.getHeight() - height * ratio) / 2f;
        }
        else { 
            ratio = (float)canvas.getHeight() / height;
            dx = (canvas.getWidth() - width * ratio) / 2f;
        }
        
        x *= ratio;
        x += dx;
        y *= ratio;
        y += dy;
        w *= ratio;
        h *= ratio;
        
        commands.add(new RectCommand(
                false, col, 
                (int)Math.floor(x), 
                canvas.getHeight() - (int)Math.floor(y + h), 
                (int)Math.ceil(w), 
                (int)Math.ceil(h), 
                layer
                ));
    }

}
