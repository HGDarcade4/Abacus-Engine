package qfta.component;

import abacus.gameobject.Collider;
import abacus.gameobject.GameComponent;
import abacus.gameobject.Scene;
import abacus.tile.TileBody;
import abacus.ui.Input;

public class Movement extends GameComponent {

    private static final int DIR_UP = 1;
    private static final int DIR_DOWN = 0;
    private static final int DIR_LEFT = 2;
    private static final int DIR_RIGHT = 3;
    
    public float moveSpeed = 1f;
    public int dir = DIR_DOWN;
    public boolean moving = false;
    
    private float dx, dy;
    
    public Movement(float speed) {
        moveSpeed = speed;
    }
    
    @Override
    public void update(Scene scene, Input input) {
        if (!gameObject.has(Collider.class)) return;
        
        TileBody body = gameObject.get(Collider.class).tileBody;
        
        moving = true;
        
        if (dy != 0 && dx == 0) {
            if (dy < 0) {
                dir = DIR_DOWN;
            }
            else {
                dir = DIR_UP;
            }
            
        }
        else if (dx != 0 && dy == 0) {
            if (dx < 0) {
                dir = DIR_LEFT;
            }
            else {
                dir = DIR_RIGHT;
            }
        }
        else if (dx == 0 && dy == 0) {
            moving = false;
        }
        else {
            dy *= 0.7f;
            dx *= 0.7f;
        }
        
        body.setVelX(dx);
        body.setVelY(dy);
    }
    
    public void move(boolean up, boolean down, boolean left, boolean right) {
        dx = 0f;
        dy = 0f;
        
        if (up) dy += moveSpeed;
        if (down) dy -= moveSpeed;
        if (left) dx -= moveSpeed;
        if (right) dx += moveSpeed;
    }
    
}
