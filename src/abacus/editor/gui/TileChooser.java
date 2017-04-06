package abacus.editor.gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JComponent;
import javax.swing.JPanel;

public class TileChooser implements GuiComponent {

    public static final Color BACKGROUND = new Color(0xDDDDDD);
    
    private JPanel panel;
    
    @SuppressWarnings("serial")
    public TileChooser() {
        panel = new JPanel() {
            public void paintComponent(Graphics g) {
                draw(g);
            }
        };
    }
    
    public void draw(Graphics g) {
        g.setColor(BACKGROUND);
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());
    }

    @Override
    public JComponent getComponent() {
        return panel;
    }
    
    
    
}
