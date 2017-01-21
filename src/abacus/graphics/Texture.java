package abacus.graphics;

public interface Texture extends Renderable {

    int getWidth();
    int getHeight();
    
    Sprite getSprite(int x, int y, int w, int h);
    
    Sprite getSprite();
    
}
