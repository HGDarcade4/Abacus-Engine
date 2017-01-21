package test;

import java.awt.event.KeyEvent;

import abacus.ResourceLoader;
import abacus.graphics.AnimationData;
import abacus.graphics.AnimationPlayer;
import abacus.graphics.AnimationRegistry;
import abacus.graphics.SpriteSheet;
import abacus.graphics.WorldRenderer;
import abacus.tile.TileMap;
import abacus.ui.Input;

public class Actor {

    private static AnimationRegistry actorAnimReg = new AnimationRegistry();
    
    private static final int UP = 1;
    private static final int DOWN = 0;
    private static final int LEFT = 2;
    private static final int RIGHT = 3;
    
    private AnimationRegistry animation;
    private float x, y;
    private int dir;
    
    public Actor() {
        animation = actorAnimReg.copy();
        
        x = y = 64.0f;
    }
    
    public void update(TileMap map, Input input) {
        float dx = 0f, dy = 0f, move = 1f / 32f;
        
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
        
        if (dy != 0) {
            dx = 0f;
            
            if (dy < 0) {
                dir = DOWN;
            }
            else {
                dir = UP;
            }
            animation.setCurrentAndPlay(dir);
        }
        else if (dx != 0) {
            if (dx < 0) {
                dir = LEFT;
            }
            else {
                dir = RIGHT;
            }
            animation.setCurrentAndPlay(dir);
        }
        else {
            animation.pause();
        }
        
        x += dx;
        y += dy;
    }
    
    public void render(WorldRenderer wr) {
        wr.drawCharacterSprite(animation, x, y);
    }
    
    public float getX() {
        return x;
    }
    
    public float getY() {
        return y;
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
