package abacus.tile;

public class TilePhysics {

    private TileMap map;
    
    public TilePhysics(TileMap map) {
        this.map = map;
    }
    
    public void update(TileBody body) {
        body.integrate();
        handleMapCollision(body);
    }
    
    public void handleMapCollision(TileBody body) {
        if (!body.collideTiles()) { 
            return;
        }
        
        int minX = (int)Math.floor(body.getMinX() / map.getTileSize());
        int maxX = (int)Math.ceil(body.getMaxX() / map.getTileSize());
        int minY = (int)Math.floor(body.getMinY() / map.getTileSize());
        int maxY = (int)Math.ceil(body.getMaxY() / map.getTileSize());
        
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                handleTileCollision(body, x, y);
            }
        }
    }
    
    private boolean handleTileCollision(TileBody body, int x, int y) {
        if (!map.getCollision(x, y)) {
            return false;
        }
        
        float minX = (float)x * map.getTileSize();
        float minY = (float)y * map.getTileSize();
        float maxX = minX + map.getTileSize();
        float maxY = minY + map.getTileSize();
        
        if (body.getMaxX() > minX &&
            body.getMinX() < maxX &&
            body.getMaxY() > minY &&
            body.getMinY() < maxY) {
            
        }
        else {
            return false;
        }
        
        final int UP = 1;
        final int DOWN = 2;
        final int LEFT = 3;
        final int RIGHT = 4;
        
        // tile sides
        float[] depth = new float[5];
        for (int i = 0; i < depth.length; i++) {
            depth[i] = Float.MAX_VALUE;
        }
        
        if (!map.getCollision(x + 1, y)) depth[RIGHT] = maxX - body.getMinX();
        if (!map.getCollision(x - 1, y)) depth[LEFT] = -minX + body.getMaxX();
        if (!map.getCollision(x, y - 1)) depth[DOWN] = body.getMaxY() - minY;
        if (!map.getCollision(x, y + 1)) depth[UP] = maxY - body.getMinY();
        
        int smallestDepth = minGTZero(depth);
        
        // no valid collision was found
        if (depth[smallestDepth] == Float.MAX_VALUE) {
            return false;
        }
        
        if (depth[smallestDepth] > 0 && depth[smallestDepth] < map.getTileSize()) {
            switch (smallestDepth) {
            case UP:
                 body.setMinY(maxY);
                break;
            case DOWN:
                body.setMaxY(minY);
                break;
            case LEFT:
                body.setMaxX(minX);
                break;
            case RIGHT:
                body.setMinX(maxX);
                break;
            }
        }
        
        return true;
    }
    
    private int minGTZero(float... list) {
        int min = 0;
        
        for (int i = 0; i < list.length; i++) {
            if (list[i] < list[min] && list[i] > 0) {
                min = i;
            }
        }
        
        return min;
    }
    
}
