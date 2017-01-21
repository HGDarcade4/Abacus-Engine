package abacus.graphics;

public class SpriteSheet {

    private Texture source;
    
    private String filename;
    private int wide, high;
    private int tWidth, tHeight;
    
    private Sprite sprites[];
    
    public SpriteSheet(Texture tex, int tWidth, int tHeight) {
        this.tWidth = tWidth;
        this.tHeight = tHeight;
        
        source = tex;
        genSprites();
    }
    
    public String getFileName() {
        return filename;
    }
    
    public Texture getSource() {
        return source;
    }
    
    public int tileWidth() {
        return tWidth;
    }
    
    public int tileHeight() {
        return tHeight;
    }
    
    public int tilesWide() {
        return wide;
    }
    
    public int tilesHigh() {
        return high;
    }

    public int getWidth() {
        return source.getWidth();
    }

    public int getHeight() {
        return source.getHeight();
    }

    public Sprite getSprite(int index) {
        return sprites[index];
    }

    public Sprite getSprite(int x, int y) {
        return sprites[x + y * wide];
    }
    
    private void genSprites() {
        wide = getWidth() / tWidth;
        high = getHeight() / tHeight;
        sprites = new Sprite[wide * high];
        
        for (int y = 0; y < high; y++) {
            for (int x = 0; x < wide; x++) {
                
                sprites[x + y * wide] = source.getSprite(x * tWidth, y * tHeight, tWidth, tHeight);
            }
        }
    }
    
}

