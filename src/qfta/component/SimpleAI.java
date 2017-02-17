package qfta.component;

import abacus.gameobject.GameComponent;
import abacus.gameobject.GameComponentProperties;
import abacus.gameobject.Scene;
import abacus.ui.Input;

public class SimpleAI extends GameComponent {

    public static final int MOVE_UP = 0;
    public static final int MOVE_DOWN = 1;
    public static final int MOVE_LEFT = 2;
    public static final int MOVE_RIGHT = 3;
    public static final int MOVE_NONE = 4;
    
    public float changeMove = 0.05f;
    public int dir = 0;
    
    @Override
    public SimpleAI copy() {
        SimpleAI ai = new SimpleAI();
        ai.dir = dir;
        return ai;
    }
    
    public SimpleAI load(GameComponentProperties props) {
        return copy();
    }
    
    @Override
    public void update(Scene scene, Input input) {
        if (Math.random() < changeMove) {
            dir = (int)(Math.random() * 5);
        }
        
        CharacterMovement move = gameObject.get(CharacterMovement.class);
        if (move == null) return;
        
        switch (dir) {
        case MOVE_UP:
            move.move(true, false, false, false);
            break;
        case MOVE_DOWN:
            move.move(false, true, false, false);
            break;
        case MOVE_LEFT:
            move.move(false, false, true, false);
            break;
        case MOVE_RIGHT:
            move.move(false, false, false, true);
            break;
        default:
            move.move(false, false, false, false);
            break;
        }
    }

}
