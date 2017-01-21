/* Window.java
 * Handles the window of the game
 */

package abacus.ui;

/* Window interface to handle the window of the game 
 */
public interface Window {
	
	/* Function Descriptions */

	// Title get and set
    void setTitle(String title);
    String getTitle();
    
	// Resolution functions
    void setResolution(int width, int height);
    void setFullscreen(boolean fs);
    boolean isFullscreen();
    int getWidth();
    int getHeight();
    
	// Virtual resolution function
    void setVirtualResolution(int width, int height);
    
	// Visiblity functions
    void show();
    void hide();
    boolean isVisible();
    
}
