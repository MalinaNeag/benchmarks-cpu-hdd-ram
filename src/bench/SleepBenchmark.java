package bench;

/**
 * A benchmark that makes the main thread sleep for a specified number of milliseconds
 */
public class SleepBenchmark implements IBenchmark {

    /** The number of milliseconds to sleep for. */
    public int n;

    /**
     * Runs the benchmark by sleeping for {@code n} milliseconds.
     */
    @Override
    public void run() {
        try {
            Thread.sleep(n);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Does not use any parameters.
     * @param params the parameters to be ignored
     */
    @Override
    public void run(Object... params) {

    }

    /**
     * Initializes the benchmark with the specified sleep time.
     * @param params an array containing one element: the number of milliseconds to sleep for
     */
    @Override
    public void initialize(Object... params) {
        n = (int) params[0];
    }

    /**
     * Does not perform any cleanup actions yet.
     */
    @Override
    public void clean() {

    }

    /**
     * Does not provide a way to cancel the benchmark yet.
     */
    @Override
    public void cancel() {

    }

    /**
     * Does not provide a way to warm up the benchmark yet.
     */
    @Override
    public void warmUp() {

    }

    @Override
    public Object getResult() {
        return null;
    }

}

