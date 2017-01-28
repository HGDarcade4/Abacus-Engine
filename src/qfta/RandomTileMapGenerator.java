package qfta;

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

    private SpriteSheet sheet, terrain;
    private int tileSize;
    
    public RandomTileMapGenerator(ResourceLoader loader, int tileSize) {
        sheet = new SpriteSheet(loader.loadTexture("res/tileset_02.png"), 16, 16);
        terrain = new SpriteSheet(loader.loadTexture("res/tileset_02.png"), 8, 8);
        
        this.tileSize = tileSize;
    }
    
    public TileMap create(int width, int height) {
        final int GROUND_LAYER = 0;
        final int WALL_LAYER = 1;
        
        final int NULL = 0;
        final int TILESET = 1;
        final int GRASS = 2;
        final int WATER = 3;
        final int CARPET = 4;
        final int CAVE_WALL = 5;
        
        TileMap map = new TileMap(width, height, 3, tileSize);
        TileRegistry tiles = map.getTileRegistry();
        tiles.register(TILESET, new SpriteSheetTile(sheet));
        tiles.register(GRASS, new ConnectedTile(terrain, 0, 0));
        tiles.register(WATER, new ConnectedTile(terrain, 10, 4, 0, 8, 0, 12, 0, 8, 0));
        tiles.register(CARPET, new ConnectedTile(terrain, 4, 6));
        tiles.register(CAVE_WALL, new WallTile(terrain, 20, 0, 16, 0));
        
        // dirt
        map.fillLayer(GROUND_LAYER, 1, 16 * 4);
        
        // cliff wall
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                if (Math.random() < 1.0) {
                    map.setTile(x, y, WALL_LAYER, CAVE_WALL, 0);
                    map.setCollision(x, y, true);
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
        
        // remove walls
        Random r = new Random();
        int x = 64;
        int y = 64;
        for (int i = 0; i < map.getWidth() * map.getHeight() / 100; i++) {
            int size = 10 + r.nextInt(30);
            x = y = 64;
            for (int j = 0; j < size; j += 2) {
                map.setTile(x, y, WALL_LAYER, NULL, 0);
                map.setCollision(x, y, false);
                x += r.nextInt(3) - 1;
                map.setTile(x, y, WALL_LAYER, NULL, 0);
                map.setCollision(x, y, false);
                y += r.nextInt(3) - 1;
            }
        }
        
        // grass
        x = 64;
        y = 64;
        for (int i = 0; i < map.getWidth() * map.getHeight() / 10; i++) {
            int size = 10 + r.nextInt(40);
            for (int j = 0; j < size; j += 2) {
                map.setTile(x, y, GROUND_LAYER, GRASS, 0);
                x += r.nextInt(3) - 1;
                map.setTile(x, y, GROUND_LAYER, GRASS, 0);
                y += r.nextInt(3) - 1;
            }
            x = r.nextInt(map.getWidth());
            y = r.nextInt(map.getHeight());
        }
        
//        int x, y;
//        Random r = new Random();
        // lakes
        for (int i = 0; i < map.getWidth() * map.getHeight() / 100; i++) {
            int size = r.nextInt(50);
            x = r.nextInt(map.getWidth());
            y = r.nextInt(map.getHeight());
            for (int j = 0; j < size; j += 2) {
                if (x == 64 && y == 64) {
                    continue;
                }
                map.setTile(x, y, WALL_LAYER, NULL, 0);
                map.setTile(x, y, GROUND_LAYER, WATER, 0);
                map.setCollision(x, y, true);
                x += r.nextInt(3) - 1;
                map.setTile(x, y, WALL_LAYER, NULL, 0);
                map.setTile(x, y, GROUND_LAYER, WATER, 0);
                map.setCollision(x, y, true);
                y += r.nextInt(3) - 1;
            }
        }
        
        return map;
    }
    
}
