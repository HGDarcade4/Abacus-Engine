/* Input.java
 * Class that handles all of the input for the game
 */
package abacus.ui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/* Input class for handling input for the game
 */
public class Input {

	/* Instance Variables */
    private Set<Integer> justDown;
    private Set<Integer> downKeys;
    private Map<String, Integer> defKeys;
    
	// Constructor for the class
    public Input() {
        downKeys = new HashSet<>();
        justDown = new HashSet<>();
        defKeys = new HashMap<>();
    }
    
	// Removes all keys from the just down set
    public void update() {
        justDown.clear();
    }
    
	// Registers a key for input
    public void registerKey(int key, String name) {
        defKeys.put(name, key);
    }
    
	// Sets a key in the position it is
    public void setKey(int key, boolean down) {
        if (down) {
            if (!getKey(key)) {
                justDown.add(key);
            }
            downKeys.add(key);
        }
        else {
            downKeys.remove(key);
        }
    }
    
	// Returns the most recently pressed down key
    public boolean getJustDownKey(int key) {
        return justDown.contains(key);
    }
    
	// Returns the most recent held down key
    public boolean getKey(int key) {
        return downKeys.contains(key);
    }
    
	// Returns the value of the given key
    public boolean getKey(String key) {
        Integer val = defKeys.get(key);
        if (val == null) {
            return false;
        }
        else {
            return getKey(val);
        }
    }
    
	// Returns the value of the given recently pressed down key
    public boolean getJustDownKey(String key) {
        Integer val = defKeys.get(key);
        if (val == null) {
            return false;
        }
        else {
            return getJustDownKey(val);
        }
    }
    
	// Returns true if any key is down. False otherwise
    public boolean anyKeyJustDown() {
        return !justDown.isEmpty();
    }
}
