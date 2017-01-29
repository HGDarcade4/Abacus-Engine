package abacus.gameobject;

import abacus.graphics.WorldRenderer;
import abacus.ui.Input;

public abstract class GameComponent {

    protected GameObject gameObject = null;
    
    public void attach() {}
    
    public void update(Scene scene, Input input) {}
    
    public void render(WorldRenderer wr) {}
    
    public void detach() {}
    
    public void collide(GameObject other) {}
    
}
