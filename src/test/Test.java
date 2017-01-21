package test;

import abacus.GameEngine;
import abacus.GameStateManager;
import abacus.ui.Window;

public class Test {

    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "True");
        System.out.println("Hardware Acceleration: " + System.getProperty("sun.java2d.opengl"));
        
        // set up engine
        GameEngine engine = GameEngine.create(GameEngine.Type.JAVA2D_FRAMEBUFFER);
        engine.setUpdateGoal(60);
        engine.setRenderGoal(60); 
        
        // register game states
        GameStateManager gsm = engine.getGameStateManager();
        gsm.registerState(SplashState.ID, new SplashState(FadeState.ID));
        gsm.registerState(FadeState.ID, new FadeState());
        gsm.registerState(ScrollTextState.ID, new ScrollTextState());
        gsm.registerState(JrpgPlayState.ID, new JrpgPlayState());
        
        // start engine
        Window window = engine.getWindow();
        window.setFullscreen(false);
        window.setVirtualResolution(480, 270);
        window.show();
        engine.start(SplashState.ID);
    }
    
}
