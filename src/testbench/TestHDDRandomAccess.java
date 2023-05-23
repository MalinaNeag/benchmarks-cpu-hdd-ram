package testbench;

import bench.IBenchmark;
import bench.hdd.HDDRandomAccess;

/**
 * Test class for running the HDDRandomAccess benchmark.
 */
public class TestHDDRandomAccess {
    public static void main(String[] args) {

        IBenchmark bench = new HDDRandomAccess();

        bench.initialize(32*1073741824l);
        bench.run("r", "ft", 16*16*8*512);
        System.out.println(((HDDRandomAccess)bench).getResult());
    }
}
