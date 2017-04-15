package qfta.component;

import abacus.ResourceLoader;
import abacus.gameobject.GameComponent;
import abacus.gameobject.GameComponentProperties;
import abacus.gameobject.Scene;
import abacus.graphics.GameFont;
import abacus.graphics.WorldRenderer;
import abacus.ui.Input;
import qfta.FadeTimer;
import qfta.TileMapState;

public class Dialogue extends GameComponent {
	
	private ResourceLoader loader;
	private FadeTimer fade;
	private boolean talks;
	private GameFont font;
	private GameFont inRangeFont;
	private TileMapState state;
	private boolean inRange;
	
	private float entityX;
	private float entityY;
	
	private DialogueGraph dGraph;
	
	public Dialogue(ResourceLoader loader, TileMapState state) {
		this.loader = loader;
		this.state = state;
		this.fade = new FadeTimer(10, 30, 10, 30, 0);
		this.talks = false;
		this.font = loader.getFontCreator().createBasicFont("res/font.png", 10, 12, 0xFFFFFF);
		this.inRangeFont = loader.getFontCreator().createBasicFont("res/font.png", 10, 12, 0xFF0000);
		this.font.setSize(25);
		this.inRangeFont.setSize(25);
		this.entityX = 0.0f;
		this.entityY = 0.0f;
		this.inRange = false;
	}
	
	@Override
	public void attach() {
		if (Math.random() < 0.2) {
			this.talks = true;
		}
	}

	@Override
	public GameComponent copy() {
		return new Dialogue(this.loader, this.state);
	}

	@Override
	public GameComponent load(GameComponentProperties props) {
		return new Dialogue(this.loader, this.state);
	}
	
	@Override
	public void update(Scene scene, Input input) {
		if (this.talks) {
			this.fade.update();
			if (this.fade.isDone()) {
				this.fade.reset();
			}
			
			this.entityX = this.gameObject.getTransform().x;
			this.entityY = this.gameObject.getTransform().y;
			
			if (this.gameObject.getTransform().distanceFrom(this.state.getPlayer().getTransform()) < 20) {
				this.inRange = true;
				
				if (input.getJustDownKey("spacebar")) {
					this.state.dialogueReady(this.gameObject);
				}
			}
			else {
				this.inRange = false;
			}
		}
	}
	
	@Override
	public void render(WorldRenderer wr) {
		if (this.talks) {
			if (this.inRange) {
				wr.drawText(this.inRangeFont, "!", this.entityX + 2, this.entityY + 24);
			}
			else {
				this.font.setAlpha(this.fade.getAlpha());
				wr.drawText(this.font, "!", this.entityX + 2, this.entityY + 24);
			}
		}
	}
	
	public void setTalks(boolean val) {
		this.talks = val;
	}

}