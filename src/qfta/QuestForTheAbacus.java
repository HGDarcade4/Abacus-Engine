package qfta;

import abacus.GameEngine;
import abacus.GameStateManager;
import abacus.awt.ImageFactory;
import abacus.ui.Window;

/*
 * Main class that starts the game
 */
public class QuestForTheAbacus {

    public static final int TILE_SIZE = 16;
    
    // game state IDs
    public static final int ID_TITLE = 0;
    public static final int ID_INTRO = 1;
    public static final int ID_PLAY = 2;
    public static final int ID_SPLASH = 3;
    public static final int ID_BATTLE = 4;
    public static final int ID_DEATH = 5;
    public static final int ID_DIALOGUE = 6;
    
    // main method
    public static void main(String[] args) {
        // enable hardware acceleration
        System.setProperty("sun.java2d.opengl", "True");
        System.out.println("Hardware Acceleration: " + System.getProperty("sun.java2d.opengl"));
        ImageFactory.volatileImages = true;
        
        // set up engine
        GameEngine engine = GameEngine.create(GameEngine.Type.JAVA2D_FRAMEBUFFER);
        engine.setUpdateGoal(60);
        engine.setRenderGoal(60); 
        
        // register game states
        GameStateManager gsm = engine.getGameStateManager();
        TileMapState tms = new TileMapState();
        gsm.registerState(ID_TITLE, new TitleState(ID_INTRO));
        gsm.registerState(ID_INTRO, new FadeState("res/intro.txt", ID_PLAY));
        gsm.registerState(ID_PLAY, tms);
        gsm.registerState(ID_SPLASH, new SplashState(ID_TITLE));
        gsm.registerState(ID_BATTLE, new BattleState());
        gsm.registerState(ID_DEATH, new DeathState());
        gsm.registerState(ID_DIALOGUE, new DialogueState(tms));
        
        // start engine
        Window window = engine.getWindow();
        window.setTitle("Grand Kingdom: Quest for the Abacus");
        window.setResolution(480 * 2, 270 * 2);
        window.setFullscreen(false);
        window.setVirtualResolution(480 * 2, 270 * 2);
        window.show();
        engine.start(ID_SPLASH);
    }
    
}
