package abacus.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class SaveState {
	private Path path;
	private int currentState;
	
	public SaveState(String fileName) {
		this.path = Paths.get(fileName);
		this.currentState = -1;
		//this.playerX = 
	}
	
	public boolean saveData() {
		try {
			BufferedWriter writer = Files.newBufferedWriter(path, StandardCharsets.UTF_8);
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public boolean loadData() {
		try {
			Scanner reader = new Scanner(path, StandardCharsets.UTF_8.name());
			
			this.currentState = Integer.getInteger(reader.nextLine());
			
			
		} catch (IOException e) {
			return false;
		}
		return true;
	}
}
