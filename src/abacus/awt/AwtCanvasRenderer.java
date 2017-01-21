package abacus.awt;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import abacus.awt.command.AwtRenderCommand;
import abacus.awt.command.ClearCommand;
import abacus.awt.command.SpriteCommand;
import abacus.graphics.Renderer;

/*
 * TODO needs a lot of work
 */
public class AwtCanvasRenderer implements Renderer {

    protected Canvas canvas;
    protected Graphics2D graphics;
    protected BufferStrategy bs;
    
    protected float layer = 0.0f;
    protected float alpha = 1.0f;
    
    protected int width, height;
    protected int drawCommands = 0;
    
    protected List<AwtRenderCommand> commands;
    protected List<AwtRenderCommand> screenCommands;
    
    public AwtCanvasRenderer(Canvas c, int width, int height) {
        ImageSprite.renderer = this;
        
        this.width = width;
        this.height = height;
        
        canvas = c;
        commands = new ArrayList<>();
        screenCommands = new ArrayList<>();
    }
    
    public void setVirtualResolution(int width, int height) {
        this.width = width;
        this.height = height;
    }
    
    @Override
    public void begin() {
        commands.clear();
        screenCommands.clear();
    }
    
    @Override
    public void finish() {
        Collections.sort(commands, new Comparator<AwtRenderCommand>() {
            @Override
            public int compare(AwtRenderCommand a, AwtRenderCommand b) {
                return a.getLayer() > b.getLayer() ? 1 : a.getLayer() < b.getLayer() ? -1 : 0;
            }
        });
        
        Collections.sort(screenCommands, new Comparator<AwtRenderCommand>() {
            @Override
            public int compare(AwtRenderCommand a, AwtRenderCommand b) {
                return a.getLayer() > b.getLayer() ? 1 : a.getLayer() < b.getLayer() ? -1 : 0;
            }
        });
        
        try {
            bs = canvas.getBufferStrategy();
            if (bs == null) {
                canvas.createBufferStrategy(2);
                return;
            }
            graphics = (Graphics2D)bs.getDrawGraphics();
            
            graphics.setColor(Color.BLACK);
            graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            
            for (AwtRenderCommand c : commands) {
                c.draw(graphics);
            }
            
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
            
            for (AwtRenderCommand c : screenCommands) {
                c.draw(graphics);
            }
            
//            graphics.drawImage(screen, 0, 0, canvas.getWidth(), canvas.getHeight(), canvas);
            
            graphics.dispose();
            bs.show();
            
            drawCommands = commands.size() + screenCommands.size();
        }
        catch (Exception e) {
            // should do something...
        }
    }
    
    @Override
    public int drawCommands() {
        return drawCommands;
    }
    
    @Override
    public int getWidth() {
        return width;//canvas.getWidth();
    }
    
    @Override
    public int getHeight() {
        return height;//canvas.getHeight();
    }
    
    @Override
    public int getRealWidth() {
        return canvas.getWidth();
    }
    
    @Override
    public int getRealHeight() {
        return canvas.getHeight();
    }

    @Override
    public void clearScreen(int r, int g, int b) {
        commands.add(new ClearCommand(new Color(r << 16 | g << 8 | b), canvas.getWidth(), canvas.getHeight()));
    }

    public void setAlpha(float alpha) {
        this.alpha = Math.min(1.0f, Math.max(0.0f, alpha));
    }

    public void setLayer(float layer) {
        this.layer = layer;
    }

    public void drawImage(BufferedImage image, float x, float y, float w, float h, boolean flip) {
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
    
    public void drawImageReal(BufferedImage image, float x, float y, float w, float h, boolean flip) {
        if (image == null) return;
        
        screenCommands.add(new SpriteCommand(image,
                (int)Math.floor(x), 
                canvas.getHeight() - (int)Math.floor(y + h), 
                (int)Math.ceil(w), 
                (int)Math.ceil(h), 
                alpha, layer, flip));
    }

}
