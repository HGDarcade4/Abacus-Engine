package abacus.awt.command;

import java.awt.Graphics2D;

public interface AwtRenderCommand {

    void draw(Graphics2D g);
    
    float getLayer();
    
}
