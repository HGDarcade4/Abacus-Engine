package abacus.tile;

import abacus.graphics.WorldRenderer;

public abstract class Tile {

    public abstract void update();
    
    public abstract void render(WorldRenderer r, TileMap map, int x, int y, int layer);
    
}
