package abacus.tile;

public class TileRegistry {

    public static final Tile NULL_TILE = new NullTile();
    
    private Tile[] tiles;
    
    public TileRegistry(int numTiles) {
        tiles = new Tile[numTiles];
        
        for (int i = 0; i < numTiles; i++) {
            tiles[i] = NULL_TILE;
        }
    }
    
    public TileRegistry() {
        this(4096);
    }
    
    public void register(int id, Tile tile) {
        tiles[id] = tile;
    }
    
    public int numTiles() {
        return tiles.length;
    }
    
    public Tile getTile(int id) {
        return tiles[id];
    }
    
}
