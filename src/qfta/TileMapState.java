package qfta;

import abacus.GameState;
import abacus.ResourceLoader;
import abacus.gameobject.Collider;
import abacus.gameobject.GameObject;
import abacus.gameobject.Scene;
import abacus.graphics.Renderer;
import abacus.graphics.WorldRenderer;
import abacus.sound.Sound;
import abacus.tile.TileMap;
import abacus.ui.Input;
import qfta.component.HumanoidRenderer;
import qfta.component.InputController;
import qfta.component.Movement;

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
        RandomTileMapGenerator mapGen = new RandomTileMapGenerator(loader, QuestForTheAbacus.TILE_SIZE);
        map = mapGen.create(128, 128);
        
        scene = new Scene(map);
        
        // create a temporary player
        HumanoidRenderer.loadAnimations(loader);
        for (int i = 0; i < 30; i++) {
            player = new GameObject();
            
            int x = -1, y = -1;
            while (map.getCollision(x, y)) {
                x = (int)(Math.random() * 128);
                y = (int)(Math.random() * 128);
            }
            
            float xpos = (float)(x + Math.random()) * 16;
            float ypos = (float)(y + Math.random()) * 16;
            
            player.attach(new Collider(xpos, ypos, 10f, 5f));
            player.attach(new Movement(1f));
            player.get(Movement.class).dir = (int)(Math.random() * 4);
            player.attach(new HumanoidRenderer());
            scene.addGameObject(player);
        }
        
        player = new GameObject();
        player.attach(new Collider(64 * 16, 64 * 16, 10f, 5f));
        player.attach(new Movement(1f));
        player.attach(new HumanoidRenderer());
        player.attach(new InputController());
        scene.addGameObject(player);
        
        // load sounds
        soundEffect = loader.loadSound("res/sound_effect.wav");
        music = loader.loadSound("res/song_idea1.wav");
    }

    @Override
    public void enter() {
        // start theme associated with map when the game state is entered
        // we just don't have a theme for this yet
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
