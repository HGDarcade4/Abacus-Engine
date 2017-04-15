package qfta;

import abacus.GameState;
import abacus.ResourceLoader;
import abacus.gameobject.GameObject;
import abacus.graphics.GameFont;
import abacus.graphics.Renderer;
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
    
    public void setGameObjects(GameObject player, GameObject enemy) {
        this.player = player;
        this.enemy = enemy;
        
        player.getTransform().x = -70;
        player.getTransform().y = 20;
        enemy.getTransform().x = 70;
        enemy.getTransform().y = 20;
        
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
        
        font = loader.getFontCreator().createBasicFont("res/font.png", 10, 12, 0xFFFF77);
        font.setSize(24);
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
        renderer.clearScreen(0x55, 0x44, 0x55);
        
        player.render(worldRender);
        enemy.render(worldRender);
        
        worldRender.drawText(font, "This is a test!", 0, 0);
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
