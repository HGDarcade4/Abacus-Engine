package qfta;

import abacus.GameState;
import abacus.ResourceLoader;
import abacus.graphics.GameFont;
import abacus.graphics.Renderer;
import abacus.graphics.Sprite;
import abacus.ui.Input;
import qfta.component.Dialogue;

public class DialogueState extends GameState {
	
	private TileMapState state;
	private Sprite textbox;
	private GameFont font;
	
	public DialogueState(TileMapState tms) {
		this.state = tms;
	}

	@Override
	public void init(ResourceLoader loader) {
		this.textbox = loader.loadTexture("res/textBox.png").getSprite();
		this.font = loader.getFontCreator().createBasicFont("res/font.png", 10, 12, 0xFFFFFF);
		this.font.setSize(20);
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
		this.textbox.draw(20, 20, renderer.getWidth() - 40, renderer.getHeight() / 2 - 40, 1, 100);
		String line = this.state.getTalker().get(Dialogue.class).getDialogue().getCurrent().getContent();
		
		String[] words = line.split(" ");
		int y = 60;
		
		String current = "";
		for (int index = 0; index < words.length; index++) {
			if (font.getWidth(current + words[index]) > renderer.getWidth() - 70) {
				font.draw(current, 50, renderer.getHeight() / 2 - y);
				current = "";
				y += 20;
			}
			else {
				current += words[index] + " ";
			}
		}
		
		font.draw(current, 50, renderer.getHeight() / 2 - y);
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
