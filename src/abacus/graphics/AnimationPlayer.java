package abacus.graphics;

public class AnimationPlayer implements Renderable {

    private AnimationData data;
    private int frame;
    
    public AnimationPlayer(AnimationData data) {
        this.data = data;
        frame = 0;
    }
    
    public AnimationPlayer copy() {
        AnimationPlayer a = new AnimationPlayer(data);
        a.frame = frame;
        return a;
    }
    
    public AnimationData getAnimationData() {
        return data;
    }
    
    public void update() {
        frame = ++frame % data.numFrames();
    }
    
    public void reset() {
        frame = 0;
    }
    
    public Sprite getSprite() {
        return data.getFrame(frame);
    }
    
}
