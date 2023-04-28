package testbench;


import bench.SleepBenchmark;
import logging.ILogger;
import timing.Timer;
import logging.ConsoleLogger;
import timing.ITimer;

/**
 * TestSleepBenchmark is a class for testing the SleepBenchmark class.
 */
public class TestSleepBenchmark {

    /**
     * The main method of TestSleepBenchmark class.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        ITimer timer = new Timer();
        SleepBenchmark bench = new SleepBenchmark();
        ILogger log = new ConsoleLogger();

        // Initialize the benchmark with a sleep time of 10 ms.
        bench.initialize(10);

        // Start the timer and run the benchmark.
        timer.start();
        bench.run();
        long time = timer.stop();

        // Calculate the offset between the actual sleep time and the requested sleep time.
        long offset = (long) (100 * (time - (bench.n)*Math.pow(10, 6)) / ((bench.n)*Math.pow(10, 6)));

        // Log the results.
        log.write("Given value: ", bench.n, "ms");
        log.write("Finished after: ", time, "ns");
        log.write("Offset is: ", offset, "%");

    }

}

