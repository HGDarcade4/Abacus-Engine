package abacus.graphics;

/*
 * Image source. To actually draw textures, you must get the texture 
 * as a sprite. You can either get the entire texture as a sprite, 
 * or you can get a sub region. 
 */
public interface Texture extends Renderable {

    // dimensions of the texture
    int getWidth();
    int getHeight();
    
    // returns a subregion of the texture
    // TODO OpenGL style float coordinates ? 
    Sprite getSprite(int x, int y, int w, int h);
    
    // returns the entire texture as a sprite
    Sprite getSprite();
    
}
