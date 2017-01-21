package abacus;

public final class Time {

    public static final long SECOND = 1000000000L;
    
    public static long getTime() {
        return System.nanoTime();
    }
    
    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        }
        catch (Exception e) {
            // TODO should probably do something...
        }
    }
    
}
