package abacus.graphics;

import abacus.Time;

public class AnimationPlayer implements Renderable {

    private AnimationData data;
    private long start, frame;
    private boolean enabled;
    
    public AnimationPlayer(AnimationData data) {
        this.data = data;
        start = Time.getTicks();
        pauseAndReset();
    }
    
    public AnimationPlayer copy() {
        AnimationPlayer a = new AnimationPlayer(data);
        a.start = start;
        return a;
    }
    
    public AnimationData getAnimationData() {
        return data;
    }
    
    public void play() {
        if (!enabled) {
            start = Time.getTicks() - frame;
        }
        enabled = true;
        frame = 0;
    }
    
    public void pause() {
        if (enabled) {
            frame = Time.getTicks() - start;
        }
        enabled = false;
    }
    
    public void pauseAndReset() {
        pause();
        reset();
    }
    
    public void reset() {
        start = Time.getTicks();
        frame = 0;
    }
    
    public Sprite getSprite() {
        int frame;
        
        if (enabled) {
            frame = (int)(Time.getTicks() - start);
        }
        else {
            frame = (int)this.frame;
        }
        
        return data.getFrame(frame);
    }
    
}
