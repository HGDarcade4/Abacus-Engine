package abacus.editorold;

public class EditorTile {

    public enum Collision {
        SOLID, OPEN, NONE;
    }
    
    public Collision solid;
    public int tileId;
    public int tileMeta;
    
    public EditorTile() 
    {
        solid = Collision.NONE;
        tileId = 0;
        tileMeta = 0;
    }
    
}
