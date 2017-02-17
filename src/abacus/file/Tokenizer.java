package abacus.file;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Tokenizer {

    private String source;
    private List<String> tokens;
    private int index;
    
    public Tokenizer(String filename) {
        tokens = new ArrayList<>();
        try {
            Scanner file = new Scanner(new FileInputStream(filename));
            collectSource(file);
            fillTokens();
            file.close();
        }
        catch (Exception e) {
            System.out.println("Problem tokening file " + filename);
            source = "";
        }
        index = 0;
    }
    
    public boolean hasNext() {
        return index < tokens.size();
    }
    
    public String next() {
        return hasNext() ? tokens.get(index++) : "";
    }
    
    public String peek() {
        return hasNext() ? tokens.get(index) : "";
    }
    
    private void collectSource(Scanner file) {
        StringBuilder sb = new StringBuilder();
        
        while (file.hasNext()) {
            String line = file.nextLine();
            if (!line.startsWith(";")) {
                sb.append(line + " \n");
            }
        }
        
        source = sb.toString();
    }
    
    private void fillTokens() {
        Scanner in = new Scanner(source);
        
        while (in.hasNext()) {
           tokens.add(in.next());
        }
        
        in.close();
    }
    
}
