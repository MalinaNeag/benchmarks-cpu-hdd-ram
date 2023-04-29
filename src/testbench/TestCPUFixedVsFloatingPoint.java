/**
 * The TestCPUFixedVsFloatingPoint class is responsible for executing and benchmarking the
 * CPUFixedVsFloatingPoint benchmark using the fixed and floating point number representations.
 * The benchmark measures the performance of arithmetic operations for both fixed and floating point
 * numbers and returns the results in the form of the time taken to execute the benchmark and the
 * number of operations performed per second.
 */

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
        ILogger log = new ConsoleLogger();
        TimeUnit timeUnit = TimeUnit.Milli;

        IBenchmark bench = new CPUFixedVsFloatingPoint();
        bench.initialize(10000000);
        bench.warmUp();

        timer.start();
        bench.run(CPUNumberRepresentation.FIXED);
		//bench.run(NumberRepresentation.FLOATING);
        long time = timer.stop();
        log.writeTime("Finished in", time, timeUnit);
        //log.write("Result is", bench.getResult());
        log.close();
    }
}

