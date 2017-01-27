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
        
        int minX = (int)Math.floor(body.getMinX());
        int maxX = (int)Math.ceil(body.getMaxX());
        int minY = (int)Math.floor(body.getMinY());
        int maxY = (int)Math.ceil(body.getMaxY());
        
        for (int y = minY; y <= maxY; y++) {
            for (int x = minX; x <= maxX; x++) {
                if (handleTileCollision(body, x, y)) {
//                    return;
                }
            }
        }
    }
    
    private boolean handleTileCollision(TileBody body, int x, int y) {
        if (!map.getCollision(x, y)) {
            return false;
        }
        
        final int UP = 0;
        final int DOWN = 1;
        final int LEFT = 2;
        final int RIGHT = 3;
        
        float minX = (float)x;
        float minY = (float)y;
        float maxX = minX + 1f;
        float maxY = minY + 1f;
        
        // tile sides
        float[] depth = new float[4];
        for (int i = 0; i < depth.length; i++) {
            depth[i] = Float.MAX_VALUE;
        }
        
        if (!map.getCollision(x + 1, y)) depth[RIGHT] = maxX - body.getMinX();
        if (!map.getCollision(x - 1, y)) depth[LEFT] = body.getMaxX() - minX;
        if (!map.getCollision(x, y - 1)) depth[DOWN] = body.getMaxY() - minY;
        if (!map.getCollision(x, y + 1)) depth[UP] = maxY - body.getMinY();
        
        for (int i = 0; i < 4; i++) {
            System.out.print(depth[i] + " ");
        }
        System.out.println();
        
        int smallestDepth = min(depth);
        
        // no valid collision was found
        if (depth[smallestDepth] == Float.MAX_VALUE) {
            return false;
        }
        
        switch (smallestDepth) {
        case UP:
            System.out.println("up");
            body.setMinY(maxY);
            break;
        case DOWN:
            System.out.println("down");
            body.setMaxY(minY);
            break;
        case LEFT:
            System.out.println("left");
            body.setMinX(maxX);
            break;
        case RIGHT:
            System.out.println("right");
            body.setMaxX(minX);
            break;
        }
        
        return true;
    }
    
    private int min(float... list) {
        int min = 0;
        
        for (int i = 0; i < list.length; i++) {
            if (list[i] < list[min]) {
                min = i;
            }
        }
        
        return min;
    }
    
}
