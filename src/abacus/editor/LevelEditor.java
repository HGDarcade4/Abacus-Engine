package abacus.editor;

import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import abacus.editor.action.ExitAction;
import abacus.editor.action.NewAction;
import abacus.editor.gui.Splitter;
import abacus.editor.gui.TileChooser;
import abacus.editor.gui.TileMapPanel;
import abacus.editor.gui.Window;

public class LevelEditor {

    public Window window;
    public TileMapPanel mapDisplay;
    public TileChooser chooser;
    
    public TileMap map = null;
    public TileImageProvider tileTypes[] = new TileImageProvider[256];
    
    private JMenuBar menuBar;
    
    public LevelEditor() {
        mapDisplay = new TileMapPanel(this);
        chooser = new TileChooser();
        Splitter s1 = new Splitter(mapDisplay, chooser, false, true, Window.DEF_WIDTH - 256);
        
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        
        fileMenu.add(new JMenuItem(new NewAction(this))).setText("New");
        fileMenu.add(new JMenuItem("Open"));
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem("Save"));
        fileMenu.add(new JMenuItem("Save As"));
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem(new ExitAction())).setText("Exit");
        
        menuBar.add(fileMenu);
        
        window = new Window(menuBar, s1);
        window.animate();
    }
    
    public void newFile(int width, int height) {
        map = new TileMap(width, height);
        mapDisplay.setMap(map);
        for (int i = 0; i < tileTypes.length; i++) {
            tileTypes[i] = null;
        }
        
        try {
            tileTypes[0] = new BasicTileImageProvider(new SpriteSheet("res/tileset_01.png", 8, 8));
            tileTypes[1] = new BasicTileImageProvider(new SpriteSheet("res/tileset_02.png", 8, 8));
            tileTypes[2] = new BasicTileImageProvider(new SpriteSheet("res/tileset_03.png", 8, 8));
            tileTypes[3] = new BasicTileImageProvider(new SpriteSheet("res/tileset_04.png", 8, 8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String args[]) {
        new LevelEditor();
    }
    
}
