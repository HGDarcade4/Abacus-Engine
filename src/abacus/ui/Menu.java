package abacus.ui;

import java.util.ArrayList;

import abacus.graphics.GameFont;
import abacus.graphics.Renderer;
import qfta.FadeTimer;

/* A class that handles a text menu with N options that
 * can be arbitrarily added
 */
public class Menu {
	
	// instance variables
	private ArrayList<MenuOption> options;
	private FadeTimer currentSelectionFader;
	private int currentSelectionIndex;
	private int startLocation;
	private int padding;
	
	// constructor
	public Menu(int startLocation, int padding) {
		this.options = new ArrayList<MenuOption>();
		this.currentSelectionFader = new FadeTimer(10, 30, 10, 30, 0);
		this.currentSelectionIndex = 0;
		this.startLocation = startLocation;
		this.padding = padding;
	}
	
	// adds an option to the menu with the given string
	// returns the id of the new option
	public int addOption(String label) {
		int index = this.options.size();
		this.options.add(new MenuOption(label, index));
		return index;
	}
	
	// renders all of the options of the menu
	public void render(Renderer renderer, GameFont font) {
		for (int index = 0; index < this.options.size(); index++) {
			int x = (int) (renderer.getWidth()/2 - font.getWidth(this.options.get(index).getLabel())/2);
			if (this.currentSelectionIndex == index) {
				font.setAlpha(this.currentSelectionFader.getAlpha());
			}
			else {
				font.setAlpha(1f);
			}
			font.draw(this.options.get(index).getLabel(), x, this.startLocation - (index * this.padding));
		}
	}
	
	// updates the fade timer of the current selection
	public void updateFadeTimer() {
		this.currentSelectionFader.update();
	}
	
	// resets the fade timer of the current selection
	public void resetFadeTimer() {
		this.currentSelectionFader.reset();
	}
	
	// returns true if the fade timer of the current selection
	// is done, false otherwise
	public boolean isDoneFadeTimer() {
		return this.currentSelectionFader.isDone();
	}
	
	// moves the current selection in the menu up by one
	public void moveSelectionUp() {
		if (this.currentSelectionIndex > 0) {
			this.currentSelectionIndex--;
			this.currentSelectionFader.reset();
		}
	}
	
	// moves the current selection in the menu down by one
	public void moveSelectionDown() {
		if (this.currentSelectionIndex < this.options.size() - 1) {
			this.currentSelectionIndex++;
			this.currentSelectionFader.reset();
		}
	}
	
	// returns the current selection of the menu
	public int getCurrentSelection() {
		return this.currentSelectionIndex;
	}
	
	// resets the current selection to zero
	public void resetCurrentSelection() {
		this.currentSelectionIndex = 0;
	}
}
