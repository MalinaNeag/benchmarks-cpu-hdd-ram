package testbench;

import bench.IBenchmark;
import bench.cpu.CPUDigitsOfPi;
import logging.ConsoleLogger;
import logging.ILogger;
import logging.TimeUnit;
import timing.ITimer;
import timing.Timer;

/**
 *  TestCPUDigitsOfPi is a class that benchmarks the CPUDigitsOfPi class by executing its run() method multiple times and logging the elapsed time for each run.
 *  The time unit used for logging the elapsed time is specified by the 'timeUnit' variable. The benchmark results are logged using the same ConsoleLogger object.
 */
public class TestCPUDigitsOfPi{

    /**
     * The main method of TestCPUDigitsOfPi class.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) throws IllegalAccessException {

        ITimer timer = new Timer();
        ILogger log = new ConsoleLogger();
        IBenchmark bench = new CPUDigitsOfPi();

        final int workload = 12000; // The precision of Pi
        TimeUnit timeUnit = TimeUnit.Sec;

        bench.initialize(workload);

        for (int i = 0; i < 1; i++){
            timer.resume();
            bench.run();
            long time = timer.pause();
            //log.writeTime("Run " + i + ": ", time, timeUnit);
            log.write(time);
        }

        //log.writeTime(timer.stop(), timeUnit);
    /*
    //The value for Pi:
    BigDecimal result = ((CPUDigitsOfPi)bench).getResult();
    log.write("Pi is: "+ result);
     */
    }

}
