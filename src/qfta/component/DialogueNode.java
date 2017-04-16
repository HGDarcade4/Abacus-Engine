package qfta.component;

import java.util.List;

public class DialogueNode {

	private String content;
	private DialogueNode next;
	private DialogueNode previous;
	private boolean choice;
	private List<String> choices;

	public DialogueNode(String content) {
		this.content = content;
		this.next = null;
		this.previous = null;
	}

	public DialogueNode(String content, DialogueNode next, DialogueNode previous) {
		this.content = content;
		this.next = next;
		this.previous = previous;
	}
	
	public String getContent() {
		return this.content;
	}
	
	public DialogueNode getNext() {
		return this.next;
	}
	
	public void setNext(DialogueNode node) {
		this.next = node;
	}
	
	public DialogueNode getPrevious() {
		return this.previous;
	}
	
	public void setPrevious(DialogueNode node) {
		this.previous = node;
	}
	
	public boolean hasChoices() {
		return this.choice;
	}
	
	public void setChoices(boolean val) {
		this.choice = val;
	}
}
