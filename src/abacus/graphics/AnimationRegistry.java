package abacus.graphics;

/*
 * Keeps track of multiple animations. It also holds a current animation
 */
public class AnimationRegistry implements Renderable {

    // array of animations
    // TODO probably make it a hash map
    private AnimationPlayer[] anims;
    // index of current animation
    private int current;
    
    // default ctor
    public AnimationRegistry() {
        this(128);
    }
    
    // ctor where you specify the size of the registry
    public AnimationRegistry(int size) {
        anims = new AnimationPlayer[size];
        current = 0;
    }
    
    // creates a copy of the registry in its current state
    public AnimationRegistry copy() { 
        AnimationRegistry reg = new AnimationRegistry(anims.length);
        for (int i = 0; i < anims.length; i++) {
            if (anims[i] != null) {
                reg.anims[i] = anims[i].copy();
            }
        }
        reg.current = current;
        return reg;
    }
    
    // add an animation to the registry
    // this does nothing to the animation player that was added
    public void register(int index, AnimationPlayer anim) {
        anims[index] = anim;
    }
    
    /* pauses the current animation if it is not the new one, 
     * and sets a new animation.
     * 
     * NOTE: this does NOT play the new animation (unless
     * the new animation was the old one)
     */
    public void setCurrent(int index) {
        if (current == index) {
            return;
        }
        
        AnimationPlayer anim = anims[current];
        if (anim != null) {
            anim.pauseAndReset();
        }
        current = index;
    }
    
    /* pauses the current animation if it is not the new one, 
     * then sets and plays a new animation.
     */
    public void setCurrentAndPlay(int index) {
        setCurrent(index);
        play();
    }
    
    // set the current animation to playing
    public void play() {
        AnimationPlayer anim = anims[current];
        if (anim != null) {
            anim.play();
        }
    }
    
    // set the current animation to paused
    public void pause() {
        AnimationPlayer anim = anims[current];
        if (anim != null) {
            anim.pause();
        }
    }
    
    // pause and reset the current animation
    public void pauseAndReset() {
        AnimationPlayer anim = anims[current];
        if (anim != null) {
            anim.pauseAndReset();
        }
    }
    
    // reset the current animation
    public void reset() {
        AnimationPlayer anim = anims[current];
        if (anim != null) {
            anim.reset();
        }
    }
    
    // get the sprite that should be currently displayed
    @Override
    public Sprite getSprite() {
        AnimationPlayer anim = anims[current];
        if (anim == null) {
            return null;
        }
        return anim.getSprite();
    }
    
}
