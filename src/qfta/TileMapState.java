package qfta;

import abacus.GameState;
import abacus.ResourceLoader;
import abacus.gameobject.Collider;
import abacus.gameobject.GameObject;
import abacus.gameobject.GameObjectLoader;
import abacus.gameobject.Scene;
import abacus.graphics.GameFont;
import abacus.graphics.Renderer;
import abacus.graphics.WorldRenderer;
import abacus.sound.Sound;
import abacus.tile.TileMap;
import abacus.ui.Input;
import abacus.ui.Menu;
import qfta.component.CharacterMovement;
import qfta.component.HumanoidRenderer;
import qfta.component.InputController;
import qfta.component.SimpleAI;
import qfta.component.WalkSound;

/*
 * Main tile map play state. 
 * 
 * This is where you load a tile map and move character on it, etc. 
 */
public class TileMapState extends GameState {

    // instance of world renderer
    private WorldRenderer worldRender;
    
    // hold the map and player
    // THIS WILL PROBABLY CHANGE A LOT
    private TileMap map;
    private Scene scene;
    private GameObject player;
    
    // sounds
    private Sound soundEffect;
    private Sound music;
    
    // fonts
    private GameFont font;
    
    // pause variables
    private boolean paused;
    private Menu pauseMenu;
    private int pauseQuit, pauseSettings;
    
    // initialize a bunch of stuff
    @Override
    public void init(ResourceLoader loader) {
        // create a world renderer
        worldRender = new WorldRenderer(engine.getRenderer());
        worldRender.setScale(4);
        
//        scene = SceneLoader.read("res/sample.scene", loader, QuestForTheAbacus.TILE_SIZE);//new Scene(map);
        
        map = new RandomTileMapGenerator(loader, 16).create(128, 128);//scene.getTileMap();
        //scene.setTileMap(map);
        scene = new Scene(map);
        
        map = scene.getTileMap();
        
        GameObjectLoader gol = new GameObjectLoader();
        gol.registerComponentType("Collider", new Collider(1f, 1f));
        gol.registerComponentType("CharacterMovement", new CharacterMovement(1f));
        gol.registerComponentType("HumanoidRenderer", new HumanoidRenderer(loader));
        gol.registerComponentType("SimpleAI", new SimpleAI());
        gol.registerComponentType("InputController", new InputController());
        gol.registerComponentType("WalkSound", new WalkSound(loader));
        
        
        //add component type for sound/movement
        
        gol.loadArchetypes("res/game_object_list.gameobject");
        
        for (int i = 0; i < 500; i++) {
            int x = -1, y = -1;
            while (map.getCollision(x, y)) {
                x = (int)(Math.random() * map.getWidth());
                y = (int)(Math.random() * map.getHeight());
            }
            
            float xpos = (float)(x + Math.random()) * 16;
            float ypos = (float)(y + Math.random()) * 16;
            
            scene.spawnArchetype("villager", xpos, ypos);
        }
        
        player = scene.spawnArchetype("player", 64 * 16, 64 * 16);
        
        // load sounds
        soundEffect = loader.loadSound("res/sound_effect.wav");
        music = loader.loadSound("res/town idea 2.1.wav");
        
        // load fonts
        this.font = loader.getFontCreator().createBasicFont("res/font.png", 10, 12, 0xFFFFFF);
        
        this.paused = false;
        
        this.pauseMenu = new Menu(200, 30);
        this.pauseSettings = this.pauseMenu.addOption("Settings");
        this.pauseQuit = this.pauseMenu.addOption("Quit");

    }

    @Override
    public void enter() {
        // start theme associated with map when the game state is entered
        // we just don't have a theme for this yet
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
        worldRender.setLayer(1);
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
        soundEffect.stop();
        music.stop();
    }

    @Override
    public void end() {}
    
}
