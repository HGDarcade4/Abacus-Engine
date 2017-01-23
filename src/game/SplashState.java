package game;

import abacus.GameState;
import abacus.ResourceLoader;
import abacus.graphics.Renderer;
import abacus.graphics.Sprite;
import abacus.ui.Input;

/*
 * Shows a splash screen
 */
public class SplashState extends GameState {

    // variables
    private Sprite splash;
    private FadeTimer fade;
    private int nextId;
    
    // need a game state id to go to when splash is done
    public SplashState(int nextId) {
        this.nextId = nextId;
    }
    
    // load splash screen
    @Override
    public void init(ResourceLoader loader) {
        fade = new FadeTimer(120, 120, 120, 120, 120);
        
        splash = loader.loadTexture("res/splash.png").getSprite();
    }

    // reset the fade timer
    @Override
    public void enter() {
        fade.reset();
    }

    // check if fade is done
    @Override
    public void update(Input input) {
        fade.update();
        
        if (fade.isDone()) {
            swapState(nextId);
        }
    }

    // draw the splash screen
    @Override
    public void render(Renderer renderer) {
        renderer.clearScreen(0, 0, 0);
        
        splash.setAlpha(fade.getAlpha());
        splash.draw(0, 0, renderer.getWidth(), renderer.getHeight());
    }

    @Override
    public void pause() {}

    @Override
    public void exit() {}

    @Override
    public void end() {}

}
