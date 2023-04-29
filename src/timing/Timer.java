package timing;

/**
 * Timer class is an implementation of the ITimer interface used to measure elapsed time.
 */
public class Timer implements ITimer {

    private long elapsed = 0;
    private long stored = 0;
    private TimerState state;

    /**
     * Constructor to initialize the Timer object.
     */
    public Timer() {
        state = TimerState.Stopped;
    }

    /**
     * Starts the timer.
     */
    @Override
    public void start() {
        stored = 0;
        resume();
    }

    /**
     * Stops the timer and returns the total elapsed time.
     *
     * @return the total elapsed time in nanoseconds
     */
    @Override
    public long stop() {
        if (state.equals(TimerState.Running)) {
            elapsed = System.nanoTime() - elapsed;
            state = TimerState.Stopped;
            stored += elapsed;

        }
        return stored;
    }

    /**
     * Pauses the timer and returns the total elapsed time so far.
     *
     * @return the total elapsed time in nanoseconds
     */
    @Override
    public long pause() {
        elapsed = System.nanoTime() - elapsed;
        state = TimerState.Paused;
        stored += elapsed;

        return stored;
    }

    /**
     * Resumes the timer from the last paused state or start state.
     */
    @Override
    public void resume() {
        state = TimerState.Running;
        elapsed = System.nanoTime();
    }
}
