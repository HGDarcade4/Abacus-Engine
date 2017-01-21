package abacus.tile;

import abacus.graphics.Sprite;
import abacus.graphics.SpriteSheet;
import abacus.graphics.WorldRenderer;

public class SpriteSheetTile extends Tile {

    private SpriteSheet sheet;
    
    public SpriteSheetTile(SpriteSheet sheet) {
        this.sheet = sheet;
    }
    
    @Override
    public void update() {
        
    }

    @Override
    public void render(WorldRenderer r, TileMap map, int x, int y, int layer) {
        Sprite tile = sheet.getSprite(map.getTileMetaData(x, y, layer));
        r.drawTileSprite(tile, x, y);
    }

}
