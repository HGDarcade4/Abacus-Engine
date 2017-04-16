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
    private boolean displayNewMenu;
    private boolean displayLoadMenu;
    private Menu mainMenu;
    private Menu quitMenu;
    private Menu newMenu;
    private Menu loadMenu;
    private int newGame, loadGame, settings, quit, quitYes, quitNo,
    			new1, new2, new3, new4, newBack,
    			load1, load2, load3, load4, loadBack;
    
    public TitleState(int nextStateId) {
    	this.nextId = nextStateId;
        fade = new FadeTimer(60, 6 * 60, Integer.MAX_VALUE, 0, 0);
        pressAnyKey = new FadePressKey("Press any key...");
        
        // create the main menu
        mainMenu = new Menu(115, 30);
        newGame = mainMenu.addOption("New Game");
        loadGame = mainMenu.addOption("Load Game");
        settings = mainMenu.addOption("Settings");
        quit = mainMenu.addOption("Quit");
        
        // create the new menu
        newMenu = new Menu(145, 30);
        new1 = newMenu.addOption("Save Slot 1");
        new2 = newMenu.addOption("Save Slot 2");
        new3 = newMenu.addOption("Save Slot 3");
        new4 = newMenu.addOption("Save Slot 4");
        newBack = newMenu.addOption("Back");

        // create the load menu
        loadMenu = new Menu(145, 30);
        load1 = loadMenu.addOption("Save Slot 1");
        load2 = loadMenu.addOption("Save Slot 2");
        load3 = loadMenu.addOption("Save Slot 3");
        load4 = loadMenu.addOption("Save Slot 4");
        loadBack = loadMenu.addOption("Back");
        
        // create the quit menu
        quitMenu = new Menu(100, 30);
        quitYes = quitMenu.addOption("Yes");
        quitNo = quitMenu.addOption("No");
        
        displayMainMenu = false;
        displayQuitMenu = false;
        displayNewMenu = false;
        displayLoadMenu = false;
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
        displayNewMenu = false;
        displayLoadMenu = false;
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
        		
        		// switch to new menu
        		if (selection == this.newGame) {
        			this.displayNewMenu = true;
        			this.displayMainMenu = false;
        		}
        		if (selection == this.loadGame) {
        			this.displayLoadMenu = true;
        			this.displayMainMenu = false;
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
        // handle new menu
        else if (displayNewMenu) {
        	newMenu.updateFadeTimer();
        	
        	if (newMenu.isDoneFadeTimer()) {
        		newMenu.resetFadeTimer();
        	}
        	
        	if (input.getJustDownKey("up_arrow")) {
        		newMenu.moveSelectionUp();
        	}
        	if (input.getJustDownKey("down_arrow")) {
        		newMenu.moveSelectionDown();
        	}
        	if (input.getJustDownKey("spacebar")) {
        		int selection = newMenu.getCurrentSelection();
        		
        		if (selection == this.new1) {
        			this.swapState(nextId);
        		}
        		if (selection == this.new2) {
        			this.swapState(nextId);
        		}
        		if (selection == this.new3) {
        			this.swapState(nextId);
        		}
        		if (selection == this.new4) {
        			this.swapState(nextId);
        		}
        		if (selection == this.newBack) {
        			this.newMenu.resetCurrentSelection();
        			this.displayNewMenu = false;
        			this.displayMainMenu = true;
        		}
        	}
        }
        else if (displayLoadMenu) {
        	loadMenu.updateFadeTimer();
        	
        	if (loadMenu.isDoneFadeTimer()) {
        		loadMenu.resetFadeTimer();
        	}
        	
        	if (input.getJustDownKey("up_arrow")) {
        		loadMenu.moveSelectionUp();
        	}
        	if (input.getJustDownKey("down_arrow")) {
        		loadMenu.moveSelectionDown();
        	}
        	if (input.getJustDownKey("spacebar")) {
        		int selection = loadMenu.getCurrentSelection();
        		
        		if (selection == this.load1) {
        			this.swapState(nextId);
        		}
        		if (selection == this.load2) {
        			this.swapState(nextId);
        		}
        		if (selection == this.load3) {
        			this.swapState(nextId);
        		}
        		if (selection == this.load4) {
        			this.swapState(nextId);
        		}
        		if (selection == this.loadBack) {
        			this.loadMenu.resetCurrentSelection();
        			this.displayLoadMenu = false;
        			this.displayMainMenu = true;
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
        else if (displayNewMenu) {
        	font.setSize(20);
        	newMenu.render(renderer, font);
        }
        else if (displayLoadMenu) {
        	font.setSize(20);
        	loadMenu.render(renderer, font);
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
