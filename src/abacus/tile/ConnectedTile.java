package abacus.tile;

import abacus.graphics.AnimationPlayer;
import abacus.graphics.AnimationData;
import abacus.graphics.Renderable;
import abacus.graphics.Sprite;
import abacus.graphics.SpriteSheet;
import abacus.graphics.WorldRenderer;

public class ConnectedTile extends Tile {

    public static final int EDGE_NONE = 0;
    public static final int EDGE_VERT = 1;
    public static final int EDGE_HORIZ = 2;
    public static final int EDGE_BOTH = 3;
    public static final int EDGE_CORNER = 4;
    
    public static final int TILE_PARTS = 4;
    public static final int EDGE_PARTS = 5;
    
    public static final int UP_LEFT = 0;
    public static final int UP_RIGHT = 1;
    public static final int DOWN_LEFT = 2;
    public static final int DOWN_RIGHT = 3;
    
    protected Renderable[][] tiles; 
    
    public ConnectedTile(SpriteSheet tileset, int x, int y) {
        tiles = new Renderable[TILE_PARTS][EDGE_PARTS];
        
        tiles[UP_LEFT][EDGE_NONE] = tileset.getSprite(x + 2, y + 4);
        tiles[UP_LEFT][EDGE_HORIZ] = tileset.getSprite(x + 0, y + 4);
        tiles[UP_LEFT][EDGE_VERT] = tileset.getSprite(x + 2, y + 2);
        tiles[UP_LEFT][EDGE_BOTH] = tileset.getSprite(x + 0, y + 2);
        tiles[UP_LEFT][EDGE_CORNER] = tileset.getSprite(x + 2, y + 0);
        
        tiles[UP_RIGHT][EDGE_NONE] = tileset.getSprite(x + 1, y + 4);
        tiles[UP_RIGHT][EDGE_HORIZ] = tileset.getSprite(x + 3, y + 4);
        tiles[UP_RIGHT][EDGE_VERT] = tileset.getSprite(x + 1, y + 2);
        tiles[UP_RIGHT][EDGE_BOTH] = tileset.getSprite(x + 3, y + 2);
        tiles[UP_RIGHT][EDGE_CORNER] = tileset.getSprite(x + 3, y + 0);
        
        tiles[DOWN_LEFT][EDGE_NONE] = tileset.getSprite(x + 2, y + 3);
        tiles[DOWN_LEFT][EDGE_HORIZ] = tileset.getSprite(x + 0, y + 3);
        tiles[DOWN_LEFT][EDGE_VERT] = tileset.getSprite(x + 2, y + 5);
        tiles[DOWN_LEFT][EDGE_BOTH] = tileset.getSprite(x + 0, y + 5);
        tiles[DOWN_LEFT][EDGE_CORNER] = tileset.getSprite(x + 2, y + 1);

        tiles[DOWN_RIGHT][EDGE_NONE] = tileset.getSprite(x + 1, y + 3);
        tiles[DOWN_RIGHT][EDGE_HORIZ] = tileset.getSprite(x + 3, y + 3);
        tiles[DOWN_RIGHT][EDGE_VERT] = tileset.getSprite(x + 1, y + 5);
        tiles[DOWN_RIGHT][EDGE_BOTH] = tileset.getSprite(x + 3, y + 5);
        tiles[DOWN_RIGHT][EDGE_CORNER] = tileset.getSprite(x + 3, y + 1);
    }
    
    public ConnectedTile(SpriteSheet sheet, int delay, int... coords) {
        tiles = new Renderable[TILE_PARTS][EDGE_PARTS];
        
        int numFrames = coords.length / 2;
        
        // TODO use a better way to generate the animations
        ConnectedTile[] frames = new ConnectedTile[numFrames];
        
        for (int i = 0; i < numFrames; i++) {
            frames[i] = new ConnectedTile(sheet, coords[i * 2], coords[i * 2 + 1]);
        }
        
        AnimationData[][] anim = new AnimationData[TILE_PARTS][EDGE_PARTS];
        
        for (int i = 0; i < TILE_PARTS; i++) {
            for (int j = 0; j < EDGE_PARTS; j++) {
                anim[i][j] = new AnimationData();
                for (int k = 0; k < numFrames; k++) {
                    Sprite s = (Sprite)(frames[k].tiles[i][j]);
                    anim[i][j].addFrame(s, delay);
                }
                tiles[i][j] = new AnimationPlayer(anim[i][j]);
            }
        }
    }
    
    @Override
    public void update() {
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[UP_LEFT].length; j++) {
                if (tiles[i][j] != null) {
                    tiles[i][j].update();
                }
            }
        }
    }
    
    @Override
    public void render(WorldRenderer r, TileMap map, int x, int y, int layer) {
        int tl = getCode(map, x, y, layer, -1, 1);
        int tr = getCode(map, x, y, layer, 1, 1);
        int bl = getCode(map, x, y, layer, -1, -1);
        int br = getCode(map, x, y, layer, 1, -1);
        
        r.drawTileSprite(
                tiles[UP_LEFT][tl], 
                tiles[UP_RIGHT][tr],
                tiles[DOWN_LEFT][bl],
                tiles[DOWN_RIGHT][br],
                x, y);
    }
    
    private int getCode(TileMap map, int x, int y, int layer, int horizontal, int vertical) {
        int flags = 0;
        
        if (map.getTile(x + horizontal, y, layer) != this) {
            flags |= EDGE_HORIZ;
        }
        if (map.getTile(x, y + vertical, layer) != this) {
            flags |= EDGE_VERT;
        }
        if (flags == 0 && map.getTile(x + horizontal, y + vertical, layer) != this) {
            flags |= EDGE_CORNER;
        }
        
        return flags;
    }

}
