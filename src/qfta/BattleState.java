package qfta;

import abacus.GameState;
import abacus.ResourceLoader;
import abacus.gameobject.GameObject;
import abacus.graphics.GameFont;
import abacus.graphics.Renderer;
import abacus.graphics.Sprite;
import abacus.graphics.WorldRenderer;
import abacus.sound.Sound;
import abacus.ui.Input;
import qfta.component.BattleStats;
import qfta.component.CharacterMovement;

public class BattleState extends GameState {

    private GameObject player, enemy;
    private Sound music;
    
    // instance of world renderer
    private WorldRenderer worldRender;
    
    // game font
    private GameFont font;
    
    private Sprite textBox;
    private Sprite cave;
    
    public void setGameObjects(GameObject player, GameObject enemy) {
        this.player = player;
        this.enemy = enemy;
        
        player.getTransform().x = -70;
        player.getTransform().y = -40;
        enemy.getTransform().x = 70;
        enemy.getTransform().y = -40;
        
        player.get(CharacterMovement.class).dir = CharacterMovement.DIR_RIGHT;
        player.get(CharacterMovement.class).moving = false;
        enemy.get(CharacterMovement.class).dir = CharacterMovement.DIR_LEFT;
        enemy.get(CharacterMovement.class).moving = true;
        
        player.get(BattleStats.class).isBattling = true;
        enemy.get(BattleStats.class).isBattling = true;
    }
    
    @Override
    public void init(ResourceLoader loader) {
        music = loader.loadSound("res/battle_music.wav");
        
        worldRender = new WorldRenderer(engine.getRenderer());
        worldRender.setScale(4);
        
        font = loader.getFontCreator().createBasicFont("res/font.png", 10, 12, 0xFFFFFF);
        font.setSize(24);
        
        textBox = loader.loadTexture("res/textBox.png").getSprite();
        cave = loader.loadTexture("res/cave.png").getSprite();
    }

    @Override
    public void enter() {
        music.playAndLoop();
    }

    @Override
    public void update(Input input) {
        
    }

    @Override
    public void render(Renderer renderer) {
        cave.setLayer(0);
        cave.setAlpha(1.0f);
        cave.draw(0, 0, renderer.getWidth(), renderer.getHeight());
        
        textBox.setLayer(1000);
        textBox.setAlpha(1.0f);
        textBox.draw(20, renderer.getHeight() / 2 + 20, renderer.getWidth() - 40, renderer.getHeight() / 2 - 40);
        
        player.render(worldRender);
        enemy.render(worldRender);
        
        font.draw("Nice job, you can't dodge enemies.", 72, renderer.getHeight() / 2 + renderer.getHeight() / 2 - 60 - 12);
        
        font.draw("Attack", 72, renderer.getHeight() / 2 + 40 + 30 * 3);
        font.draw("Chance", 72, renderer.getHeight() / 2 + 40 + 30 * 2);
        font.draw("Block", 72, renderer.getHeight() / 2 + 40 + 30 * 1);
        font.draw("Run", 72, renderer.getHeight() / 2 + 40 + 30 * 0);
        
        font.draw(">", 52, renderer.getHeight() / 2 + 40 + 30 * 3);
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void exit() {
        music.stop();
    }

    @Override
    public void end() {
        // TODO Auto-generated method stub
        
    }

}
