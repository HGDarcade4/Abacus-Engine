package abacus.awt;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import abacus.graphics.Sprite;
import abacus.graphics.Texture;

/*
 * Java2D implementation of Texture and Sprite. 
 * 
 * in Java2D, they can be the same thing, but in
 * OpenGL, it would be something like Texture and 
 * then a TextureRegion for the sprite.
 * 
 * You never work directly with this class.
 */
public class ImageSprite extends Sprite implements Texture {

    // needs a reference to renderer so it can draw itself
    // I do it this way instead of something like renderer.drawSprite(sprite, x, y, w, h)
    // because you would have to cast to ImageSprite and that's ugly
    protected static AwtCanvasRenderer renderer;
    
    // image data
    private BufferedImage image;
    // state data
    private float alpha, layer;
    
    // creates an image sprite from an image
    public ImageSprite(BufferedImage image) {
        this.image = image;
    }
    
    // gets the buffered image
    public BufferedImage getImage() {
        return image;
    }
    
    // creates a buffered image copy
    public BufferedImage getBufferedImageCopy() {
        BufferedImage copy = ImageFactory.createBufferedImage(getWidth(), getHeight());
        Graphics2D g = copy.createGraphics();
        g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        g.dispose();
        return copy;
    }
    
    // width in pixels
    @Override
    public int getWidth() {
        return image.getWidth();
    }
    
    // height in pixels
    @Override
    public int getHeight() {
        return image.getHeight();
    }

    // set transparency
    @Override
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    // set z-index layer
    @Override
    public void setLayer(float layer) {
        this.layer = layer;
    }

    // draws the image using virtual resolution dimensions
    @Override
    public void draw(float x, float y, float w, float h) {
        renderer.setAlpha(alpha);
        renderer.setLayer(layer);
        renderer.drawImage(image, x, y, w, h, false);
    }
    
    // draws the image using the canvas resolution
    @Override
    public void drawReal(float x, float y, float w, float h) {
        renderer.setAlpha(alpha);
        renderer.setLayer(layer);
        renderer.drawImageReal(image, x, y, w, h, false);
    }

    // get a subsection of a texture
    @Override
    public Sprite getSprite(int x, int y, int w, int h) {
        return new ImageSprite(image.getSubimage(x, image.getHeight() - y - 1, w, h));
    }

    // get the sprite of the whole texture
    @Override
    public Sprite getSprite() {
        return this;
    }
    
}
