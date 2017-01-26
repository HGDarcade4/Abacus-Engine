package abacus.editor;

import java.util.HashMap;

public class Properties {

    private HashMap<String, String> props = new HashMap<>();
    
    public void set(String name, Object prop) {
        if (prop != null) {
            props.put(name, prop.toString());
        }
    }
    
    public String get(String name, String def) {
        String s = props.get(name);
        
        if (s == null) {
            return def;
        }
        else {
            return s;
        }
    }
    
    public int getInt(String name, int def) {
        try {
            String s = props.get(name);
            int i = Integer.parseInt(s);
            return i;
        }
        catch (Exception e) {
            return def;
        }
    }
    
}
