package abacus.editor.gui;

import java.awt.Graphics;

import javax.swing.JPanel;

import abacus.editor.LevelEditor;

@SuppressWarnings("serial")
public class TileMapPanel extends JPanel {
    
    private LevelEditor editor;
    
    public TileMapPanel(LevelEditor editor) {
        this.editor = editor;
    }
    
    public void paintComponent(Graphics g) {
        if (editor.getMap() == null) {
            return;
        }
    }
    
}
