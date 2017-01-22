package test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import abacus.GameState;
import abacus.ResourceLoader;
import abacus.graphics.DebugRenderer;
import abacus.graphics.GameFont;
import abacus.graphics.Renderer;
import abacus.ui.Input;

public class ScrollTextState extends GameState {

    public static final int ID = 1;
    
    private GameFont font;
    private float y;
    
    private List<String> lines;
    
    private String source;
    
    public ScrollTextState() {
        this.source = "res/scroll_text.txt";
    }
    
    @Override
    public void init(ResourceLoader loader) {
        font = loader.getFontCreator().createBasicFont("res/font.png", 10, 12, 0xFFFFFF);
        font.setSize(12f);
        
        lines = new ArrayList<>();
        
        float width = engine.getRenderer().getWidth();
        
        try {
            Scanner scan = new Scanner(new FileInputStream(new File(source)));
            
            String line = "", word = "";
            
            while (scan.hasNext()) {
                word = scan.next();
                
                if (font.getWidth(line + " " + word) < width) {
                    if (line.length() != 0) {
                        line += " ";
                    }
                    line += word;
                }
                else {
                    lines.add(line);
                    line = word;
                    word = "";
                }
            }
            
            if (!line.equals("")) {
                lines.add(line);
            }
            
            scan.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void enter() {
        if (font != null) {
            y = -font.getHeight() * 4;
        }
        
    }

    @Override
    public void update(Input input) {
        y += 0.25f;
        
        if (y - lines.size() * font.getHeight() * 2 > engine.getRenderer().getHeight()
                || input.anyKeyJustDown()) {
            swapState(JrpgPlayState.ID);
        }
    }

    @Override
    public void render(Renderer renderer) {
        renderer.clearScreen(0, 0, 0);
        
        float halfScreenWidth = renderer.getWidth() / 2f;
        
        for (int i = 0; i < lines.size(); i++) {
            float halfWidth = font.getWidth(lines.get(i)) / 2f;
            
            font.draw(lines.get(i), halfScreenWidth - halfWidth, y - i * font.getHeight() * 2);
        }
        
        font.setSize(12f);
        DebugRenderer sr = new DebugRenderer(renderer, font);
        
        sr.debugLine("draw commands: " + renderer.drawCommands());
        sr.debugLine("updates per second: " + String.format("%1.2f", engine.getUps()));
        sr.debugLine("frames per second: " + String.format("%1.2f", engine.getFps()));
        
        font.setSize(12f);
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
