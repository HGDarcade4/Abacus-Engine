package abacus.gameobject;

import abacus.graphics.WorldRenderer;
import abacus.ui.Input;

public abstract class ActorComponent {

    protected Actor actor;
    
    protected final void setActor(Actor actor) {
        this.actor = actor;
    }
    
    public void update(World world, Input input) {}
    
    public void render(WorldRenderer renderer) {}
    
    public void onCollide(Actor other) {}
    
}
