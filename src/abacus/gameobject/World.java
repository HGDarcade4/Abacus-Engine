package abacus.gameobject;

import abacus.tile.TileMap;

public class World {

    private TileMap map;
    
    public World(TileMap map) {
        this.map = map;
    }
    
    public TileMap getTileMap() { 
        return map;
    }
    
}
