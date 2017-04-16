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
		Sound s = loader.loadSound("res/walk.wav");
		s.setVolume(1.0f);
		sounds.add(s);
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
