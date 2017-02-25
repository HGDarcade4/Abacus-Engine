package abacus.tile;

import static abacus.tile.ConnectionTiles.*;

import abacus.graphics.SpriteSheet;
import abacus.graphics.WorldRenderer;

public class WallTile extends Tile {

    private ConnectionTiles wall[], top[];
    
    public WallTile(SpriteSheet sheet, int delay, int frames) {
        int w = sheet.tilesWide() / (8 * frames);
        int h = sheet.tilesHigh() / 6;
        
        wall = new ConnectionTiles[w * h];//(terrain, xwall, ywall);
        top = new ConnectionTiles[w * h];//(terrain, xtop, ytop);
        
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                wall[x + y * w] = new ConnectionTiles();
                top[x + y * w] = new ConnectionTiles();
                for (int i = 0; i < frames; i++) {
                    wall[x + y * w].addFrame(sheet, (x * 8) * frames + i * 8 + 4, y * 6, delay);
                    top[x + y * w].addFrame(sheet, (x * 8) * frames + i * 8, y * 6, delay);
                }
            }
        }
    }

    @Override
    public void render(WorldRenderer r, TileMap map, int x, int y, int layer) {
        int tl = getCode(this, map, x, y, layer, -1, 1);
        int tr = getCode(this, map, x, y, layer, 1, 1);
        int bl = getCode(this, map, x, y, layer, -1, -1);
        int br = getCode(this, map, x, y, layer, 1, -1);
        
        int tileId = map.getTileMetaData(x, y, layer);
        
        r.drawTileSprite(
                wall[tileId].get(UP_LEFT, tl), 
                wall[tileId].get(UP_RIGHT, tr),
                wall[tileId].get(DOWN_LEFT, bl),
                wall[tileId].get(DOWN_RIGHT, br),
                map.getTileSize(),
                x, y);
        
        // used for 3 high wall instead of 2
//        r.setLayer(layer);
//        r.drawTileSprite(
//                wall.get(UP_LEFT, tl), 
//                wall.get(UP_RIGHT, tr),
//                wall.get(DOWN_LEFT, bl & EDGE_HORIZ),
//                wall.get(DOWN_RIGHT, br & EDGE_HORIZ),
//                map.getTileSize(), 
//                x, y + 1);
        
        r.drawTileSprite(
                top[tileId].get(UP_LEFT, tl), 
                top[tileId].get(UP_RIGHT, tr),
                top[tileId].get(DOWN_LEFT, bl),
                top[tileId].get(DOWN_RIGHT, br),
                map.getTileSize(), 
                x, y + 1);//y + 2);

    }

}
