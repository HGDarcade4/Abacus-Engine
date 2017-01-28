package abacus.awt.command;

import java.awt.Color;
import java.awt.Graphics2D;

public class RectCommand implements AwtRenderCommand {

    private boolean fill;
    private int col;
    private int x, y, w, h;
    private float layer;
    
    public RectCommand(boolean fill, int col, int x, int y, int w, int h, float layer) {
        this.fill = fill;
        this.col = col;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.layer = layer;
    }
    
    @Override
    public void draw(Graphics2D g) {
        g.setColor(new Color(col));
        if (fill) {
            g.fillRect(x, y, w, h);
        }
        else {
            g.drawRect(x, y, w, h);
        }
    }
    
    @Override
    public float getLayer() {
        return layer;
    }

}
