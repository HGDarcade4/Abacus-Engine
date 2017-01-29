package abacus.gameobject;

import java.util.ArrayList;
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
    
    public Scene(TileMap map) {
        this.map = map;
        physics = new TilePhysics(map);
        
        gameObjects = new ArrayList<>();
    }
    
    public void addGameObject(GameObject go) {
        gameObjects.add(go);
    }
    
    public TileMap getTileMap() { 
        return map;
    }
    
    public void update(Input input) {
        map.update();
        
        for (GameObject go : gameObjects) {
            go.update(this, input);
            physics.update(go.getBody());
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
