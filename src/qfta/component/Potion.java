package qfta.component;

import abacus.gameobject.GameComponent;
import abacus.gameobject.GameComponentProperties;

public class Potion extends GameComponent {

    public Potion() {}
    
    @Override
    public GameComponent copy() {
        return new Potion();
    }

    @Override
    public GameComponent load(GameComponentProperties props) {
        return new Potion();
    }

}
