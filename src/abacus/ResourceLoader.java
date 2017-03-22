/* ResourceLoader.java
 * This interface handles loading resources
 */

package abacus;

import java.awt.image.BufferedImage;

import abacus.graphics.FontCreator;
import abacus.graphics.Texture;
import abacus.sound.Sound;

/* Resource loader interface for resource loaders 
 */
public interface ResourceLoader {

	/* Function Definitions */
    Texture loadTexture(String filename);
    Texture createTexture(BufferedImage image);
    Texture colorize(Texture tex, int[] replace);
    
    Sound loadSound(String filename);
    FontCreator getFontCreator();
}
