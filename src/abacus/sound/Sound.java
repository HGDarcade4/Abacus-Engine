package abacus.sound;

public class Sound {

    private String name;
    
    protected Sound(String name) {
        this.name = name;
    }
    
    public void play() {
        SoundManager.playSound(name, false);
    }
    
    public void playAndLoop() {
        SoundManager.playSound(name, true);
    }
    
    public void stop() {
        SoundManager.stopSound(name);
    }
    
}
