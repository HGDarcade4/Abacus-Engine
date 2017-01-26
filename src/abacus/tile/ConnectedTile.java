package abacus.tile;

import abacus.graphics.SpriteSheet;
import abacus.graphics.WorldRenderer;

import static abacus.tile.ConnectionTiles.*;

public class ConnectedTile extends Tile {

    private ConnectionTiles tiles;
    
    public ConnectedTile(SpriteSheet tileset, int x, int y) {
        this(tileset, 1, x, y);
    }
    
    public ConnectedTile(SpriteSheet sheet, int delay, int... coords) {
        tiles = new ConnectionTiles();
        
        int numFrames = coords.length / 2;
        
        for (int i = 0; i < numFrames; i++) {
            tiles.addFrame(sheet, coords[i * 2], coords[i * 2 + 1], delay);
        }
    }
    
    @Override
    public void update() {
        
    }
    
    @Override
    public void render(WorldRenderer r, TileMap map, int x, int y, int layer) {
        int tl = getCode(this, map, x, y, layer, -1, 1);
        int tr = getCode(this, map, x, y, layer, 1, 1);
        int bl = getCode(this, map, x, y, layer, -1, -1);
        int br = getCode(this, map, x, y, layer, 1, -1);
        
        r.drawTileSprite(
                tiles.get(UP_LEFT, tl), 
                tiles.get(UP_RIGHT, tr),
                tiles.get(DOWN_LEFT, bl),
                tiles.get(DOWN_RIGHT, br),
                x, y);
    }
    
    

}
