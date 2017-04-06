package abacus.editorold.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import abacus.editorold.LevelEditor;

@SuppressWarnings("serial")
public class NewAction extends AbstractAction {

    private LevelEditor editor;
    
    public NewAction(LevelEditor editor) {
        this.editor = editor;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO ask for map size
        int width, height;
        width = 10;
        height = 10;
        
        editor.newMap(width, height);
        editor.getMap().addLayers(1);
        editor.getWindow().setTitle("untitled");
    }

}
