package abacus.tile;

import static abacus.tile.ConnectionTiles.*;

import abacus.graphics.SpriteSheet;
import abacus.graphics.WorldRenderer;

public class WallTile extends Tile {

    private ConnectionTiles wall, top;
    
    public WallTile(SpriteSheet terrain, int xwall, int ywall, int xtop, int ytop) {
        wall = new ConnectionTiles(terrain, xwall, ywall);
        top = new ConnectionTiles(terrain, xtop, ytop);
    }

    @Override
    public void render(WorldRenderer r, TileMap map, int x, int y, int layer) {
        int tl = getCode(this, map, x, y, layer, -1, 1);
        int tr = getCode(this, map, x, y, layer, 1, 1);
        int bl = getCode(this, map, x, y, layer, -1, -1);
        int br = getCode(this, map, x, y, layer, 1, -1);
        
        r.drawTileSprite(
                wall.get(UP_LEFT, tl), 
                wall.get(UP_RIGHT, tr),
                wall.get(DOWN_LEFT, bl),
                wall.get(DOWN_RIGHT, br),
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
                top.get(UP_LEFT, tl), 
                top.get(UP_RIGHT, tr),
                top.get(DOWN_LEFT, bl),
                top.get(DOWN_RIGHT, br),
                map.getTileSize(), 
                x, y + 1);//y + 2);

    }

}
