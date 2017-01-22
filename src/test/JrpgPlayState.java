package test;

import abacus.GameState;
import abacus.ResourceLoader;
import abacus.graphics.DebugRenderer;
import abacus.graphics.GameFont;
import abacus.graphics.Renderer;
import abacus.graphics.WorldRenderer;
import abacus.sound.Sound;
import abacus.tile.TileMap;
import abacus.ui.Input;

public class JrpgPlayState extends GameState {

    public static final int ID = 0;
    
    private WorldRenderer worldRender;
    
    private TileMap map;
    private Actor player;
    
    private Sound soundEffect;
    private Sound music;
    
    @Override
    public void init(ResourceLoader loader) {
        // create a world renderer
        worldRender = new WorldRenderer(engine.getRenderer());
        worldRender.setTileSize(32);
        worldRender.setCharOffset(0.25f);
        
        // create tile map, normally you would just load a file instead
        RandomTileMapGenerator mapGen = new RandomTileMapGenerator(loader);
        map = mapGen.create(128, 128);
        
        // create a temporary player
        Actor.loadAnimations(loader);
        player = new Actor();
        
        // load sounds
        soundEffect = loader.loadSound("res/sound_effect.wav");
        music = loader.loadSound("res/song_idea1.wav");
    }

    @Override
    public void enter() {
        // start sounds when the game state is entered
//        soundEffect.playAndLoop();
//        music.playAndLoop();
    }

    @Override
    public void update(Input input) {
        // update game logic
        map.update();
        player.update(map, input);
    }

    @Override
    public void render(Renderer renderer) {
        worldRender.reset();
        
        // clear the screen to a light blue
        renderer.clearScreen(0xCC, 0xEE, 0xFF);
        
        // center camera at the player
        worldRender.setView(player.getX(), player.getY());
        
        // draw the map
        map.render(worldRender);
        
        // player draw layer
        worldRender.setLayer(2.5f);
        player.render(worldRender);
        
        engine.debugLine("");
        engine.debugLine("(" + player.getX() + ", " + player.getY() + ")");
    }

    @Override
    public void pause() {

    }

    @Override
    public void exit() {
        // stop music when exiting the game state
        soundEffect.stop();
        music.stop();
    }

    @Override
    public void end() {

    }
    
}
