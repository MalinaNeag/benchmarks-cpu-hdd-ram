package timing;

/**
 * An interface for a timer that can be started, stopped, paused, and resumed.
 */
public interface ITimer {

    /**
     * Saves the current elapsed time in a long variable and resets any previously stored total time.
     */
    void start();

    /**
     * Stops the timer and returns the elapsed time since the start of the timer.
     *
     * @return The total elapsed time in nanoseconds.
     */
    long stop();

    /**
     * Resumes the timer from the last paused state. Saves the current elapsed time in a variable, without resetting
     * any previous saved times.
     */
    void resume();

    /**
     * Pauses the timer and returns the elapsed time (difference) since the last start or resume of the timer.
     *
     * @return The elapsed time in nanoseconds.
     */
    long pause();
}

