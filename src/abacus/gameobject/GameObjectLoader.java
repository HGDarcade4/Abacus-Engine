package abacus.gameobject;

import java.util.HashMap;
import java.util.Map;

import abacus.file.Tokenizer;

public class GameObjectLoader {

    private Map<String, GameComponent> compLoaders;
    
    public GameObjectLoader() {
        compLoaders = new HashMap<>();
    }
    
    public void loadArchetypes(String filename) {
        Tokenizer token = new Tokenizer(filename);
        
        while (token.peek().equals("object")) {
            loadGameObject(token);
        }
    }
    
    public void registerComponentType(String name, GameComponent comp) {
        compLoaders.put(name, comp);
    }
    
    private void loadGameObject(Tokenizer token) {
        GameObject go = null;
        String name = null;
        
        if (!expect(token, "object")) {
            return;
        }
        
        name = token.next();
        go = new GameObject();
        
        while (token.peek().equals("attach")) {
            loadComponents(token, go);
        }
        
        if (go != null) {
            GameObject.registerArchetype(name, go);
        }
    }
    
    private void loadComponents(Tokenizer token, GameObject go) {
        if (!expect(token, "attach")) {
            return;
        }
        
        String compName = token.next();
        GameComponent loader = compLoaders.get(compName);
        if (loader == null) {
            System.out.println("Error loading GameObject: Unknown GameComponentLoader: " + compName);
            return;
        }
        
        GameComponentProperties props = new GameComponentProperties();
        while (token.peek().equals("set")) {
            token.next();
            String word = token.next();
            switch (word) {
            case "number":
                word = token.next();
                props.setNumber(word, Double.parseDouble(token.next()));
                break;
            case "bool":
                word = token.next();
                props.setBoolean(word, Boolean.parseBoolean(token.next()));
                break;
            }
        }
        
        GameComponent comp = loader.load(props);
        go.attach(comp);
    }
    
    private boolean expect(Tokenizer token, String word) {
        String str;
        if (!(str = token.next()).equals(word)) {
            System.out.println("Error loading GameObject: Unexpected token: " + str + " instead of " + word);
        }
        return true;
    }
    
}
