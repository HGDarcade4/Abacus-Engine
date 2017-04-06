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
import abacus.editor.imageprovider.BasicTileImageProvider;
import abacus.editor.imageprovider.ConnectedTileImageProvider;
import abacus.editor.imageprovider.NullTileImageProvider;
import abacus.editor.imageprovider.TileImageProvider;

public class LevelEditor {

	public static volatile long tick = 0;
	
    public Window window;
    public TileMapPanel mapDisplay;
    public TileChooser chooser;
    
    public TileMap map = null;
    public TileImageProvider tileTypes[] = new TileImageProvider[256];
    
    public int currentLayer = 0;
    public int currentId = 1;
    public int[][] currentMeta = new int[][] {{ 0 }};
    
    private JMenuBar menuBar;
    
    public LevelEditor() {
        mapDisplay = new TileMapPanel(this);
        chooser = new TileChooser(this);
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
    	currentId = 3;
    	currentLayer = 0;
    	currentMeta = new int[][] {{ 0 }};
    	
        map = new TileMap(width, height);
        mapDisplay.setMap(map);
        for (int i = 0; i < tileTypes.length; i++) {
            tileTypes[i] = null;
        }
        
        try {
        	tileTypes[0] = new NullTileImageProvider();
            tileTypes[1] = new BasicTileImageProvider(new SpriteSheet("res/tileset_01.png", 8, 8));
            tileTypes[2] = new ConnectedTileImageProvider(new SpriteSheet("res/tileset_02.png", 8, 8), 1);
            tileTypes[3] = new ConnectedTileImageProvider(new SpriteSheet("res/tileset_03.png", 8, 8), 4);
            tileTypes[4] = new BasicTileImageProvider(new SpriteSheet("res/tileset_04.png", 8, 8));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        mapDisplay.fillAllLayerTiles();
    }
    
    public static void main(String args[]) {
    	System.setProperty("sun.java2d.opengl", "False");
        System.out.println("Hardware Acceleration: " + System.getProperty("sun.java2d.opengl"));
    	
        new LevelEditor();
    }
    
}
