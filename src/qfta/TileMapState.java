package qfta;

import abacus.GameState;
import abacus.ResourceLoader;
import abacus.gameobject.Collider;
import abacus.gameobject.GameObject;
import abacus.gameobject.Scene;
import abacus.gameobject.SceneLoader;
import abacus.graphics.Renderer;
import abacus.graphics.WorldRenderer;
import abacus.sound.Sound;
import abacus.tile.TileMap;
import abacus.ui.Input;
import qfta.component.HumanoidRenderer;
import qfta.component.InputController;
import qfta.component.CharacterMovement;
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
    private Sound soundEffect;
    private Sound music;
    
    // initialize a bunch of stuff
    @Override
    public void init(ResourceLoader loader) {
        // create a world renderer
        worldRender = new WorldRenderer(engine.getRenderer());
        worldRender.setScale(4);
        
        // create tile map, normally you would just load a file instead
//        RandomTileMapGenerator mapGen = new RandomTileMapGenerator(loader, QuestForTheAbacus.TILE_SIZE);
//        map = mapGen.create(128, 128);
        
        scene = SceneLoader.read("res/sample.scene", loader, QuestForTheAbacus.TILE_SIZE);//new Scene(map);
        
        map = scene.getTileMap();
        
        player = new GameObject();
        player.attach(new Collider(10f, 5f));
        player.attach(new CharacterMovement(0.5f));
        player.get(CharacterMovement.class).randomDir = true;
        player.attach(new HumanoidRenderer(loader));
        player.get(HumanoidRenderer.class).randomColor = true;
        player.attach(new SimpleAI());
        GameObject.registerArchetype("villager", player);
        
        player = new GameObject();
        player.attach(new Collider(10f, 5f));
        player.attach(new CharacterMovement(1f));
        player.attach(new HumanoidRenderer(loader));
        player.attach(new InputController());
        GameObject.registerArchetype("player", player);
        
        for (int i = 0; i < 1000; i++) {
            int x = -1, y = -1;
            while (map.getCollision(x, y)) {
                x = (int)(Math.random() * map.getWidth());
                y = (int)(Math.random() * map.getHeight());
            }
            
            float xpos = (float)(x + Math.random()) * 16;
            float ypos = (float)(y + Math.random()) * 16;
            
//            scene.spawnArchetype("villager", xpos, ypos);
        }
        
        player = scene.spawnArchetype("player", 5 * 16, 5 * 16);
        
        // load sounds
        soundEffect = loader.loadSound("res/sound_effect.wav");
        music = loader.loadSound("res/town idea 2.1.wav");
    }

    @Override
    public void enter() {
        // start theme associated with map when the game state is entered
        // we just don't have a theme for this yet
        music.playAndLoop();
    }

    // update logic
    @Override
    public void update(Input input) {
        // update game logic
        map.update();
        scene.update(input);
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
        worldRender.setLayer(1);
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
        soundEffect.stop();
        music.stop();
    }

    @Override
    public void end() {}
    
}
