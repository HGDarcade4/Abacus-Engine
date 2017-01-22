package abacus.graphics;

/*
 * Base renderer for the game engine
 */
public interface Renderer {
    
    // don't touch these, they are used by the engine
    void begin();
    void finish();
    
    // get the number of draw commands that were issued last frame
    int drawCommands();
    
    // virtual resolution dimensions
    int getWidth();
    int getHeight();
    
    // real screen dimensions
    int getRealWidth();
    int getRealHeight();
    
    // screen clear color
    void clearScreen(int r, int g, int b);
    
}
