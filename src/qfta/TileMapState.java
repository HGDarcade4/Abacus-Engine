package qfta;

import abacus.GameState;
import abacus.ResourceLoader;
import abacus.graphics.Renderer;
import abacus.graphics.WorldRenderer;
import abacus.sound.Sound;
import abacus.tile.TileMap;
import abacus.tile.TilePhysics;
import abacus.ui.Input;

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
    private TilePhysics physics;
    private Actor player;
    
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
        
        physics = new TilePhysics(map);
        
        // create a temporary player
        Actor.loadAnimations(loader);
        player = new Actor();
        
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
        player.update(map, input);
        physics.update(player.getBody());
    }

    // render map and player
    @Override
    public void render(Renderer renderer) {
        worldRender.reset();
        worldRender.setDebug(engine.getDebug());
        
        // clear the screen to a light blue
        renderer.clearScreen(0xCC, 0xEE, 0xFF);
        
        // center camera at the player
        worldRender.setView(player.getX() + 0.5f, player.getY());
        
        // player draw layer
        worldRender.setLayer(1);
        player.render(worldRender);
        
        // draw the map
        map.render(worldRender);
        
        engine.debugLine("");
        engine.debugLine("(" + player.getX() + ", " + player.getY() + ")");
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
