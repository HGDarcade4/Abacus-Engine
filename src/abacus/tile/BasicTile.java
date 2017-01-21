package abacus.tile;

import abacus.graphics.Renderable;
import abacus.graphics.WorldRenderer;

public class BasicTile extends Tile {

    private Renderable sprite;
    
    public BasicTile(Renderable sprite) {
        this.sprite = sprite;
    }
    
    @Override
    public void update() {
        sprite.update();
    }
    
    @Override
    public void render(WorldRenderer r, TileMap map, int x, int y, int layer) {
        r.setLayer(layer);
        r.drawTileSprite(sprite, x, y);
    }

}
