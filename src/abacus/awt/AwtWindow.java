package abacus.awt;

import java.awt.Canvas;
import java.awt.Frame;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;

import abacus.ui.Input;
import abacus.ui.Window;

public class AwtWindow implements Window {

    private Frame frame;
    private Canvas canvas;
    private boolean canRender;
    private Input input;
    private AwtCanvasRenderer renderer;
    private boolean fullScreen;
    private int width, height;
    
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
        
        canRender = false;
        fullScreen = false;
        
        frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "no pointer"));
    }
    
    public Input getInput() {
        return input;
    }
    
    public AwtCanvasRenderer getRenderer() {
        return renderer;
    }
    
    @Override
    public void setTitle(String title) {
        frame.setTitle(title);
    }
    
    @Override
    public String getTitle() {
        return frame.getTitle();
    }
    
    @Override
    public void setFullscreen(boolean fs) {
        fullScreen = fs;
        
        GraphicsDevice dev = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        
        if (fullScreen) {
            frame.dispose();
            frame.setExtendedState(Frame.MAXIMIZED_BOTH);
            frame.setVisible(false);
            frame.setUndecorated(true);
            frame.setResizable(false);
            dev.setFullScreenWindow(frame);
        }
        else {
            dev.setFullScreenWindow(null);
            frame.dispose();
            frame.setUndecorated(false);
            frame.setResizable(true);
            frame.setUndecorated(false);
            setResolution(width, height);
            
            if (canRender) {
                show();
            }
        }
    }
    
    @Override
    public boolean isFullscreen() {
        return fullScreen;
    }
    
    @Override
    public void setResolution(int width, int height) {
        this.width = width;
        this.height = height;
        canvas.setSize(width, height);
        frame.pack();
        frame.setLocationRelativeTo(null);
    }
    
    @Override
    public int getWidth() {
        return canvas.getWidth();
    }
    
    @Override
    public int getHeight() {
        return canvas.getHeight();
    }
    
    @Override
    public void setVirtualResolution(int width, int height) {
        renderer.setVirtualResolution(width, height);
    }
    
    @Override
    public void show() {
        frame.setVisible(true);
        canRender = true;
        
        canvas.setFocusable(true);
        canvas.requestFocus();
    }
    
    @Override
    public void hide() {
        frame.setVisible(false);
        canRender = false;
    }
    
    @Override
    public boolean isVisible() {
        return canRender;
    }
    
    private class WindowEvents implements WindowListener, KeyListener {

        @Override
        public void windowActivated(WindowEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void windowClosed(WindowEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void windowClosing(WindowEvent e) {
            hide();
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void windowIconified(WindowEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void windowOpened(WindowEvent e) {
            // TODO Auto-generated method stub
            
        }

        @Override
        public void keyPressed(KeyEvent e) {
            input.setKey(e.getKeyCode(), true);
        }

        @Override
        public void keyReleased(KeyEvent e) {
            input.setKey(e.getKeyCode(), false);
        }

        @Override
        public void keyTyped(KeyEvent e) {
            // TODO Auto-generated method stub
            
        }
        
    }

}
