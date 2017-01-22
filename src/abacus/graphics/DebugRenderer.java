package abacus.graphics;

/*
 * Draws debug text. This uses the true screen resolution
 */
public class DebugRenderer {

    // font that will be used
    private GameFont debugFont;
    // current y value that will be used
    private float debugY;
    // reference to renderer
    private Renderer renderer;
    
    // ctor
    public DebugRenderer(Renderer renderer, GameFont debugFont) {
        this.debugFont = debugFont;
        this.renderer = renderer;
        
        reset();
    }
    
    // call this at the beginning of the render method
    // sets the location to draw to the top left
    public void reset() {
        debugY = renderer.getRealHeight();
    }
    
    // draws a line of debug info
    public void debugLine(String text) {
        float height = debugFont.getHeight(); 
        
        debugY -= height;
        
        float drawX = 0;
        float drawY = debugY;
        
        debugFont.drawReal(text, drawX, drawY);
    }
    
}