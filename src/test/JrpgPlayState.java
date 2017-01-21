package test;

import abacus.GameState;
import abacus.ResourceLoader;
import abacus.graphics.BasicFont;
import abacus.graphics.DebugRenderer;
import abacus.graphics.GameFont;
import abacus.graphics.Renderer;
import abacus.graphics.SpriteSheet;
import abacus.graphics.WorldRenderer;
import abacus.sound.Sound;
import abacus.tile.TileMap;
import abacus.ui.Input;

public class JrpgPlayState extends GameState {

    public static final int ID = 0;
    
    private TileMap map;
    private GameFont font;
    private Actor player;
    
    private Sound soundEffect;
    private Sound music;
    
    @Override
    public void init(ResourceLoader loader) {
        // load font for debug text
        font = new BasicFont(new SpriteSheet(loader.loadTexture("res/font_debug.png"), 10, 12));
        font.setSize(6f);
        
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
        soundEffect.playAndLoop();
        music.playAndLoop();
    }

    @Override
    public void update(Input input) {
        // update game logic
        map.update();
        player.update(map, input);
    }

    @Override
    public void render(Renderer renderer) {
        // clear the screen to a light blue
        renderer.clearScreen(0xCC, 0xEE, 0xFF);
        
        // create a world renderer
        WorldRenderer wr = new WorldRenderer(renderer);
        wr.setTileSize(32);
        // center camera at the player
        wr.setView(player.getX(), player.getY());
        wr.setCharOffset(0.25f);
        
        // draw the map
        map.render(wr);
        
        // player draw layer
        wr.setLayer(2.5f);
        player.render(wr);
        
        // render debug information
        debugText();
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
    
    private void debugText() {
        DebugRenderer dr = new DebugRenderer(engine.getRenderer(), font);
        
        // get various debug information
        String fps = String.format("%1.2f", engine.getFps());
        String ups = String.format("%1.2f", engine.getUps());
        int drawCommands = engine.getRenderer().drawCommands();
        
        // draw the information to the screen
        font.setSize(12f);
        dr.debugLine("(" + (int)Math.round(player.getX()) + ", " + (int)Math.round(player.getY()) + ")");
        dr.debugLine("draw commands: " + drawCommands);
        dr.debugLine("updates per second: " + fps);
        dr.debugLine("frames per second: " + ups);
    }

}
