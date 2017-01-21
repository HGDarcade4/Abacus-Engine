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
import abacus.awt.command.SpriteCommand;

/*
 * TODO needs a lot of work
 */
public class AwtBufferRenderer extends AwtCanvasRenderer {

    private Image screen;
    
    public AwtBufferRenderer(Canvas c, int width, int height) {
        super(c, width, height);
        
        setVirtualResolution(width, height);
    }
    
    @Override
    public void setVirtualResolution(int width, int height) {
        this.width = width;
        this.height = height;
        screen = ImageFactory.create(width, height);
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
            
            Graphics2D g = null;
            if (screen instanceof VolatileImage) {
                g = ((VolatileImage)screen).createGraphics();
            }
            else {
                g = ((BufferedImage)screen).createGraphics();
            }
            
            for (AwtRenderCommand c : commands) {
                c.draw(g);
            }
            
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
            
//            graphics.drawImage(screen, 0, 0, canvas.getWidth(), canvas.getHeight(), canvas);
            
            for (AwtRenderCommand c : screenCommands) {
                c.draw(graphics);
            }
            
            graphics.dispose();
            bs.show();
            
            drawCommands = commands.size() + screenCommands.size();
        }
        catch (Exception e) {
            // should do something...
        }
    }

    public void drawImage(BufferedImage image, float x, float y, float w, float h, boolean flip) {
        if (image == null) return;
        
        commands.add(new SpriteCommand(image,
                (int)Math.floor(x), 
                height - (int)Math.floor(y + h), 
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
