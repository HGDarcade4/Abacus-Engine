package abacus.tile;

import static abacus.tile.ConnectionTiles.DOWN_LEFT;
import static abacus.tile.ConnectionTiles.DOWN_RIGHT;
import static abacus.tile.ConnectionTiles.UP_LEFT;
import static abacus.tile.ConnectionTiles.UP_RIGHT;
import static abacus.tile.ConnectionTiles.getCode;

import abacus.graphics.SpriteSheet;
import abacus.graphics.WorldRenderer;

public class WallTile extends Tile {

    private ConnectionTiles wall, top;
    
    public WallTile(SpriteSheet terrain, int xwall, int ywall, int xtop, int ytop) {
        wall = new ConnectionTiles(terrain, xwall, ywall);
        top = new ConnectionTiles(terrain, xtop, ytop);
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
        
        r.setLayer(layer);
        r.drawTileSprite(
                wall.get(UP_LEFT, tl), 
                wall.get(UP_RIGHT, tr),
                wall.get(DOWN_LEFT, bl),
                wall.get(DOWN_RIGHT, br),
                x, y);
        
        r.setLayer(layer + 10);
        r.drawTileSprite(
                top.get(UP_LEFT, tl), 
                top.get(UP_RIGHT, tr),
                top.get(DOWN_LEFT, bl),
                top.get(DOWN_RIGHT, br),
                x, y + 1);

    }

}
