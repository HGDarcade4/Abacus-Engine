package abacus.editor;

import java.io.IOException;

import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import abacus.editor.action.ExitAction;
import abacus.editor.action.LayerAction;
import abacus.editor.action.NewAction;
import abacus.editor.action.SaveAction;
import abacus.editor.action.TileSetAction;
import abacus.editor.gui.BrushSize;
import abacus.editor.gui.InfoText;
import abacus.editor.gui.Splitter;
import abacus.editor.gui.TileChooser;
import abacus.editor.gui.TileMapPanel;
import abacus.editor.gui.Window;
import abacus.editor.imageprovider.BasicTileImageProvider;
import abacus.editor.imageprovider.ConnectedTileImageProvider;
import abacus.editor.imageprovider.NullTileImageProvider;
import abacus.editor.imageprovider.TileImageProvider;
import abacus.editor.imageprovider.WallTileImageProvider;

public class LevelEditor {

	public static volatile long tick = 0;
	
	public static String[] layerNames = new String[] {
	        "Background", 
	        "Main Tiles", 
	        "Detail"
	};
	
    public Window window;
    public TileMapPanel mapDisplay;
    public TileChooser chooser;
    public InfoText infoText;
    
    public TileMap map = null;
    public TileImageProvider tileTypes[] = new TileImageProvider[8];
    
    public int currentLayer = 0;
    public int currentId = 1;
    public int brushSize = 1;
    public int[][] currentMeta = new int[][] {{ 0 }};
    
    private JMenuBar menuBar;
    
    public LevelEditor() {
        mapDisplay = new TileMapPanel(this);
        chooser = new TileChooser(this);
        infoText = new InfoText(this);
        Splitter s1 = new Splitter(mapDisplay, chooser, false, true, Window.DEF_WIDTH - 256);
        
        menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        
        fileMenu.add(new JMenuItem(new NewAction(this))).setText("New");
//        fileMenu.add(new JMenuItem("Open"));
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem(new SaveAction(this))).setText("Save");
//        fileMenu.add(new JMenuItem("Save As"));
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem(new ExitAction())).setText("Exit");
        
        JMenu tileMenu = new JMenu("Tileset");
        
        tileMenu.add(new JMenuItem(new TileSetAction(this, 1))).setText("Basic Tiles");
        tileMenu.add(new JMenuItem(new TileSetAction(this, 2))).setText("Static Connected Tiles");
        tileMenu.add(new JMenuItem(new TileSetAction(this, 3))).setText("Animated Connected Tiles");
        tileMenu.add(new JMenuItem(new TileSetAction(this, 4))).setText("Wall Tiles");
        
        JMenu layerMenu = new JMenu("Layer");
        
        layerMenu.add(new JMenuItem(new LayerAction(this, 0))).setText(layerNames[0]);
        layerMenu.add(new JMenuItem(new LayerAction(this, 1))).setText(layerNames[1]);
        layerMenu.add(new JMenuItem(new LayerAction(this, 2))).setText(layerNames[2]);
        
        menuBar.add(fileMenu);
        menuBar.add(tileMenu);
        menuBar.add(layerMenu);
        menuBar.add(infoText.getComponent());
        menuBar.add(new JLabel("  |  Brush Size: "));
        menuBar.add(new BrushSize(this).getComponent());
        
        window = new Window(menuBar, s1);
        window.animate();
    }
    
    public void newFile(int width, int height) {
    	currentId = 2;
    	currentLayer = 0;
    	currentMeta = new int[][] {{ 0 }};
    	
        map = new TileMap(width, height);
        map.addLayers(3);
        mapDisplay.setMap(map);
        for (int i = 0; i < tileTypes.length; i++) {
            tileTypes[i] = new NullTileImageProvider();
        }
        
        try {
        	tileTypes[0] = new NullTileImageProvider();
            tileTypes[1] = new BasicTileImageProvider(new SpriteSheet("res/tileset_01.png", 8, 8));
            tileTypes[2] = new ConnectedTileImageProvider(new SpriteSheet("res/tileset_02.png", 8, 8), 1);
            tileTypes[3] = new ConnectedTileImageProvider(new SpriteSheet("res/tileset_03.png", 8, 8), 4);
            tileTypes[4] = new WallTileImageProvider(new SpriteSheet("res/tileset_04.png", 8, 8), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        mapDisplay.fillAllLayerTiles();
        
        currentId = 4;
        chooser.updateCurrentTileSet();
    }
    
    public static void main(String args[]) {
    	System.setProperty("sun.java2d.opengl", "False");
        System.out.println("Hardware Acceleration: " + System.getProperty("sun.java2d.opengl"));
    	
        new LevelEditor();
    }
    
}
