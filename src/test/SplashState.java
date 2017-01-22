package test;

import abacus.GameState;
import abacus.ResourceLoader;
import abacus.graphics.Renderer;
import abacus.graphics.Sprite;
import abacus.ui.Input;

public class SplashState extends GameState {

    public static final int ID = 3;
    
    private Sprite splash;
    private FadeTimer fade;
    private int nextId;
    
    public SplashState(int nextId) {
        this.nextId = nextId;
    }
    
    @Override
    public void init(ResourceLoader loader) {
        fade = new FadeTimer(120, 120, 120, 120, 120);
        
        splash = loader.loadTexture("res/splash.png").getSprite();
    }

    @Override
    public void enter() {
        fade.reset();
    }

    @Override
    public void update(Input input) {
        fade.update();
        
        if (fade.isDone()) {
            swapState(nextId);
        }
    }

    @Override
    public void render(Renderer renderer) {
        renderer.clearScreen(0, 0, 0);
        
        splash.setAlpha(fade.getAlpha());
        splash.draw(0, 0, renderer.getWidth(), renderer.getHeight());
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void exit() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void end() {
        // TODO Auto-generated method stub
        
    }

}
