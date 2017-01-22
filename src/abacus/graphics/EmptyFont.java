package abacus.graphics;

/*
 * Used when font loading failed
 */
public class EmptyFont extends GameFont {

    @Override
    public Sprite getSprite(char c) {
        return null;
    }

    @Override
    public float getHeight() {
        return 0;
    }

    @Override
    public float getWidth(char c) {
        return 0;
    }

}
