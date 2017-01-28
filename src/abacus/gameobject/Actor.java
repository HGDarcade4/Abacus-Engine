package abacus.gameobject;

import abacus.tile.TileBody;

public class Actor {
    
    private TileBody body;
    
    public Actor(float x, float y, float w, float h) {
        body = new TileBody(x, y, w, h);
    }
    
    public TileBody getBody() {
        return body;
    }
    
}
