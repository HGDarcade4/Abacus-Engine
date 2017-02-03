package qfta.component;

import abacus.gameobject.GameComponent;
import abacus.gameobject.Transform;
import abacus.graphics.AnimationData;
import abacus.graphics.AnimationPlayer;
import abacus.graphics.AnimationRegistry;
import abacus.graphics.SpriteSheet;
import abacus.graphics.Texture;
import abacus.graphics.WorldRenderer;
import qfta.Resource;

public class HumanoidRenderer extends GameComponent {

    private Texture texture;
    private AnimationRegistry animation, clothes;
    
    public HumanoidRenderer() {
        texture = Resource.loadTexture("res/rpg_male.png");
        buildAnimations();
    }
    
    private HumanoidRenderer(Texture tex) {
        texture = tex;
        buildAnimations();
    }
    
    public HumanoidRenderer copy() {
    	HumanoidRenderer hr = new HumanoidRenderer(texture);
    	return hr;
    }
    
    @Override
    public void render(WorldRenderer r) {
        if (!gameObject.has(Movement.class)) return;
        
        Movement movement = gameObject.get(Movement.class);
        Transform tfm = gameObject.getTransform();
        
        animation.setCurrent(movement.dir);
        clothes.setCurrent(movement.dir);
        
        if (movement.moving) {
            animation.play();
            clothes.play();
        }
        else {
            animation.pauseAndReset();
            clothes.pauseAndReset();
        }
        
        r.drawCharacterSprite(animation, tfm.x, tfm.y, 16f, 24f);
        r.drawCharacterSprite(clothes, tfm.x, tfm.y, 16f, 24f);
    }
    
    private void buildAnimations() {
        SpriteSheet player = new SpriteSheet(texture, 16, 24);
        
        animation = new AnimationRegistry();
        clothes = new AnimationRegistry();
        
        AnimationData data, cloth;
        for (int i = 0; i < 4; i++) {
            data = new AnimationData();
            cloth = new AnimationData();
            int speed = 6;
            data.addFrame(player.getSprite(0 + 0, i), speed);
            data.addFrame(player.getSprite(0 + 1, i), speed);
            data.addFrame(player.getSprite(0 + 0, i), speed);
            data.addFrame(player.getSprite(0 + 2, i), speed);
            animation.register(i, new AnimationPlayer(data));
            
            cloth.addFrame(player.getSprite(3 + 0, i), speed);
            cloth.addFrame(player.getSprite(3 + 1, i), speed);
            cloth.addFrame(player.getSprite(3 + 0, i), speed);
            cloth.addFrame(player.getSprite(3 + 2, i), speed);
            clothes.register(i, new AnimationPlayer(cloth));
        }
    }
    
}
