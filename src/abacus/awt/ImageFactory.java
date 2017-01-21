package abacus.awt;

import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.image.BufferedImage;

public final class ImageFactory {

    public static Image create(int w, int h, boolean bitmask) {
        if (bitmask) {
            return createBitmasked(w, h);
        }
        else {
            return create(w, h);
        }
    }
    
    public static Image create(int w, int h) {
        return create(w, h, BufferedImage.TRANSLUCENT);
    }
    
    private static Image createBitmasked(int w, int h) {
        return create(w, h, BufferedImage.BITMASK);
    }
    
    private static Image create(int w, int h, int type) {
        return GraphicsEnvironment
                .getLocalGraphicsEnvironment()
                .getDefaultScreenDevice()
                .getDefaultConfiguration()
                .createCompatibleVolatileImage(w, h, type);
    }
    
}
