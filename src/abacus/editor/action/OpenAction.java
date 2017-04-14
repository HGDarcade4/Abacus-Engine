package abacus.editor.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

import abacus.editor.LevelEditor;

@SuppressWarnings("serial")
public class OpenAction extends AbstractAction {

    private LevelEditor editor;
    
    public OpenAction(LevelEditor editor) {
        this.editor = editor;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        String filename = JOptionPane.showInputDialog(null, "Enter file name: ", "Open", JOptionPane.OK_CANCEL_OPTION);
        
        if (filename == null) {
            return;
        }
        
        try {
            Scanner in = new Scanner(new File(filename));
            
            // tile types should not be changed
            for (int i = 0; i < 9; i++) {
                in.nextLine();
            }
            
            int layers = in.nextInt();
            int width = in.nextInt();
            int height = in.nextInt();
            editor.newFile(width, height);
            for (int i = 0; i < layers; i++) {
                for (int y = 0; y < height; y++) {
                    for (int x = 0; x < width; x++) {
                        editor.map.getLayer(i).getTile(x, y).tileId = in.nextInt();
                        editor.map.getLayer(i).getTile(x, y).tileMeta = in.nextInt();
                    }
                }
            }
            
            while (in.hasNext()) {
                String word = in.next();
                
                if (word.equals("tp")) {
                    int x = in.nextInt();
                    int y = in.nextInt();
                    String file = in.next();
                    editor.map.getLayer(0).getTile(x, y).tp = file;
                }
                else if (word.equals("start")) {
                    in.nextInt();
                    in.nextInt();
                }
                else if (word.equals("music")) {
                    in.next();
                }
            }
            
            in.close();
        } catch (FileNotFoundException e1) {
            JOptionPane.showMessageDialog(null, "File not found", "File not found", JOptionPane.OK_OPTION);
        }
    }

}
