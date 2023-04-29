/**
 * This class provides a main method to run the CPUFixedPoint benchmark and output the results to the console.
 * It uses a Timer object to measure the elapsed time and a ConsoleLogger object to write the results to the console.
 * The workload and benchmark options are defined at the beginning of the main method.
 * It then runs the benchmark and calculates the number of operations per second (OPS) and million operations per second (MOPS).
 * The results are written to the console using the ConsoleLogger object.
 */
package testbench;

import bench.IBenchmark;
import bench.cpu.CPUFixedPoint;
import bench.cpu.EnumOptionsFixedPoint;
import logging.ConsoleLogger;
import logging.ILogger;
import logging.TimeUnit;
import timing.ITimer;
import timing.Timer;

public class TestCPUFixedPoint {

    public static void main(String[] args) {

        ITimer timer = new Timer();
        ILogger log = new ConsoleLogger();
        IBenchmark bench = new CPUFixedPoint();
        TimeUnit timeUnit = TimeUnit.Sec;

        EnumOptionsFixedPoint option = EnumOptionsFixedPoint.ArrayAccessAndAssignment;

        final long workload = 1000000000;
        timer.start();
        bench.initialize(workload, option);
        bench.warmUp();
        bench.run();
        long stopTime = timer.stop();

        long operations;

        switch (option) {
            case IntegerArithmeticTest:
                operations =  29 * workload;
                break;
            case BranchingTest:
                operations = 11 * workload;
                break;
            case ArrayAccessAndAssignment:
                operations = 14 * workload;
                break;
            default:
                operations = 0;
                break;
        }

        double seconds = TimeUnit.Sec.timeConversion(stopTime);
        double OPS = operations / seconds;
        double MOPS = OPS / 1e6;

        log.write("\nMOPS: ", MOPS);
        log.write("\nOPS: ", OPS);
    }
}