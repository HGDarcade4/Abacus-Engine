package game;

import java.util.Random;

import abacus.ResourceLoader;
import abacus.graphics.SpriteSheet;
import abacus.tile.ConnectedTile;
import abacus.tile.SpriteSheetTile;
import abacus.tile.TileMap;
import abacus.tile.TileRegistry;
import abacus.tile.WallTile;

/*
 * IGNORE THIS I'M GOING TO WORK ON TILES LATER
 */
public class RandomTileMapGenerator {

    SpriteSheet sheet, terrain;
    
    public RandomTileMapGenerator(ResourceLoader loader) {
        sheet = new SpriteSheet(loader.loadTexture("res/tileset_02.png"), 16, 16);
        terrain = new SpriteSheet(loader.loadTexture("res/tileset_02.png"), 8, 8);
    }
    
    public TileMap create(int width, int height) {
        TileMap map = new TileMap(width, height, 3);
        TileRegistry tiles = map.getTileRegistry();
        tiles.register(1, new SpriteSheetTile(sheet));
        tiles.register(2, new ConnectedTile(terrain, 0, 0));
        tiles.register(3, new ConnectedTile(terrain, 10, 4, 0, 8, 0, 12, 0, 8, 0));
        tiles.register(4, new ConnectedTile(terrain, 4, 6));
        tiles.register(5, new WallTile(terrain, 20, 0, 16, 0));
        
        // dirt
        map.fillLayer(0, 1, 16 * 4);
        
        // cliff wall
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                if (Math.random() < 1.0) {
                    map.setTile(x, y, 2, 5, 0);
                }
            }
        }
        
//        // grass
//        for (int y = 0; y < map.getHeight(); y++) {
//            for (int x = 0; x < map.getWidth(); x++) {
//                if (Math.random() < 0.2) {
//                    map.setTile(x, y, 1, 2, 0);
//                }
//            }
//        }
        
        Random r = new Random();
        int x = 64;
        int y = 64;
        for (int i = 0; i < map.getWidth() * map.getHeight() / 100; i++) {
            int size = 10 + r.nextInt(30);
            x = y = 64;
            for (int j = 0; j < size; j += 2) {
                map.setTile(x, y, 2, 0, 0);
                map.setCollision(x, y, false);
                x += r.nextInt(3) - 1;
                map.setTile(x, y, 2, 0, 0);
                map.setCollision(x, y, false);
                y += r.nextInt(3) - 1;
            }
        }
        
        x = 64;
        y = 64;
        for (int i = 0; i < map.getWidth() * map.getHeight() / 10; i++) {
            int size = 10 + r.nextInt(40);
            for (int j = 0; j < size; j += 2) {
                map.setTile(x, y, 1, 2, 0);
                map.setCollision(x, y, false);
                x += r.nextInt(3) - 1;
                map.setTile(x, y, 1, 2, 0);
                map.setCollision(x, y, false);
                y += r.nextInt(3) - 1;
            }
            x = r.nextInt(map.getWidth());
            y = r.nextInt(map.getHeight());
        }
        
        // lakes
        for (int i = 0; i < map.getWidth() * map.getHeight() / 100; i++) {
            int size = r.nextInt(50);
            x = r.nextInt(map.getWidth());
            y = r.nextInt(map.getHeight());
            for (int j = 0; j < size; j += 2) {
                if (x == 64 && y == 64) {
                    continue;
                }
                map.setTile(x, y, 2, 0, 0);
                map.setTile(x, y, 1, 3, 0);
                map.setCollision(x, y, true);
                x += r.nextInt(3) - 1;
                map.setTile(x, y, 2, 0, 0);
                map.setTile(x, y, 1, 3, 0);
                map.setCollision(x, y, true);
                y += r.nextInt(3) - 1;
            }
        }
        
        return map;
    }
    
}
