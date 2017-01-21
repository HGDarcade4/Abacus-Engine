package abacus.ui;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Input {

    private Set<Integer> justDown;
    private Set<Integer> downKeys;
    private Map<String, Integer> defKeys;
    
    public Input() {
        downKeys = new HashSet<>();
        justDown = new HashSet<>();
        defKeys = new HashMap<>();
    }
    
    public void update() {
        justDown.clear();
    }
    
    public void registerKey(int key, String name) {
        defKeys.put(name, key);
    }
    
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
    
    public boolean getJustDownKey(int key) {
        return justDown.contains(key);
    }
    
    public boolean getKey(int key) {
        return downKeys.contains(key);
    }
    
    public boolean getKey(String key) {
        Integer val = defKeys.get(key);
        if (val == null) {
            return false;
        }
        else {
            return getKey(val);
        }
    }
    
    public boolean getJustDownKey(String key) {
        Integer val = defKeys.get(key);
        if (val == null) {
            return false;
        }
        else {
            return getJustDownKey(val);
        }
    }
    
    public boolean anyKeyJustDown() {
        return !justDown.isEmpty();
    }
    
}
