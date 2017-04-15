package qfta.component;

import java.util.List;

public class DialogueNode {

	private String content;
	private DialogueNode next;
	private boolean choice;
	private List<String> choices;

	public DialogueNode(String content) {
		this.content = content;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public DialogueNode next() {
		return this.next;
	}
	
	public boolean hasChoices() {
		return this.choice;
	}
	
	public void setChoices(boolean val) {
		this.choice = val;
	}
}
