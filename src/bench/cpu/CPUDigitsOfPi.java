package bench.cpu;

import bench.IBenchmark;

import java.math.BigDecimal;

/**
 * The CPUDigitsOfPi class implements the IBenchmark interface for calculating the
 * digits of Pi using the Gauss-Legendre algorithm.
 */
public class CPUDigitsOfPi implements IBenchmark {
    private GaussLegendre gl;
    private BigDecimal result;

    /**
     * Returns the generated result.
     *
     * @return the result
     */
    public BigDecimal getResult() {
        return result;
    }

    /**
     * Runs the benchmark.
     */
    @Override
    public void run() {
        result = GaussLegendre.approxPi();
    }

    /**
     * Runs the benchmark with the specified options.
     *
     * @param options the options to use when running the benchmark
     */
    @Override
    public void run(Object... options){
        result = GaussLegendre.approxPi();
    }

    /**
     * Initializes the benchmark with the specified parameters.
     *
     * @param params the parameters to use when initializing the benchmark
     */
    @Override
    public void initialize(Object... params) {
        gl = new GaussLegendre();
        int precision = (Integer) params[0];
        gl.setPrecision(precision);
    }

    /**
     * Cleans up any resources used by the benchmark.
     */
    @Override
    public void clean() {

    }

    /**
     * Cancels the benchmark.
     */
    @Override
    public void cancel() {

    }

    /**
     * Warms up the benchmark by running it several times.
     */
    @Override
    public void warmUp() {
        for (int i=0; i<10; i++){
            result = GaussLegendre.approxPi();
        }
    }

}
