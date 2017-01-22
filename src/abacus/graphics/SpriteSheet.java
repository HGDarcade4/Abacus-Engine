package abacus.graphics;

/*
 * Splits up a texture into sprites of a specific width and height
 * you can index these sprites with an index, or an x and y coordinate.
 * 
 * You can then use the individual sprites for tiles, animations, etc.
 */
public class SpriteSheet {

    // source of the sprite sheet
    private Texture source;
    
    // how many sprites does the sheet have horizontally and vertically
    private int tilesWide, tileHigh;
    // what is the width and height of a sprite
    private int tWidth, tHeight;
    // list of sprites
    private Sprite sprites[];
    
    // ctor: needs texture to split, width and height of an individual sprite
    public SpriteSheet(Texture tex, int tWidth, int tHeight) {
        this.tWidth = tWidth;
        this.tHeight = tHeight;
        
        source = tex;
        genSprites();
    }
    
    // returns the source texture
    public Texture getSource() {
        return source;
    }
    
    // width of a single sprite
    public int tileWidth() {
        return tWidth;
    }
    
    // height of a single sprite
    public int tileHeight() {
        return tHeight;
    }
    
    // number of sprites horizontally
    public int tilesWide() {
        return tilesWide;
    }
    
    // number of sprites vertically
    public int tilesHigh() {
        return tileHigh;
    }

    // width of texture
    public int getWidth() {
        return source.getWidth();
    }

    // height of texture
    public int getHeight() {
        return source.getHeight();
    }

    /* returns the sprite at the index
     * 
     * NOTE: sprite index increments horizontally from top to bottom, so:
     * (0, 0) = 0
     * (1, 0) = 1
     * (0, 1) = tilesWide
     * (1, 1) = tilesWide + 1
     */
    public Sprite getSprite(int index) {
        return sprites[index];
    }

    // returns the sprite at x, y tile coordinates, top to bottom, left to right
    public Sprite getSprite(int x, int y) {
        return sprites[x + y * tilesWide];
    }
    
    // creates the individual sprites
    private void genSprites() {
        tilesWide = getWidth() / tWidth;
        tileHigh = getHeight() / tHeight;
        sprites = new Sprite[tilesWide * tileHigh];
        
        for (int y = 0; y < tileHigh; y++) {
            for (int x = 0; x < tilesWide; x++) {
                
                sprites[x + y * tilesWide] = source.getSprite(x * tWidth, source.getHeight() - y * tHeight - 1, tWidth, tHeight);
            }
        }
    }
    
}

