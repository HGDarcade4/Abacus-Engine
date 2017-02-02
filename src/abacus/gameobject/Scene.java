package abacus.gameobject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import abacus.graphics.WorldRenderer;
import abacus.tile.TileMap;
import abacus.tile.TilePhysics;
import abacus.ui.Input;

public class Scene {

    private TileMap map;
    private TilePhysics physics;
    private List<GameObject> gameObjects;
    
    private HashMap<String, GameObject> archetypes;
    
    public Scene(TileMap map) {
        this.map = map;
        physics = new TilePhysics(map);
        
        gameObjects = new ArrayList<>();
        
        archetypes = new HashMap<>();
    }
    
    public void addGameObject(GameObject go) {
        gameObjects.add(go);
    }
    
    public TileMap getTileMap() { 
        return map;
    }
    
    public TilePhysics getTilePhysics() {
        return physics;
    }
    
    public void registerArchetype(String name, GameObject go) {
        archetypes.put(name, go);
    }
    
    public GameObject spawnArchetype(String name, float x, float y) {
        GameObject arch = archetypes.get(name);
        
        if (arch != null) {
            GameObject spawned = arch.copy(x, y);
            addGameObject(spawned);
            return spawned;
        }
        else {
            return null;
        }
    }
    
    public void update(Input input) {
        map.update();
        
        for (GameObject go : gameObjects) {
            go.update(this, input);
        }
        
        Iterator<GameObject> it = gameObjects.iterator();
        while (it.hasNext()) {
            if (it.next().shouldRemove()) it.remove();
        }
    }
    
    public void render(WorldRenderer renderer) {
        for (GameObject go : gameObjects) {
            go.render(renderer);
        }
        
        map.render(renderer);
    }
    
}
