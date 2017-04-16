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
	private PremadeDialogues graphs;
	
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
		this.graphs = new PremadeDialogues();
	}
	
	@Override
	public void attach() {
		if (Math.random() < 0.2) {
			this.talks = true;
			
			//if (Math.random() < 0.8) {
				this.dGraph = this.graphs.getRandomSimpleDialogue();
			//}
			//else {
				//this.dGraph = this.graphs.getRandomComplexDialogue();
			//}
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
	
	public DialogueGraph getDialogue() {
		return this.dGraph;
	}

}

class PremadeDialogues {
	
	private DialogueGraph[] simpleDialogues = new DialogueGraph[10];
	private DialogueGraph[] complexDialogues = new DialogueGraph[10];
	
	public PremadeDialogues() {
		for (int index = 0; index < 10; index++) {
			simpleDialogues[index] = new DialogueGraph();
		}
		
		simpleDialogues[0].addNewNode(new DialogueNode("WUBBA LUBBA DUB DUB!"));
		simpleDialogues[1].addNewNode(new DialogueNode("Be careful in the caves. There's a lot of dangerous things in there!"));
		simpleDialogues[2].addNewNode(new DialogueNode("Ohh yeah, you gotta get Schwifty"));
		simpleDialogues[3].addNewNode(new DialogueNode("I like standing by the water. It's just so peaceful"));
		simpleDialogues[4].addNewNode(new DialogueNode("I heard some creepy sounds in the cave. Be careful if you go in there!"));
		simpleDialogues[5].addNewNode(new DialogueNode("It's dangerous to go alone... But I already gave a sword to the last hero that came by. Sorry"));
		simpleDialogues[6].addNewNode(new DialogueNode("Nothing is true. Everything is permitted"));
		simpleDialogues[7].addNewNode(new DialogueNode("You ever wonder why we're here?"));
		simpleDialogues[8].addNewNode(new DialogueNode("History is written by the victor"));
		simpleDialogues[9].addNewNode(new DialogueNode("A hero need not speak. When he is gone, the world will speak for him"));
	}
	
	public DialogueGraph getRandomSimpleDialogue() {
		return simpleDialogues[(int) (Math.random() * 10)];
	}

	public DialogueGraph getRandomComplexDialogue() {
		return null;
	}
	
}