package abacus.graphics;

/*
 * Holds an image. This is the base object you use to draw things
 * to the screen. 
 */
public abstract class Sprite implements Renderable {
    
    // get the width and height of the sprite in pixels
    public abstract int getWidth();
    public abstract int getHeight();
    
    // sets the transparency of the image: 0 is clear -> 1 is opaque
    public abstract void setAlpha(float alpha);
    
    // set the z-index of the image. A larger number means it will be more in 
    // front of the screen
    public abstract void setLayer(float layer);
    
    /*
     * draws the sprite in the renderer's virtual resolution. 
     * 
     * You should normally use this method or it's derivatives
     * in order to draw sprites to the screen.
     * 
     * NOTE: the position (0, 0) is at the bottom left, instead
     * of the top left like with Java Graphics. 
     */
    public abstract void draw(float x, float y, float w, float h);
    
    /*
     * draws the sprite in the renderer's true resolution. 
     * 
     * You will most likely never have to work with this, it is 
     * mostly for debug text. 
     * 
     * NOTE: the position (0, 0) is at the bottom left, instead
     * of the top left like with Java Graphics. 
     */
    public abstract void drawReal(float x, float y, float w, float h);
    
    // helper method for draw() that also sets alpha and layer all in one method
    public void draw(float x, float y, float w, float h, float alpha, float layer) {
        setAlpha(alpha);
        setLayer(layer);
        draw(x, y, w, h);
    }
    
    // helper method for drawReal() that also sets alpha and layer all in one method
    public void drawReal(float x, float y, float w, float h, float alpha, float layer) {
        setAlpha(alpha);
        setLayer(layer);
        drawReal(x, y, w, h);
    }
    
    // ============================================================
    // these methods are just here because it implements Renderable
    // ============================================================
    
    public Sprite getSprite() {
        return this;
    }
    
    public void play() {}
    
    public void pause() {}
    
    public void reset() {}
    
    public void pauseAndReset() {}
    
}
