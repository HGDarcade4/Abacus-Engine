package abacus.graphics;

/*
 * Some renderer's (WorldRenderer) will take a Renderable argument.
 * This is so I don't have to create methods for Sprite, AnimationPlayer,
 * and AnimationRegistry. 
 */
public interface Renderable {

    // get the current sprite that should be drawn
    Sprite getSprite();
    
    // start animation if it exists
    void play();
    
    // pause animation if it exists
    void pause();
    
    // reset animation if it exists
    void reset();
    
    // pause and then reset animation if it exists
    void pauseAndReset();
    
}
