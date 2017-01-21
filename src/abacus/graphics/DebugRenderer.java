package abacus.graphics;

public class DebugRenderer {

    private GameFont debugFont;
    private float debugY;
    
    public DebugRenderer(Renderer renderer, GameFont debugFont) {
        this.debugFont = debugFont;
        
        debugY = renderer.getRealHeight();
    }
    
    public void debugLine(String text) {
        float height = debugFont.getHeight(); 
        
        debugY -= height;
        
        float drawX = 0;
        float drawY = debugY;
        
        debugFont.drawReal(text, drawX, drawY);
    }
    
}