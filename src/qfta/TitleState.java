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
    private boolean displayQuitMenu;
    private Menu mainMenu;
    private Menu quitMenu;
    private int newGame, quit, quitYes, quitNo;
    
    public TitleState(int nextStateId) {
    	this.nextId = nextStateId;
        fade = new FadeTimer(60, 6 * 60, Integer.MAX_VALUE, 0, 0);
        pressAnyKey = new FadePressKey("Press any key...");
        
        // create the main menu
        mainMenu = new Menu(115, 30);
        newGame = mainMenu.addOption("Play Game");
        quit = mainMenu.addOption("Quit");
        
        // create the quit menu
        quitMenu = new Menu(100, 30);
        quitYes = quitMenu.addOption("Yes");
        quitNo = quitMenu.addOption("No");
        
        displayMainMenu = false;
        displayQuitMenu = false;
    }
    
    // load resources
    @Override
    public void init(ResourceLoader loader) {
        font = loader.getFontCreator().createBasicFont("res/font.png", 10, 12, 0xFFFFFF);

        title = loader.loadTexture("res/title.png").getSprite();
        
        
        music = loader.loadSound("res/song_idea1.wav");
        music.setVolume(0.9f);
        click = loader.loadSound("res/button_select.wav");
        click.setVolume(0.8f);
        
        displayMainMenu = false;
        displayQuitMenu = false;
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
        
        // handle main menu
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
        			swapState(this.nextId);
        		}
        		// switch to quit menu
        		if (selection == this.quit) {
        			displayMainMenu = false;
        			displayQuitMenu = true;
        		}
        	}
        }
        // handle quit menu
        else if (displayQuitMenu) {
        	quitMenu.updateFadeTimer();
        	
        	if (quitMenu.isDoneFadeTimer()) {
        		quitMenu.resetFadeTimer();
        	}
        	
        	if (input.getJustDownKey("up_arrow")) {
        		quitMenu.moveSelectionUp();
        	}
        	if (input.getJustDownKey("down_arrow")) {
        		quitMenu.moveSelectionDown();
        	}
        	if (input.getJustDownKey("spacebar")) {
        		int selection = quitMenu.getCurrentSelection();
        		
        		if (selection == this.quitYes) {
        			engine.stop();
        		}
        		else {
        			quitMenu.resetCurrentSelection();
        			displayMainMenu = true;
        			displayQuitMenu = false;
        		}
        	}
        }
        // handle press any key to start before main menu
        else {
        	if (fade.getAlpha() == 1f) {
        		pressAnyKey.updateFadeTimer();

        		if (pressAnyKey.isDoneFadeTimer()) {
        			pressAnyKey.resetFadeTimer();
        		}

        		if (input.anyKeyJustDown()) {
        			displayMainMenu = true;
        			displayQuitMenu = false;
        		}
        	}
        	
        	if (input.anyKeyJustDown()) {
        		fade.forceOn();
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
        	font.setSize(20);
        	mainMenu.render(renderer, font);
        }
        else if (displayQuitMenu) {
        	font.setSize(20);
        	font.setAlpha(1f);
        	int x = (int) (renderer.getWidth()/2 - font.getWidth("Are you sure?")/2);
        	font.draw("Are you sure?", x, 145);
        	quitMenu.render(renderer, font);
        }
        else {
        	font.setSize(12);
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
