package abacus.ui;

public class MenuOption {

	private String label;
	private int id;
	
	public MenuOption(String label, int id) {
		this.label = label;
		this.id = id;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public int getId() {
		return this.id;
	}
}
