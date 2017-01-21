package abacus.graphics;

public interface Renderable {

    Sprite getSprite();
    
    void play();
    
    void pause();
    
    void reset();
    
    void pauseAndReset();
    
}
