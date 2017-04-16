package abacus.gameobject;

import java.io.FileInputStream;
import java.util.Scanner;

import abacus.ResourceLoader;
import abacus.graphics.SpriteSheet;
import abacus.tile.ConnectedTile;
import abacus.tile.NullTile;
import abacus.tile.SpriteSheetTile;
import abacus.tile.TileMap;
import abacus.tile.TileRegistry;
import abacus.tile.WallTile;

// TODO shouldn't be singleton
public final class SceneLoader {

    private SceneLoader() {}
    
    public static Scene read(String filename, ResourceLoader loader, int tileSize) {
        try {
            Scanner in = new Scanner(new FileInputStream(filename));
            TileRegistry tiles = new TileRegistry();
            
            int numTiles = in.nextInt();
            for (int i = 0; i < numTiles; i++) {
                loadTile(tiles, in, loader);
            }
            
            TileMap map = loadTileMap(tiles, in, tileSize);
            
            Scene scene = new Scene(map, filename);
            
            while (in.hasNext()) {
                String command = in.next();
                if (command.equals("music")) {
                    scene.setMusicFileName(in.next());
                }
                else if (command.equals("start")) {
                    int x = in.nextInt();
                    int y = in.nextInt();
                    scene.setStartPos(x, y);
                }
                else if (command.equals("tp")) {
                    int x = in.nextInt();
                    int y = in.nextInt();
                    String tp = in.next();
                    map.setTeleport(x, y, tp);
                }
                else if (command.equals("spawn")) {
                    String archetype = in.next();
                    int amt = in.nextInt();
                    spawn(scene, archetype, amt);
                }
            }
            
            in.close();
            return scene;
        } catch (Exception e) {
            System.out.println("Problem reading scene from " + filename + "\n");
            e.printStackTrace();
            return null;
        }
    }
    
    private static void spawn(Scene scene, String archetype, int amt) {
        for (int i = 0; i < amt; i++) {
            int x = -1, y = -1;
            TileMap map = scene.getTileMap();
            while (map.getCollision(x, y) || archetype.equals("boss") && y < 20) {
                x = (int)(Math.random() * map.getWidth());
                y = (int)(Math.random() * map.getHeight());
            }
            
            float xpos = (float)(x + Math.random()) * 16;
            float ypos = (float)(y + Math.random()) * 16;
            
            scene.spawnArchetype(archetype, xpos, ypos);
        }
    }

    private static TileMap loadTileMap(TileRegistry tiles, Scanner in, int tileSize) {
        int layers = in.nextInt();
        int width = in.nextInt();
        int height = in.nextInt();
        in.nextLine();
        
        TileMap map = new TileMap(width, height, layers, tileSize);
        map.setTileRegistry(tiles);
        
        for (int layer = 0; layer < layers; layer++) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int id = in.nextInt();
                    int meta = in.nextInt();
                    map.setTile(x, y, layer, id, meta);
                    
                    if (id == 4) {
                        map.setCollision(x, y, true);
                    }
                    else if (id == 3 && meta == 0) {
                        map.setCollision(x, y, true);
                    }
                    else if (id == 1) {
                        map.setCollision(x, y, false);
                    }
                }
            }
        }
        
        return map;
    }
    
    private static void loadTile(TileRegistry tiles, Scanner in, ResourceLoader loader) {
        int id = in.nextInt();
        String type = in.next();
        
        String tileset;
        SpriteSheet sheet;
        int delay;
        int frames;
        
        switch (type) {
        case "null":
            tiles.register(id, new NullTile());
            break;
        case "basic":
            tileset = in.next();
            sheet = new SpriteSheet(loader.loadTexture(tileset), 16, 16);
            
            tiles.register(id, new SpriteSheetTile(sheet));
            break;
        case "connect":
            tileset = in.next();
            sheet = new SpriteSheet(loader.loadTexture(tileset), 8, 8);
            frames = in.nextInt();
            delay = in.nextInt();
            
            tiles.register(id, new ConnectedTile(sheet, delay, frames));
            break;
        case "wall":
            tileset = in.next();
            sheet = new SpriteSheet(loader.loadTexture(tileset), 8, 8);
            frames = in.nextInt();
            delay = in.nextInt();
            
            tiles.register(id, new WallTile(sheet, delay, frames));
            break;
        }
        
        in.nextLine();
    }
    
}
