package abacus.awt;

import java.awt.image.BufferedImage;

import abacus.graphics.Sprite;
import abacus.graphics.Texture;

public class ImageSprite extends Sprite implements Texture {

    protected static AwtCanvasRenderer renderer;
    
    private BufferedImage image;
    private float alpha, layer;
    
    public ImageSprite(BufferedImage image) {
        this.image = image;
    }
    
    public BufferedImage getImage() {
        return image;
    }
    
    @Override
    public int getWidth() {
        return image.getWidth();
    }
    
    @Override
    public int getHeight() {
        return image.getHeight();
    }

    @Override
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    @Override
    public void setLayer(float layer) {
        this.layer = layer;
    }

    @Override
    public void draw(float x, float y, float w, float h) {
        renderer.setAlpha(alpha);
        renderer.setLayer(layer);
        renderer.drawImage(image, x, y, w, h, false);
    }
    
    @Override
    public void drawReal(float x, float y, float w, float h) {
        renderer.setAlpha(alpha);
        renderer.setLayer(layer);
        renderer.drawImageReal(image, x, y, w, h, false);
    }

    @Override
    public Sprite getSprite(int x, int y, int w, int h) {
        return new ImageSprite(image.getSubimage(x, image.getHeight() - y - 1, w, h));
    }

    @Override
    public Sprite getSprite() {
        return this;
    }
    
}
