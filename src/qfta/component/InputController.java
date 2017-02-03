package qfta.component;

import java.awt.event.KeyEvent;

import abacus.gameobject.GameComponent;
import abacus.gameobject.Scene;
import abacus.ui.Input;

public class InputController extends GameComponent {

	public InputController copy() {
		return new InputController();
	}
	
    @Override
    public void update(Scene scene, Input input) {
        if (!gameObject.has(CharacterMovement.class)) return;
        
        gameObject.get(CharacterMovement.class).move(
                input.getKey(KeyEvent.VK_UP),
                input.getKey(KeyEvent.VK_DOWN),
                input.getKey(KeyEvent.VK_LEFT),
                input.getKey(KeyEvent.VK_RIGHT));
    }
    
}
