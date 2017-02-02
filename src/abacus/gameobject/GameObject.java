package abacus.gameobject;

import java.util.HashMap;

import abacus.graphics.WorldRenderer;
import abacus.ui.Input;

public class GameObject {
    
    public static final int MAX_COMPONENTS = 32;
    
    private static final HashMap<Class<?>, Integer> compIds = new HashMap<>();
    private static int nextId = 0;
    
    private boolean remove;
    private Transform transform;
    private GameComponent[] comps;
    
    public GameObject() {
        transform = new Transform();
        remove = false;
        
        comps = new GameComponent[MAX_COMPONENTS];
    }
    
    public void attach(GameComponent c) {
        int id = getId(c.getClass());
        
        if (comps[id] != null) {
            detach(comps[id]);
        }
        
        comps[id] = c;
        c.gameObject = this;
        c.attach();
    }
    
    public boolean detach(GameComponent c) {
        int id = getId(c.getClass());
        
        if (c == comps[id]) {
            comps[id] = null;
            c.detach();
            c.gameObject = null;
            return true;
        }
        else {
            return false;
        }
    }
    
    @SuppressWarnings("unchecked")
    public <E extends GameComponent> E get(Class<E> c) {
        int id = getId(c);
        
        GameComponent comp = comps[id];
        
        return comp == null ? null : (E)comp;
    }
    
    public <E extends GameComponent> boolean has(Class<E> c) {
        return get(c) != null;
    }
    
    public void update(Scene scene, Input input) {
        for (GameComponent gc : comps) {
            if (gc != null) gc.preUpdate(scene, input);
        }
        
        for (GameComponent gc : comps) {
            if (gc != null) gc.update(scene, input);
        }
        
        for (GameComponent gc : comps) {
            if (gc != null) gc.postUpdate(scene, input);
        }
    }
    
    public void render(WorldRenderer r) {
        for (GameComponent gc : comps) {
            if (gc != null) gc.render(r);
        }
    }
    
    public void collide(GameObject other) {
        for (GameComponent gc : comps) {
            if (gc != null) gc.collide(other);
        }
    }
    
    public Transform getTransform() {
        return transform;
    }
    
    public void flagForRemoval() {
        remove = true;
    }
    
    public boolean shouldRemove() {
        return remove;
    }
    
    private static int getId(Class<?> c) {
        Integer id = compIds.get(c);
        
        if (id == null) {
            compIds.put(c, nextId);
            return nextId++;
        }
        else {
            return id;
        }
    }
    
}
