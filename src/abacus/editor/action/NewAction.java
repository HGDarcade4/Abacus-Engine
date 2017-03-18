package abacus.editor.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import abacus.editor.LevelEditor;

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
        editor.getWindow().setTitle("untitled");
    }

}
