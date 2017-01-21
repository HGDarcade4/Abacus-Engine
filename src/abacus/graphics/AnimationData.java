package abacus.graphics;

import java.util.ArrayList;
import java.util.List;

/*
 * Contains data for animations.
 * Since separate animations of the same kind only differ 
 * in what frame they are in, their reference to sprite 
 * data can be the same, which allows less memory to be
 * consumed. 
 */
public class AnimationData {

    // sprite data
    private List<Sprite> frames;
    
    // ctor
    public AnimationData() {
        frames = new ArrayList<>();
    }
    
    // returns the sprite during the current frame
    // makes sure frame is within the frame range
    public Sprite getFrame(int frame) {
        frame = (frame % numFrames() + numFrames()) % numFrames();
        
        return frames.get(frame);
    }
    
    // adds a sprite to the frame data for [ticks] amount of updates
    public void addFrame(Sprite sprite, int ticks) {
        for (int i = 0; i < ticks; i++) {
            frames.add(sprite);
        }
    }
    
    // returns how many updates long the animation is
    public int numFrames() {
        return frames.size();
    }
    
}
