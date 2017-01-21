package abacus.graphics;

public abstract class Sprite implements Renderable {
    
    public abstract int getWidth();
    public abstract int getHeight();
    
    public abstract void setAlpha(float alpha);
    public abstract void setLayer(float layer);
    
    public abstract void draw(float x, float y, float w, float h);
    
    public abstract void drawReal(float x, float y, float w, float h);
    
    public void draw(float x, float y, float w, float h, float alpha, float layer) {
        setAlpha(alpha);
        setLayer(layer);
        draw(x, y, w, h);
    }
    
    public void drawReal(float x, float y, float w, float h, float alpha, float layer) {
        setAlpha(alpha);
        setLayer(layer);
        drawReal(x, y, w, h);
    }
    
    public Sprite getSprite() {
        return this;
    }
    
    public final void update() {}
    
}
