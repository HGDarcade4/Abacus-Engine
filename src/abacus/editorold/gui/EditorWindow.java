package abacus.editorold.gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import abacus.editorold.LevelEditor;
import abacus.editorold.action.ExitAction;
import abacus.editorold.action.NewAction;

/*
 * WORK IN PROGRESS DON'T MESS WITH THIS
 */
public class EditorWindow {

    private static final String TITLE = "Tile Map Editor";
    
    private static final int DEF_WIDTH = 1200;
    private static final int DEF_HEIGHT = 800;
    
    private static final int DEF_TILE_SELECT_WIDTH = 250;
    private static final int DEF_LAYER_HEIGHT = 200;
    
    private JFrame window;
    private JPanel mapEditor, tileSelector, tilesetSelector, layerSelector, propsSelector;
    private JSplitPane mapTileSplit, mapLayerSplit, tileTilesetSplit, layerPropSplit;
    
    private JMenuBar menuBar;
    private JMenu fileMenu, editMenu;
    
    private LevelEditor editor;
    
    public static void main(String[] args) {
//        System.setProperty("sun.java2d.opengl", "True");
//        System.out.println("Hardware Acceleration: " + System.getProperty("sun.java2d.opengl"));
        
        EditorWindow window = new EditorWindow();
        window.setTitle("untitled");
    }
    
    public EditorWindow() {
        editor = new LevelEditor(this);
        
        window = new JFrame(TITLE);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        window.getContentPane().setPreferredSize(new Dimension(DEF_WIDTH, DEF_HEIGHT));
        window.pack();
        
        createPanels();
        createMenus();
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
    
    public void setTitle(String title) {
        window.setTitle(TITLE + " - " + title);
    }
    
    public void draw() {
        window.repaint();
    }
    
    private void createPanels() {
        mapEditor = new TileMapPanel(editor);
        tileSelector = new JPanel();
        layerSelector = new JPanel();
        tilesetSelector = new JPanel();
        propsSelector = new JPanel();

        tileTilesetSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
                new JScrollPane(tilesetSelector, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), 
                new JScrollPane(tileSelector, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED)
                );
        tileTilesetSplit.setDividerLocation(DEF_LAYER_HEIGHT);
        tileTilesetSplit.setResizeWeight(0);
        
        layerPropSplit = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
                new JScrollPane(layerSelector, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),
                new JScrollPane(propsSelector, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER)
                );
        layerPropSplit.setDividerLocation(DEF_LAYER_HEIGHT);
        layerPropSplit.setResizeWeight(0);
        
        mapLayerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
                layerPropSplit, 
                new JScrollPane(mapEditor, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS)
                );
        mapLayerSplit.setDividerLocation(DEF_TILE_SELECT_WIDTH);
        mapLayerSplit.setResizeWeight(0);
        
        mapTileSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, 
                mapLayerSplit, 
                tileTilesetSplit
                );
        mapTileSplit.setDividerLocation(DEF_WIDTH - DEF_TILE_SELECT_WIDTH);
        mapTileSplit.setResizeWeight(1);
        
        window.add(mapTileSplit);
    }
    
    private void createMenus() {
        menuBar = new JMenuBar();
        
        createFileMenu();
        createEditMenu();
        
        window.setJMenuBar(menuBar);
    }
    
    private void createFileMenu() {
        fileMenu = new JMenu("File");
        
        fileMenu.add(new JMenuItem(new NewAction(editor))).setText("New");
        fileMenu.add(new JMenuItem("Open"));
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem("Save"));
        fileMenu.add(new JMenuItem("Save As"));
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem("Preferences"));
        fileMenu.addSeparator();
        fileMenu.add(new JMenuItem(new ExitAction())).setText("Exit");
        
        menuBar.add(fileMenu);
    }
    
    private void createEditMenu() {
        editMenu = new JMenu("Edit");
        
        editMenu.add(new JMenuItem("Undo"));
        editMenu.add(new JMenuItem("Redo"));
        
        menuBar.add(editMenu);
    }

}
