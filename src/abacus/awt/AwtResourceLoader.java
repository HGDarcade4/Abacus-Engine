package abacus.awt;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import abacus.ResourceLoader;
import abacus.graphics.Texture;
import abacus.sound.Sound;
import abacus.sound.SoundManager;

public class AwtResourceLoader implements ResourceLoader {

    public AwtResourceLoader() {
        
    }

    @Override
    public Texture loadTexture(String filename) {
        BufferedImage image = null;
        
        try {
            image = ImageIO.read(new File(filename));
        }
        catch (Exception e) {
            System.out.println("Could not load image from " + filename);
        }
        
        return createTexture(image);
    }
    
    @Override
    public Texture createTexture(BufferedImage image) {
        return new ImageSprite(image);
    }

    @Override
    public Sound loadSound(String file) {
        return SoundManager.loadSound(file, file);
    }
    
}
