package abacus;

import java.awt.event.KeyEvent;

import abacus.awt.AwtResourceLoader;
import abacus.awt.AwtWindow;
import abacus.graphics.DebugRenderer;
import abacus.graphics.GameFont;
import abacus.graphics.Renderer;
import abacus.ui.Input;
import abacus.ui.Window;

/*
 * This is the class that controls the game
 * 
 * You create a GameEngine with GameEngine.create(Type)
 */
public class GameEngine {

    // holds game states
    private GameStateManager gsManager;
    // what do you want frame rate and update rate to be
    private int fpsGoal, upsGoal;
    // what is the current frame rate and update rate
    private float fps, ups; 
    // is the game running
    private boolean running;
    private Thread gameThread;
    // game input
    private Input input;
    // game renderer
    private Renderer renderer;
    // game resource loader
    private ResourceLoader loader;
    // game window
    private Window window;
    // debug 
    private boolean debug = true;
    private GameFont debugFont;
    private DebugRenderer debugRenderer;
    
    // type of engine
    public enum Type {
        // draws directly to the canvas, can be smoother, but more graphical glitches
        JAVA2D_CANVAS, 
        // draws to a frame buffer, allows a more genuine pixel feel
        JAVA2D_FRAMEBUFFER
    }
    
    // ctor
    private GameEngine(
            Window w, 
            Input in, 
            Renderer r, 
            ResourceLoader rl) {
        gsManager = new GameStateManager();
        
        // default rate goals
        fpsGoal = 60;
        upsGoal = 60;
        
        // game is not running until you call start()
        running = false;
        
        input = in;
        renderer = r;
        loader = rl;
        window = w;
        
        // register default keys
        input.registerKey(KeyEvent.VK_F11, "fullscreen");
        input.registerKey(KeyEvent.VK_ESCAPE, "exit");
    }
    
    // use this method to create the game engine
    public static GameEngine create(Type type) {
        switch (type) {
        case JAVA2D_CANVAS:
            return createAwtEngine(false);
        case JAVA2D_FRAMEBUFFER:
            return createAwtEngine(true);
        default:
            return null;
        }
    }
    
    // creates the engine with Java2D rendering
    private static GameEngine createAwtEngine(boolean framebuffer) {
        AwtWindow window = new AwtWindow(framebuffer);
        window.setTitle("Abacus Demo Game");
        window.setResolution(800, 640);
        
        GameEngine engine = new GameEngine(window, window.getInput(), window.getRenderer(), new AwtResourceLoader());
        return engine;
    }
    
    // returns the renderer
    public Renderer getRenderer() {
        return renderer;
    }
    
    // returns the resource loader
    public ResourceLoader getResourceLoader() {
        return loader;
    }
    
    // write a debug line
    public void debugLine(String line) {
        debugRenderer.debugLine(line);
    }
    
    // sets what the update rate should be
    public void setUpdateGoal(int ups) {
        upsGoal = ups;
    }
    
    // sets what the frame rate should be
    public void setRenderGoal(int fps) {
        fpsGoal = fps;
    }
    
    // returns the window
    public Window getWindow() {
        return window;
    }
    
    // returns the game state manager
    // this is where you register your game states
    public GameStateManager getGameStateManager() {
        return gsManager;
    }
    
    // returns the current frame rate
    public float getFps() {
        return fps;
    }
    
    // returns the current update rate
    public float getUps() {
        return ups;
    }
    
    // begins the game loop
    // this also pushes the game state with id 
    // in the game state manager
    public void start(int id) {
        if (running) {
            return;
        }
        running = true;
        gameThread = new Thread("Game-Loop") {
            public void run() {
                gameLoop(id);
            }
        };
        gameThread.start();
    }
    
    // stops the game at the end of the game loop
    public void stop() {
        running = false;
    }
    
    // play the game loop
    private void gameLoop(int id) {
        // keep track of update, render, and time
        long updateTimer = Time.getTime();
        long frameTimer = Time.getTime();
        long timer = Time.getTime();
        
        // for use with frame and update rate handling
        long incrUpdate = Time.SECOND / upsGoal;
        long incrRender = Time.SECOND / fpsGoal;
        
        // number of updates and frames
        int updates = 0;
        int frames = 0;
        
        // initialize all game states
        init();
        
        // push the first game state
        gsManager.pushState(id);
        
        // the actual game loop
        while (running) {
            // keep updating the current game state until updateTimer is not behind current time
            while (updateTimer < Time.getTime()) {
                update();
                
                updates++;
                updateTimer += incrUpdate;
            }
            
            // render game states if frameTimer is behind the current time
            if (frameTimer < Time.getTime()) {
                render();
                
                frames++;
                frameTimer += incrRender;
            }
            
            // this is for getting somewhat real time frame and update rate information
            float time = 0.1f;
            float lerp = 0.5f;
            if (Time.getTime() - timer >= time * Time.SECOND) {
                fps = lerp * fps + (1f - lerp) * frames / time;
                ups = lerp * ups + (1f - lerp) * updates / time;
                frames = updates = 0;
                timer = Time.getTime();
            }
            
            // if there is no active game state or the window is closed, end the game
            if (gsManager.noActiveStates() || !window.isVisible()) {
                stop();
            }
            
            // TODO have an option to sleep the thread
//            Time.sleep(1);
        }
        
        // ends the game, lets all game states know the game is ending
        end();
    }
    
    // initialize all game states that are registered
    private void init() {
        debugFont = loader.getFontCreator().createBasicFont("res/font.png", 10, 12, 0xFFFFFF, 1.0f, 0x000000, 0.5f);
        debugFont.setSize(12.0f);
        debugRenderer = new DebugRenderer(renderer, debugFont);
        debugRenderer.setDebugMode(debug);
        
        GameState[] states = gsManager.getRegisteredStates();
        
        for (GameState state : states) {
            state.setGameEngine(this);
            state.init(loader);
        }
    }
    
    // updates the current game state
    private void update() {
        if (gsManager.noActiveStates() || input == null) {
            return;
        }
        gsManager.getCurrentState().tick();
        Time.setTicks(gsManager.getCurrentState().getTicks());
        gsManager.getCurrentState().update(input);
        
        // checks if game should stop or if full screen should be toggled
        if (input.getJustDownKey("exit")) {
            stop();
        }
        else if (input.getJustDownKey("fullscreen")) {
            window.setFullscreen(!window.isFullscreen());
        }
        
        input.update();
    }
    
    // renders all game states in the stack, 
    // this allows the map to be shown underneath a pause state, etc.
    private void render() {
        if (renderer == null) {
            return;
        }
        
        renderer.begin();
        debugRenderer.reset();
        debugText();
        
        GameState[] states = gsManager.getActiveStates();
        
        for (GameState state : states) {
            state.render(renderer);
        }
        
        renderer.finish();
    }
    
    // tells all game states that the game is about the stop
    private void end() {
        while (!gsManager.noActiveStates()) {
            gsManager.popState();
        }
        
        GameState[] states = gsManager.getRegisteredStates();
        
        for (GameState state : states) {
            state.end();
        }
        
        System.exit(0);
    }
    
    private void debugText() {
        // get various debug information
        String fps = String.format("%1.2f", getFps());
        String ups = String.format("%1.2f", getUps());
        int drawCommands = renderer.drawCommands();
        
        // draw the information to the screen
        debugLine("draw commands: " + drawCommands);
        debugLine("updates per second: " + ups);
        debugLine("frames per second: " + fps);
    }
    
}
