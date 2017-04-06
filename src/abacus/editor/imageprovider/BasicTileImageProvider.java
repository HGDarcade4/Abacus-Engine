package abacus.editor.imageprovider;

import java.awt.image.BufferedImage;

import abacus.editor.SpriteSheet;
import abacus.editor.TileMap;

public class BasicTileImageProvider implements TileImageProvider {

    private SpriteSheet sheet;
    
    public BasicTileImageProvider(SpriteSheet sheet) {
        this.sheet = sheet;
    }
    
    @Override
    public void getImages(TileMap map, int x, int y, int layer, BufferedImage[] images) {
        int meta = map.getLayer(layer).getTile(x, y).tileMeta;
        int sx = meta % tilesWide();
        int sy = meta / tilesWide();
        
        getDisplayImages(sx, sy, images);
    }

	@Override
	public int tilesWide() {
		return sheet.tilesWide() / 2;
	}

	@Override
	public int tilesHigh() {
		return sheet.tilesHigh() / 2;
	}

	@Override
	public void getDisplayImages(int sx, int sy, BufferedImage[] images) {
		images[0] = sheet.get(sx*2, sy*2);
        images[1] = sheet.get(sx*2 + 1, sy*2); 
        images[2] = sheet.get(sx*2, sy*2 + 1); 
        images[3] = sheet.get(sx*2 + 1, sy*2 + 1);
	}

	@Override
	public int getMeta(int tileX, int tileY) {
		return tileX + tileY * tilesWide();
	}

}
