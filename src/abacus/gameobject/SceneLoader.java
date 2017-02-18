package abacus.gameobject;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
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
            Map<Integer, SpriteSheet> tilesets = new HashMap<>();
            TileRegistry tiles = new TileRegistry();
            
            int numSets = in.nextInt();
//            System.out.println("# Tilesets: " + numSets);
            in.nextLine();
            for (int i = 0; i < numSets; i++) {
                loadTileset(tilesets, in, loader);
            }
            
            int numTiles = in.nextInt();
//            System.out.print("# Tiletypes: " + numTiles);
            in.nextLine();
            for (int i = 0; i < numTiles; i++) {
                loadTile(tiles, tilesets, in, loader);
            }
            
//            System.out.println("Loading TileMap");
            TileMap map = loadTileMap(tiles, in, tileSize);
            
//            System.out.println("Done with map: " + map);
            
            Scene scene = new Scene(map);
            
            in.close();
            return scene;
        } catch (Exception e) {
            System.out.println("Problem reading scene from " + filename);
            return null;
        }
    }
    
    private static TileMap loadTileMap(TileRegistry tiles, Scanner in, int tileSize) {
        int width = in.nextInt();
        int height = in.nextInt();
        int layers = in.nextInt();
        in.nextLine();
        
//        System.out.println(width + " " + height + " " + layers);
        
        TileMap map = new TileMap(width, height, layers, tileSize);
        map.setTileRegistry(tiles);
        
        for (int layer = 0; layer < layers; layer++) {
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    int id = in.nextInt();
                    int meta = in.nextInt();
                    map.setTile(x, y, layer, id, meta);
                }
                in.nextLine();
            }
        }
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                boolean solid = in.nextInt() != 0;
                map.setCollision(x, y, solid);
            }
            in.nextLine();
        }
        
        return map;
    }

    private static void loadTileset(Map<Integer, SpriteSheet> tilesets, Scanner in, ResourceLoader loader) {
        int id = in.nextInt();
        String file = "res/" + in.next() + ".png";
        int width = in.nextInt();
        int height = in.nextInt();
        in.nextLine();
        
//        System.out.println(id + " " + file + " " + width + " " + height);
        
        tilesets.put(id, new SpriteSheet(loader.loadTexture(file), width, height));
    }
    
    private static void loadTile(TileRegistry tiles, Map<Integer, SpriteSheet> tilesets, Scanner in, ResourceLoader loader) {
        int id = in.nextInt();
        String type = in.next();
        
//        System.out.println(id + " " + type);
        
        int tileset;
        SpriteSheet sheet;
        int delay;
        int[] anim;
        
        switch (type) {
        case "Empty":
            tiles.register(id, new NullTile());
            break;
        case "TileSet":
            tileset = in.nextInt();
            sheet = tilesets.get(tileset);
            
            tiles.register(id, new SpriteSheetTile(sheet));
            break;
        case "Connected":
            tileset = in.nextInt();
            sheet = tilesets.get(tileset);
            
            anim = new int[in.nextInt() * 2];
            delay = in.nextInt();
            for (int i = 0; i < anim.length; i++) {
                anim[i] = in.nextInt();
            }
            
//            System.out.println(anim.length + " " + delay + " " + Arrays.toString(anim));
            
            tiles.register(id, new ConnectedTile(sheet, delay, anim));
            break;
        case "Wall":
            // TODO animated wall tiles
            tileset = in.nextInt();
            sheet = tilesets.get(tileset);
            
            anim = new int[in.nextInt() * 2];
            delay = in.nextInt();
//            System.out.println(anim.length + " " + delay + " start");
            
            for (int i = 0; i < anim.length; i++) {
                anim[i] = in.nextInt();
            }
            
//            System.out.println(anim.length + " " + delay + " " + Arrays.toString(anim));
            
            tiles.register(id, new WallTile(sheet, anim[0] + 4, anim[1], anim[0], anim[1]));
            break;
        }
        
        in.nextLine();
    }
    
}
