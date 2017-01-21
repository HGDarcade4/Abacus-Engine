package abacus.graphics;

import java.util.ArrayList;
import java.util.List;

public class AnimationData {

    private List<Sprite> frames;
    
    public AnimationData() {
        frames = new ArrayList<>();
    }
    
    public Sprite getFrame(int frame) {
        frame = (frame % numFrames() + numFrames()) % numFrames();
        
        return frames.get(frame);
    }
    
    public void addFrame(Sprite sprite, int ticks) {
        for (int i = 0; i < ticks; i++) {
            frames.add(sprite);
        }
    }
    
    public int numFrames() {
        return frames.size();
    }
    
}
