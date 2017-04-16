package qfta;

import java.awt.event.KeyEvent;

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

    public static final int NUM_CHOICES = 5;
    
    public static final int ATTACK = 4;
    public static final int CHANCE = 3;
    public static final int BLOCK = 2;
    public static final int DRINK = 1;
    public static final int RUN = 0;
    
    private GameObject player, enemy;
    private BattleStats pStats, eStats;
    private Sound music;
    private Sound punch, magic, sound;
    
    // instance of world renderer
    private WorldRenderer worldRender;
    
    // game font
    private GameFont font;
    
    private Sprite textBox;
    private Sprite cave;
    
    private int cursor = 0;
    private String infoText = "";
    private String enemyText = "";
    
    private boolean playerTurn = true;
    private int animation = 0;
    
    private boolean doInput = true;
    
    private boolean defeated = false;
    
    private int enemyAttack = 0;
    
    public void setBackground(String file) {
        cave = engine.getResourceLoader().loadTexture(file).getSprite();
    }
    
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
        
        pStats = player.get(BattleStats.class);
        eStats = enemy.get(BattleStats.class);
        
        if (!eStats.music.equals("")) {
            music = engine.getResourceLoader().loadSound(eStats.music);
            music.setVolume(0.8f);
        }
        
        infoText = eStats.name + " has appeared!";
        enemyText = "";
        
        animation = 0;
        playerTurn = true;
        doInput = true;
        defeated = false;
    }
    
    @Override
    public void init(ResourceLoader loader) {
        music = loader.loadSound("res/battle_music.wav");
        music.setVolume(0.75f);
        
        worldRender = new WorldRenderer(engine.getRenderer());
        worldRender.setScale(4);
        
        font = loader.getFontCreator().createBasicFont("res/font.png", 10, 12, 0xFFFFFF);
        font.setSize(24);
        
        textBox = loader.loadTexture("res/textBox.png").getSprite();
        cave = loader.loadTexture("res/cave.png").getSprite();
        
        magic = loader.loadSound("res/MagicBolt.wav");
        punch = loader.loadSound("res/punch.wav");
        sound = punch;
    }

    @Override
    public void enter() {
        music.playAndLoop();
        cursor = NUM_CHOICES - 1;
    }

    @Override
    public void update(Input input) {
        if (eStats.health <= 0) {
            infoText = eStats.name + " has been defeated!";
            if (input.anyKeyJustDown()) {
                done();
            }
        }
        
        animation--;
        if (animation == 16) {
            if (!playerTurn) {
                int attack = ATTACK;
                
                if (Math.random() < 0.2) {
                    attack = CHANCE;
                }
                if (Math.random() < 0.2) {
                    attack = BLOCK;
                }
                if (eStats.health < eStats.maxHealth && Math.random() < 0.3 && eStats.potions > 0) {
                    attack = DRINK;
                }
                
                enemyAttack = attack;
                sound = null;
                if (enemyAttack == ATTACK) {
                    sound = punch;
                }
                else if (enemyAttack == CHANCE) {
                    sound = magic;
                }
                else {
                    sound = null;
                }
            }
            
            if (sound != null) sound.play();
        }
        else if (animation < 0) {
            animation = 0;
            
            if (!doInput) {
                if (playerTurn) {
                    playerTurn = false;
                    doTurn(pStats, eStats, cursor);
                    animation = 32;
                }
                else {
                    if (!defeated) {
                        doTurn(eStats, pStats, enemyAttack);
                    }
                    doInput = true;
                    playerTurn = true;
                }
            }
            
        }
        
        if (doInput) {
            if (input.getJustDownKey(KeyEvent.VK_UP)) {
                cursor++;
                if (cursor >= NUM_CHOICES) {
                    cursor = NUM_CHOICES - 1;
                }
            }
            if (input.getJustDownKey(KeyEvent.VK_DOWN)) {
                cursor--;
                if (cursor < 0) {
                    cursor = 0;
                }
            }
            if (input.getJustDownKey("spacebar")) {
                animation = 32;
                doInput = false;
                infoText = "";
                enemyText = "";
                if (sound != null) sound.stop();
                sound = null;
                if (cursor == ATTACK) {
                    sound = punch;
                }
                else if (cursor == CHANCE) {
                    sound = magic;
                }
                else {
                    sound = null;
                }
            }
        }
    }

    @Override
    public void render(Renderer renderer) {
        cave.setLayer(0);
        cave.setAlpha(1.0f);
        cave.draw(0, 0, renderer.getWidth(), renderer.getHeight());
        
        textBox.setLayer(1000);
        textBox.setAlpha(1.0f);
        textBox.draw(20, renderer.getHeight() / 2 - 20, renderer.getWidth() - 40, renderer.getHeight() / 2);
        
        if (pStats.health < 0) pStats.health = 0;
        if (eStats.health < 0) eStats.health = 0;
        
        if (playerTurn) {
            player.getTransform().x = -70 + 16 - Math.abs(animation - 16);
        }
        else {
            enemy.getTransform().x = 70 - 16 + Math.abs(animation - 16);
        }
        
        player.render(worldRender);
        enemy.render(worldRender);
        
        int start = renderer.getHeight() / 2 + 0;
        
        font.draw(infoText, 72, renderer.getHeight() / 2 + renderer.getHeight() / 2 - 60 - 12);
        font.draw(enemyText, 72, renderer.getHeight() / 2 + renderer.getHeight() / 2 - 60 - 12 - 30);
        
        font.draw("Basic Attack", 72, start + 30 * ATTACK);
        font.draw("Magic Attack", 72, start + 30 * CHANCE);
        font.draw("Attempt Block", 72, start + 30 * BLOCK);
        font.draw("Run Away", 72, start + 30 * RUN);
        font.draw("Drink Potion", 72, start + 30 * DRINK);
        
        font.draw(">", 52, start + 30 * cursor);
    }

    @Override
    public void pause() {
    }

    @Override
    public void exit() {
        music.stop();
    }

    @Override
    public void end() {
    }
    
    private void done() {
        pStats.isBattling = false;
        ((TileMapState)engine.getGameStateManager().getGameStateById(QuestForTheAbacus.ID_PLAY)).downWithBattle(pStats);
        swapState(QuestForTheAbacus.ID_PLAY);
    }
    
    private void doTurn(BattleStats current, BattleStats other, int attack) {
        if (defeated) return;
        
        int dmg = 0;
        
        current.blocking = false;
        
        String text = "";
        
        switch (attack) {
        case ATTACK:
            dmg = (int)(Math.random() * current.level * 6) + 1;
            if (other.blocking && Math.random() < 0.8) {
                dmg = 0;
            }
            text = current.name + " dealt " + dmg + " HP of damage.";
            other.health -= dmg;
            break;
        case CHANCE:
            if (Math.random() < 0.2) {
                dmg = (int)(Math.random() * 4 * other.level) + 1;
                text = current.name + " took " + dmg + " HP of damage!";
                current.health -= dmg;
            }
            else {
                dmg = (int)(Math.random() * 8 * other.level) + 8;
                if (other.blocking && Math.random() < 0.3) {
                    dmg = 0;
                }
                text = current.name + " dealt " + dmg + " HP of damage!";
                other.health -= dmg;
            }
            break;
        case BLOCK:
            current.blocking = true;
            text = current.name + " will try to block next attack.";
            break;
        case DRINK:
            current.blocking = true;
            if (current.potions <= 0) {
                text = current.name + " could not drink a potion!";
            }
            else {
                int hp = 15;
                current.potions--;
                current.health += hp;
                if (current.health > current.maxHealth) {
                    current.health = current.maxHealth;
                }
                text = current.name + " gained " + hp + " HP.";
            }
            break;
        case RUN:
            text = "Success! You ran away!";
            done();
            break;
        }
        
        if (other.health <= 0 || current.health <= 0) {
            defeated = true;
            if (pStats.health <= 0) {
                swapState(QuestForTheAbacus.ID_DEATH);
            }
        }
        
        if (pStats == current) {
            infoText = text;
            if (defeated && Math.random() < 0.2) {
                enemyText = pStats.name + " leveled up!";
                pStats.maxHealth += 20;
                pStats.health = pStats.maxHealth;
                pStats.level++;
            }
            else {
                enemyText = "";
            }
        }
        else {
            enemyText = text;
        }
    }

}
