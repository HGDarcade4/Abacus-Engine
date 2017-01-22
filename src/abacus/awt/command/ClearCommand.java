package abacus.awt.command;

import java.awt.Color;
import java.awt.Graphics2D;

/*
 * Command to clear the screen to a color
 * 
 * You never work directly with this class.
 */
public class ClearCommand implements AwtRenderCommand {

    // color to clear
    private Color col;
    private int width, height;
    
    // ctor
    public ClearCommand(Color col, int width, int height) {
        this.col = col;
        this.width = width;
        this.height = height;
    }
    
    // fill a rectangle with the color
    @Override
    public void draw(Graphics2D g) {
        g.setColor(col);
        g.fillRect(0, 0, width, height);
    }

    // this should be at the very back
    @Override
    public float getLayer() {
        return -Float.MAX_VALUE;
    }

}
