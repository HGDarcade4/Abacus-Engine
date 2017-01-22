package abacus.graphics;

/*
 * Base class for fonts. These keep a sprite for different characters.
 */
public abstract class GameFont {

    // font size and transparency
    protected float size, alpha = 1f;
    
    // returns the sprite that is used by a character
    public abstract Sprite getSprite(char c);
    
    // returns the height of sprites used, should be the same as [size]
    public abstract float getHeight();
    
    // returns the width of a character. Preferably, it should maintain 
    // the sprite's width-height ratio
    public abstract float getWidth(char c);
    
    // sets the size of the font (which determines the height)
    // for best results, try to make size a multiple of the sprite height
    public void setSize(float size) {
        this.size = size;
    }
    
    // set transparency
    public void setAlpha(float alpha) {
        this.alpha = alpha;
    }

    // gets the total width of a string of text
    public float getWidth(String s) {
        int width = 0;
        for (char c : s.toCharArray()) {
            width += getWidth(c);
        }
        return width;
    }
    
    // draws text using this font using the virtual resolution. 
    // (x, y) is the bottom left corner of the text
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
    
    // draws text using this font using the real screen. 
    // (x, y) is the bottom left corner of the text
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