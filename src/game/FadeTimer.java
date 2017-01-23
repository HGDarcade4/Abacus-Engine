package game;

/*
 * Keeps track of what alpha value something should be
 * 
 * TODO remove need for update() ?
 */
public class FadeTimer {

    // time positions
    private long atFadeIn, atPause, atFadeOut, atWait, atDone;
    private long fadeIn, fadeOut;
    
    // time and alpha value
    private long time;
    private float alpha;
    
    // argument units are in updates
    public FadeTimer(int wait, int fadeIn, int pause, int fadeOut, int done) {
        time = 0;
        
        time += wait;
        this.atWait = time;
        
        time += fadeIn;
        this.atFadeIn = time;
        
        time += pause;
        this.atPause = time;
        
        time += fadeOut;
        this.atFadeOut = time;
        
        time += done;
        this.atDone = time;
        
        this.fadeIn = fadeIn;
        this.fadeOut = fadeOut;
        
        reset();
    }
    
    // update time
    // TODO make this like animations, not needing update() ? 
    public void update() {
        time++;
        
        if (time <= atWait) {
            alpha = 0f;
        }
        else if (time <= atFadeIn) {
            alpha = (float)(time - atWait) / fadeIn;
        }
        else if (time <= atPause) {
            alpha = 1f;
        }
        else if (time <= atFadeOut) {
            alpha = 1f - (float)(time - atPause) / fadeOut;
        }
        else {
            alpha = 0f;
        }
    }
    
    // get what the current alpha value should be
    public float getAlpha() {
        return alpha;
    }
    
    // is the fade finished
    public boolean isDone() {
        return time >= atDone;
    }
    
    // restart the fade
    public void reset() {
        time = 0;
        alpha = 0;
    }
    
}
