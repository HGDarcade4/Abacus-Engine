package qfta.component;

import java.util.ArrayList;

import abacus.ResourceLoader;
import abacus.gameobject.GameComponent;
import abacus.gameobject.GameComponentProperties;
import abacus.gameobject.Scene;
import abacus.sound.Sound;
import abacus.ui.Input;

public class WalkSound extends GameComponent {

	private ArrayList<Sound> sounds = new ArrayList<>();
	private ResourceLoader loader;
	
	public WalkSound(ResourceLoader loader) {
		this.loader = loader;
//		sounds.add(loader.loadSound("res/Jump.wav"));
//		sounds.add(loader.loadSound("res/sound_effect.wav"));
//		sounds.add(loader.loadSound("res/Angryslime.wav"));
//		sounds.add(loader.loadSound("res/Bowshot.wav"));
//		sounds.add(loader.loadSound("res/Button select.wav"));
//		sounds.add(loader.loadSound("res/button_select.wav"));
//		sounds.add(loader.loadSound("res/Dying.wav"));
//		sounds.add(loader.loadSound("res/Dying2.wav"));
//		sounds.add(loader.loadSound("res/Dying3.wav"));
//		sounds.add(loader.loadSound("res/falling.wav"));
//		sounds.add(loader.loadSound("res/pitfall.wav"));
		sounds.add(loader.loadSound("res/walk.wav"));
	}
	

	@Override
	public GameComponent copy() {
		// TODO Auto-generated method stub
		return new WalkSound(loader);
	}

	@Override
	public GameComponent load(GameComponentProperties props) {
		// TODO Auto-generated method stub
		return new WalkSound(loader);
	}

	@Override
	public void update(Scene scene, Input input) {

		CharacterMovement move = gameObject.get(CharacterMovement.class);

		if (move == null) {
			return;
		}
		
		//only play movement sounds when moving (duh)
		if (move.moving) {
			
			//just return if a sound is already playing
			for (int i = 0; i < sounds.size(); i++) {
				if (sounds.get(i).isRunning()){
					return;
				}
			}
			
			//choose a random sound
			int index = (int) (Math.random()*sounds.size());
			sounds.get(index).playIfNotRunning();
		}
		
	}

}
