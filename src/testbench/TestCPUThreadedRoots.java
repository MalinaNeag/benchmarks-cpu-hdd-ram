/**
 * The TestCPUThreadedRoots class is used to benchmark the CPUThreadedRoots class
 * by running it with different thread configurations and computing a score for each run.
 */
package testbench;

import bench.IBenchmark;
import bench.cpu.CPUThreadedRoots;
import logging.ConsoleLogger;
import logging.ILogger;
import logging.TimeUnit;
import timing.Timer;

public class TestCPUThreadedRoots {

    /**
     * The main method initializes the benchmark, runs it with different thread configurations,
     * computes a score for each run, and outputs the results to the console.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Timer timer = new Timer();
        ILogger log = new ConsoleLogger();
        TimeUnit timeUnit = TimeUnit.Sec;

        int workload = 10000000 * 3;

        IBenchmark bench = new CPUThreadedRoots();
        bench.initialize(workload);
        bench.warmUp();

        for(int i = 1; i <= 8; i *= 2) {
            timer.start();
            bench.run(i);
            long time = timer.stop();
            double score = (double) workload / (time * 10E-6 * i);

            log.writeTime("Finished in: ", time, timeUnit);
            log.write("The score for run " + i + " is: " + score);
        }

        double result = Double.parseDouble(((CPUThreadedRoots)bench).getResult());

        //log.write("\nThe result of the computations: " + result);
    }
}