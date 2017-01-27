package abacus.awt;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.VolatileImage;
import java.util.Collections;
import java.util.Comparator;

import abacus.awt.command.AwtRenderCommand;
import abacus.awt.command.ClearCommand;
import abacus.awt.command.SpriteCommand;

/*
 * Basic Java2D implementation of Renderer which uses 
 * the equivalent of a OpenGL frame buffer.
 * 
 * You never work directly with this class
 */
public class AwtBufferRenderer extends AwtCanvasRenderer {

    // frame buffer
    private Image screen;
    
    // ctor arguments are the canvas to draw to and the virtual resolution
    public AwtBufferRenderer(Canvas c, int width, int height) {
        super(c, width, height);
        
        setVirtualResolution(width, height);
    }
    
    // sets the virtual resolution
    @Override
    public void setVirtualResolution(int width, int height) {
        this.width = width;
        this.height = height;
        screen = ImageFactory.create(width, height);
    }
    
    // draws all draw commands that were accumulated to the frame buffer
    // and then draws the frame buffer to the screen
    @Override
    public void finish() {
        // sort commands by layer
        Collections.sort(commands, new Comparator<AwtRenderCommand>() {
            @Override
            public int compare(AwtRenderCommand a, AwtRenderCommand b) {
                return a.getLayer() > b.getLayer() ? 1 : a.getLayer() < b.getLayer() ? -1 : 0;
            }
        });
        // sort commands that go directly to the canvas by layer
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
            
            // set the screen to black
            graphics.setColor(Color.BLACK);
            graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
            
            // create the graphics for the screen. 
            // for some stupid reason Image doesn't have
            // a createGraphics() method, so you have to 
            // check what type of image it is
            Graphics2D g = null;
            if (screen instanceof VolatileImage) {
                g = ((VolatileImage)screen).createGraphics();
            }
            else {
                g = ((BufferedImage)screen).createGraphics();
            }
            
            // draw all commands to the frame buffer
            for (AwtRenderCommand c : commands) {
                c.draw(g);
            }
            
            // good practice is to dispose of graphics contexts
            g.dispose();
            
            // this big mess centers the image on the canvas
            // which creates black bars if the ratio of
            // the frame buffer and the canvas aren't the same
            float ratio = (float)width / height;
            float sRatio = (float)canvas.getWidth() / canvas.getHeight();
            int dx = 0;
            int dy = 0;
            graphics.setColor(Color.BLACK);
            if (ratio > sRatio) {
                ratio = (float)canvas.getWidth() / width;
                dy = (int)((canvas.getHeight() - height * ratio) / 2f);
                
                graphics.drawImage(screen, 0, dy, canvas.getWidth(), canvas.getHeight() - dy * 2, canvas);
            }
            else { 
                ratio = (float)canvas.getHeight() / height;
                dx = (int)((canvas.getWidth() - width * ratio) / 2f);
                
                graphics.drawImage(screen, dx, 0, canvas.getWidth() - dx * 2, canvas.getHeight(), canvas);
            }
            
            // draw commands that go directly to the canvas
            for (AwtRenderCommand c : screenCommands) {
                c.draw(graphics);
            }
            
            // dispose of the buffer stategy's graphics and swap buffers
            graphics.dispose();
            bs.show();
            
            // keep track of number of commands
            drawCommands = commands.size() + screenCommands.size();
        }
        catch (Exception e) {
            // should do something...
        }
    }
    
    // clears the screen to a specific color
    @Override
    public void clearScreen(int r, int g, int b) {
        commands.add(new ClearCommand(new Color(r << 16 | g << 8 | b), width, height));
    }

    // creates and stores a command to draw an image to the frame buffer
    public void drawImage(Image image, float x, float y, float w, float h, boolean flip) {
        if (image == null) return;
        
        commands.add(new SpriteCommand(image,
                (int)Math.floor(x), 
                height - (int)Math.floor(y + h), 
                (int)Math.ceil(w), 
                (int)Math.ceil(h), 
                alpha, layer, flip));
    }
    
    // creates and stores a command to draw an image to the canvas
    public void drawImageReal(Image image, float x, float y, float w, float h, boolean flip) {
        if (image == null) return;
        
        screenCommands.add(new SpriteCommand(image,
                (int)Math.floor(x), 
                canvas.getHeight() - (int)Math.floor(y + h), 
                (int)Math.ceil(w), 
                (int)Math.ceil(h), 
                alpha, layer, flip));
    }

}
