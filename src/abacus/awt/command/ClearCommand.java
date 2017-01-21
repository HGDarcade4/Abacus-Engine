package abacus.awt.command;

import java.awt.Color;
import java.awt.Graphics2D;

public class ClearCommand implements AwtRenderCommand {

    private Color col;
    private int width, height;
    
    public ClearCommand(Color col, int width, int height) {
        this.col = col;
        this.width = width;
        this.height = height;
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(col);
        g.fillRect(0, 0, width, height);
    }

    @Override
    public float getLayer() {
        return -10000.0f;
    }

}
