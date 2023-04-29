package bench;

import java.util.*;
/**
 * The DemoBenchmark class implements the IBenchmark interface and provides
 * a benchmark for sorting an array of integers using bubble sort algorithm.
 * */
public class DemoBenchmark implements IBenchmark {

    protected int n;
    protected int[] array;

    /**
     * Runs the benchmark.
     */
    @Override
    public void run(){

    }

    /**
     * Runs the benchmark.
     */
    @Override
    public void run(Object ... params) {

        boolean isSorted;
        int sortedPortion = 1;

        do{
            isSorted = true;
            for(int i = 0; i < this.n - sortedPortion; i++) {
                if(array[i] > array[i+1]) {
                    int temp = array[i];
                    array[i] = array[i+1];
                    array[i+1] = temp;

                    isSorted = false;
                }
            }

            sortedPortion++;

        }while(!isSorted);

    }

    /**
     * Initializes the benchmark.
     *
     * @param params an array of Objects containing the benchmark parameters.
     * params[0] is an Integer specifying the number of integers to be sorted.
     */
    @Override
    public void initialize(Object ... params) {

        Random random = new Random();
        this.n = (Integer) params[0];
        this.array = new int[n];

        for (int i=0 ; i<n; i++){
            array[i] = random.nextInt(1000);
        }

    }

    /**
     * Cleans up any resources used by the benchmark.
     */
    @Override
    public void clean(){

    }

    /**
     * Cancels the benchmark if it is running.
     */
    @Override
    public void cancel() {

    }

    /**
     * Warms up the benchmark.
     */
    @Override
    public void warmUp() {

    }

    /**
     * Gets the result of the benchmark.
     * @return the sorted array of integers.
     */
    @Override
    public Object getResult() {
        return array;
    }

}
