package abacus.graphics;

import abacus.Time;

/*
 * Plays animations. You can play, pause, and reset animations
 */
public class AnimationPlayer implements Renderable {

    // data for the animation frames
    private AnimationData data;
    // keeps track of which frame should be played
    private long start, frame;
    private boolean enabled;
    
    /* ctor, give the player what sprites it should play
     * 
     * NOTE: The animation player defaults to paused
     * You will have to manually call play()
     */
    public AnimationPlayer(AnimationData data) {
        this.data = data;
        start = Time.getTicks();
        pauseAndReset();
    }
    
    // creates a copy of the animation player in its current state
    public AnimationPlayer copy() {
        AnimationPlayer a = new AnimationPlayer(data);
        a.start = start;
        a.frame = frame;
        a.enabled = enabled;
        return a;
    }
    
    // returns the sprite data
    public AnimationData getAnimationData() {
        return data;
    }
    
    // starts the animation if it isn't already
    public void play() {
        if (!enabled) {
            start = Time.getTicks() - frame;
        }
        enabled = true;
        frame = 0;
    }
    
    // pauses the animation
    public void pause() {
        if (enabled) {
            frame = Time.getTicks() - start;
        }
        enabled = false;
    }
    
    // helper method to pause and reset
    public void pauseAndReset() {
        pause();
        reset();
    }
    
    // resets the animation to the beginning of the animation
    public void reset() {
        start = Time.getTicks();
        frame = 0;
    }
    
    // returns the sprite that should currently be displayed
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

    public void setFrame(int frame) {
        start = (int)(Time.getTicks() - frame);
        this.frame = frame;
    }
    
}
