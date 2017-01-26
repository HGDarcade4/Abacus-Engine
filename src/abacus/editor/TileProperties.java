package abacus.editor;

import java.util.HashMap;
import java.util.Map;

public class TileProperties {

    private Map<String, Object> text = new HashMap<>();
    
    public void set(String name, Object prop) {
        text.put(name, prop);
    }
    
    public boolean hasProperty(String name) {
        return text.containsKey(name);
    }
    
}
