package qfta.component;

import abacus.ResourceLoader;
import abacus.gameobject.GameComponent;
import abacus.gameobject.GameComponentProperties;
import abacus.gameobject.Transform;
import abacus.graphics.AnimationData;
import abacus.graphics.AnimationPlayer;
import abacus.graphics.SpriteSheet;
import abacus.graphics.WorldRenderer;

public class PotionRenderer extends GameComponent {

    private ResourceLoader loader;
    private AnimationPlayer anim;
    
    public PotionRenderer(ResourceLoader loader) {
        this.loader = loader;
        
        SpriteSheet sheet = new SpriteSheet(loader.loadTexture("res/potion.png"), 16, 16);
        
        AnimationData data = new AnimationData();
        data.addFrame(sheet.getSprite(0), 10);
        data.addFrame(sheet.getSprite(1), 10);
        data.addFrame(sheet.getSprite(2), 10);
        data.addFrame(sheet.getSprite(1), 10);
        
        anim = new AnimationPlayer(data);
        anim.setFrame((int)(Math.random() * data.numFrames()));
    }
    
    private PotionRenderer(PotionRenderer r) {
        this.loader = r.loader;
        this.anim = new AnimationPlayer(r.anim.getAnimationData());
        this.anim.setFrame((int)(Math.random() * r.anim.getAnimationData().numFrames()));
    }
    
    @Override
    public void render(WorldRenderer r) {
        Transform tfm = gameObject.getTransform();
        
        anim.play();
        r.drawCharacterSprite(anim, tfm.x, tfm.y, 16f, 16f);
    }
    
    @Override
    public GameComponent copy() {
        return new PotionRenderer(this);
    }

    @Override
    public GameComponent load(GameComponentProperties props) {
        return copy();
    }

}
