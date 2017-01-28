package qfta.comp;

import abacus.ResourceLoader;
import abacus.gameobject.ActorComponent;
import abacus.graphics.AnimationData;
import abacus.graphics.AnimationPlayer;
import abacus.graphics.AnimationRegistry;
import abacus.graphics.SpriteSheet;
import abacus.graphics.WorldRenderer;
import abacus.tile.TileBody;

public class NPCRenderComponent extends ActorComponent {
    
    // TODO list of animation reg's
    private static final AnimationRegistry ANIMATIONS = new AnimationRegistry();
    
    public static final int WIDTH = 16;
    public static final int HEIGHT = 24;
    
    public static final int DIR_NORTH = 0;
    public static final int DIR_SOUTH = 1;
    public static final int DIR_LEFT  = 2;
    public static final int DIR_RIGHT = 3;
    
    private AnimationRegistry playerBody;
    
    public NPCRenderComponent(ResourceLoader loader) {
        playerBody = ANIMATIONS.copy();
    }
    
    @Override
    public void render(WorldRenderer wr) {
        TileBody body = actor.getBody();
        
        wr.drawCharacterSprite(
                playerBody.getSprite(), 
                body.getCenterX(),
                body.getMinY(),
                WIDTH,
                HEIGHT);
    }
    
    public static void loadResources(ResourceLoader loader) {
        SpriteSheet player = new SpriteSheet(
                loader.loadTexture("res/rpg_male.png"), 
                16, 24);
        
        AnimationData data;
        int speed = 6;
        for (int i = 0; i < 4; i++) {
            data = new AnimationData();
            data.addFrame(player.getSprite(3 + 0, i), speed);
            data.addFrame(player.getSprite(3 + 1, i), speed);
            data.addFrame(player.getSprite(3 + 0, i), speed);
            data.addFrame(player.getSprite(3 + 2, i), speed);
            ANIMATIONS.register(i, new AnimationPlayer(data));
        }
    }
    
}
