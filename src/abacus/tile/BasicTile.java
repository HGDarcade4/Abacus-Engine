package abacus.tile;

import abacus.graphics.Renderable;
import abacus.graphics.WorldRenderer;
import abacus.sound.Sound;

public class BasicTile extends Tile {
	
    private Renderable sprite;
    
    public BasicTile(Renderable sprite) {
        this.sprite = sprite;
        sprite.play();
    }
    
    
    @Override
    public void render(WorldRenderer r, TileMap map, int x, int y, int layer) {
        r.setLayer(layer);
        r.drawTileSprite(sprite, map.getTileSize(), x, y);
    }


}
