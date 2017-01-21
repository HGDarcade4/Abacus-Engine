package abacus;

import java.awt.event.KeyEvent;

import abacus.awt.AwtResourceLoader;
import abacus.awt.AwtWindow;
import abacus.graphics.Renderer;
import abacus.ui.Input;
import abacus.ui.Window;

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
    
    private static GameEngine createAwtEngine(boolean framebuffer) {
        AwtWindow window = new AwtWindow(framebuffer);
        window.setTitle("Abacus Demo Game");
        window.setResolution(800, 640);
        
        GameEngine engine = new GameEngine(window, window.getInput(), window.getRenderer(), new AwtResourceLoader());
        return engine;
    }
    
    public Renderer getRenderer() {
        return renderer;
    }
    
    public ResourceLoader getResourceLoader() {
        return loader;
    }
    
    public void setUpdateGoal(int ups) {
        upsGoal = ups;
    }
    
    public void setRenderGoal(int fps) {
        fpsGoal = fps;
    }
    
    public Window getWindow() {
        return window;
    }
    
    public GameStateManager getGameStateManager() {
        return gsManager;
    }
    
    public float getFps() {
        return fps;
    }
    
    public float getUps() {
        return ups;
    }
    
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
    
    public void stop() {
        running = false;
    }
    
    private void gameLoop(int id) {
        long updateTimer = Time.getTime();
        long frameTimer = Time.getTime();
        long timer = Time.getTime();
        
        long incrUpdate = Time.SECOND / upsGoal;
        long incrRender = Time.SECOND / fpsGoal;
        
        int updates = 0;
        int frames = 0;
        
        init();
        
        gsManager.pushState(id);
        
        while (running) {
            while (updateTimer < Time.getTime()) {
                update();
                
                updates++;
                updateTimer += incrUpdate;
            }
            
            if (frameTimer < Time.getTime()) {
                render();
                
                frames++;
                frameTimer += incrRender;
            }
            
            float time = 0.1f;
            float lerp = 0.5f;
            if (Time.getTime() - timer >= time * Time.SECOND) {
                fps = lerp * fps + (1f - lerp) * frames / time;
                ups = lerp * ups + (1f - lerp) * updates / time;
                frames = updates = 0;
                timer = Time.getTime();
            }
            
            if (gsManager.noActiveStates() || !window.isVisible()) {
                stop();
            }
            
//            Time.sleep(1);
        }
        
        end();
    }
    
    private void init() {
        GameState[] states = gsManager.getRegisteredStates();
        
        for (GameState state : states) {
            state.setGameEngine(this);
            state.init(loader);
        }
    }
    
    private void update() {
        if (gsManager.noActiveStates() || input == null) {
            return;
        }
        gsManager.getCurrentState().update(input);
        
        if (input.getJustDownKey("exit")) {
            stop();
        }
        else if (input.getJustDownKey("fullscreen")) {
            window.setFullscreen(!window.isFullscreen());
        }
        
        input.update();
    }
    
    private void render() {
        if (renderer == null) {
            return;
        }
        
        renderer.begin();
        
        GameState[] states = gsManager.getActiveStates();
        
        for (GameState state : states) {
            state.render(renderer);
        }
        
        renderer.finish();
    }
    
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
    
}
