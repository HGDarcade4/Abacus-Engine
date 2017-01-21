/* GameStateManager.java
 * This class manages the current state of the game
 */

package abacus;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/* GameStateManager class for handling the current state of the game
 */
public class GameStateManager {

	/* Instance Variables */
    private Map<Integer, GameState> stateMap;
    private Stack<GameState> active;
    
	// Constructor
    public GameStateManager() {
        stateMap = new HashMap<>();
        active = new Stack<>();
    }
    
	// Puts the given state into the state map
    public void registerState(int key, GameState state) {
        stateMap.put(key, state);
    }
    
	// Pushes the given state to the active stack
    public void pushState(int key) {
        GameState state = stateMap.get(key);
        
        if (state == null) {
            return;
        }

        pause();
        active.push(state);
        enter();
    }
    
	// Removes the current state from the active stack
    public void popState() {
        if (!noActiveStates()) {
            exit();
            active.pop();
            enter();
        }
    }
    
	// Swap the currently running state with the given one
    public void swapState(int key) {
        GameState state = stateMap.get(key);
        
        if (state == null) {
            return;
        }

        if (noActiveStates()) {
            active.push(state);
            enter();
        }
        else {
            exit();
            active.pop();
            active.push(state);
            enter();
        }
    }
    
	// Returns true if there is not an active state. False otherwise
    public boolean noActiveStates() {
        return active.isEmpty();
    }
    
	// Returns the currently active state
    public GameState getCurrentState() {
        return active.peek();
    }
    
	// Returns all of the states on the active stack
    public GameState[] getActiveStates() {
        return active.toArray(new GameState[active.size()]);
    }
    
	// Returns all of the registered states in the game
    public GameState[] getRegisteredStates() {
        return stateMap.values().toArray(new GameState[stateMap.values().size()]);
    }
    
	// Exits the currently running state
    private void exit() {
        if (!noActiveStates()) {
            getCurrentState().exit();
        }
    }
    
	// Enters the currently running state
    private void enter() {
        if (!noActiveStates()) {
            getCurrentState().enter();
        }
    }
    
	// Pauses the currently running state
    private void pause() {
        if (!noActiveStates()) {
            getCurrentState().pause();
        }
    }
}
