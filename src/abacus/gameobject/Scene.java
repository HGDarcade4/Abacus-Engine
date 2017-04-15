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
    private String music;
    private int startX, startY;
    private String file;
    
    public Scene(TileMap map, String file) {
        this.map = map;
        physics = new TilePhysics(map);
        
        this.file = file;
        
        gameObjects = new ArrayList<>();
    }
    
    public String getFileName() {
        return file;
    }
    
    public void setMusicFileName(String filename) {
        music = filename;
    }
    
    public String getMusicFileName() {
        return music;
    }
    
    public void addGameObject(GameObject go) {
        if (go != null) gameObjects.add(go);
    }
    
    public TileMap getTileMap() { 
        return map;
    }
    
    public TilePhysics getTilePhysics() {
        return physics;
    }
    
    public GameObject spawnArchetype(String name, float x, float y) {
        GameObject spawned = GameObject.spawnArchetype(name, x, y);
        if (spawned == null) {
            return null;
        }
        
        addGameObject(spawned);
        return spawned;
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

    public void setTileMap(TileMap map) {
        this.map = map;
        physics = new TilePhysics(map);
    }

    public void setStartPos(int x, int y) {
        startX = x;
        startY = y;
    }
    
    public int getStartX() {
        return startX;
    }
    
    public int getStartY() {
        return startY;
    }
    
}
