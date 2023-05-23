package testbench;

import bench.IBenchmark;
import bench.ram.VirtualMemoryBenchmark;
import logging.ConsoleLogger;
import logging.ILogger;

/**
 * Test class for the VirtualMemoryBenchmark.
 */
public class TestVirtualMemory {
    public static void main(String[] args) {

        IBenchmark bench = new VirtualMemoryBenchmark();
        ILogger logger = new ConsoleLogger();
        bench.run(11*1024*1024*1024L, 4*1024);
        logger.write(bench.getResult());

    }
}
