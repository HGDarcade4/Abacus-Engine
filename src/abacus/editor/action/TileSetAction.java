package abacus.editor.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import abacus.editor.LevelEditor;

@SuppressWarnings("serial")
public class TileSetAction extends AbstractAction {

    private LevelEditor editor;
    private int id;
    
    public TileSetAction(LevelEditor editor, int id) {
        this.editor = editor;
        this.id = id;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        editor.currentId = id;
        editor.currentMeta = new int[][] {{ 0 }};
        editor.chooser.updateCurrentTileSet();
        editor.chooser.resetSelection();
    }

}
