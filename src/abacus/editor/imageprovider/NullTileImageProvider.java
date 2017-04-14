package abacus.editor.imageprovider;

import java.awt.image.BufferedImage;
import java.io.PrintWriter;

import abacus.editor.TileMap;

public class NullTileImageProvider implements TileImageProvider {

	@Override
	public void getImages(TileMap map, int x, int y, int layer, BufferedImage[] images) {
		images[0] = null;
		images[1] = null;
		images[2] = null;
		images[3] = null;
	}

	@Override
	public int tilesWide() {
		return 1;
	}

	@Override
	public int tilesHigh() {
		return 1;
	}

	@Override
	public void getDisplayImages(int tileX, int tileY, BufferedImage[] images) {
		images[0] = null;
		images[1] = null;
		images[2] = null;
		images[3] = null;
	}

	@Override
	public int getMeta(int tileX, int tileY) {
		return 0;
	}
	
	@Override
    public void saveData(PrintWriter out) {
        out.print("null");
    }

}
