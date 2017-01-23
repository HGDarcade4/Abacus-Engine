package game;

import abacus.GameEngine;
import abacus.GameStateManager;
import abacus.ui.Window;

/*
 * Main class that starts the game
 */
public class Game {

    // game state IDs
    public static final int ID_INTRO = 0;
    public static final int ID_TITLE = 1;
    public static final int ID_PLAY = 2;
    
    // main method
    public static void main(String[] args) {
        // enable hardware acceleration
        System.setProperty("sun.java2d.opengl", "True");
        System.out.println("Hardware Acceleration: " + System.getProperty("sun.java2d.opengl"));
        
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
        window.setFullscreen(true);
        window.setVirtualResolution(480, 270);
        window.show();
        engine.start(ID_INTRO);
    }
    
}
