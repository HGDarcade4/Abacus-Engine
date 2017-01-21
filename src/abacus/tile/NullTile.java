package abacus.tile;

import abacus.graphics.WorldRenderer;

public class NullTile extends Tile {

    @Override
    public void update() {
        // do nothing
    }
    
    @Override
    public void render(WorldRenderer r, TileMap map, int x, int y, int layer) {
        // do nothing
    }

}
