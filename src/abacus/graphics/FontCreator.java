package abacus.graphics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import abacus.ResourceLoader;

/*
 * Use this to create fonts. It allows you to set the color of a font
 */
public class FontCreator {

    private ResourceLoader loader;
    
    public FontCreator(ResourceLoader loader) {
        this.loader = loader;
    }
    
    // create a font in a specific RGB hex color
    public GameFont createBasicFont(String file, int width, int height, int color) {
        return createBasicFont(file, width, height, color, 1.0f, 0, 0.0f);
    }
    
    // create a font in a specific RGB hex color and a background color
    public GameFont createBasicFont(String file, int width, int height, int color, float colorAlpha, int back, float backAlpha) {
        GameFont font = new EmptyFont();
        
        Texture texture = loader.loadTexture(file);
        
        if (texture == null) {
            return null;
        }
        
        back = (back & 0xFFFFFF) | (int)(backAlpha * 255) << 24;
        color = (color & 0xFFFFFF) | (int)(colorAlpha * 255) << 24;
        
        BufferedImage image = texture.getBufferedImageCopy();
        Graphics2D g = image.createGraphics();
        g.setColor(Color.BLACK);
        g.setXORMode(new Color(color));
        g.fillRect(0, 0, image.getWidth(), image.getHeight());
        // fill in any transparency with [back]
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                if ((image.getRGB(x, y) & 0xFF000000) == 0xFF000000) {
                    image.setRGB(x, y, color);
                }
                else {
                    image.setRGB(x, y, back);
                }
            }
        }
        g.dispose();
        
        Texture newTex = loader.createTexture(image);
        SpriteSheet sheet = new SpriteSheet(newTex, width, height);
        font = new BasicFont(sheet);
        
        return font;
    }
    
}
