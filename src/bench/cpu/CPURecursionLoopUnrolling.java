package bench.cpu;

import bench.IBenchmark;

public class CPURecursionLoopUnrolling implements IBenchmark {

    private long prime, size;
    long sum = 0;
    private int count;
    @Override
    public void initialize(Object... params) {
        size = (long)params[0];
    }
    @Override
    public void run() {

    }

    @Override
    public void run(Object... params) {

        boolean isUsedUnrolled = (boolean) params[0];
        int unrollLevel;
        long x;

        if(isUsedUnrolled == true){

            unrollLevel = (int) params[1];
            sum = recursiveUnrolled(1, unrollLevel, size, 0);
        }
        else {
            x = recursive(1, size, 0);
        }

    }

    private long recursive(long start, long size, int counter) {
        if (start > size) {
            //System.out.println("Reached nr " + start + "/" + size + " after " + counter + " calls.");
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
            //System.out.println("Reached nr " + start + "/" + size + " after " + counter + " calls.");
            return sum;
        } catch (NoClassDefFoundError e) {
            //System.out.println("Reached nr " + start + "/" + size + " after " + counter + " calls.");
            return sum;
        }
        return sum;
    }

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

    public long getPrime(){
        return this.prime;
    }

    public int getCount(){
        return this.count;
    }

    @Override
    public void clean() {

    }

    @Override
    public void cancel() {

    }

    @Override
    public void warmUp() {

    }

    @Override
    public Object getResult() {
        return sum;

    }
}
