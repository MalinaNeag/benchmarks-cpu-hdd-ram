package testbench;

import bench.DemoBenchmark;
import bench.IBenchmark;
import bench.cpu.CPUThreadedHashing;
import bench.cpu.CPUThreadedHashing2;
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
        int hashCode = 524381996; //frodo 3 sec
        //int hashCode = 52703576; //airbnb 45 sec
        //int hashCode = 605107138; //brasov 51 sec

        //Homework
        //int hashCode = 1018655712; //break 1.444999 sec
        //int hashCode = 317266982; //direct 52.5079358 sec

        timer.start();
        bench.run(maxLength, nThreads, hashCode);
        long time = timer.stop();
        TimeUnit timeUnit = TimeUnit.Sec;
        log.writeTime("Finished in", time, timeUnit);
        log.write("Result is", bench.getResult());
    }
}
