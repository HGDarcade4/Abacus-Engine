package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import abacus.GameState;
import abacus.ResourceLoader;
import abacus.graphics.BasicFont;
import abacus.graphics.GameFont;
import abacus.graphics.Renderer;
import abacus.graphics.SpriteSheet;
import abacus.ui.Input;

public class FadeState extends GameState {

    public static final int ID = 2;
    
    private ArrayList<String> lines;
    private int line;
    private FadeTimer fade;
    private GameFont font;
    
    @Override
    public void init(ResourceLoader loader) {
        lines = new ArrayList<>();
        line = 0;
        fade = new FadeTimer(100, 100, 100, 100);
        
        font = new BasicFont(new SpriteSheet(loader.loadTexture("res/font_white.png"), 10, 12));
        font.setSize(12);
        
        try {
            Scanner scan = new Scanner(new FileInputStream(new File("res/scroll_text.txt")));
            
            while (scan.hasNextLine()) {
                lines.add(scan.nextLine().trim());
            }
            
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void enter() {
        line = 0;
        fade.reset();
    }

    @Override
    public void update(Input input) {
        fade.update();
        
        if (fade.isDone()) {
            line++;
            fade.reset();
        }
        
        if (line >= lines.size() || input.anyKeyJustDown()) {
            swapState(JrpgPlayState.ID);
        }
    }

    @Override
    public void render(Renderer renderer) {
        renderer.clearScreen(0, 0, 0);
        
        font.setSize(12f);
        font.setAlpha(fade.getAlpha());
        
        float width = font.getWidth(lines.get(line));
        float height = font.getHeight();
        
        font.draw(lines.get(line), renderer.getWidth()/2 - width/2, renderer.getHeight()/2 - height/2);
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
