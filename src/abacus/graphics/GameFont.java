package abacus.graphics;

public abstract class GameFont {

    protected float size, alpha = 1f;
    
    public abstract Sprite getSprite(char c);
    
    public abstract float getHeight();
    
    public abstract float getWidth(char c);
    
    public void setSize(float size) {
        this.size = size;
    }
    
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    public float getWidth(String s) {
        int width = 0;
        for (char c : s.toCharArray()) {
            width += getWidth(c);
        }
        return width;
    }
    
    public void draw(String text, float x, float y) {
        float height = size; 
        
        float drawX = x;
        float drawY = y;
        
        for (char c : text.toCharArray()) {
            Sprite s = getSprite(c);
            if (s != null) {
                float w = (float)s.getWidth() * height / s.getHeight();
                s.draw(drawX, drawY, w, height, alpha, Float.MAX_VALUE);
                drawX += w;
            }
        }
    }
    
    public void drawReal(String text, float x, float y) {
        float height = size; 
        
        float drawX = x;
        float drawY = y;
        
        for (char c : text.toCharArray()) {
            Sprite s = getSprite(c);
            if (s != null) {
                float w = (float)s.getWidth() * height / s.getHeight();
                s.drawReal(drawX, drawY, w, height, alpha, Float.MAX_VALUE);
                drawX += w;
            }
        }
    }
    
}