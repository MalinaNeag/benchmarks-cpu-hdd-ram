/**
 * This class provides a benchmark implementation for comparing fixed-point and floating-point arithmetic on CPUs.
 */
package bench.cpu;

import bench.IBenchmark;

public class CPUFixedVsFloatingPoint implements IBenchmark {

    private int result;
    private int size;

    /**
     * Initializes the benchmark with the given parameters.
     *
     * @param params An array containing the size of the test data.
     */
    @Override
    public void initialize(Object ... params) {
        this.size = (Integer)params[0];
    }

    /**
     * Performs a warm-up phase for the benchmark.
     */
    @Override
    public void warmUp() {
        for (int i = 0; i < size; ++i) {
            result = i/256 ; // fixed
            result = i/256; // floating
        }
    }

    /**
     * Runs the benchmark with default parameters.
     *
     * @deprecated This method should not be used.
     */
    @Override
    @Deprecated
    public void run() {
    }

    /**
     * Runs the benchmark with the given parameters.
     *
     * @param options An array containing optional parameters for the benchmark.
     */
    @Override
    public void run(Object ...options) {
        result = 0;

        switch ((CPUNumberRepresentation) options[0]) {
            case FLOATING:
                for (int i = 0; i < size; ++i)
                    result += (i/256.0);
                break;
            case FIXED:
                for (int i = 0; i < size; ++i)
                    result += (i/256);
                break;
            default:
                break;
        }

    }

    /**
     * Cancels the benchmark execution.
     */
    @Override
    public void cancel() {

    }

    /**
     * Cleans up any resources used by the benchmark.
     */
    @Override
    public void clean() {
    }

    /**
     * Returns the result of the benchmark.
     *
     * @return A string representation of the result.
     */
    @Override
    public String getResult() {
        return String.valueOf(result);
    }

}