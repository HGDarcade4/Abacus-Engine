package abacus.gameobject;

public abstract class GameComponentLoader {

    private String compName;
    
    public GameComponentLoader(String componentName) {
        compName = componentName;
    }
    
    public abstract GameComponent createGameComponent(GameComponentProperties props);
    
    public String getComponentName() {
        return compName;
    }
    
}
