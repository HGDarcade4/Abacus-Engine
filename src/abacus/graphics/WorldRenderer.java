package abacus.graphics;

public class WorldRenderer {

    private int tileSize;
    private float camX, camY;
    private int halfWidth, halfHeight;
    private float charOffset;
    private float layer, alpha = 1f;
    
    public WorldRenderer(Renderer r) {
        halfWidth = r.getWidth() / 2;
        halfHeight = r.getHeight() / 2;
    }
    
    public void setTileSize(int size) {
        tileSize = size;
    }
    
    public void setView(float x, float y) {
        camX = x;
        camY = y;
    }
    
    public void setCharOffset(float off) {
        charOffset = off;
    }
    
    public void setLayer(float layer) {
        this.layer = layer;
    }
    
    public float getMinX() {
        return camX - (float)halfWidth / tileSize;
    }
    
    public float getMaxX() {
        return camX + (float)halfWidth / tileSize;
    }
    
    public float getMinY() {
        return camY - (float)halfHeight / tileSize;
    }
    
    public float getMaxY() {
        return camY + (float)halfHeight / tileSize;
    }
    
    // draws sprite 1 unit big
    public void drawTileSprite(Renderable image, int x, int y) {
        Sprite sprite = image.getSprite();
        
        int drawX = halfWidth + (int)Math.floor((x - camX - 0.5f) * tileSize);
        int drawY = halfHeight + (int)Math.floor((y - camY - 0.5f) * tileSize);
        
        sprite.draw(drawX, drawY, tileSize, tileSize, alpha, layer);
    }
    
    public void drawTileSprite(Renderable ul, Renderable ur, Renderable dl, Renderable dr, int x, int y) {
        int drawX = halfWidth + (int)Math.floor((x - camX - 0.5f) * tileSize);
        int drawY = halfHeight + (int)Math.floor((y - camY - 0.5f) * tileSize);
        
        int half = tileSize / 2;
        
        ul.getSprite().draw(drawX, drawY + half, half, half, alpha, layer);
        ur.getSprite().draw(drawX + half, drawY + half, half, half, alpha, layer);
        dl.getSprite().draw(drawX, drawY, half, half, alpha, layer);
        dr.getSprite().draw(drawX + half, drawY, half, half, alpha, layer);
    }
    
    // draws sprite 1 unit big
    public void drawCharacterSprite(Renderable image, float x, float y) {
        Sprite sprite = image.getSprite();
        
        int drawX = halfWidth + (int)Math.floor((x - camX - 0.5f) * tileSize);
        int drawY = halfHeight + (int)Math.floor((y - camY - 0.5f + charOffset) * tileSize);
        
        int height = (int)Math.ceil(tileSize * (float)sprite.getHeight() / sprite.getWidth());
        
        sprite.draw(drawX, drawY, tileSize, height, alpha, layer);
    }
    
    // centers text at point on world
    public void drawText(GameFont font, String text, float x, float y) {
//        int height = size; //(int)(font.getHeight() * size);
        float width = font.getWidth(text);
        
        float drawX = halfWidth + (float)Math.floor((x - camX) * tileSize);
        float drawY = halfHeight + (float)Math.floor((y - camY) * tileSize);
        
        drawX -= width/2;
        
        font.draw(text, drawX, drawY);
    }
    
}