package abacus.editor.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import abacus.editor.LevelEditor;

@SuppressWarnings("serial")
public class LayerAction extends AbstractAction {

    private LevelEditor editor;
    private int id;
    
    public LayerAction(LevelEditor editor, int id) {
        this.editor = editor;
        this.id = id;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        editor.currentLayer = id;
        editor.infoText.refresh();
    }

}
