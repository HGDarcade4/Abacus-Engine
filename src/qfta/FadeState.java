package qfta;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import abacus.GameState;
import abacus.ResourceLoader;
import abacus.graphics.GameFont;
import abacus.graphics.Renderer;
import abacus.ui.Input;

/*
 * Fades text in and out
 * 
 * TODO allow more than one line at a time
 */
public class FadeState extends GameState {

    // variables
    private ArrayList<String> lines;
    private int line;
    private int numLines;
    private FadeTimer fade;
    private GameFont font;
    private String source;
    private int nextId;
    private FadePressKey pressSkipKey;
    
    // arguments are source text file name and game state id to swap to
    public FadeState(String src, int nextId) {
        this.source = src;
        this.nextId = nextId;
    }

    // put lines of text file into array
    @Override
    public void init(ResourceLoader loader) {
    	// variables for FadeTimer
    	int wait, fadeIn, fadeOut, pause, done;
        lines = new ArrayList<>();
        line = 0;
        
        pressSkipKey = new FadePressKey("Press any key to skip...");
        
        // set font for text
        font = loader.getFontCreator().createBasicFont("res/font.png", 10, 12, 0xFFFFFF);
        font.setSize(24f);
        
        // scan input file
        try {
            Scanner scan = new Scanner(new FileInputStream(new File(source)));
            
            while (scan.hasNextLine()) {
                lines.add(scan.nextLine().trim());
            }
            
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        // first 5 values in file are for FadeTimer
        wait = Integer.parseInt(lines.get(line++));
        fadeIn = Integer.parseInt(lines.get(line++));
        pause = Integer.parseInt(lines.get(line++));
        fadeOut = Integer.parseInt(lines.get(line++));
        done = Integer.parseInt(lines.get(line++));

        fade = new FadeTimer(wait, fadeIn, pause, fadeOut, done);

        // set how many lines to print for first block of text
        numLines = Integer.parseInt(lines.get(line++));
    }

    // reset to beginning
    @Override
    public void enter() {
        fade.reset();
        pressSkipKey.resetFadeTimer();
    }

    // update fade
    @Override
    public void update(Input input) {
        fade.update();
        pressSkipKey.updateFadeTimer();
        
        if (pressSkipKey.isDoneFadeTimer()) {
        	pressSkipKey.resetFadeTimer();
        }
        
        if (input.anyKeyJustDown()) {
            swapState(nextId);
        }
        
        // once a block is done fading, move to the next block
        if (fade.isDone()) {
        	line += numLines;
        	// if we have reached the end of the file, move on to the next state
        	if (line >= lines.size()) {
        		swapState(nextId);
        		return;
        	}

        	numLines = Integer.parseInt(lines.get(line++));
            fade.reset();
        }
        
    }

    // render text at center
    // TODO make a helper class for rendering text
    @Override
    public void render(Renderer renderer) {
    	float width;
    	float height;
    	int padding = 40;
    	
    	// clear the screen
        renderer.clearScreen(0, 0, 0);
        
        font.setSize(12f);
        font.setAlpha(fade.getAlpha());
        
        // grab all text in this block and render it
        for (int index = 0; index < numLines; index++) {
        	width = font.getWidth(lines.get(line + index));
        	height = font.getHeight();
        
        	font.draw(lines.get(line + index), renderer.getWidth()/2 - width/2, renderer.getHeight()/2 - height/2 + padding);
        	padding -= 14;
        }
        
        pressSkipKey.render(renderer, font);
    }

    @Override
    public void pause() {}

    @Override
    public void exit() {}

    @Override
    public void end() {}

}
