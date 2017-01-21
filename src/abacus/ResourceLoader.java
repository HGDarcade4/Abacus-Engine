package abacus;

import java.awt.image.BufferedImage;

import abacus.graphics.Texture;
import abacus.sound.Sound;

public interface ResourceLoader {

    Texture loadTexture(String filename);
    
    Texture createTexture(BufferedImage image);
    
    Sound loadSound(String filename);
    
}
