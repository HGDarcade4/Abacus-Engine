package qfta;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import abacus.ResourceLoader;
import abacus.graphics.Texture;

/*
 * IGNORE THIS I'LL WORK ON IT LATER
 * 
 * although it might stay like this, not sure yet
 */
public final class Resource {

    private static final Map<String, Texture> textures = new HashMap<>();
    private static ResourceLoader loader;
    
    private Resource() {}
    
    public static void setLoader(ResourceLoader loader) {
        Resource.loader = loader;
    }
    
    public static Texture loadTexture(String file) {
        Texture tex = textures.get(file);
        
        if (tex == null) {
            tex = loader.loadTexture(file);
            registerTexture(file, tex);
        }
        
        return tex;
    }
    
    public static Texture loadTexture(BufferedImage image) {
        return loader.createTexture(image);
    }
    
    private static void registerTexture(String file, Texture tex) {
        textures.put(file, tex);
    }
    
}
