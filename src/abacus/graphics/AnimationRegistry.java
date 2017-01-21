package abacus.graphics;

public class AnimationRegistry implements Renderable {

    private AnimationPlayer[] anims;
    private int current;
    
    public AnimationRegistry() {
        this(128);
    }
    
    public AnimationRegistry(int size) {
        anims = new AnimationPlayer[size];
        current = 0;
    }
    
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
    
    public void register(int index, AnimationPlayer anim) {
        anims[index] = anim;
    }
    
    public void setCurrent(int index) {
        if (current == index) {
            return;
        }
        
        AnimationPlayer anim = anims[current];
        if (anim != null) {
            anim.reset();
        }
        current = index;
    }
    
    public void update() {
        AnimationPlayer anim = anims[current];
        if (anim == null) {
            return;
        }
        anim.update();
    }
    
    @Override
    public Sprite getSprite() {
        AnimationPlayer anim = anims[current];
        if (anim == null) {
            return null;
        }
        return anim.getSprite();
    }
    
}
