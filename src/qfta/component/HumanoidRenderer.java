package qfta.component;

import abacus.ResourceLoader;
import abacus.gameobject.Collider;
import abacus.gameobject.GameComponent;
import abacus.graphics.AnimationData;
import abacus.graphics.AnimationPlayer;
import abacus.graphics.AnimationRegistry;
import abacus.graphics.SpriteSheet;
import abacus.graphics.WorldRenderer;
import abacus.tile.TileBody;

public class HumanoidRenderer extends GameComponent {

    private static AnimationRegistry actorAnimReg = new AnimationRegistry();
    private static AnimationRegistry clothesAnimReg = new AnimationRegistry();
    
    private AnimationRegistry animation, clothes;
    
    public static void loadAnimations(ResourceLoader loader) {
        SpriteSheet player = new SpriteSheet(loader.loadTexture("res/rpg_male.png"), 16, 24);
        
        AnimationData data, clothes;
        for (int i = 0; i < 4; i++) {
            data = new AnimationData();
            clothes = new AnimationData();
            int speed = 6;
            data.addFrame(player.getSprite(0 + 0, i), speed);
            data.addFrame(player.getSprite(0 + 1, i), speed);
            data.addFrame(player.getSprite(0 + 0, i), speed);
            data.addFrame(player.getSprite(0 + 2, i), speed);
            actorAnimReg.register(i, new AnimationPlayer(data));
            
            clothes.addFrame(player.getSprite(3 + 0, i), speed);
            clothes.addFrame(player.getSprite(3 + 1, i), speed);
            clothes.addFrame(player.getSprite(3 + 0, i), speed);
            clothes.addFrame(player.getSprite(3 + 2, i), speed);
            clothesAnimReg.register(i, new AnimationPlayer(clothes));
        }
    }
    
    public HumanoidRenderer() {
        animation = actorAnimReg.copy();
        clothes = clothesAnimReg.copy();
    }
    
    @Override
    public void render(WorldRenderer r) {
        if (!gameObject.has(Movement.class)) return;
        if (!gameObject.has(Collider.class)) return;
        
        Movement movement = gameObject.get(Movement.class);
        TileBody body = gameObject.get(Collider.class).tileBody;
        
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
        
        r.drawCharacterSprite(animation, body.getCenterX(), body.getMinY(), 16f, 24f);
        r.drawCharacterSprite(clothes, body.getCenterX(), body.getMinY(), 16f, 24f);
        r.drawDebugRect(0xFF0000, body.getMinX(), body.getMinY(), body.getWidth(), body.getHeight());
    }
    
}
