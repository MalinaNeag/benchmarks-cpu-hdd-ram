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
        //log.writeTime("Finished in ",  stopTime, TimeUnit.Sec);
        double seconds= TimeUnit.Sec.timeConversion(stopTime);

        double OPS = operations/seconds;

        double MOPS = OPS/1e6;

        log.write("\nMOPS: ", MOPS);
        log.write("\nOPS: ", OPS);
    }
}
