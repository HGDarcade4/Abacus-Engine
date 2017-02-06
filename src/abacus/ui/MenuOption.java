package abacus.ui;

/* A class that contains the details for an option of
 * a menu, primarily it's label and id
 */
public class MenuOption {

	// instance variables
	private String label;
	private int id;
	
	// constructor
	public MenuOption(String label, int id) {
		this.label = label;
		this.id = id;
	}
	
	// returns the label of the option
	public String getLabel() {
		return this.label;
	}
	
	// returns the id of the option
	public int getId() {
		return this.id;
	}
}
