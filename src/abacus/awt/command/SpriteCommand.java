package abacus.awt.command;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;

/*
 * Draw command for drawing a sprite
 * 
 * You never work directly with this class.
 */
public class SpriteCommand implements AwtRenderCommand {

    // image to draw and other states
    private Image image;
    private int x, y, w, h;
    private float alpha;
    private float layer;
    
    // ctor
    public SpriteCommand(Image image, int x, int y, int w, int h, float alpha, float layer, boolean flip) {
        if (flip) {
            x += w;
            w = -w;
        }
        
        this.image = image;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.alpha = alpha;
        this.layer = layer;
    }
    
    // draws the image
    @Override
    public void draw(Graphics2D g) {
        if (alpha < 1.0f) {
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)); 
        }
        else {
            g.setComposite(AlphaComposite.SrcOver);
        }
        g.drawImage(image, x, y, w, h, null);
    }

    // return whatever layer was set
    @Override
    public float getLayer() {
        return layer;
    }

}
