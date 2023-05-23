/** The HDDWriteSpeed class implements the IBenchmark interface to benchmark
 * the write speed of a hard disk drive (HDD).
 */
package bench.hdd;

import java.io.IOException;
import bench.IBenchmark;

public class HDDWriteSpeed implements IBenchmark {

    /**
     * Initializes the HDDWriteSpeed benchmark.
     * @param params - parameters (not used)
     */
    @Override
    public void initialize(Object... params) {
    }

    /**
     * Throws an UnsupportedOperationException because the run() method is not implemented. Use the run(Object...) method instead.
     */
    @Override
    public void run() {
        throw new UnsupportedOperationException("Method not implemented. Use run(Object) instead");
    }

    /**
     * Runs the HDDWriteSpeed benchmark with the given options.
     * @param options - an array of objects containing options for the benchmark:
     *                - options[0] : String - either "fs" - fixed size, or "fb" - fixed buffer
     *                - options[1] : Boolean - true/false whether the written files should be deleted at the end
     */
    @Override
    public void run(Object... options) {
        FileWriter writer = new FileWriter();

        String option = (String) options[0];
        Boolean clean = (Boolean) options[1];

        String prefix = "D:\\IdeaProjects";
        String suffix = ".dat";
        int minIndex = 0;
        int maxIndex = 8;
        long fileSize = 512*1024*1024L;
        int bufferSize = 2*1024;

        try {
            if (option.equals("fs"))
                writer.streamWriteFixedFileSize(prefix, suffix, minIndex, maxIndex, fileSize, clean);
            else if (option.equals("fb"))
                writer.streamWriteFixedBufferSize(prefix, suffix, minIndex, maxIndex, bufferSize, clean);
            else
                throw new IllegalArgumentException("Argument " + options[0].toString() + " is undefined");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cleans up any resources used by the HDDWriteSpeed benchmark.
     */
    @Override
    public void clean() {
    }

    /**
     * Cancels the HDDWriteSpeed benchmark.
     */
    @Override
    public void cancel() {
    }

    /**
     * Warms up the HDDWriteSpeed benchmark.
     */
    @Override
    public void warmUp() {
    }

    /**
     * Returns the result of the HDDWriteSpeed benchmark.
     * @return the result of the benchmark (null or MBps)
     */
    public String getResult() {
        return null; // or MBps
    }
}
