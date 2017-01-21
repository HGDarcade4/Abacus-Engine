/* GameState.java
 * This file handles the state of the game.
 * For example, there would be a state for the main menu,
 * pause menu, battles, and general traversal of the world.
 */

package abacus;

import abacus.graphics.Renderer;
import abacus.ui.Input;

/* GameState abstract class for creating different states for
 * within the game
 */
public abstract class GameState {

	/* Instance Variables */
    protected GameEngine engine;	

	// Initializes the state when it is registered
    public abstract void init(ResourceLoader loader);
    
	// Used when entering the game state
    public abstract void enter();
    
	// Used when updating the game state
    public abstract void update(Input input);
    
	// Used when rendering the game state
    public abstract void render(Renderer renderer);
    
	// Used when another state is pushed on top of it
    public abstract void pause();
    
	// Used when exiting the game state
    public abstract void exit();
    
	// Used when the game exits
    public abstract void end();
   
	// Sets the game engine property of the state
    public final void setGameEngine(GameEngine engine) {
        this.engine = engine;
    }
    
	// Pushes a new state onto the state manager
    public final void pushState(int id) {
        engine.getGameStateManager().pushState(id);
    }
    
	// Swaps the current state of the game with the one passed
    public final void swapState(int id) {
        engine.getGameStateManager().swapState(id);
    }
    
	// Pops the current state so the previous one can be returned to
    public final void popState() {
        engine.getGameStateManager().popState();
    }
}
