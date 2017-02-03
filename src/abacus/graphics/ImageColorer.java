package abacus.graphics;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class ImageColorer {

    private BufferedImage image;
    private Map<Integer, Integer> replace;
    
    public ImageColorer(BufferedImage image) {
        this.image = image;
        
        replace = new HashMap<>();
    }
    
    public void replace(int col, int newCol) {
        replace.put(col, newCol);
    }
    
    public BufferedImage recolorBufferedImage() {
        Graphics2D g = image.createGraphics();
        
        for (int y = 0; y < image.getWidth(); y++) {
            for (int x = 0; x < image.getHeight(); x++) {
                int col = image.getRGB(x, y);
                
                Integer repl = replace.get(col);
                
                if (repl != null) {
                    image.setRGB(x, y, repl.intValue());
                }
            }
        }
        
        g.dispose();
        return image;
    }
    
}
