package abacus.editor;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

public class SpriteSheet {

    private String filename;
    private int tileWidth, tileHeight;
    private int tilesWide, tilesHigh;
    private List<BufferedImage> images;
    
    public SpriteSheet(String filename, int tileWidth, int tileHeight) throws IOException {
        this.filename = filename;
        images = new ArrayList<>();
        this.tileHeight = tileHeight;
        this.tileWidth = tileWidth;
        genSprites(filename);
    }
    
    public String getFileName() {
        return filename;
    }
    
    public BufferedImage get(int i) {
        return images.get(i);
    }
    
    public BufferedImage get(int x, int y) {
        return images.get(x + y * tilesWide);
    }
    
    public int tilesWide() { return tilesWide; }
    public int tilesHigh() { return tilesHigh; }
    
    public int tileWidth() { return tileWidth; }
    public int tileHeight() { return tileHeight; }
    
    private void genSprites(String filename) throws IOException {
        BufferedImage source = ImageIO.read(new File(filename));
        int w = tilesWide = source.getWidth() / tileWidth;
        int h = tilesHigh = source.getHeight() / tileHeight;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                images.add(source.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight));
            }
        }
    }
    
}
