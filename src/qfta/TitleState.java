package qfta;

import abacus.GameState;
import abacus.ResourceLoader;
import abacus.graphics.GameFont;
import abacus.graphics.Renderer;
import abacus.graphics.Sprite;
import abacus.sound.Sound;
import abacus.ui.Input;
import abacus.ui.Menu;

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
    private boolean displayMainMenu;
    private Menu mainMenu;
    private int newGame, loadGame, settings, quit;
    
    public TitleState(int nextStateId) {
    	this.nextId = nextStateId;
        fade = new FadeTimer(60, 6 * 60, Integer.MAX_VALUE, 0, 0);
        pressAnyKey = new FadePressKey("Press any key...");
        
        mainMenu = new Menu();
        newGame = mainMenu.addOption("New Game");
        loadGame = mainMenu.addOption("Load Game");
        settings = mainMenu.addOption("Settings");
        quit = mainMenu.addOption("Quit");
        
        displayMainMenu = false;
    }
    
    // load resources
    @Override
    public void init(ResourceLoader loader) {
        font = loader.getFontCreator().createBasicFont("res/font.png", 10, 12, 0xFFFFFF);

        title = loader.loadTexture("res/title.png").getSprite();
        
        
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
        
        if (displayMainMenu) {
        	mainMenu.updateFadeTimer();
        	
        	if (mainMenu.isDoneFadeTimer()) {
        		mainMenu.resetFadeTimer();
        	}
        	
        	if (input.getJustDownKey("up_arrow")) {
        		mainMenu.moveSelectionUp();
        	}
        	if (input.getJustDownKey("down_arrow")) {
        		mainMenu.moveSelectionDown();
        	}
        	if (input.getJustDownKey("spacebar")) {
        		int selection = mainMenu.getCurrentSelection();
        		
        		if (selection == this.newGame) {
        			swapState(nextId);
        		}
        		if (selection == this.quit) {
        			System.exit(0);
        		}
        	}
        }
        else {
        	if (fade.getAlpha() == 1f) {
        		pressAnyKey.updateFadeTimer();

        		if (pressAnyKey.isDoneFadeTimer()) {
        			pressAnyKey.resetFadeTimer();
        		}

        		if (input.anyKeyJustDown()) {
        			displayMainMenu = true;
        		}
        	}
        
        }
        
    }

    // draw background and text
    @Override
    public void render(Renderer renderer) {
        renderer.clearScreen(0, 0, 0);
        
        title.setAlpha(fade.getAlpha());
        title.draw(0, 0, renderer.getWidth(), renderer.getHeight());
        
        if (displayMainMenu) {
        	mainMenu.render(renderer, font);
        }
        else {
        	pressAnyKey.render(renderer, font);
        }
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
