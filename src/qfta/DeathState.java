package qfta;

import abacus.GameState;
import abacus.ResourceLoader;
import abacus.graphics.GameFont;
import abacus.graphics.Renderer;
import abacus.ui.Input;

public class DeathState extends GameState {

    private GameFont font;
    
    @Override
    public void init(ResourceLoader loader) {
        font = loader.getFontCreator().createBasicFont("res/font.png", 10, 12, 0xFF0000);
        font.setSize(96);
    }

    @Override
    public void enter() {
        
    }

    @Override
    public void update(Input input) {
        if (input.anyKeyJustDown()) {
            engine.getGameStateManager().getGameStateById(QuestForTheAbacus.ID_PLAY).init(engine.getResourceLoader());
            engine.getGameStateManager().getGameStateById(QuestForTheAbacus.ID_TITLE).init(engine.getResourceLoader());
            swapState(QuestForTheAbacus.ID_TITLE);
        }
    }

    @Override
    public void render(Renderer renderer) {
        renderer.clearScreen(0, 0, 0);
        float width = font.getWidth("You Died");
        float height = font.getHeight();
        
        font.draw("You Died", renderer.getWidth()/2 - width/2, renderer.getHeight()/2 - height/2);
    }

    @Override
    public void pause() {
        
    }

    @Override
    public void exit() {
        
    }

    @Override
    public void end() {
        
    }

}
