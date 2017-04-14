package abacus.editor.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import javax.swing.AbstractAction;

import abacus.editor.LevelEditor;
import abacus.editor.Tile;

@SuppressWarnings("serial")
public class SaveAction extends AbstractAction {

    private LevelEditor editor;
    
    public SaveAction(LevelEditor editor) {
        this.editor = editor;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO ask for file name
        String filename = "untitled.scene";
        
        File file = new File(filename);
        
        try {
            PrintWriter out = new PrintWriter(file);
            
            out.println(editor.tileTypes.length);
            for (int i = 0; i < editor.tileTypes.length; i++) {
                out.print(i + " ");
                editor.tileTypes[i].saveData(out);
                out.println();
                out.flush();
            }
            out.println(editor.map.getLayerCount() + " " + editor.map.getWidth() + " " + editor.map.getHeight());
            for (int layer = 0; layer < editor.map.getLayerCount(); layer++) {
                for (int y = 0; y < editor.map.getHeight(); y++) {
                    for (int x = 0; x < editor.map.getWidth(); x++) {
                        Tile tile = editor.map.getLayer(layer).getTile(x, y);
                        out.print(tile.tileId + " " + tile.tileMeta + " ");
                        out.flush();
                    }
                    out.println();
                    out.flush();
                }
            }
            out.flush();
            
            out.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
    }

}
