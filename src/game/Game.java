package game;

import abacus.GameEngine;
import abacus.GameStateManager;
import abacus.awt.ImageFactory;
import abacus.ui.Window;

/*
 * Main class that starts the game
 */
public class Game {

    public static final int TILE_SIZE = 16;
    
    // game state IDs
    public static final int ID_INTRO = 0;
    public static final int ID_TITLE = 1;
    public static final int ID_PLAY = 2;
    
    // main method
    public static void main(String[] args) {
        // enable hardware acceleration
        System.setProperty("sun.java2d.opengl", "False");
        System.out.println("Hardware Acceleration: " + System.getProperty("sun.java2d.opengl"));
        ImageFactory.volatileImages = false;
        
//        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
//        GraphicsDevice gd = ge.getDefaultScreenDevice();
//        GraphicsConfiguration gc = gd.getDefaultConfiguration();
//        BufferCapabilities bc = gc.getBufferCapabilities();
        
        // set up engine
        GameEngine engine = GameEngine.create(GameEngine.Type.JAVA2D_FRAMEBUFFER);
        engine.setUpdateGoal(60);
        engine.setRenderGoal(60); 
        
        // register game states
        GameStateManager gsm = engine.getGameStateManager();
        gsm.registerState(ID_INTRO, new FadeState("res/intro.txt", ID_TITLE));
        gsm.registerState(ID_TITLE, new TitleState());
        gsm.registerState(ID_PLAY, new TileMapState());
        
        // start engine
        Window window = engine.getWindow();
        window.setResolution(480 * 2, 270 * 2);
        window.setFullscreen(false);
        window.setVirtualResolution(480 * 2, 270 * 2);
        window.show();
        engine.start(ID_INTRO);
    }
    
}
