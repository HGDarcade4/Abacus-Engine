/* Time.java
 * This class handles the time within the game
 */

package abacus;

/* Time class for handling time
 */
public final class Time {

	/* Instance Variables */
    public static final long SECOND = 1000000000L;
    
	// Returns the current system time
    public static long getTime() {
        return System.nanoTime();
    }
    
	// Sleeps for the given amount of time in milliseconds
    public static void sleep(int millis) {
        try {
            Thread.sleep(millis);
        }
        catch (Exception e) {
            // TODO should probably do something...
        }
    }
}
