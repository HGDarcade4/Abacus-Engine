package abacus.sound;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public final class SoundManager {

    private static final Map<String, Clip> clips = new HashMap<>();
    
    private SoundManager() {}
    
    public static Sound loadSound(String name, String file) {
        try {
            AudioInputStream in = AudioSystem.getAudioInputStream(new File(file));
            
            Clip sound = AudioSystem.getClip();
            
            sound.open(in);
            
            clips.put(name, sound);
        } catch (UnsupportedAudioFileException e) {
            System.out.println("Unsupported sound file: " + file);
        } catch (IOException e) {
            System.out.println("Could not read sound file: " + file);
        } catch (LineUnavailableException e) {
            System.out.println("Could not create new audio line");
        }
        
        return new Sound(name);
    }
    
    public static void playSound(String name, boolean loop) {
        Clip sound = clips.get(name);
        
        if (sound != null) {
            if (sound.isRunning()) {
                sound.stop();
            }
            sound.setFramePosition(0);
            if (loop) {
                sound.loop(Clip.LOOP_CONTINUOUSLY);
            }
            else {
                sound.loop(0);
            }
            sound.start();
        }
    }
    
    public static void stopSound(String name) {
        Clip sound = clips.get(name);
        
        if (sound != null) {
            if (sound.isRunning()) {
                sound.stop();
            }
        }
    }
    
}
