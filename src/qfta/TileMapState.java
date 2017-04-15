package qfta;

import java.util.HashMap;
import java.util.Map;

import abacus.GameState;
import abacus.ResourceLoader;
import abacus.gameobject.Collider;
import abacus.gameobject.GameObject;
import abacus.gameobject.GameObjectLoader;
import abacus.gameobject.Scene;
import abacus.gameobject.SceneLoader;
import abacus.graphics.GameFont;
import abacus.graphics.Renderer;
import abacus.graphics.WorldRenderer;
import abacus.sound.Sound;
import abacus.tile.TileMap;
import abacus.ui.Input;
import abacus.ui.Menu;
import qfta.component.BatRenderer;
import qfta.component.BattleStats;
import qfta.component.CharacterMovement;
import qfta.component.HumanoidRenderer;
import qfta.component.InputController;
import qfta.component.SimpleAI;
import qfta.component.SkeletonRenderer;
import qfta.component.SlimeRenderer;
import qfta.component.WalkSound;

/*
 * Main tile map play state. 
 * 
 * This is where you load a tile map and move character on it, etc. 
 */
public class TileMapState extends GameState {

    private static final String DEFAULT_SONG = "res/town.wav";
    
    // instance of world renderer
    private WorldRenderer worldRender;
    
    // hold the map and player
    // THIS WILL PROBABLY CHANGE A LOT
    private TileMap map;
    private Scene scene;
    private GameObject player;
    
    // sounds
    private Sound music;
    
    private ResourceLoader loader;
    
    private Map<String, PlayerPosition> scenePositions;
    // fonts
    private GameFont font;
    
    // pause variables
    private boolean paused;
    private Menu pauseMenu;
    private int pauseQuit, pauseSettings;
    
    // initialize a bunch of stuff
    @Override
    public void init(ResourceLoader loader) {
        scenePositions = new HashMap<>();
        
        this.loader = loader;
        
        // create a world renderer
        worldRender = new WorldRenderer(engine.getRenderer());
        worldRender.setScale(4);

        GameObjectLoader gol = new GameObjectLoader();
        gol.registerComponentType("Collider", new Collider(1f, 1f));
        gol.registerComponentType("CharacterMovement", new CharacterMovement(1f));
        gol.registerComponentType("HumanoidRenderer", new HumanoidRenderer(loader));
        gol.registerComponentType("SimpleAI", new SimpleAI());
        gol.registerComponentType("InputController", new InputController());
        gol.registerComponentType("BatRenderer", new BatRenderer(loader));
        gol.registerComponentType("WalkSound", new WalkSound(loader));
        gol.registerComponentType("SkeletonRenderer", new SkeletonRenderer(loader));
        gol.registerComponentType("SlimeRenderer", new SlimeRenderer(loader));
        gol.registerComponentType("BattleStats", new BattleStats(loader));
        
        gol.loadArchetypes("res/game_object_list.gameobject");
        
        player = GameObject.spawnArchetype("player", 0 * 16, 0 * 16);
        
        // load sounds
        music = loader.loadSound(DEFAULT_SONG);
        
        // load scene
        loadScene("res/start.scene");
        
        // load fonts
        this.font = loader.getFontCreator().createBasicFont("res/font.png", 10, 12, 0xFFFFFF);
        
        this.paused = false;
        
        this.pauseMenu = new Menu(200, 30);
        this.pauseSettings = this.pauseMenu.addOption("Settings");
        this.pauseQuit = this.pauseMenu.addOption("Quit");
    }

    @Override
    public void enter() {
        music.playAndLoop();
    }

    // update logic
    @Override
    public void update(Input input) {
        // update game logic
        if (input.getJustDownKey("p")) {
        	this.pause();
        }

        if (!paused) {
        	map.update();
        	scene.update(input);
        	
            String tp = map.getTeleport((int)(player.getTransform().x/16), (int)(player.getTransform().y/16));
            if (tp != null) {
                loadScene(tp);
                music.playAndLoop();
            }
            
            checkPlayerEnemyCollision();
        }
    }

    // render map and player
    @Override
    public void render(Renderer renderer) {
        worldRender.reset();
        worldRender.setDebug(engine.getDebug());
        
        // clear the screen to a light blue
        renderer.clearScreen(0xCC, 0xEE, 0xFF);
        
        // center camera at the player
        worldRender.setView(player.getTransform().x, player.getTransform().y);
        
        // player draw layer
        worldRender.setLayer(3);
        scene.render(worldRender);
        
        // draw the map
        map.render(worldRender);

        engine.debugLine("");
        engine.debugLine("(" + player.getTransform().x + ", " + player.getTransform().y + ")");

        if (paused) {
        	String label = "PAUSED";
        	font.setSize(20);
        	int x = (int) (renderer.getWidth()/2 - font.getWidth(label)/2);
        	int y = (int) (renderer.getHeight()/2);
        	font.draw(label, x, y);
        	
        	this.pauseMenu.render(renderer, font);
        }
    }

    @Override
    public void pause() {
    	this.paused = !this.paused;
    }

    // stop music when exiting the game state
    @Override
    public void exit() {
        music.stop();
    }

    @Override
    public void end() {}
    
    private void checkPlayerEnemyCollision() {
//        BattleStats pStats = player.get(BattleStats.class);
        Collider pCol = player.get(Collider.class);
        
        for (int i = 0; i < scene.numGameObjects(); i++) {
            GameObject go = scene.getGameObjectByIndex(i);
            
            if (go.has(BattleStats.class) && go.has(Collider.class)) {
                BattleStats stats = go.get(BattleStats.class);
                Collider col = go.get(Collider.class);
                
                if (!stats.isPlayer) {
                    if (col.tileBody.isColliding(pCol.tileBody)) {
                        BattleState state = (BattleState) engine.getGameStateManager()
                                .getGameStateById(QuestForTheAbacus.ID_BATTLE);
                        state.setGameObjects(player.copy(0, 0), go.copy(0, 0));
                        swapState(QuestForTheAbacus.ID_BATTLE);
                    }
                }
            }
        }
    }
    
    private void loadScene(String filename) {
        music.stop();
        
        if (scene != null) {
            PlayerPosition pos = scenePositions.get(scene.getFileName());
            if (pos == null) {
                pos = new PlayerPosition();
                scenePositions.put(scene.getFileName(), pos);
            }
            pos.x = player.getTransform().x - player.get(Collider.class).tileBody.getVelX() * 10;
            pos.y = player.getTransform().y - player.get(Collider.class).tileBody.getVelY() * 10;
        }
        
        scene = SceneLoader.read(filename, loader, QuestForTheAbacus.TILE_SIZE);
        
        map = scene.getTileMap();
        
        PlayerPosition pos = scenePositions.get(scene.getFileName());
        if (pos == null) {
            player.getTransform().x = scene.getStartX() * 16;
            player.getTransform().y = scene.getStartY() * 16;
        }
        else {
            player.getTransform().x = pos.x;
            player.getTransform().y = pos.y;
        }
        
        scene.addGameObject(player);
        
        if (scene.getMusicFileName() != null) {
            music = loader.loadSound(scene.getMusicFileName());
        }
        else {
            music = loader.loadSound(DEFAULT_SONG);
        }
    }
    
}
