package abacus.editor.imageprovider;

import static abacus.tile.ConnectionTiles.getCode;

import java.awt.image.BufferedImage;

import abacus.editor.SpriteSheet;
import abacus.editor.Tile;
import abacus.editor.TileMap;

public class ConnectedTileImageProvider implements TileImageProvider {

	public static final int UP_LEFT = 0;
    public static final int UP_RIGHT = 1;
    public static final int DOWN_LEFT = 2;
    public static final int DOWN_RIGHT = 3;
    
    public static final int EDGE_NONE = 0;
    public static final int EDGE_VERT = 1;
    public static final int EDGE_HORIZ = 2;
    public static final int EDGE_BOTH = 3;
    public static final int EDGE_CORNER = 4;
    
    public static final int TILE_PARTS = 4;
    public static final int EDGE_PARTS = 5;
	
    private static int offX[][], offY[][];
    
    static {
    	offX = new int[4][5];
    	offY = new int[4][5];
    	
		offX[UP_LEFT][EDGE_NONE] = 2;
		offX[UP_LEFT][EDGE_HORIZ] = 0;
		offX[UP_LEFT][EDGE_VERT] = 2;
		offX[UP_LEFT][EDGE_BOTH] = 0;
		offX[UP_LEFT][EDGE_CORNER] = 2;

		offX[UP_RIGHT][EDGE_NONE] = 1;
		offX[UP_RIGHT][EDGE_HORIZ] = 3;
		offX[UP_RIGHT][EDGE_VERT] = 1;
		offX[UP_RIGHT][EDGE_BOTH] = 3;
		offX[UP_RIGHT][EDGE_CORNER] = 3;

		offX[DOWN_LEFT][EDGE_NONE] = 2;
		offX[DOWN_LEFT][EDGE_HORIZ] = 0;
		offX[DOWN_LEFT][EDGE_VERT] = 2;
		offX[DOWN_LEFT][EDGE_BOTH] = 0;
		offX[DOWN_LEFT][EDGE_CORNER] = 2;

		offX[DOWN_RIGHT][EDGE_NONE] = 1;
		offX[DOWN_RIGHT][EDGE_HORIZ] = 3;
		offX[DOWN_RIGHT][EDGE_VERT] = 1;
		offX[DOWN_RIGHT][EDGE_BOTH] = 3;
		offX[DOWN_RIGHT][EDGE_CORNER] = 3;

		offY[UP_LEFT][EDGE_NONE] = 4;
		offY[UP_LEFT][EDGE_HORIZ] = 4;
		offY[UP_LEFT][EDGE_VERT] = 2;
		offY[UP_LEFT][EDGE_BOTH] = 2;
		offY[UP_LEFT][EDGE_CORNER] = 0;

		offY[UP_RIGHT][EDGE_NONE] = 4;
		offY[UP_RIGHT][EDGE_HORIZ] = 4;
		offY[UP_RIGHT][EDGE_VERT] = 2;
		offY[UP_RIGHT][EDGE_BOTH] = 2;
		offY[UP_RIGHT][EDGE_CORNER] = 0;

		offY[DOWN_LEFT][EDGE_NONE] = 3;
		offY[DOWN_LEFT][EDGE_HORIZ] = 3;
		offY[DOWN_LEFT][EDGE_VERT] = 5;
		offY[DOWN_LEFT][EDGE_BOTH] = 5;
		offY[DOWN_LEFT][EDGE_CORNER] = 1;

		offY[DOWN_RIGHT][EDGE_NONE] = 3;
		offY[DOWN_RIGHT][EDGE_HORIZ] = 3;
		offY[DOWN_RIGHT][EDGE_VERT] = 5;
		offY[DOWN_RIGHT][EDGE_BOTH] = 5;
		offY[DOWN_RIGHT][EDGE_CORNER] = 1;
    }
    
    private SpriteSheet sheet;
    private int frames;
    
	public ConnectedTileImageProvider(SpriteSheet sheet, int frames) {
		this.sheet = sheet;
		this.frames = frames;
	}
	
	@Override
	public void getImages(TileMap map, int x, int y, int layer, BufferedImage[] images) {
		Tile tile = map.getLayer(layer).getTile(x, y);
		
		int tl = getCode(tile, map, x, y, layer, -1, 1);
        int tr = getCode(tile, map, x, y, layer, 1, 1);
        int bl = getCode(tile, map, x, y, layer, -1, -1);
        int br = getCode(tile, map, x, y, layer, 1, -1);
        
        getConnectedTile(
        		tile.tileMeta % tilesWide(), 
        		tile.tileMeta / tilesWide(), 
        		tl, tr, bl, br, images);
	}

	@Override
	public int tilesWide() {
		return sheet.tilesWide() / 4;
	}

	@Override
	public int tilesHigh() {
		return sheet.tilesHigh() / 6;
	}

	@Override
	public int getMeta(int tileX, int tileY) {
		return tileX + tileY * tilesWide();
	}

	@Override
	public void getDisplayImages(int tileX, int tileY, BufferedImage[] images) {
		getConnectedTile(tileX, tileY, EDGE_BOTH, EDGE_BOTH, EDGE_BOTH, EDGE_BOTH, images);
	}
	
	private void getConnectedTile(int tileX, int tileY, int tl, int tr, int bl, int br, BufferedImage[] images) {
		images[0] = sheet.get(tileX*4 + offX[UP_LEFT][tl], tileY*6 + offY[UP_LEFT][tl]);
		images[1] = sheet.get(tileX*4 + offX[UP_RIGHT][tr], tileY*6 + offY[UP_RIGHT][tr]);
		images[2] = sheet.get(tileX*4 + offX[DOWN_LEFT][bl], tileY*6 + offY[DOWN_LEFT][bl]);
		images[3] = sheet.get(tileX*4 + offX[DOWN_RIGHT][br], tileY*6 + offY[DOWN_RIGHT][br]);
	}
	
	public int getCode(Tile compare, TileMap map, int x, int y, int layer, int horizontal, int vertical) {
    	int flags = 0;
        Tile other = null;
        
        other = map.getLayer(layer).getTile(x + horizontal, y);
        if (other == null || other.tileId != compare.tileId || other.tileMeta != compare.tileMeta) {
            flags |= EDGE_HORIZ;
        }
        
        other = map.getLayer(layer).getTile(x, y + vertical);
        if (other == null || other.tileId != compare.tileId || other.tileMeta != compare.tileMeta) {
            flags |= EDGE_VERT;
        }
        
        other = map.getLayer(layer).getTile(x + horizontal, y + vertical);
        if (flags == 0 && (other == null || other.tileId != compare.tileId || other.tileMeta != compare.tileMeta)) {
            flags |= EDGE_CORNER;
        }
        
        return flags;
    }

}
