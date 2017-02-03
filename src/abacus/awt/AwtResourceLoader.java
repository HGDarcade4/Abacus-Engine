package abacus.awt;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import abacus.ResourceLoader;
import abacus.graphics.FontCreator;
import abacus.graphics.ImageColorer;
import abacus.graphics.Texture;
import abacus.sound.Sound;
import abacus.sound.SoundManager;

/*
 * Resource loader for Java2D
 * 
 * You never work directly with this class.
 */
public class AwtResourceLoader implements ResourceLoader {

    private FontCreator fontCreator;
    
    public AwtResourceLoader() {
        fontCreator = new FontCreator(this);
    }
    
    // TODO keep track of previously loaded images
    // load a texture from a file
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
    public Texture colorize(Texture texture, int[] replace) {
        ImageColorer imgcol = new ImageColorer(texture.getBufferedImageCopy());
        
        for (int i = 0; i < replace.length - 1; i += 2) {
            imgcol.replace(replace[i], replace[i + 1]);
        }
        
        return createTexture(imgcol.recolorBufferedImage());
    }
    
    // creates a texture from a buffered image
    // this allows you to edit images before using them as textures
    @Override
    public Texture createTexture(BufferedImage image) {
        return new ImageSprite(image);
    }

    // loads a sound file
    @Override
    public Sound loadSound(String file) {
        return SoundManager.loadSound(file, file);
    }
    
    // return font creation class
    @Override
    public FontCreator getFontCreator() {
        return fontCreator;
    }
    
}
