package abacus.awt.command;

import java.awt.Graphics2D;

/*
 * Interface for drawing things with Graphics
 * 
 * You never work directly with this class.
 */
public interface AwtRenderCommand {

    // draw the command
    void draw(Graphics2D g);
    
    // used for sorting
    float getLayer();
    
}
