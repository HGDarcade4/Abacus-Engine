package test;

import java.util.Random;

import abacus.ResourceLoader;
import abacus.graphics.SpriteSheet;
import abacus.tile.ConnectedTile;
import abacus.tile.SpriteSheetTile;
import abacus.tile.TileMap;
import abacus.tile.TileRegistry;

public class RandomTileMapGenerator {

    SpriteSheet sheet, terrain;
    
    public RandomTileMapGenerator(ResourceLoader loader) {
        sheet = new SpriteSheet(loader.loadTexture("res/tileset_02.png"), 16, 16);
        terrain = new SpriteSheet(loader.loadTexture("res/tileset_02.png"), 8, 8);
    }
    
    public TileMap create(int width, int height) {
        TileMap map = new TileMap(width, height, 2);
        TileRegistry tiles = map.getTileRegistry();
        tiles.register(1, new SpriteSheetTile(sheet));
        tiles.register(2, new ConnectedTile(terrain, 0, 0));
        tiles.register(3, new ConnectedTile(terrain, 10, 4, 0, 8, 0, 12, 0, 8, 0));
        tiles.register(4, new ConnectedTile(terrain, 4, 6));
        
        map.fillLayer(0, 1, 16 * 4);
        
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                if (Math.random() < 0.9) {
                    map.setTile(x, y, 1, 2, 0);
                }
            }
        }
        
        // lakes
        Random r = new Random();
        for (int i = 0; i < map.getWidth() * map.getHeight() / 100; i++) {
            int size = r.nextInt(50);
            int x = r.nextInt(map.getWidth());
            int y = r.nextInt(map.getHeight());
            for (int j = 0; j < size; j += 2) {
                if (x == 64 && y == 64) {
                    continue;
                }
                map.setTile(x, y, 1, 3, 0);
                map.setCollision(x, y, true);
                x += r.nextInt(3) - 1;
                map.setTile(x, y, 1, 3, 0);
                map.setCollision(x, y, true);
                y += r.nextInt(3) - 1;
            }
        }
        
        return map;
    }
    
}
