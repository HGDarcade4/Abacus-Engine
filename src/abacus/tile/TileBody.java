package abacus.tile;

public class TileBody {

    private float x, y;
    private float dx, dy;
    private float width, height;
    private boolean collideTiles;
    
    public TileBody(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        dx = dy = 0.0f;
        
        collideTiles = true;
    }
    
    public void integrate() {
        x += dx; 
        y += dy;
    }
    
    // Getters
    public float getX() { return x; }
    public float getY() { return y; }
    
    public float getMinX() { return x; }
    public float getMinY() { return y; }
    
    public float getCenterX() { return x + width / 2; }
    public float getCenterY() { return y + height / 2; }
    
    public float getMaxX() { return x + width; }
    public float getMaxY() { return y + height; }
    
    public float getVelX() { return dx; }
    public float getVelY() { return dy; }
    
    public float getWidth() { return width; }
    public float getHeight() { return height; }
    
    public boolean collideTiles() { return collideTiles; }
    
    // Setters
    public void setX(float x) { this.x = x; }
    public void setY(float y) { this.y = y; }
    
    public void setMinX(float x) { this.x = x; }
    public void setMinY(float y) { this.y = y; }
    
    public void setMaxX(float x) { this.x = x - width; }
    public void setMaxY(float y) { this.y = y - height; }
    
    public void setWidth(float w) { width = w; }
    public void setHeight(float h) { height = h; }
    
    public void setVelX(float dx) { this.dx = dx; }
    public void setVelY(float dy) { this.dy = dy; }
    
    public void setCollideTiles(boolean ct) { collideTiles = ct; }
    
}
