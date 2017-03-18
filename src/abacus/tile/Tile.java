package abacus.tile;

import abacus.graphics.WorldRenderer;

/*
 * Tile class
 */
public abstract class Tile {
	
    public abstract void render(WorldRenderer r, TileMap map, int x, int y, int layer);
    
}
