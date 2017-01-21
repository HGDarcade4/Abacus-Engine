package test;

import java.util.HashMap;
import java.util.Map;

import abacus.graphics.AnimationRegistry;

public final class Resource {

    private static final Map<String, AnimationRegistry> animations = new HashMap<>();
    
    private Resource() {}
    
    public static void register(String name, AnimationRegistry reg) {
        animations.put(name, reg);
    }
    
    public static AnimationRegistry getAnimationRegistry(String name) {
        AnimationRegistry reg = animations.get(name);
        
        if (reg != null) {
            reg = reg.copy();
        }
        
        return reg;
    }
    
}
