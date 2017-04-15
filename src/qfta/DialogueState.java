package qfta;

import abacus.GameState;
import abacus.ResourceLoader;
import abacus.graphics.Renderer;
import abacus.ui.Input;
import qfta.component.Dialogue;

public class DialogueState extends GameState {
	
	private TileMapState state;
	
	public DialogueState(TileMapState tms) {
		this.state = tms;
	}

	@Override
	public void init(ResourceLoader loader) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enter() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Input input) {
		if (input.getJustDownKey("spacebar")) {
			this.state.getTalker().get(Dialogue.class).setTalks(false);
			this.popState();
		}
	}

	@Override
	public void render(Renderer renderer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}

}
