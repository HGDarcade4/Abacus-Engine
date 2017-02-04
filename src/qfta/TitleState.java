package qfta;

import abacus.GameState;
import abacus.ResourceLoader;
import abacus.graphics.GameFont;
import abacus.graphics.Renderer;
import abacus.graphics.Sprite;
import abacus.sound.Sound;
import abacus.ui.Input;

/*
 * Screen before you reach the main menu
 */
public class TitleState extends GameState {

    // misc variables
    private GameFont font;
    private Sprite title;
    private FadeTimer fade;
    private Sound music;
    private Sound click;
    private FadePressKey pressAnyKey;
    private int nextId;
    
    public TitleState(int nextStateId) {
    	this.nextId = nextStateId;
    }
    
    // load resources
    @Override
    public void init(ResourceLoader loader) {
        font = loader.getFontCreator().createBasicFont("res/font.png", 10, 12, 0xFFFFFF);

        title = loader.loadTexture("res/title.png").getSprite();
        
        fade = new FadeTimer(60, 6 * 60, Integer.MAX_VALUE, 0, 0);
        pressAnyKey = new FadePressKey("Press any key...");
        
        music = loader.loadSound("res/song_idea1.wav");
        click = loader.loadSound("res/button_select.wav");
    }

    // play music and reset fading text
    @Override
    public void enter() {
        music.playAndLoop();
        fade.reset();
        pressAnyKey.resetFadeTimer();
    }

    // update text fade
    @Override
    public void update(Input input) {
        fade.update();
        if (fade.getAlpha() == 1f) {
            pressAnyKey.updateFadeTimer();
        }
        
        if (pressAnyKey.isDoneFadeTimer()) {
            pressAnyKey.resetFadeTimer();
        }
        
        if (input.anyKeyJustDown()) {
            swapState(this.nextId);
        }
    }

    // draw background and text
    @Override
    public void render(Renderer renderer) {
        renderer.clearScreen(0, 0, 0);
        
        title.setAlpha(fade.getAlpha());
        title.draw(0, 0, renderer.getWidth(), renderer.getHeight());
        
        pressAnyKey.render(renderer, font);
    }

    @Override
    public void pause() {}

    // stop music before leaving
    @Override
    public void exit() {
        music.stop();
        click.play();
    }

    @Override
    public void end() {}

}
