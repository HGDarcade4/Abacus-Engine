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
        width = 32;
        height = 32;
        
        editor.newFile(width, height);
    }

}
