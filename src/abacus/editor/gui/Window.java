package abacus.editor.gui;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;

import abacus.editor.LevelEditor;

public class Window {

    public static final String PREFIX = "Level Editor - ";
    public static final String UNTITLED = "untitled";
    
    public static final int DEF_WIDTH = 800;
    public static final int DEF_HEIGHT = 600;
    
    private JFrame frame;
    
    public Window(JMenuBar menu, GuiComponent display) {
        frame = new JFrame(PREFIX + UNTITLED);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(DEF_WIDTH, DEF_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.add(display.getComponent());
        frame.setJMenuBar(menu);
        
        frame.setVisible(true);
    }
    
    public void animate() {
        new Thread() {
            public void run() {
                while (true) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                        	LevelEditor.tick++;
                            frame.repaint();
                        }
                    });
                    
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }
    
}
