package bench.cpu;

import bench.IBenchmark;

public class CPUThreadedRoots implements IBenchmark {

    private double result;
    private int size;
    private boolean running;

    @Override
    public void initialize(Object... params) {
        this.size = (int)params[0];
    }

    @Override
    public void warmUp() {
        // call run method: call run() once
        // detect number of cores: Runtime.....availableProcessors();
        int cores = Runtime.getRuntime().availableProcessors();
        run(cores);
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException(
                "Method not implemented. Use run(Objects...) instead");
    }

    @Override
    public void run(Object... options) {
        // options[0] -> number of threads
        // ...
        int nThreads = (int)options[0];

        Thread[] threads = new Thread[nThreads];

        // e.g. 1 to 10,000 on 4 threads = 2500 jobs per thread
        final int jobPerThread = size/nThreads; /**/

        running = true; // flag used to stop all started threads
        // create a thread for each runnable (SquareRootTask) and start it
        for (int i = 0; i < nThreads; ++i) {
            SquareRootTask srt = new SquareRootTask(i * jobPerThread, (i + 1) * jobPerThread);

            threads[i] = new Thread(srt);
            threads[i].start();

            result += srt.getResult();
        }

        // join threads
        for (int i = 0; i < nThreads; ++i) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void clean() {
        // only implement if needed
    }

    @Override
    public void cancel() {

    }

    //@Override
    public String getResult() {
        return String.valueOf(result);
    }

    class SquareRootTask implements Runnable {

        private int from, to;
        private final double precision = 1e-4; // fixed
        private double result = 0.0;

        public SquareRootTask(int from, int to) {
            this.from = from;
            this.to = to;
        }

        @Override
        public void run() {
            // compute Newtonian square root on each number from i = 'from' to 'to', and also check 'running'
            // save (+=) each computed square root in the local 'result' variable
            // extra: send 'result' back to main thread and sum up with all results
            for(int i = from; i <= to && running == true; i++) {
                result += getNewtonian(i);
            }
        }

        private double getNewtonian(double x) {
            // ... implement the algorithm for Newton's square root(x) here
            double current = x/2.0;
            double previous;

            do {
                previous = current;
                current = (x/current + current)/2.0;

            } while(Math.abs(current-previous) > precision);

            return current;
        }

        public double getResult() {
            return result;
        }

        // extra: compute sum, pass it back to wrapper class. Use synchronized
    }

}