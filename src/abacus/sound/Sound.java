package abacus.sound;

/*
 * Use this to play sounds
 * 
 * While I am going to keep working on how sound works, 
 * this class will hopefully stay mostly the same
 */
public class Sound {

    // name of the sound in SoundManager
    private String name;
    
    // right now only SoundManager makes these
    // NOTE: use the ResourceLoader to load sounds, 
    // it takes care of that for you
    protected Sound(String name) {
        this.name = name;
    }
    
    // play a sound once, restarts the sound if it was playing
    public void play() {
        SoundManager.playSound(name, false);
    }
    
    // play a sound continuously, restarts the sound if it was playing
    public void playAndLoop() {
        SoundManager.playSound(name, true);
    }
    
    // resets and stops the sound
    public void stop() {
        SoundManager.stopSound(name);
    }
    
}
