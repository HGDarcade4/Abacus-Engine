package qfta.component;

import java.awt.Color;

import abacus.ResourceLoader;
import abacus.gameobject.GameComponent;
import abacus.gameobject.GameComponentProperties;
import abacus.gameobject.Transform;
import abacus.graphics.AnimationData;
import abacus.graphics.AnimationPlayer;
import abacus.graphics.AnimationRegistry;
import abacus.graphics.SpriteSheet;
import abacus.graphics.Texture;
import abacus.graphics.WorldRenderer;

public class HumanoidRenderer extends GameComponent {

    public static final int CLOTH = 0xA4A2A5;
    public static final int CLOTH_SHADOW = 0x818082;
    public static final int CLOTH_OUTLINE = 0x343035;
    
    public boolean randomColor = false;
    
    private ResourceLoader loader;
    private Texture texture;
    private AnimationRegistry animation, clothes;
    
    public HumanoidRenderer(ResourceLoader loader) {
        this.loader = loader;
        texture = loader.loadTexture("res/rpg_male.png");
        buildAnimations();
    }
    
    public HumanoidRenderer copy() {
    	HumanoidRenderer hr = new HumanoidRenderer(loader);
    	hr.randomColor = randomColor;
    	return hr;
    }
    
    public HumanoidRenderer load(GameComponentProperties props) {
        HumanoidRenderer c = copy();
        
        if (props.containsBoolean("randomColor")) {
            c.randomColor = props.getBoolean("randomColor");
        }
        
        return c;
    }
    
    public void setClothingColor(int col) {
        Color color = new Color(col);
        
        int[] replace = new int[] {
                CLOTH, color.getRGB(),
                CLOTH_SHADOW, color.darker().getRGB(),
                CLOTH_OUTLINE, Color.BLACK.getRGB(),
        };
        
        texture = loader.colorize(texture, replace);
        buildAnimations();
    }
    
    @Override
    public void attach() {
        if (randomColor) {
            setClothingColor((int)(Math.random() * 0x1000000));
        }
    }
    
    @Override
    public void render(WorldRenderer r) {
        if (!gameObject.has(CharacterMovement.class)) return;
        
        CharacterMovement movement = gameObject.get(CharacterMovement.class);
        Transform tfm = gameObject.getTransform();
        
        animation.setCurrent(movement.dir);
        clothes.setCurrent(movement.dir);
        
        if (movement.moving) {
            animation.play();
            clothes.play();
        }
        else {
            animation.pauseAndReset();
            clothes.pauseAndReset();
        }
        
        r.drawCharacterSprite(animation, tfm.x, tfm.y, 16f, 24f);
        r.drawCharacterSprite(clothes, tfm.x, tfm.y, 16f, 24f);
    }
    
    private void buildAnimations() {
        SpriteSheet player = new SpriteSheet(texture, 16, 24);
        
        animation = new AnimationRegistry();
        clothes = new AnimationRegistry();
        
        AnimationData data, cloth;
        for (int i = 0; i < 4; i++) {
            data = new AnimationData();
            cloth = new AnimationData();
            int speed = 6;
            data.addFrame(player.getSprite(0 + 0, i), speed);
            data.addFrame(player.getSprite(0 + 1, i), speed);
            data.addFrame(player.getSprite(0 + 0, i), speed);
            data.addFrame(player.getSprite(0 + 2, i), speed);
            animation.register(i, new AnimationPlayer(data));
            
            cloth.addFrame(player.getSprite(3 + 0, i), speed);
            cloth.addFrame(player.getSprite(3 + 1, i), speed);
            cloth.addFrame(player.getSprite(3 + 0, i), speed);
            cloth.addFrame(player.getSprite(3 + 2, i), speed);
            clothes.register(i, new AnimationPlayer(cloth));
        }
    }
    
}
