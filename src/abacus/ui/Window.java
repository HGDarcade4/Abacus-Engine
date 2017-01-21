package abacus.ui;

public interface Window {

    void setTitle(String title);
    String getTitle();
    
    void setResolution(int width, int height);
    void setFullscreen(boolean fs);
    boolean isFullscreen();
    int getWidth();
    int getHeight();
    
    void setVirtualResolution(int width, int height);
    
    void show();
    void hide();
    boolean isVisible();
    
}
