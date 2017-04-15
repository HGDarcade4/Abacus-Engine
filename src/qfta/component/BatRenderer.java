package qfta.component;

import abacus.ResourceLoader;
import abacus.gameobject.GameComponent;
import abacus.gameobject.GameComponentProperties;
import abacus.gameobject.Transform;
import abacus.graphics.AnimationData;
import abacus.graphics.AnimationPlayer;
import abacus.graphics.SpriteSheet;
import abacus.graphics.WorldRenderer;

public class BatRenderer extends GameComponent {

    private ResourceLoader loader;
    private AnimationPlayer anim;
    
    public BatRenderer(ResourceLoader loader) {
        this.loader = loader;
        
        SpriteSheet sheet = new SpriteSheet(loader.loadTexture("res/enemy_bat.png"), 24, 16);
        
        AnimationData data = new AnimationData();
        for (int i = 0; i < 6; i++) {
            data.addFrame(sheet.getSprite(i), 10);
            
            System.out.println(sheet.getSprite(i));
        }
        
        anim = new AnimationPlayer(data);
        anim.setFrame((int)(Math.random() * data.numFrames()));
    }
    
    private BatRenderer(BatRenderer r) {
        this.loader = r.loader;
        this.anim = new AnimationPlayer(r.anim.getAnimationData());
        this.anim.setFrame((int)(Math.random() * r.anim.getAnimationData().numFrames()));
    }
    
    @Override
    public void render(WorldRenderer r) {
        Transform tfm = gameObject.getTransform();
        
        anim.play();
        r.drawCharacterSprite(anim, tfm.x, tfm.y + 6f, 24f, 16f);
    }
    
    @Override
    public GameComponent copy() {
        return new BatRenderer(this);
    }

    @Override
    public GameComponent load(GameComponentProperties props) {
        return copy();
    }

}
