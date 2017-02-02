package abacus.gameobject;

import abacus.graphics.WorldRenderer;
import abacus.tile.TileBody;
import abacus.ui.Input;

public class Collider extends GameComponent {
    
    public TileBody tileBody;
    
    private Transform transform;
    
    public Collider(float w, float h) {
        tileBody = new TileBody(0, 0, w, h);
    }
    
    public Collider copy() {
    	return new Collider(tileBody.getWidth(), tileBody.getHeight());
    }
    
    public void attach() {
        transform = gameObject.getTransform();
    }
    
    public void update(Scene scene, Input input) {
        tileBody.setCenterX(transform.x);
        tileBody.setMinY(transform.y);
        
        scene.getTilePhysics().update(tileBody);
    }
    
    public void postUpdate(Scene scene, Input input) {
        Transform tfm = gameObject.getTransform();
        
        // set position to bottom center
        tfm.x = tileBody.getCenterX();
        tfm.y = tileBody.getMinY();
    }
    
    public void render(WorldRenderer r) {
        r.drawDebugRect(0xFF0000, tileBody.getMinX(), tileBody.getMinY(), tileBody.getWidth(), tileBody.getHeight());
    }
    
}
