package abacus.editor.gui;

import javax.swing.JComponent;
import javax.swing.JLabel;

import abacus.editor.LevelEditor;

public class InfoText implements GuiComponent {

    private JLabel label;
    private LevelEditor editor;
    
    public InfoText(LevelEditor editor) {
        this.editor = editor;
        label = new JLabel();
        refresh();
    }
    
    public void refresh() {
        label.setText("  |  Current Layer: " + LevelEditor.layerNames[editor.currentLayer] + "  |  Cursor: (" + 
                editor.cursorX + ", " + editor.cursorY + ")" );
    }

    @Override
    public JComponent getComponent() {
        return label;
    }
    
}
