package abacus.awt;

import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;

/*
 * Creates images.
 * 
 * You never work directly with this class.
 */
public final class ImageFactory {

    public static boolean volatileImages = true;
    
    // create an image with width [w] and height [h], with either 
    // alpha transparency or bit-mask transparency
    public static Image create(int w, int h, boolean bitmask) {
        if (bitmask) {
            return createBitmasked(w, h);
        }
        else {
            return create(w, h);
        }
    }
    
    // create an alpha transparent image
    public static Image create(int w, int h) {
        return create(w, h, BufferedImage.TRANSLUCENT);
    }
    
    // create a bit-masked image
    private static Image createBitmasked(int w, int h) {
        return create(w, h, BufferedImage.BITMASK);
    }
    
    public static BufferedImage createBufferedImage(int w, int h) {
        return createBufferedImage(w, h, BufferedImage.TRANSLUCENT);
    }
    
    // create a buffered image
    public static BufferedImage createBufferedImage(int w, int h, int type) {
        return GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration()
                .createCompatibleImage(w, h, type);
    }
    
    // create an image with a certain type of transparency
    public static Image createVolatileImage(int w, int h, int type) {
        return GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration()
                .createCompatibleVolatileImage(w, h, type);
    }
    
    public static Image create(int w, int h, int type) {
        if (volatileImages) {
            return createVolatileImage(w, h, type);
        }
        else {
            return createBufferedImage(w, h, type);
        }
    }
    
}
