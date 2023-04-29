package bench.cpu;

import bench.IBenchmark;

/**
 * This class provides a benchmark implementation for fixed-point arithmetic on CPUs.
 */
public class CPUFixedPoint implements IBenchmark {

    private long maxIterations;
    private EnumOptionsFixedPoint option;

    /**
     * Initializes the benchmark with the given parameters.
     *
     * @param params An array containing the maximum number of iterations and the benchmark option.
     */
    @Override
    public void initialize(Object... params) {
        maxIterations = (long) params[0];
        option = (EnumOptionsFixedPoint) params[1];
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

    }

    @Override
    public Object getResult() {
        return null;
    }

    /**
     * Runs the benchmark with the given parameters.
     *
     * @param params An array containing optional parameters for the benchmark.
     */
    @Override
    public void run(Object... params) {
        switch (option) {
            case IntegerArithmeticTest -> integerArithmetic();
            case BranchingTest -> branching();
            case ArrayAccessAndAssignment -> arrayOperations();
            default -> {
            }
        }

    }

    /**
     * Runs the benchmark with default parameters.
     */
    @Override
    public void run() {
        run(0);
    }

    /**
     * Performs integer arithmetic operations.
     */
    private void integerArithmetic(){

        int[] num = {0, 1, 2, 3};
        int i;
        int j = 1;
        int k = 2;
        int l = 3;
        int[] res = new int[l-1];

        for(i=0; i < maxIterations; i++) {
            j = num[1] * (k - j) * (l - k);
            k = num[3] * k - (l - j) * k;
            l = (l - k) * (num[2] + j);
            res[l - 2] = j + k + l;
            res[k - 2] = j * k * l;
        }

    }

    /**
     * Performs branching operations.
     */
    public void branching() {

        int[] num = {0, 1, 2, 3};
        int j = 0;

        for (int i = 0; i < maxIterations; i++) {
            if (j == 1) {
                j = num[2];
            } else {
                j = num[3];
            }
            if (j > 2) {
                j = num[0];
            } else {
                j = num[1];
            }
            if (j < 1) {
                j = num[1];
            } else {
                j = num[0];
            }
        }
    }

    /**
     * Performs array access and assignment operations.
     */
    private void arrayOperations() {

        int size = 4;
        int[] a = new int[size];
        int[] b = new int[size];
        int[] c = new int[size];
        int[] d = new int[size];

        //initializing the arrays with some values
        for (int i = 0; i < size; i++) {
            a[i] = i;
            b[i] = size + i - 2;
            c[i] = 4;
            d[i] = size - i - 1;
        }

        for (int i = 0; i < maxIterations; i++) {
            a[2] = b[2];
            b[3] = d[3];
            c[3] = a[d[0]];
            d[0] = a[1];
        }
    }
}

