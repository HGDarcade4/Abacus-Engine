package qfta.component;

import abacus.gameobject.Collider;
import abacus.gameobject.GameComponent;
import abacus.gameobject.GameComponentProperties;
import abacus.gameobject.Scene;
import abacus.tile.TileBody;
import abacus.ui.Input;

public class CharacterMovement extends GameComponent {

    private static final int DIR_UP = 1;
    private static final int DIR_DOWN = 0;
    private static final int DIR_LEFT = 2;
    private static final int DIR_RIGHT = 3;
    
    public boolean randomDir = false;
    public float moveSpeed = 1f;
    public int dir = DIR_DOWN;
    public boolean moving = false;
    
    private float dx, dy;
    
    public CharacterMovement(float speed) {
        moveSpeed = speed;
    }
    
    public CharacterMovement copy() {
    	CharacterMovement m = new CharacterMovement(moveSpeed);
    	
    	m.dir = dir;
    	m.moving = moving;
    	m.dx = dx;
    	m.dy = dy;
    	m.randomDir = randomDir;
    	
    	return m;
    }
    
    public CharacterMovement load(GameComponentProperties props) {
        CharacterMovement c = copy();
        if (props.containsNumber("moveSpeed")) {
            c.moveSpeed = props.getNumber("moveSpeed");
        }
        if (props.containsBoolean("randomDir")) {
            c.randomDir = props.getBoolean("randomDir");
        }
        return c;
    }
    
    @Override
    public void attach() {
        dir = (int)(Math.random() * 4);
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
        
        move(false, false, false, false);
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
