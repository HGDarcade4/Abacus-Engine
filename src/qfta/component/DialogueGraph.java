package qfta.component;

import java.util.List;

public class DialogueGraph {

	private DialogueNode current;
	private DialogueNode start;
	
	public DialogueGraph() {
		this.current = null;
		this.start = null;
	}
	
	public void addNewNode(DialogueNode node) {
		if (this.start == null) {
			this.start = node;
			this.current = this.start;
		}
		else {
			this.current.setNext(node);
			this.current = this.current.getNext();
		}
	}
	
	public void resetTree() {
		this.current = this.start;
	}
	
	public DialogueNode getCurrent() {
		return this.current;
	}
	
	public int moveForwards(int num) {
		int index;
		for (index = 0; index < num; index++) {
			if (this.current.getNext() == null) break;
			
			this.current = this.current.getNext();
		}
		
		return index;
	}
	
	public int moveBackwards(int num) {
		int index;
		for (index = 0; index < num; index++) {
			if (this.current.getPrevious() == null) break;
			
			this.current = this.current.getPrevious();
		}
		
		return index;
	}
	
}