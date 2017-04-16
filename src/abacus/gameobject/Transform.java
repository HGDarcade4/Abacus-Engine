package abacus.gameobject;

public class Transform {

    public float x, y;
    
    public Transform() {
        x = y = 0.0f;
    }
    
    public float distanceFrom(Transform otherObject) {
    	return (float) Math.sqrt(Math.pow((this.x - otherObject.x), 2) + Math.pow((this.y - otherObject.y), 2));
    }
    
}
