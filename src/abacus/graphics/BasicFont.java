package abacus.graphics;

/*
 * Mono-spaced font
 */
public class BasicFont extends GameFont {

    // this is how all BasicFont's must be set up
    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789.!?,()[]+-*/: ";
    
    // sprites for characters
    private Sprite[] regions;
    
    // ctor, [sheet] should be a font sprite sheet
    // defaults the size to the sprite height
    public BasicFont(SpriteSheet sheet) {
        regions = new Sprite[1024];
        
        for (int i = 0; i < LETTERS.length(); i++) {
            regions[LETTERS.charAt(i)] = sheet.getSprite(i);
        }
        
        size = sheet.tileHeight();
    }

    // returns the sprite for character [c]
    @Override
    public Sprite getSprite(char c) {
        if (c >= 0 && c < regions.length) {
            return regions[c];
        }
        return null;
    }
    
    // returns the height that sprites will be drawn at
    @Override
    public float getHeight() {
        return size; 
    }
    
    // returns the width that [c] would be drawn at
    @Override
    public float getWidth(char c) {
        Sprite s = getSprite(c);
        return s == null ? 0 : s.getWidth() * size / s.getHeight();
    }

}