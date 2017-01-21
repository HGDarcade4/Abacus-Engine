package abacus.graphics;

public interface Renderer {
    
    // don't touch these, they are used by the engine
    void begin();
    void finish();
    
    int drawCommands();
    
    // dimensions
    int getWidth();
    int getHeight();
    
    int getRealWidth();
    int getRealHeight();
    
    // screen clear color
    void clearScreen(int r, int g, int b);
    
}
