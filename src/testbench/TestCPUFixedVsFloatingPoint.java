package testbench;

import bench.IBenchmark;
import bench.cpu.CPUFixedVsFloatingPoint;
import bench.cpu.CPUNumberRepresentation;
import logging.ConsoleLogger;
import logging.ILogger;
import logging.TimeUnit;
import timing.ITimer;
import timing.Timer;

public class TestCPUFixedVsFloatingPoint {

    public static void main(String[] args) throws IllegalAccessException {

        ITimer timer = new Timer();
        ILogger log = /* new FileLogger("bench.log"); */new ConsoleLogger();
        TimeUnit timeUnit = TimeUnit.Milli;

        IBenchmark bench = new CPUFixedVsFloatingPoint();
        bench.initialize(10000000);
        bench.warmUp();

        timer.start();
        bench.run(CPUNumberRepresentation.FIXED);
//		bench.run(NumberRepresentation.FLOATING);
        long time = timer.stop();
        log.writeTime("Finished in", time, timeUnit);
        //log.write("Result is", bench.getResult());

        bench.clean();
        log.close();
    }
}
