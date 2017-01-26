package abacus.tile;

import abacus.graphics.AnimationData;
import abacus.graphics.AnimationPlayer;
import abacus.graphics.Renderable;
import abacus.graphics.SpriteSheet;

public class ConnectionTiles {

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
    
    private AnimationData[][] data;
    private AnimationPlayer[][] anims;
    
    public ConnectionTiles(SpriteSheet tileset, int x, int y) {
        this();
        addFrame(tileset, x, y, 1);
    }
    
    public ConnectionTiles() {
        data = new AnimationData[TILE_PARTS][EDGE_PARTS];
        anims = new AnimationPlayer[TILE_PARTS][EDGE_PARTS];
        
        for (int i = 0; i < TILE_PARTS; i++) {
            for (int j = 0; j < EDGE_PARTS; j++) {
                data[i][j] = new AnimationData();
                anims[i][j] = new AnimationPlayer(data[i][j]);
                anims[i][j].play();
            }
        }
    }
    
    public Renderable get(int corner, int edge) {
        return anims[corner][edge];
    }
    
    public void addFrame(SpriteSheet tileset, int x, int y, int delay) {
        data[UP_LEFT][EDGE_NONE].addFrame(tileset.getSprite(x + 2, y + 4), delay);
        data[UP_LEFT][EDGE_HORIZ].addFrame(tileset.getSprite(x + 0, y + 4), delay);
        data[UP_LEFT][EDGE_VERT].addFrame(tileset.getSprite(x + 2, y + 2), delay);
        data[UP_LEFT][EDGE_BOTH].addFrame(tileset.getSprite(x + 0, y + 2), delay);
        data[UP_LEFT][EDGE_CORNER].addFrame(tileset.getSprite(x + 2, y + 0), delay);
        
        data[UP_RIGHT][EDGE_NONE].addFrame(tileset.getSprite(x + 1, y + 4), delay);
        data[UP_RIGHT][EDGE_HORIZ].addFrame(tileset.getSprite(x + 3, y + 4), delay);
        data[UP_RIGHT][EDGE_VERT].addFrame(tileset.getSprite(x + 1, y + 2), delay);
        data[UP_RIGHT][EDGE_BOTH].addFrame(tileset.getSprite(x + 3, y + 2), delay);
        data[UP_RIGHT][EDGE_CORNER].addFrame(tileset.getSprite(x + 3, y + 0), delay);
        
        data[DOWN_LEFT][EDGE_NONE].addFrame(tileset.getSprite(x + 2, y + 3), delay);
        data[DOWN_LEFT][EDGE_HORIZ].addFrame(tileset.getSprite(x + 0, y + 3), delay);
        data[DOWN_LEFT][EDGE_VERT].addFrame(tileset.getSprite(x + 2, y + 5), delay);
        data[DOWN_LEFT][EDGE_BOTH].addFrame(tileset.getSprite(x + 0, y + 5), delay);
        data[DOWN_LEFT][EDGE_CORNER].addFrame(tileset.getSprite(x + 2, y + 1), delay);

        data[DOWN_RIGHT][EDGE_NONE].addFrame(tileset.getSprite(x + 1, y + 3), delay);
        data[DOWN_RIGHT][EDGE_HORIZ].addFrame(tileset.getSprite(x + 3, y + 3), delay);
        data[DOWN_RIGHT][EDGE_VERT].addFrame(tileset.getSprite(x + 1, y + 5), delay);
        data[DOWN_RIGHT][EDGE_BOTH].addFrame(tileset.getSprite(x + 3, y + 5), delay);
        data[DOWN_RIGHT][EDGE_CORNER].addFrame(tileset.getSprite(x + 3, y + 1), delay);
    }
    
    public static int getCode(Tile compare, TileMap map, int x, int y, int layer, int horizontal, int vertical) {
        int flags = 0;
        
        if (map.getTile(x + horizontal, y, layer) != compare) {
            flags |= EDGE_HORIZ;
        }
        if (map.getTile(x, y + vertical, layer) != compare) {
            flags |= EDGE_VERT;
        }
        if (flags == 0 && map.getTile(x + horizontal, y + vertical, layer) != compare) {
            flags |= EDGE_CORNER;
        }
        
        return flags;
    }
    
}
