package abacus;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class GameStateManager {

    private Map<Integer, GameState> stateMap;
    
    private Stack<GameState> active;
    
    public GameStateManager() {
        stateMap = new HashMap<>();
        active = new Stack<>();
    }
    
    public void registerState(int key, GameState state) {
        stateMap.put(key, state);
    }
    
    public void pushState(int key) {
        GameState state = stateMap.get(key);
        
        if (state == null) {
            return;
        }

        pause();
        active.push(state);
        enter();
    }
    
    public void popState() {
        if (!noActiveStates()) {
            exit();
            active.pop();
            enter();
        }
    }
    
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
    
    public boolean noActiveStates() {
        return active.isEmpty();
    }
    
    public GameState getCurrentState() {
        return active.peek();
    }
    
    public GameState[] getActiveStates() {
        return active.toArray(new GameState[active.size()]);
    }
    
    public GameState[] getRegisteredStates() {
        return stateMap.values().toArray(new GameState[stateMap.values().size()]);
    }
    
    private void exit() {
        if (!noActiveStates()) {
            getCurrentState().exit();
        }
    }
    
    private void enter() {
        if (!noActiveStates()) {
            getCurrentState().enter();
        }
    }
    
    private void pause() {
        if (!noActiveStates()) {
            getCurrentState().pause();
        }
    }
    
}
