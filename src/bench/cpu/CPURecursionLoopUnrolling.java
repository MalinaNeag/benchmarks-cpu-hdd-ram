/**
 * The CPURecursionLoopUnrolling class implements the IBenchmark interface
 * and provides methods to calculate the sum of prime numbers using recursion
 * and loop unrolling techniques.
 */
package bench.cpu;

import bench.IBenchmark;

public class CPURecursionLoopUnrolling implements IBenchmark {

    private long prime, size;
    long sum = 0;
    private int count;

    /**
     * Initializes the size parameter required for the calculation.
     *
     * @param params The size of the range of numbers to calculate the sum for.
     */
    @Override
    public void initialize(Object... params) {
        size = (long)params[0];
    }

    @Override
    public void run() {
    }

    /**
     * Executes the benchmark with the given parameters.
     *
     * @param params An array of parameters containing a boolean value indicating whether
     *               loop unrolling should be used or not, and an integer value indicating
     *               the level of unrolling to be used.
     */
    @Override
    public void run(Object... params) {

        boolean isUsedUnrolled = (boolean) params[0];
        int unrollLevel;
        long x;

        if(isUsedUnrolled){

            unrollLevel = (int) params[1];
            sum = recursiveUnrolled(1, unrollLevel, size, 0);
        }
        else {
            x = recursive(1, size, 0);
        }

    }

    /**
     * Calculates the sum of prime numbers using recursion.
     *
     * @param start   The starting number for the calculation.
     * @param size    The size of the range of numbers to calculate the sum for.
     * @param counter A counter to keep track of the number of recursive calls.
     * @return The sum of prime numbers in the range.
     */
    private long recursive(long start, long size, int counter) {
        if (start > size) {
            return sum;
        }
        try {
            if (isPrime((int) start)) {
                this.prime = start;
                this.count = counter;
                sum += start + recursive(start + 1, size, counter + 1);
            } else {
                return sum += recursive(start + 1, size, counter + 1);
            }
        } catch (StackOverflowError e) {
            return sum;
        } catch (NoClassDefFoundError e) {
            return sum;
        }
        return sum;
    }
    /**
     * A private method that recursively calculates the sum of prime numbers within a specified range.
     * @param start the starting point of the range
     * @param unrollLevel the level of loop unrolling
     * @param size the ending point of the range
     * @param counter the counter of recursive calls
     * @return the sum of prime numbers within the range
     */
    private long recursiveUnrolled(long start, int unrollLevel, long size, int counter) {
        if (start > size) {
            return 0;
        }
        for (int i = 0; i < unrollLevel; i++) {
            if (isPrime((int) start)) {
                this.prime = start;
                this.count = counter;
                sum += start;
            }
            start++;
        }
        try {
            sum += recursiveUnrolled(start, unrollLevel, size, counter + 1);
        } catch (StackOverflowError e) {
            return 0;
        } catch (NoClassDefFoundError e) {
            return 0;
        }
        return sum;
    }

    /**
     * A private method that checks whether a given number is a prime number or not.
     * @param x the number to check
     * @return true if the given number is a prime number, false otherwise
     */
    private boolean isPrime(int x){

        if (x <= 2){
            return true;
        }
        for (int i=2; i <= Math.sqrt(x); i++){
            if (x%i == 0){
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the last prime number found by the recursiveUnrolled method.
     * @return the last prime number found
     */
    public long getPrime(){
        return this.prime;
    }

    /**
     * Gets the number of recursive calls made by the recursiveUnrolled method.
     * @return the number of recursive calls that took place
     */
    public int getCount(){
        return this.count;
    }

    /**
     * Unused method to clean up resources.
     */
    @Override
    public void clean() {
    }

    /**
     * Unused method to cancel the benchmark.
     */
    @Override
    public void cancel() {
    }

    /**
     * Unused method to warm up.
     */
    @Override
    public void warmUp() {
    }

    /**
     * Returns the result of the recursiveUnrolled method, which is the sum of prime numbers within the specified range.
     * @return the sum of prime numbers within the range
     */
    @Override
    public Object getResult() {
        return sum;
    }
}


