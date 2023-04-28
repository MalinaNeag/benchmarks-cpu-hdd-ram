package bench;

/**
 * An interface for a benchmark that can be run with different parameters.
 */
public interface IBenchmark {

    /**
     * Runs the benchmark with default parameters.
     */
    void run();

    /**
     * Runs the benchmark with the specified parameters.
     *
     * @param params The parameters to use for the benchmark.
     */
    void run(Object... params);

    /**
     * Initializes the benchmark with the specified parameters.
     *
     * @param params The parameters to use for initializing the benchmark.
     */
    void initialize(Object... params);

    /**
     * Cleans up any resources used by the benchmark.
     */
    void clean();

    /**
     * Cancels the benchmark, stopping any currently running process.
     */
    void cancel();

    void warmUp();

    Object getResult();

}
