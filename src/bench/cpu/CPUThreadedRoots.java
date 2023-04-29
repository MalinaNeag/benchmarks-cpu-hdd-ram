/**
 * The CPUThreadedRoots class implements the IBenchmark interface
 * for computing square roots of numbers using Newton's method
 * with multiple threads.
 * It uses the SquareRootTask class to define the task performed by each thread.
 */
package bench.cpu;

import bench.IBenchmark;

public class CPUThreadedRoots implements IBenchmark {

    private double result;
    private int size;
    private boolean running;

    /**
     * Initializes the size of the input to be used in the benchmark.
     * @param params an array of objects that contains the parameters for the benchmark
     *               params[0] is expected to be an Integer that represents the size of the input
     */
    @Override
    public void initialize(Object... params) {
        this.size = (int)params[0];
    }

    /**
     * Runs the benchmark once to warm up the JVM and to detect the number of available processors.
     */
    @Override
    public void warmUp() {
        int cores = Runtime.getRuntime().availableProcessors();
        run(cores);
    }

    /**
     * Throws an UnsupportedOperationException because this method is not used.
     */
    @Override
    public void run() {
        throw new UnsupportedOperationException(
                "Method not implemented. Use run(Objects...) instead");
    }

    /**
     * Runs the benchmark with the specified number of threads.
     * @param options an array of objects that contains the options for the benchmark
     *                options[0] is expected to be an Integer that represents the number of threads to use
     */
    @Override
    public void run(Object... options) {
        int nThreads = (int)options[0];

        Thread[] threads = new Thread[nThreads];

        final int jobPerThread = size/nThreads;

        running = true;
        for (int i = 0; i < nThreads; ++i) {
            SquareRootTask srt = new SquareRootTask(i * jobPerThread, (i + 1) * jobPerThread);

            threads[i] = new Thread(srt);
            threads[i].start();

            result += srt.getResult();
        }

        for (int i = 0; i < nThreads; ++i) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Cleans up any resources used by the benchmark.
     */
    @Override
    public void clean() {
        // only implement if needed
    }

    /**
     * Cancels the execution of the benchmark.
     */
    @Override
    public void cancel() {

    }

    /**
     * Returns the result of the benchmark as a String.
     * @return the result of the benchmark as a String
     */
    public String getResult() {
        return String.valueOf(result);
    }

    /**
     * A class representing a task that computes the Newtonian square root of each number from a given range of integers.
     * The result of each computation is stored in a local variable. The task is run on a separate thread.
     */
    public class SquareRootTask implements Runnable {

        private int from, to;
        private final double precision = 1e-4; // fixed
        private double result = 0.0;

        /**
         * Constructs a new SquareRootTask object with the given range of integers.
         *
         * @param from the starting integer of the range
         * @param to   the ending integer of the range
         */
        public SquareRootTask(int from, int to) {
            this.from = from;
            this.to = to;
        }

        /**
         * Runs the task, computing the Newtonian square root of each number in the specified range of integers.
         * The result of each computation is stored in a local variable.
         */
        @Override
        public void run() {
            for (int i = from; i <= to && running == true; i++) {
                result += getNewtonian(i);
            }
        }

        /**
         * Computes and returns the Newtonian square root of a given number.
         *
         * @param x the number to compute the square root of
         * @return the computed square root of the given number
         */
        private double getNewtonian(double x) {
            double current = x / 2.0;
            double previous;
                do {
                    previous = current;
                    current = (x / current + current) / 2.0;

                } while (Math.abs(current - previous) > precision);

                return current;
            }
        /**
         * * Returns the computed result of the task.
         *
         * @return the computed result of the task
         */
        public double getResult() {
            return result;
        }
        // extra: compute sum, pass it back to wrapper class. Use synchronized
    }
}