package abacus.awt;

import java.awt.Canvas;
import java.awt.DisplayMode;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import abacus.ui.Input;
import abacus.ui.Window;

/*
 * Java2D implementation of Window
 * 
 * You never work directly with this class.
 */
public class AwtWindow implements Window {

    // the window
    public static Frame frame;
    // the display
    private Canvas canvas;
    private boolean isVisible;
    // keyboard input
    private Input input;
    // Java2D renderer
    private AwtCanvasRenderer renderer;
    // is window full screen
    private boolean fullScreen;
    // true resolution, NOT virtual resolution
    private int width, height;
    
    // ctor, argument on whether to use canvas renderer or frame buffer renderer
    public AwtWindow(boolean frameBuffer) {
        input = new Input();
        
        WindowEvents listener = new WindowEvents();
        
        frame = new Frame();
        frame.addWindowListener(listener);
        
        canvas = new Canvas();
        canvas.addKeyListener(listener);
        frame.add(canvas);
        
        if (frameBuffer) {
            renderer = new AwtBufferRenderer(canvas, 320, 224);
        } 
        else {
            renderer = new AwtCanvasRenderer(canvas, 320, 224);
        }
        
        isVisible = false;
        fullScreen = false;
        
        // remove the cursor
//        frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "no pointer"));
    }
    
    // returns reference to input
    public Input getInput() {
        return input;
    }
    
    // returns reference to renderer
    public AwtCanvasRenderer getRenderer() {
        return renderer;
    }
    
    // sets title of window
    @Override
    public void setTitle(String title) {
        frame.setTitle(title);
    }
    
    // gets the title of the window
    @Override
    public String getTitle() {
        return frame.getTitle();
    }
    
    // sets whether the window is full screen
    // TODO should be correct, but probably should check this a bit more
    @Override
    public void setFullscreen(boolean fs) {
        fullScreen = fs;
        
        GraphicsConfiguration gc = frame.getGraphicsConfiguration();
        GraphicsDevice dev = gc.getDevice();
        DisplayMode dm = dev.getDisplayMode();
        
        if (fullScreen) {
            frame.dispose();
            frame.setVisible(false);
            frame.setUndecorated(true);
            frame.setResizable(false);
            frame.setSize(dm.getWidth(), dm.getHeight());
            frame.setLocation(gc.getBounds().x, gc.getBounds().y);
//            dev.setFullScreenWindow(frame);
            
            if (isVisible) {
                show();
            }
        }
        else {
//            dev.setFullScreenWindow(null);
            frame.dispose();
            frame.setUndecorated(false);
            frame.setResizable(true);
            frame.setUndecorated(false);
            setResolution(width, height);
            
            if (isVisible) {
                show();
            }
        }
    }
    
    // returns true if window is in full screen mode
    @Override
    public boolean isFullscreen() {
        return fullScreen;
    }
    
    // sets dimensions of the display, NOT virtual resolution, 
    // also NOT window size, since there can be extra border and title pixels
    // you do that in the renderer
    @Override
    public void setResolution(int width, int height) {
        this.width = width;
        this.height = height;
        canvas.setSize(width, height);
        frame.pack();
        
        GraphicsConfiguration gc = frame.getGraphicsConfiguration();
        frame.setLocation(
                (int)gc.getBounds().getCenterX() - frame.getWidth()/2, 
                (int)gc.getBounds().getCenterY() - frame.getHeight()/2);
        
//        frame.setLocationRelativeTo(null);
    }
    
    // width of the display
    @Override
    public int getWidth() {
        return canvas.getWidth();
    }
    
    // height of the display
    @Override
    public int getHeight() {
        return canvas.getHeight();
    }
    
    // sets the virtual resolution
    @Override
    public void setVirtualResolution(int width, int height) {
        renderer.setVirtualResolution(width, height);
    }
    
    // displays the window
    @Override
    public void show() {
        frame.setVisible(true);
        isVisible = true;
        
        canvas.setFocusable(true);
        canvas.requestFocus();
    }
    
    // hides the window
    @Override
    public void hide() {
        frame.setVisible(false);
        isVisible = false;
    }
    
    // is the window shown
    @Override
    public boolean isVisible() {
        return isVisible;
    }
    
    // this class handles input events
    private class WindowEvents implements WindowListener, KeyListener {

        @Override
        public void windowActivated(WindowEvent e) {}

        @Override
        public void windowClosed(WindowEvent e) {}

        // happens when you click the 'X'
        @Override
        public void windowClosing(WindowEvent e) {
            hide();
        }

        @Override
        public void windowDeactivated(WindowEvent e) {}

        @Override
        public void windowDeiconified(WindowEvent e) {}

        @Override
        public void windowIconified(WindowEvent e) {}

        @Override
        public void windowOpened(WindowEvent e) {}

        // when you type a key
        @Override
        public void keyPressed(KeyEvent e) {
            input.setKey(e.getKeyCode(), true);
        }

        // when you release a key
        @Override
        public void keyReleased(KeyEvent e) {
            input.setKey(e.getKeyCode(), false);
        }

        @Override
        public void keyTyped(KeyEvent e) {}
        
    }

}
