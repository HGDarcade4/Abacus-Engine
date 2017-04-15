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
    
    private static GameFont font = null;
    
    public BattleStats(ResourceLoader loader) {
        if (font == null) {
            font = loader.getFontCreator().createBasicFont("res/font.png", 10, 12, 0xFFFFFF);
            font.setSize(18);
        }
    }
    
    private BattleStats() {}
    
    public void render(WorldRenderer wr) {
        float x, y;
        
        if (isBattling) {
            x = gameObject.getTransform().x;
            y = gameObject.getTransform().y;
            wr.drawText(font, "HP: " + health + " / " + maxHealth, x, y - 8);
        }
    }
    
    @Override
    public GameComponent copy() {
        BattleStats copy = new BattleStats();
        copy.health = health;
        copy.maxHealth = maxHealth;
        copy.isPlayer = isPlayer;
        copy.isBattling = isBattling;
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
        return stats;
    }

}
