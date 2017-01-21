package abacus;

import abacus.graphics.Renderer;
import abacus.ui.Input;

public abstract class GameState {

    protected GameEngine engine;
    
    public abstract void init(ResourceLoader loader);
    
    public abstract void enter();
    
    public abstract void update(Input input);
    
    public abstract void render(Renderer renderer);
    
    public abstract void pause();
    
    public abstract void exit();
    
    public abstract void end();
    
    public final void setGameEngine(GameEngine e) {
        engine = e;
    }
    
    public final void pushState(int id) {
        engine.getGameStateManager().pushState(id);
    }
    
    public final void swapState(int id) {
        engine.getGameStateManager().swapState(id);
    }
    
    public final void popState() {
        engine.getGameStateManager().popState();
    }
    
}
