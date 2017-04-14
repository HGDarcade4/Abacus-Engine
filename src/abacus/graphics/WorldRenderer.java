package abacus.graphics;

/*
 * Renders objects with respect to a camera. This uses the virtual resolution
 */
public class WorldRenderer {

    private static final float Y_LAYER = 1f / 131072f;
    
    // reference to renderer
    private Renderer renderer;
    // how much to scale sprites by
    private int scale;
    // position of the camera
    private float camX, camY;
    // center of the screen
    private int halfWidth, halfHeight;
    // layer and transparency to draw at
    private float layer, alpha = 1f;
    
    private boolean debug = false;
    
    // ctor
    public WorldRenderer(Renderer r) {
        this.renderer = r;
        reset();
        scale = 1;
    }
    
    // whether debug drawings should be displayed
    public void setDebug(boolean db) {
        debug = db;
    }
    
    // call this at the beginning of render method
    public void reset() {
        halfWidth = renderer.getWidth() / 2;
        halfHeight = renderer.getHeight() / 2;
    }
    
    // sets the scale of the sprites, 2 for example
    public void setScale(int size) {
        scale = size;
    }
    
    // sets the location of the camera
    public void setView(float x, float y) {
        camX = x;
        camY = y;
    }
    
    // sets the layer to draw at
    public void setLayer(float layer) {
        this.layer = layer;
    }
    
    // returns the x value at the left-hand side of the virtual screen
    public float getMinX() {
        return camX - (float)halfWidth / scale;
    }
    
    // returns the x value at the right-hand side of the virtual screen
    public float getMaxX() {
        return camX + (float)halfWidth / scale;
    }
    
    // returns the y value at the bottom of the virtual screen
    public float getMinY() {
        return camY - (float)halfHeight / scale;
    }
    
    // returns the y value at the top of the virtual screen
    public float getMaxY() {
        return camY + (float)halfHeight / scale;
    }
    
    // draws sprite using camera offset
    public void drawTileSprite(Renderable image, float tileSize, float x, float y) {
        Sprite sprite = image.getSprite();
        
        x *= tileSize;
        y *= tileSize;
        
        int drawX = halfWidth + (int)Math.floor((x) * scale) - (int)(camX * scale);
        int drawY = halfHeight + (int)Math.floor((y) * scale) - (int)(camY * scale);
        
        float layer = this.layer + yAmt(y);
        
        sprite.draw(drawX, drawY, tileSize * scale, tileSize * scale, alpha, layer);
    }
    
    // draws a tile sprite, but cut into four corners
    public void drawTileSprite(Renderable ul, Renderable ur, Renderable dl, Renderable dr, float tileSize, float x, float y) {
        x *= tileSize;
        y *= tileSize;
        
        int drawX = halfWidth + (int)Math.floor((x) * scale) - (int)(camX * scale);
        int drawY = halfHeight + (int)Math.floor((y) * scale) - (int)(camY * scale);
        
        int half = (int) (tileSize * scale / 2);
        
        float layer = this.layer + yAmt(y);
        
        ul.getSprite().draw(drawX, drawY + half, half, half, alpha, layer);
        ur.getSprite().draw(drawX + half, drawY + half, half, half, alpha, layer);
        dl.getSprite().draw(drawX, drawY, half, half, alpha, layer);
        dr.getSprite().draw(drawX + half, drawY, half, half, alpha, layer);
    }
    
    public void drawTileSprite(Renderable ul, Renderable ur, Renderable dl, Renderable dr, float tileSize, float x, float y, float extraLayer) {
        x *= tileSize;
        y *= tileSize;
        
        int drawX = halfWidth + (int)Math.floor((x) * scale) - (int)(camX * scale);
        int drawY = halfHeight + (int)Math.floor((y) * scale) - (int)(camY * scale);
        
        int half = (int) (tileSize * scale / 2);
        
        float layer = this.layer + yAmt(y) + extraLayer;
        
        ul.getSprite().draw(drawX, drawY + half, half, half, alpha, layer);
        ur.getSprite().draw(drawX + half, drawY + half, half, half, alpha, layer);
        dl.getSprite().draw(drawX, drawY, half, half, alpha, layer);
        dr.getSprite().draw(drawX + half, drawY, half, half, alpha, layer);
    }
    
    public void drawDebugRect(int col, float x, float y, float w, float h) {
        int drawX = halfWidth + (int)Math.floor((x) * scale) - (int)(camX * scale);
        int drawY = halfHeight + (int)Math.floor((y) * scale) - (int)(camY * scale);
        int drawW = (int)(w * scale);
        int drawH = (int)(h * scale);
        
        if (debug) renderer.drawRect(col, drawX, drawY, drawW, drawH, Float.MAX_VALUE);
    }
    
    /*
     * draws a character, such as a NPC, enemy, etc. at the location (x, y) in world space
     * 
     * the center of the character's feet (bottom of the sprite) will be drawn at that position. 
     * 
     * this can be used to draw characters in the center of a tile
     */
    public void drawCharacterSprite(Renderable image, float x, float y, float w, float h) {
        Sprite sprite = image.getSprite();
        
        int drawX = halfWidth + (int)Math.floor((x - w/2) * scale) - (int)(camX * scale);
        int drawY = halfHeight + (int)Math.floor((y) * scale) - (int)(camY * scale);
        
        float layer = this.layer + yAmt(y);
        
        if (drawX < renderer.getWidth() &&
            drawX + scale * w > 0 &&
            drawY < renderer.getHeight() &&
            drawY + scale * h > 0) {
        
            sprite.draw(drawX, drawY, scale * w, scale * h, alpha, layer);
        }
    }
    
    /*
     *  draw text at the location (x, y) on the world world, putting the center bottom of the text
     * at that location
     * 
     * NOTE: this DOES make use of [charOffset]
     */
    public void drawText(GameFont font, String text, float x, float y) {
        float width = font.getWidth(text);
        
        float drawX = halfWidth + (float)Math.floor((x - camX) * scale);
        float drawY = halfHeight + (float)Math.floor((y - camY) * scale);
        
        drawX -= width/2;
        
        font.draw(text, drawX, drawY);
    }
    
    private float yAmt(float y) {
        return 1f - y * Y_LAYER;
    }
    
}