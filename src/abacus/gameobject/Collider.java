package abacus.gameobject;

import abacus.tile.TileBody;
import abacus.ui.Input;

public class Collider extends GameComponent {
    
    public TileBody tileBody;
    
    public Collider(float x, float y, float w, float h) {
        tileBody = new TileBody(0, 0, w, h);
        tileBody.setCenterX(x);
        tileBody.setMinY(y);
    }
    
    public Collider(float w, float h) {
        this(0f, 0f, w, h);
    }
    
    public Collider copy() {
    	return new Collider(tileBody.getCenterX(), tileBody.getMinY(), tileBody.getWidth(), tileBody.getHeight());
    }
    
    public void update(Scene scene, Input input) {
        scene.getTilePhysics().update(tileBody);
    }
    
    public void postUpdate(Scene scene, Input input) {
        Transform tfm = gameObject.getTransform();
        
        // set position to bottom center
        tfm.x = tileBody.getCenterX();
        tfm.y = tileBody.getMinY();
    }
    
}
