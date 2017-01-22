package abacus.graphics;

/*
 * Renders objects with respect to a camera. This uses the virtual resolution
 */
public class WorldRenderer {

    // reference to renderer
    private Renderer renderer;
    // size of a tile (the base unit of the game) in pixels
    private int tileSize;
    // position of the camera
    private float camX, camY;
    // center of the screen
    private int halfWidth, halfHeight;
    // offset for displaying characters: NPCs, enemies, etc. 
    // explained in more detail at drawCharacterSprite()
    private float charOffset;
    // layer and transparency to draw at
    private float layer, alpha = 1f;
    
    // ctor
    public WorldRenderer(Renderer r) {
        this.renderer = r;
        reset();
    }
    
    // call this at the beginning of render method
    public void reset() {
        halfWidth = renderer.getWidth() / 2;
        halfHeight = renderer.getHeight() / 2;
    }
    
    // sets the size of a tile in pixels, 32 for example
    public void setTileSize(int size) {
        tileSize = size;
    }
    
    // sets the location of the camera
    public void setView(float x, float y) {
        camX = x;
        camY = y;
    }
    
    // sets the offset of character sprites
    public void setCharOffset(float off) {
        charOffset = off;
    }
    
    // sets the layer to draw at
    public void setLayer(float layer) {
        this.layer = layer;
    }
    
    // returns the x value at the left-hand side of the virtual screen
    public float getMinX() {
        return camX - (float)halfWidth / tileSize;
    }
    
    // returns the x value at the right-hand side of the virtual screen
    public float getMaxX() {
        return camX + (float)halfWidth / tileSize;
    }
    
    // returns the y value at the bottom of the virtual screen
    public float getMinY() {
        return camY - (float)halfHeight / tileSize;
    }
    
    // returns the y value at the top of the virtual screen
    public float getMaxY() {
        return camY + (float)halfHeight / tileSize;
    }
    
    // draws sprite 1 unit big centered at (x, y) in world space
    public void drawTileSprite(Renderable image, int x, int y) {
        Sprite sprite = image.getSprite();
        
        int drawX = halfWidth + (int)Math.floor((x - camX - 0.5f) * tileSize);
        int drawY = halfHeight + (int)Math.floor((y - camY - 0.5f) * tileSize);
        
        sprite.draw(drawX, drawY, tileSize, tileSize, alpha, layer);
    }
    
    // draws a tile sprite, but cut into four corners
    public void drawTileSprite(Renderable ul, Renderable ur, Renderable dl, Renderable dr, int x, int y) {
        int drawX = halfWidth + (int)Math.floor((x - camX - 0.5f) * tileSize);
        int drawY = halfHeight + (int)Math.floor((y - camY - 0.5f) * tileSize);
        
        int half = tileSize / 2;
        
        ul.getSprite().draw(drawX, drawY + half, half, half, alpha, layer);
        ur.getSprite().draw(drawX + half, drawY + half, half, half, alpha, layer);
        dl.getSprite().draw(drawX, drawY, half, half, alpha, layer);
        dr.getSprite().draw(drawX + half, drawY, half, half, alpha, layer);
    }
    
    /*
     * draws a character, such as a NPC, enemy, etc. at the location (x, y) in world space
     * 
     * the center of the character's feet (bottom of the sprite) will be drawn at that position. 
     * that position is offset by [charOffset].
     * 
     * charOffset is the fraction of tile size upwards the character will be drawn, 
     * so an offset of 0.25f will draw the character a quarter of a tileSize higher up. 
     * 
     * this can be used to draw characters in the center of a tile
     */
    public void drawCharacterSprite(Renderable image, float x, float y) {
        Sprite sprite = image.getSprite();
        
        int drawX = halfWidth + (int)Math.floor((x - camX - 0.5f) * tileSize);
        int drawY = halfHeight + (int)Math.floor((y - camY - 0.5f + charOffset) * tileSize);
        
        int height = (int)Math.ceil(tileSize * (float)sprite.getHeight() / sprite.getWidth());
        
        sprite.draw(drawX, drawY, tileSize, height, alpha, layer);
    }
    
    /*
     *  draw text at the location (x, y) on the world world, putting the center bottom of the text
     * at that location
     * 
     * NOTE: this DOES make use of [charOffset]
     */
    public void drawText(GameFont font, String text, float x, float y) {
        float width = font.getWidth(text);
        
        float drawX = halfWidth + (float)Math.floor((x - camX) * tileSize);
        float drawY = halfHeight + (float)Math.floor((y - camY + charOffset) * tileSize);
        
        drawX -= width/2;
        
        font.draw(text, drawX, drawY);
    }
    
}