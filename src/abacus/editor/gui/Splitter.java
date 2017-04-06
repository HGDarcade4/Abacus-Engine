package abacus.editor.gui;

import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

public class Splitter implements GuiComponent {

    private JSplitPane pane;
    
    public Splitter(GuiComponent a, GuiComponent b, boolean vertical, boolean dynSizeFirst, int firstSize) {
        pane = new JSplitPane(
                vertical ? JSplitPane.VERTICAL_SPLIT : JSplitPane.HORIZONTAL_SPLIT, 
                new JScrollPane(a.getComponent(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED), 
                new JScrollPane(b.getComponent(), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
        pane.setResizeWeight(dynSizeFirst ? 1 : 0);
        pane.setDividerLocation(firstSize);
    }

    @Override
    public JComponent getComponent() {
        return pane;
    }
    
    
}
