/**
 The HDDRandomAccess class implements the IBenchmark interface and provides methods for performing
 random access operations on a hard disk drive (HDD) file.
 The class creates a temporary file with random content for reading and writing operations.
 It supports reading and writing of fixed-size data or performing operations for a fixed time.
 */
package bench.hdd;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Random;

import timing.Timer;
import bench.IBenchmark;

public class HDDRandomAccess implements IBenchmark {

    private final static String PATH = "D:\\test.raf";
    private String result;

    /**
     * Initializes the benchmark by creating a temporary file with random content for reading and writing.
     *
     * @param params An array of parameters. The first parameter should be the desired file size in bytes.
     */
    @Override
    public void initialize(Object... params) {
        File tempFile = new File(PATH);
        RandomAccessFile rafFile;
        long fileSizeInBytes = (Long) params[0];

        // Create a temporary file with random content to be used for
        // reading and writing
        try {
            rafFile = new RandomAccessFile(tempFile, "rw");
            Random rand = new Random();
            int bufferSize = 4 * 1024; // 4KB
            long toWrite = fileSizeInBytes / bufferSize;
            byte[] buffer = new byte[bufferSize];
            long counter = 0;

            while (counter++ < toWrite) {
                rand.nextBytes(buffer);
                rafFile.write(buffer);
            }
            rafFile.close();
            tempFile.deleteOnExit();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
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
     * Runs the benchmark with the specified options.
     *
     * @param options An array of options that control the benchmark behavior.
     *                For example, to perform random reads, use: `{"r", "fs", 4*1024}`.
     */
    @Override
    public void run(Object ...options) {
        Object[] param = (Object[]) options;
        // used by the fixed size option
        final int steps = 25000; // number of I/O ops
        // used by the fixed time option
        final int runtime = 5000; // ms

        try {
            // read benchmark
            if (String.valueOf(param[0]).toLowerCase().equals("r")) {
                // buffer size given as parameter
                int bufferSize = Integer.parseInt(String.valueOf(param[2]));

                // read a fixed size and measure time
                if (String.valueOf(param[1]).toLowerCase().equals("fs")) {

                    long timeMs = new RandomAccess().randomReadFixedSize(PATH, bufferSize, steps);
                    result = steps + " random reads in " + timeMs + " ms ["  + (steps * bufferSize / 1024 / 1024) + " MB, " + 1.0 * (steps * bufferSize / 1024 / 1024) / timeMs * 1000 + "MB/s]";
                }
                // read for a fixed time amount and measure time
                else if (String.valueOf(param[1]).toLowerCase().equals("ft")) {

                    int ios = new RandomAccess().randomReadFixedTime(PATH, bufferSize, runtime);
                    result = ios + " I/Os per second [" + ((long)ios * bufferSize / 1024 / 1024) + " MB, " + 1.0 * ((long)ios * bufferSize / 1024 / 1024) / runtime * 1000 + "MB/s]";
                }
                else
                {
                    throw new UnsupportedOperationException("Read option \"" + String.valueOf(param[1]) + "\" is not implemented");
                }
            }
            // write benchmark
            else if (String.valueOf(param[0]).toLowerCase().equals("w")) {
                // your code here: implement all cases for param[[0]: fs, ft, other
                // buffer size given as parameter
                int bufferSize = Integer.parseInt(String.valueOf(param[2]));

                // read a fixed size and measure time
                if (String.valueOf(param[1]).toLowerCase().equals("fs")) {

                    long timeMs = new RandomAccess().randomWriteFixedSize(PATH, bufferSize, steps);
                    result = steps + " random writes in " + timeMs + " ms ["  + (steps * bufferSize / 1024 / 1024) + " MB, " + 1.0 * (steps * bufferSize / 1024 / 1024) / timeMs * 1000 + "MB/s]";
                }
                // read for a fixed time amount and measure time
                else if (String.valueOf(param[1]).toLowerCase().equals("ft")) {

                    int ios = new RandomAccess().randomWriteFixedTime(PATH, bufferSize, runtime);
                    result = ios + " I/Os per second [" + ((long)ios * bufferSize / 1024 / 1024) + " MB, " + 1.0 * ((long)ios * bufferSize / 1024 / 1024) / runtime * 1000 + "MB/s]";
                }
                else {
                    throw new UnsupportedOperationException("Write option \"" + String.valueOf(param[1]) + "\" is not implemented");
                }
            }
            else {
                throw new UnsupportedOperationException("Benchmark option \"" + String.valueOf(param[0]) + "\" is not implemented");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Cleans up the benchmark resources (not used in this implementation).
     */
    @Override
    public void clean() {
        /* not used */
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

    public String getResult()
    {
        return String.valueOf(result);
    }

    /**
     * The `RandomAccess` class encapsulates the random access operations.
     * It provides methods for reading and writing data from a file at random positions.
     */
    class RandomAccess {
        private Random random;

        RandomAccess()
        {
            random = new Random();
        }

        /**
         * Reads data from random positions into a fixed size buffer from a
         * given file using RandomAccessFile
         *
         * @param filePath
         *            Full path to file on disk
         * @param bufferSize
         *            Size of byte buffer to read at each step
         * @param toRead
         *            Number of steps to repeat random read
         * @return Amount of time needed to finish given reads in milliseconds
         * @throws IOException
         */
        public long randomReadFixedSize(String filePath, int bufferSize, int toRead) throws IOException {
            // file to read from
            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
            // size of file
            long fileSize = file.getChannel().size();
            // counter for number of reads
            int counter = 0;
            // buffer for reading
            byte[] bytes = new byte[bufferSize];
            // timer
            Timer timer = new Timer();
            long randomPosition;

            timer.start();
            while (counter++ < toRead) {
                randomPosition=random.nextLong();
                randomPosition=randomPosition<0 ? -randomPosition : randomPosition;
                randomPosition=randomPosition%(fileSize-bufferSize);
                file.seek(randomPosition);
                file.read(bytes);
            }

            file.close();
            return timer.stop() / 1000000; // ns to ms!
        }

        /**
         * Reads data from random positions into a fixed size buffer from a
         * given file using RandomAccessFile for one second, or any other given
         * time
         *
         * @param filePath
         *            Full path to file on disk
         * @param bufferSize
         *            Size of byte buffer to read at each step
         * @param millis
         *            Total time to read from file
         * @return Number of reads in the given amount of time
         * @throws IOException
         */
        public int randomReadFixedTime(String filePath, int bufferSize, int millis) throws IOException {
            // file to read from
            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
            // size of file
            long fileSize = file.getChannel().size();
            // counter for number of reads
            int counter = 0;
            // buffer for reading
            byte[] bytes = new byte[bufferSize];
            long randomPosition;

            long now = System.nanoTime();
            // read for a fixed amount of time
            while ((System.nanoTime() - now) / 1000000 < millis) {
                randomPosition=random.nextLong();
                randomPosition=randomPosition<0 ? -randomPosition : randomPosition;
                randomPosition=randomPosition%(fileSize-bufferSize);
                file.seek(randomPosition);
                file.read(bytes);
                counter++;
            }

            file.close();
            return counter;
        }

        /**
         * Read data from a file at a specific position
         *
         * @param filePath
         *            Path to file
         * @param position
         *            Position in file
         * @param size
         *            Number of bytes to reads from the given position
         * @return Data that was read
         * @throws IOException
         */
        public byte[] readFromFile(String filePath, long position, int size) throws IOException {
            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
            file.seek(position);
            byte[] bytes = new byte[size];
            file.read(bytes);
            file.close();
            return bytes;
        }

        /**
         * Write data to a file at a specific position
         *
         * @param filePath
         *            Path to file
         * @param data
         *            Data to be written
         * @param position
         *            Start position in file
         * @throws IOException
         */
        public void writeToFile(String filePath, String data, long position) throws IOException {

            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
            file.seek(position);
            file.write(data.getBytes());
            file.close();
        }

        /**
         * Writes random data to a file for a fixed number of times.
         *
         * @param filePath   the path of the file to write to
         * @param bufferSize the size of the buffer used for writing
         * @param toWrite    the number of times to write random data to the file
         * @return the elapsed time in milliseconds for the write operation
         * @throws IOException if an I/O error occurs
         */
        public long randomWriteFixedSize(String filePath, int bufferSize, int toWrite) throws IOException {
            // file to read from
            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
            // size of file
            long fileSize = file.getChannel().size();
            // counter for number of reads
            int counter = 0;
            // buffer for reading
            byte[] bytes = new byte[bufferSize];
            // timer
            Timer timer = new Timer();
            long randomPosition;

            timer.start();
            while (counter++ < toWrite) {
                randomPosition=random.nextLong();
                randomPosition=randomPosition<0 ? -randomPosition : randomPosition;
                randomPosition=randomPosition%(fileSize-bufferSize);
                random.nextBytes(bytes);
                file.seek(randomPosition);
                file.write(bytes);
            }

            file.close();
            return timer.stop() / 1000000; // ns to ms!
        }

        /**
         * Writes random data to a file for a fixed amount of time.
         *
         * @param filePath   the path of the file to write to
         * @param bufferSize the size of the buffer used for writing
         * @param millis     the duration in milliseconds to write random data to the file
         * @return the number of times random data was written to the file
         * @throws IOException if an I/O error occurs
         */
        public int randomWriteFixedTime(String filePath, int bufferSize, int millis) throws IOException {
            // file to read from
            RandomAccessFile file = new RandomAccessFile(filePath, "rw");
            // size of file
            long fileSize = file.getChannel().size();
            // counter for number of reads
            int counter = 0;
            // buffer for reading
            byte[] bytes = new byte[bufferSize];
            long randomPosition;

            long now = System.nanoTime();
            // read for a fixed amount of time
            while ((System.nanoTime() - now) / 1000000 < millis) {
                randomPosition=random.nextLong();
                randomPosition=randomPosition<0 ? -randomPosition : randomPosition;
                randomPosition=randomPosition%(fileSize-bufferSize);
                random.nextBytes(bytes);
                file.seek(randomPosition);
                file.write(bytes);
                counter++;
            }

            file.close();
            return counter;
        }
    }

}
