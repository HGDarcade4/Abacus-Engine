package abacus.tile;

import static abacus.tile.ConnectionTiles.DOWN_LEFT;
import static abacus.tile.ConnectionTiles.DOWN_RIGHT;
import static abacus.tile.ConnectionTiles.UP_LEFT;
import static abacus.tile.ConnectionTiles.UP_RIGHT;
import static abacus.tile.ConnectionTiles.getCode;

import abacus.graphics.SpriteSheet;
import abacus.graphics.WorldRenderer;

public class ConnectedTile extends Tile {

    private ConnectionTiles[] tiles;
    
//    public ConnectedTile(SpriteSheet tileset, int x, int y) {
//        this(tileset, 1, x, y);
//    }
    
    public ConnectedTile(SpriteSheet sheet, int delay, int frames) {
        int w = sheet.tilesWide() / (4 * frames);
        int h = sheet.tilesHigh() / 6;
        
        tiles = new ConnectionTiles[w * h];
        
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                tiles[x + y * w] = new ConnectionTiles();
                for (int i = 0; i < frames; i++) {
                    tiles[x + y * w].addFrame(sheet, (x * 4) * frames + i * 4, y * 6, delay);
                }
            }
        }
        
//        for (int i = 0; i < numFrames; i++) {
//            tiles.addFrame(sheet, coords[i * 2], coords[i * 2 + 1], delay);
//        }
    }
    
    @Override
    public void render(WorldRenderer r, TileMap map, int x, int y, int layer) {
        int tl = getCode(this, map, x, y, layer, -1, 1);
        int tr = getCode(this, map, x, y, layer, 1, 1);
        int bl = getCode(this, map, x, y, layer, -1, -1);
        int br = getCode(this, map, x, y, layer, 1, -1);
        
        int tileId = map.getTileMetaData(x, y, layer);
        
        r.drawTileSprite(
                tiles[tileId].get(UP_LEFT, tl), 
                tiles[tileId].get(UP_RIGHT, tr),
                tiles[tileId].get(DOWN_LEFT, bl),
                tiles[tileId].get(DOWN_RIGHT, br),
                map.getTileSize(),
                x, y);
    }
    
    

}
