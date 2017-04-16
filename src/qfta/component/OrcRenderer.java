package qfta.component;

import abacus.ResourceLoader;
import abacus.gameobject.GameComponent;
import abacus.gameobject.GameComponentProperties;
import abacus.gameobject.Transform;
import abacus.graphics.AnimationData;
import abacus.graphics.AnimationPlayer;
import abacus.graphics.SpriteSheet;
import abacus.graphics.WorldRenderer;

public class OrcRenderer extends GameComponent {

    private ResourceLoader loader;
    private AnimationPlayer anim;
    
    public OrcRenderer(ResourceLoader loader) {
        this.loader = loader;
        
        SpriteSheet sheet = new SpriteSheet(loader.loadTexture("res/orc.png"), 32, 48);
        
        System.out.println(sheet.tilesWide());
        
        AnimationData data = new AnimationData();
        for (int i = 0; i < 4; i++) {
            data.addFrame(sheet.getSprite(i), 10);
        }
        
        anim = new AnimationPlayer(data);
        anim.setFrame((int)(Math.random() * data.numFrames()));
    }
    
    private OrcRenderer(OrcRenderer r) {
        this.loader = r.loader;
        this.anim = new AnimationPlayer(r.anim.getAnimationData());
        this.anim.setFrame((int)(Math.random() * r.anim.getAnimationData().numFrames()));
    }
    
    @Override
    public void render(WorldRenderer r) {
        Transform tfm = gameObject.getTransform();
        
        anim.play();
        r.drawCharacterSprite(anim, tfm.x, tfm.y, -32f * 2f, 48f * 2f, 100000);
    }
    
    @Override
    public GameComponent copy() {
        return new OrcRenderer(this);
    }

    @Override
    public GameComponent load(GameComponentProperties props) {
        return copy();
    }

}
