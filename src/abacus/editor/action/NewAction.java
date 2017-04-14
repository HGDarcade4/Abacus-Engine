package abacus.editor.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

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
        JTextField widthText = new JTextField();
        JTextField heightText = new JTextField();
        
        Object[] vals = new Object[] {
                new JLabel("Width: "), 
                widthText, 
                new JLabel("Height: "),
                heightText
        };
        
        int status = JOptionPane.showConfirmDialog(null, vals, "Enter Map Size", JOptionPane.OK_CANCEL_OPTION);
        
        if (status != JOptionPane.OK_OPTION) {
            return;
        }
        
        int width, height;
        width = Integer.parseInt(widthText.getText());
        height = Integer.parseInt(heightText.getText());
        
        editor.newFile(width, height);
    }

}
