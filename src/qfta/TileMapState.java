package qfta;

import abacus.GameState;
import abacus.ResourceLoader;
import abacus.gameobject.Collider;
import abacus.gameobject.GameObject;
import abacus.gameobject.GameObjectLoader;
import abacus.gameobject.Scene;
import abacus.gameobject.SceneLoader;
import abacus.graphics.Renderer;
import abacus.graphics.WorldRenderer;
import abacus.sound.Sound;
import abacus.tile.TileMap;
import abacus.ui.Input;
import qfta.component.CharacterMovement;
import qfta.component.HumanoidRenderer;
import qfta.component.InputController;
import qfta.component.SimpleAI;

/*
 * Main tile map play state. 
 * 
 * This is where you load a tile map and move character on it, etc. 
 */
public class TileMapState extends GameState {

    // instance of world renderer
    private WorldRenderer worldRender;
    
    // hold the map and player
    // THIS WILL PROBABLY CHANGE A LOT
    private TileMap map;
    private Scene scene;
    private GameObject player;
    
    // sounds
    private Sound music;
    
    private ResourceLoader loader;
    
    // initialize a bunch of stuff
    @Override
    public void init(ResourceLoader loader) {
        this.loader = loader;
        
        // create a world renderer
        worldRender = new WorldRenderer(engine.getRenderer());
        worldRender.setScale(4);

        GameObjectLoader gol = new GameObjectLoader();
        gol.registerComponentType("Collider", new Collider(1f, 1f));
        gol.registerComponentType("CharacterMovement", new CharacterMovement(1f));
        gol.registerComponentType("HumanoidRenderer", new HumanoidRenderer(loader));
        gol.registerComponentType("SimpleAI", new SimpleAI());
        gol.registerComponentType("InputController", new InputController());
        
        gol.loadArchetypes("res/game_object_list.gameobject");
        
        player = GameObject.spawnArchetype("player", 0 * 16, 0 * 16);
        
        // load sounds
        music = loader.loadSound("res/town idea 2.1.wav");
        
        loadScene("res/test4.scene");
    }

    @Override
    public void enter() {
    }

    // update logic
    @Override
    public void update(Input input) {
        // update game logic
        map.update();
        scene.update(input);
        
        String tp = map.getTeleport((int)(player.getTransform().x/16), (int)(player.getTransform().y/16));
        if (tp != null) {
            loadScene(tp);
        }
    }

    // render map and player
    @Override
    public void render(Renderer renderer) {
        worldRender.reset();
        worldRender.setDebug(engine.getDebug());
        
        // clear the screen to a light blue
        renderer.clearScreen(0xCC, 0xEE, 0xFF);
        
        // center camera at the player
        worldRender.setView(player.getTransform().x, player.getTransform().y);
        
        // player draw layer
        worldRender.setLayer(3);
        scene.render(worldRender);
        
        // draw the map
        map.render(worldRender);
        
        engine.debugLine("");
        engine.debugLine("(" + player.getTransform().x + ", " + player.getTransform().y + ")");
    }

    @Override
    public void pause() {}

    // stop music when exiting the game state
    @Override
    public void exit() {
        music.stop();
    }

    @Override
    public void end() {}
    
    private void loadScene(String filename) {
        music.stop();
        
        scene = SceneLoader.read(filename, loader, QuestForTheAbacus.TILE_SIZE);
        
        map = scene.getTileMap();
        
        for (int i = 0; i < 100; i++) {
            int x = -1, y = -1;
            while (map.getCollision(x, y)) {
                x = (int)(Math.random() * map.getWidth());
                y = (int)(Math.random() * map.getHeight());
            }
            
            float xpos = (float)(x + Math.random()) * 16;
            float ypos = (float)(y + Math.random()) * 16;
            
            scene.spawnArchetype("villager", xpos, ypos);
        }
        
        player.getTransform().x = scene.getStartX() * 16;
        player.getTransform().y = scene.getStartY() * 16;
        scene.addGameObject(player);
        
        if (scene.getMusicFileName() != null) {
            music = loader.loadSound(scene.getMusicFileName());
        }
        else {
            music = loader.loadSound("res/town idea 2.1.wav");
        }
        music.playAndLoop();
    }
    
}
