package test;

public class FadeTimer {

    private int atFadeIn, atPause, atFadeOut, atWait;
    private int fadeIn, fadeOut;
    
    private int time;
    private float alpha;
    
    public FadeTimer(int wait, int fadeIn, int pause, int fadeOut) {
        time = 0;
        
        time += wait;
        this.atWait = time;
        
        time += fadeIn;
        this.atFadeIn = time;
        
        time += pause;
        this.atPause = time;
        
        time += fadeOut;
        this.atFadeOut = time;
        
        this.fadeIn = fadeIn;
        this.fadeOut = fadeOut;
    }
    
    public void update() {
        time++;
        
        if (time < atWait) {
            alpha = 0f;
        }
        else if (time < atFadeIn) {
            alpha = (float)(time - atWait) / fadeIn;
        }
        else if (time < atPause) {
            alpha = 1f;
        }
        else if (time < atFadeOut) {
            alpha = 1f - (float)(time - atPause) / fadeOut;
        }
        else {
            alpha = 0f;
        }
    }
    
    public float getAlpha() {
        return alpha;
    }
    
    public boolean isDone() {
        return time >= atFadeOut;
    }
    
    public void reset() {
        time = 0;
        alpha = 0;
    }
    
}
