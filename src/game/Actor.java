package game;

import java.awt.event.KeyEvent;

import abacus.ResourceLoader;
import abacus.graphics.AnimationData;
import abacus.graphics.AnimationPlayer;
import abacus.graphics.AnimationRegistry;
import abacus.graphics.SpriteSheet;
import abacus.graphics.WorldRenderer;
import abacus.tile.TileBody;
import abacus.tile.TileMap;
import abacus.ui.Input;

/*
 * IGNORE THIS
 * 
 * I'M GOING TO CHANGE THIS A LOT
 */
public class Actor {

    private static AnimationRegistry actorAnimReg = new AnimationRegistry();
    
    private static final int UP = 1;
    private static final int DOWN = 0;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;
    
    private AnimationRegistry animation;
    private TileBody body;
    private int dir;
    
    public Actor() {
        animation = actorAnimReg.copy();
        
        body = new TileBody(64f * 16, 64f * 16, 10f, 5f);
    }
    
    public void update(TileMap map, Input input) {
        float dx = 0f, dy = 0f, move = 1.0f;
        
        if (input.getKey(KeyEvent.VK_UP)) {
            dy += move;
        }
        if (input.getKey(KeyEvent.VK_DOWN)) {
            dy -= move;
        }
        if (input.getKey(KeyEvent.VK_LEFT)) {
            dx -= move;
        }
        if (input.getKey(KeyEvent.VK_RIGHT)) {
            dx += move;
        }
        
        animation.setCurrentAndPlay(dir);
        
        if (dy != 0 && dx == 0) {
            if (dy < 0) {
                dir = DOWN;
            }
            else {
                dir = UP;
            }
            
        }
        else if (dx != 0 && dy == 0) {
            if (dx < 0) {
                dir = LEFT;
            }
            else {
                dir = RIGHT;
            }
        }
        else if (dx == 0 && dy == 0) {
            animation.pauseAndReset();
        }
        else {
            dy *= 0.7f;
            dx *= 0.7f;
        }
        
        body.setVelX(dx);
        body.setVelY(dy);
    }
    
    public void render(WorldRenderer wr) {
        wr.drawCharacterSprite(animation, body.getCenterX(), body.getMinY(), 16f, 22f);
        wr.drawDebugRect(0xFF0000, body.getMinX(), body.getMinY(), body.getWidth(), body.getHeight());
    }
    
    public float getX() {
        return body.getCenterX();
    }
    
    public float getY() {
        return body.getCenterY();
    }
    
    public TileBody getBody() {
        return body;
    }
    
    public static void loadAnimations(ResourceLoader loader) {
        SpriteSheet player = new SpriteSheet(loader.loadTexture("res/rpg_male.png"), 16, 24);
        
        AnimationData data;
        for (int i = 0; i < 4; i++) {
            data = new AnimationData();
            int speed = 6;
            data.addFrame(player.getSprite(3 + 0, i), speed);
            data.addFrame(player.getSprite(3 + 1, i), speed);
            data.addFrame(player.getSprite(3 + 0, i), speed);
            data.addFrame(player.getSprite(3 + 2, i), speed);
            actorAnimReg.register(i, new AnimationPlayer(data));
        }
    }
    
}
