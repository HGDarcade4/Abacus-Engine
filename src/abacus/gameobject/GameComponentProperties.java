package abacus.gameobject;

import java.util.HashMap;
import java.util.Map;

public class GameComponentProperties {

    private Map<String, String> props;
    private Map<String, Double> numProps;
    private Map<String, Boolean> boolProps;
    
    public GameComponentProperties() {
        props = new HashMap<>();
        numProps = new HashMap<>();
        boolProps = new HashMap<>();
    }
    
    public void setString(String key, String value) {
        props.put(key, value);
    }
    
    public void setNumber(String key, double value) {
        numProps.put(key, value);
    }
    
    public void setBoolean(String key, boolean value) {
        boolProps.put(key, value);
    }
    
    public boolean containsString(String key) {
        return props.containsKey(key);
    }
    
    public boolean containsNumber(String key) {
        return numProps.containsKey(key);
    }
    
    public boolean containsBoolean(String key) {
        return boolProps.containsKey(key);
    }
    
    public String getString(String key) {
        return props.get(key);
    }
    
    public float getNumber(String key) {
        return (float)numProps.get(key).doubleValue();
    }
    
    public boolean getBoolean(String key) {
        return boolProps.get(key).booleanValue();
    }
    
}
