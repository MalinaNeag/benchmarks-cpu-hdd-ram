package bench.ram;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Random;

import timing.Timer;
import bench.IBenchmark;

import static java.security.DrbgParameters.nextBytes;

/**
 * This benchmark maps a large file into RAM triggering the virtual memory mechanism. Performs
 * reads and writes to the respective file.<br>
 * The access speeds depend on the file size: if the file can fit the available
 * RAM, then we are measuring RAM speeds.<br>
 * Conversely, we are measuring the access speed of virtual memory, implying a
 * mixture of RAM and HDD access speeds (i.e., lower speeds).
 */
public class VirtualMemoryBenchmark implements IBenchmark {
    MemoryMapper core;
    private String result = "";

    /**
     * Initializes the benchmark with the specified parameters (not used in this implementation).
     *
     * @param params The initialization parameters.
     */
    @Override
    public void initialize(Object... params) {
        /* not used */
    }

    /**
     * Runs the benchmark. This method is not supported and will throw an UnsupportedOperationException.
     * Use the run(Object[]) method instead.
     *
     * @throws UnsupportedOperationException Always thrown to indicate that this method is not supported.
     */
    @Override
    public void run() {
        throw new UnsupportedOperationException("Use run(Object[]) instead");
    }


    /**
     * Runs the virtual memory benchmark.
     *
     * @param options The benchmark options. Expected: {fileSize, bufferSize}.
     */
    @Override
    public void run(Object... options) {
        // expected: {fileSize, bufferSize}
        Object[] params = (Object[]) options;
        long fileSize = Long.parseLong(params[0].toString()); // e.g. 2-16GB
        int bufferSize = Integer.parseInt(params[1].toString()); // e.g. 4+KB

        try {
            core = new MemoryMapper("D:\\000_core", fileSize); // change path as needed
            byte[] buffer = new byte[bufferSize];
            Random rand = new Random();
            NumberFormat format = NumberFormat.getInstance();
            format.setMinimumFractionDigits(2);
            format.setMaximumFractionDigits(2);
            Timer timer = new Timer();

            // write to VM
            timer.start();
            for (long i = 0; i < fileSize; i += bufferSize) {
                // 1. generate random content (see assignments 9,11)
                rand.nextBytes(buffer);
                // 2. write to memory mapper
                core.put(i, buffer);
            }
            double speed1 = (double) (fileSize>>20)/(timer.stop()/Math.pow(10, 9)); /* 3. fileSize/time [MB/s] */

            result = "\nWrote " + (fileSize / 1024 / 1024L)
                    + " MB to virtual memory at " + format.format(speed1) /* 4. speed, with exactly 2 decimals*/ + " MB/s";

            // read from VM
            timer.start();
            for (long i = 0; i < fileSize; i += bufferSize) {
                // 5. get from memory mapper
                buffer = core.get(i, bufferSize);
            }
            double speed2 = (double) (fileSize>>20)/(timer.stop()/Math.pow(10, 9)); /* 6. MB/s */

            // append to previous 'result' string
            result += "\nRead " + (fileSize / 1024 / 1024L)
                    + " MB from virtual memory at " + format.format(speed2) /*7. speed, with exactly 2 decimals*/ + " MB/s";

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (core != null)
                core.purge();
        }
    }
    /**
     * Cleans up the benchmark resources by purging the memory mapper.
     */
    @Override
    public void clean() {
        if (core != null) {
            core.purge();
        }
    }

    /**
     * Cancels the benchmark (not used in this implementation).
     */
    @Override
    public void cancel() {
        /* not used */
    }

    /**
     * Warms up the benchmark (not used in this implementation).
     */
    @Override
    public void warmUp() {
        /* not used */
    }

    /**
     * Retrieves the result of the benchmark.
     *
     * @return The result string containing the write and read speeds.
     */
    public String getResult() {
        return result;
    }

}
