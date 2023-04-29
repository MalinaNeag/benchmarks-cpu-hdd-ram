package testbench;

import bench.IBenchmark;
import bench.cpu.CPUFixedPoint;
import bench.cpu.CPURecursionLoopUnrolling;
import logging.ConsoleLogger;
import logging.ILogger;
import logging.TimeUnit;
import timing.ITimer;
import timing.Timer;

/**
 * Test class for the CPURecursionLoopUnrolling benchmark.
 */

public class TestCPURecursionLoopUnrolling {
    /**
     * Main method that runs the benchmark with the specified workload and loop unrolling factor.
     *
     * @param args an array of two arguments: the workload and the loop unrolling factor
     */
    public static void main(String[] args) {

        long prime;
        int count;

        long workload  = Long.parseLong(args[0]);
        int unroll = Integer.valueOf(args[1]);

        ITimer timer = new Timer();
        ILogger log = new ConsoleLogger();
        IBenchmark bench = new CPURecursionLoopUnrolling();
        TimeUnit timeUnit = TimeUnit.Sec;

        bench.initialize(workload);
        timer.resume();
        if (unroll != 0) {
            bench.run(true, unroll);
        }
        else {
            bench.run(false);
        }

        long time = timer.pause();
        prime = ((CPURecursionLoopUnrolling) bench).getPrime();
        count = ((CPURecursionLoopUnrolling) bench).getCount();

        System.out.println("Reached prime number " + prime + " after " + count + " calls.");
        log.writeTime("Time: ", time, timeUnit);

        double timeInMilli= timeUnit.Nano.timeConversion(time);
        double score = (1000000*workload) / timeInMilli;
        System.out.println("Score: " +  score);


    }
}


