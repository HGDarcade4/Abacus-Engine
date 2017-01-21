package abacus.graphics;

public class BasicFont extends GameFont {

    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789.!?,()[]+-*/: ";
    
    private Sprite[] regions;
    
    public BasicFont(SpriteSheet sheet) {
        regions = new Sprite[1024];
        
        for (int i = 0; i < LETTERS.length(); i++) {
            regions[LETTERS.charAt(i)] = sheet.getSprite(i);
        }
        
        size = sheet.tileHeight();
    }

    @Override
    public Sprite getSprite(char c) {
        if (c >= 0 && c < regions.length) {
            return regions[Character.toUpperCase(c)];
        }
        return null;
    }
    
    @Override
    public float getHeight() {
        return size; //getSprite(' ').getHeight();
    }
    
    @Override
    public float getWidth(char c) {
        Sprite s = getSprite(c);
        return s == null ? 0 : s.getWidth() * size / s.getHeight();
    }

}