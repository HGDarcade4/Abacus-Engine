package qfta.component;

import abacus.ResourceLoader;
import abacus.gameobject.GameComponent;
import abacus.gameobject.GameComponentProperties;
import abacus.graphics.GameFont;
import abacus.graphics.WorldRenderer;

public class BattleStats extends GameComponent {

    public int maxHealth = 1;
    public int health = 1;
    public boolean isPlayer = false;
    public boolean isBattling = false;
    public String name = "Unnamed Entity";
    public boolean blocking = false;
    public int level = 1;
    public int potions = 0;
    
    private static GameFont font = null;
    
    public BattleStats(ResourceLoader loader) {
        if (font == null) {
            font = loader.getFontCreator().createBasicFont("res/font.png", 10, 12, 0xFFFFFF);
            font.setSize(18);
        }
    }
    
    public void set(BattleStats b) {
        maxHealth = b.maxHealth;
        health = b.health;
        isBattling = b.isBattling;
        isPlayer = b.isPlayer;
        blocking = b.blocking;
        level = b.level;
        potions = b.potions;
        name = b.name;
    }
    
    private BattleStats() {}
    
    public void render(WorldRenderer wr) {
        float x, y;
        
        if (isBattling) {
            x = gameObject.getTransform().x;
            y = gameObject.getTransform().y;
            wr.drawText(font, "HP " + health + " / " + maxHealth, x, y - 8);
            wr.drawText(font, "Potions " + potions, x, y - 16);
            wr.drawText(font, "LVL " + level, x, y - 24);
        }
    }
    
    @Override
    public GameComponent copy() {
        BattleStats copy = new BattleStats();
        copy.set(this);
        return copy;
    }

    @Override
    public GameComponent load(GameComponentProperties props) {
        BattleStats stats = new BattleStats();
        if (props.containsBoolean("player")) {
            stats.isPlayer = props.getBoolean("player");
        }
        if (props.containsNumber("health")) {
            stats.health = (int)props.getNumber("health");
        }
        if (props.containsNumber("maxHealth")) {
            stats.maxHealth = (int)props.getNumber("maxHealth");
        }
        if (props.containsString("name")) {
            stats.name = props.getString("name");
        }
        if (props.containsNumber("level")) {
            stats.level = (int)props.getNumber("level");
        }
        if (props.containsNumber("potions")) {
            stats.potions = (int)props.getNumber("potions");
        }
        return stats;
    }

}
