package abacus.editor.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JPanel;

import abacus.editor.LevelEditor;

public class TileChooser implements GuiComponent, MouseListener, MouseMotionListener {

    public static final Color BACKGROUND = new Color(0xDDDDDD);
    
    private LevelEditor editor;
    private JPanel panel;
    private int tileSize = 32;
    
    private int startX, endX;
    private int startY, endY;
    
    @SuppressWarnings("serial")
    public TileChooser(LevelEditor editor) {
        panel = new JPanel() {
            public void paintComponent(Graphics g) {
                draw(g);
            }
        };
        this.editor = editor;
        
        panel.addMouseListener(this);
        panel.addMouseMotionListener(this);
    }
    
    public void draw(Graphics g) {
    	updateCurrentTileSet();
        g.setColor(BACKGROUND);
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());
        
        int id = editor.currentId;
    	if (editor.tileTypes[id] == null) return;
    	
    	BufferedImage[] images = new BufferedImage[8];
    	
    	for (int x = 0; x < editor.tileTypes[id].tilesWide(); x++) {
    		for (int y = 0; y < editor.tileTypes[id].tilesHigh(); y++) {
    			editor.tileTypes[id].getDisplayImages(x, y, images);
    			g.drawImage(
                        images[0], 
                        tileSize * x,
                        tileSize * y, 
                        tileSize/2, 
                        tileSize/2, 
                        panel);
                g.drawImage(
                        images[1], 
                        tileSize * x + tileSize/2,
                        tileSize * y, 
                        tileSize/2 - 1, 
                        tileSize/2, 
                        panel);
                g.drawImage(
                        images[2], 
                        tileSize * x,
                        tileSize * y + tileSize/2, 
                        tileSize/2, 
                        tileSize/2 - 1, 
                        panel);
                g.drawImage(
                        images[3], 
                        tileSize * x + tileSize/2,
                        tileSize * y + tileSize/2, 
                        tileSize/2 - 1, 
                        tileSize/2 - 1, 
                        panel);
    		}
    	}
    	
    	for (int i = 0; i < 3; i++) {
    		g.setColor(Color.BLUE);
        	g.drawRect(
        			startX * tileSize + i*2,
        			startY * tileSize + i*2, 
        			(endX - startX + 1) * tileSize - i*4, 
        			(endY - startY + 1) * tileSize - i*4);
        	
        	g.setColor(Color.YELLOW);
        	g.drawRect(
        			startX * tileSize + 1 + i*2,
        			startY * tileSize + 1 + i*2, 
        			(endX - startX + 1) * tileSize - 2 - i*4, 
        			(endY - startY + 1) * tileSize - 2 - i*4);
    	}
    }
    
    public void updateCurrentTileSet() {
    	int id = editor.currentId;
    	if (editor.tileTypes[id] == null) return;
    	
    	
    	panel.setPreferredSize(new Dimension(
    			editor.tileTypes[id].tilesWide() * tileSize, 
    			editor.tileTypes[id].tilesHigh() * tileSize));
    	panel.revalidate();
    }
    
    public void resetSelection() {
        startX = endX = 0;
        startY = endY = 0;
        panel.repaint();
    }
    
    public void updateSelection(int x, int y) {
    	endX = x;
    	endY = y;
    	
		int tmp;
		
		if (startX > endX) {
			tmp = startX;
			startX = endX;
			endX = tmp;
		}
		
		if (startY > endY) {
			tmp = startY;
			startY = endY;
			endY = tmp;
		}
		
		startX = Math.min(editor.tileTypes[editor.currentId].tilesWide() - 1, Math.max(0, startX));
		startY = Math.min(editor.tileTypes[editor.currentId].tilesHigh() - 1, Math.max(0, startY));
		endX = Math.min(editor.tileTypes[editor.currentId].tilesWide() - 1, Math.max(0, endX));
		endY = Math.min(editor.tileTypes[editor.currentId].tilesHigh() - 1, Math.max(0, endY));
    	
		editor.currentMeta = new int[endX - startX + 1][endY - startY + 1];
		for (int xx = 0; xx < editor.currentMeta.length; xx++) {
    		for (int yy = 0; yy < editor.currentMeta[0].length; yy++) {
    			editor.currentMeta[xx][yy] = editor.tileTypes[editor.currentId].getMeta(startX + xx, startY + yy);
    		}
    	}
		
		panel.repaint();
	}

    @Override
    public JComponent getComponent() {
        return panel;
    }

	@Override
	public void mouseDragged(MouseEvent e) {
		int x = e.getX() / tileSize;
		int y = e.getY() / tileSize;
		
		updateSelection(x, y);
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		startX = endX = e.getX() / tileSize;
		startY = endY = e.getY() / tileSize;
		
		updateSelection(startX, startY);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		int x = e.getX() / tileSize;
		int y = e.getY() / tileSize;
		
		updateSelection(x, y);
	}
    
}
