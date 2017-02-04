package abacus.ui;

import java.util.ArrayList;

import abacus.graphics.GameFont;
import abacus.graphics.Renderer;
import qfta.FadeTimer;

public class Menu {
	
	private ArrayList<MenuOption> options;
	private FadeTimer currentSelectionFader;
	private int currentSelectionIndex;
	
	public Menu() {
		this.options = new ArrayList<MenuOption>();
		this.currentSelectionFader = new FadeTimer(10, 30, 10, 30, 0);
		this.currentSelectionIndex = 0;
	}
	
	public int addOption(String label) {
		int index = this.options.size();
		this.options.add(new MenuOption(label, index));
		return index;
	}
	
	public void render(Renderer renderer, GameFont font) {
		for (int index = 0; index < this.options.size(); index++) {
			int x = (int) (renderer.getWidth()/2 - font.getWidth(this.options.get(index).getLabel())/2);
			if (this.currentSelectionIndex == index) {
				font.setAlpha(this.currentSelectionFader.getAlpha());
			}
			font.draw(this.options.get(index).getLabel(), x, 0 + (index * 5));
		}
	}
	
	public void updateFadeTimer() {
		this.currentSelectionFader.update();
	}
	
	public void resetFadeTimer() {
		this.currentSelectionFader.reset();
	}
	
	public boolean isDoneFadeTimer() {
		return this.currentSelectionFader.isDone();
	}
	
	public void moveSelectionUp() {
		if (this.currentSelectionIndex > 0) {
			this.currentSelectionIndex--;
		}
	}
	
	public void moveSelectionDown() {
		if (this.currentSelectionIndex < this.options.size()) {
			this.currentSelectionIndex++;
		}
	}
	
	public int getCurrentSelection() {
		return this.currentSelectionIndex;
	}
	
}
