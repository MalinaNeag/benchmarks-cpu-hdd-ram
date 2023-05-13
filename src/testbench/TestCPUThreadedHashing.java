/**
 * The TestCPUThreadedHashing class is used to test the CPUThreadedHashing benchmark class. It creates an instance of the benchmark class and runs it with the given parameters.
 * The result of the benchmark is then printed to the console using the ConsoleLogger class.
 */
package testbench;

import bench.IBenchmark;
import bench.cpu.CPUThreadedHashing;
import logging.ILogger;
import logging.TimeUnit;
import timing.Timer;
import logging.ConsoleLogger;
import timing.ITimer;

public class TestCPUThreadedHashing {

    public static void main(String[] args) {

        ITimer timer = new Timer();
        ILogger log = new ConsoleLogger();
        IBenchmark bench = new CPUThreadedHashing();

        int maxLength = 10;
        int nThreads = 8;
        //int hashCode = 524381996; //frodo 3 sec
        //int hashCode = 52703576; //airbnb 12 sec
        //int hashCode = 605107138; //brasov 24 sec

        //Homework
        //int hashCode = 1018655712; //break 0.729486901 sec
        //int hashCode = 317266982; //direct 34.060774401 sec

        int hashCode = 940791187;
        timer.start();
        bench.run(maxLength, nThreads, hashCode);
        long time = timer.stop();
        TimeUnit timeUnit = TimeUnit.Sec;
        log.writeTime("Finished in", time, timeUnit);
        log.write("Result is", bench.getResult());
    }
}


