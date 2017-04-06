package abacus.editor;

public class Tile {

    public enum Collision {
        SOLID, OPEN, NONE;
    }
    
    public Collision solid;
    public int tileId;
    public int tileMeta;
    
    public Tile() 
    {
        solid = Collision.NONE;
        tileId = 0;
        tileMeta = 0;
    }
    
}
