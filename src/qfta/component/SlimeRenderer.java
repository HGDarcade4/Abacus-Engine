package qfta.component;

import abacus.ResourceLoader;
import abacus.gameobject.GameComponent;
import abacus.gameobject.GameComponentProperties;
import abacus.gameobject.Transform;
import abacus.graphics.AnimationData;
import abacus.graphics.AnimationPlayer;
import abacus.graphics.AnimationRegistry;
import abacus.graphics.SpriteSheet;
import abacus.graphics.Texture;
import abacus.graphics.WorldRenderer;

public class SlimeRenderer extends GameComponent {

    private ResourceLoader loader;
    private Texture texture;
    private AnimationRegistry animation;
    
    public SlimeRenderer(ResourceLoader loader) {
        this.loader = loader;
        texture = loader.loadTexture("res/enemy_slime.png");
        buildAnimations();
    }
    
    public SlimeRenderer copy() {
    	SlimeRenderer hr = new SlimeRenderer(loader);
    	return hr;
    }
    
    public SlimeRenderer load(GameComponentProperties props) {
        SlimeRenderer c = copy();
        return c;
    }
    
    @Override
    public void attach() {
        
    }
    
    @Override
    public void render(WorldRenderer r) {
        if (!gameObject.has(CharacterMovement.class)) return;
        
        CharacterMovement movement = gameObject.get(CharacterMovement.class);
        Transform tfm = gameObject.getTransform();
        
        animation.setCurrent(movement.dir);
        
        if (movement.moving) {
            animation.play();
        }
        else {
            animation.pauseAndReset();
        }
        
        r.drawCharacterSprite(animation, tfm.x, tfm.y, 16f, 24f);
    }
    
    private void buildAnimations() {
        SpriteSheet player = new SpriteSheet(texture, 16, 24);
        
        animation = new AnimationRegistry();
        
        int[] dir = new int[] {
                3, 0, 1, 2
        };
        
        AnimationData data;
        for (int i = 0; i < 4; i++) {
            data = new AnimationData();
            int speed = 6;
            data.addFrame(player.getSprite(0 + 0, i), speed);
            data.addFrame(player.getSprite(0 + 1, i), speed);
            data.addFrame(player.getSprite(0 + 2, i), speed);
            data.addFrame(player.getSprite(0 + 3, i), speed);
            data.addFrame(player.getSprite(0 + 4, i), speed);
            data.addFrame(player.getSprite(0 + 5, i), speed);
            data.addFrame(player.getSprite(0 + 6, i), speed);
            animation.register(dir[i], new AnimationPlayer(data));
        }
    }
    
}
