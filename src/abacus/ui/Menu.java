package abacus.ui;

import java.util.ArrayList;

public class Menu {
	
	private ArrayList<Option> options;
	
	public Menu() {
		this.options = new ArrayList<Option>();
	}
	
	public int addOption(String label) {
		int size = this.options.size();
		this.options.add(new Option(label, size));
		return size;
	}
	
}
