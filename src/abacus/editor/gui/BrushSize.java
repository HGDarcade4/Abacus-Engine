package abacus.editor.gui;

import java.awt.Dimension;

import javax.swing.JComponent;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import abacus.editor.LevelEditor;

public class BrushSize implements GuiComponent {

    private JSpinner spinner;
    
    public BrushSize(LevelEditor editor) {
        SpinnerNumberModel model = new SpinnerNumberModel(1, 1, 100, 1);
        spinner = new JSpinner(model);
        Dimension d = spinner.getPreferredSize();
        d.width = 40;
        spinner.setPreferredSize(d); 
        spinner.setMaximumSize(d);
        spinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                editor.brushSize = (Integer)spinner.getValue();
            }
        });
    }
    
    @Override
    public JComponent getComponent() {
        return spinner;
    }

}
