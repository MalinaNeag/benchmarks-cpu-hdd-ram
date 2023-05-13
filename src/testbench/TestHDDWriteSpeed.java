package testbench;

import bench.IBenchmark;
import bench.hdd.HDDWriteSpeed;

public class TestHDDWriteSpeed {
    /**
     * The main method of the TestHDDWriteSpeed class is used to create a new instance of the
     * HDDWriteSpeed benchmark, run it with the file name "fb", and display the output to the console.
     * @param args The command line arguments passed to the program (not used).
     */
    public static void main(String[] args) {

        IBenchmark bench = new HDDWriteSpeed();

        bench.run("fb", true);
    }
}
