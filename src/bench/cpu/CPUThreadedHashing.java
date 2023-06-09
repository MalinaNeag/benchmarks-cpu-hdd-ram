/**

 The CPUThreadedHashing class implements the IBenchmark interface for a multithreaded hash code breaker.
 Given a hash code, the class attempts to find a corresponding string using a brute-force search strategy.
 The search space is the set of all possible strings of length up to a specified maximum, constructed using
 the English alphabet.
 This class uses an ExecutorService to manage a thread pool, with each thread being assigned a task to
 compute the hash of a particular string. The class uses two stop conditions: (1) the length of the strings
 being searched exceeds a maximum specified length, and (2) a hash code corresponding to a string is found.
 */
package bench.cpu;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bench.IBenchmark;

public class CPUThreadedHashing implements IBenchmark {

    private String result;
    volatile boolean running = true;

    @Override
    public void initialize(Object... params) {

    }

    @Override
    public void warmUp() {

    }

    @Override
    public void run() {
        throw new UnsupportedOperationException(
                "Method not implemented. Use run(Object) instead");
    }

    /**
     * This method runs the benchmark to find a string that hashes to a given hash code.
     *
     * @param options an array of three parameters:
     * - maxTextLength: the maximum length of the string to be searched
     * - nThreads: the number of threads to use in the thread pool
     * - hashCode: the target hash code
     */
    @Override
    public void run(Object... options) {

        // maximum text length
        int maxTextLength = (Integer)options[0];
        // thread pool size
        int nThreads = (Integer)options[1];
        // hash code
        int hashCode = (Integer)options[2];

        // try to break these hash codes (in ascending order of difficulty):
        // 524381996
        // 52703576
        // 605107138

        int length = 2;

        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        HashManager hasher = new HashManager();
        String text = "aa";

        while (running) {
            HashBreakerTask worker = new HashBreakerTask(hasher, text, hashCode);
            // assign new runnable to executor
            executor.execute(worker);
            // get next string (new task) OR NULL if final combination "zzz..z" reached
            text = hasher.getNextString(text);

            // stop search condition#1
            if (length > maxTextLength) {
                running = false;
            }

            // reset string to "aaa...a" with length+1
            if (text == null) {
                length++;
                text = "aa";
                for (int i = 2; i < length; ++i)
                    text += "a";
            }
        }

        // stop executor
        executor.shutdown();
        while (!executor.isTerminated()) {
        }

    }

    @Override
    public void clean() {
    }

    @Override
    public void cancel() {

    }

    /**
     * Returns the string that was found to produce the target hash code.
     *
     * @return the string that was found to produce the target hash code
     */
    @Override
    public String getResult() {
        return String.valueOf(result);
    }

    /**
     * The HashBreakerTask class is a Runnable that is used to compute the hash of a given string and compare it
     * to a target hash code. If a match is found, the task sets the running flag to false and saves the password
     * as the result to be printed on screen.
     */
    class HashBreakerTask implements Runnable {

        // used to compute hashes from strings
        private final HashManager hasher;
        // the string to be hashed
        private final String text;
        // the expected hash output
        private final int expectedHash;

        public HashBreakerTask(HashManager hasher, String text, int expectedHash) {
            this.hasher = hasher;
            this.text = text;
            // 'text' is hashed and compared to 'expected hash'
            this.expectedHash = expectedHash;
        }

        @Override
        public void run() {
            // if we found the hash
            if (expectedHash == hasher.hash(text)) {
                // stop condition#2
                running = false;
                //save password text as result to be printed on screen
                result = text;
            }
        }
    }

    /**
     * Used to compute hashes from strings
     */
    class HashManager {

        // do not change alphabet
        private final String charSet = "abcdefghijklmnopqrstuvwxyz";

        // do not change function
        public int hash(String text) {
            int a = 0;
            int b = 0;
            for (char c : text.toCharArray()) {
                int index = charSet.indexOf(c);
                if (index == -1)
                    index = charSet.length() + 1;
                for (int i = 0; i < 17; i++) {
                    a = a * -6 + b + 0x74FA - index;
                    b = b / 3 + a + 0x81BE - index;
                }
            }

            return (a ^ b) % Integer.MAX_VALUE;
        }

        public String getNextString(String text) {

            int[] index = new int[text.length()];
            int end = charSet.length() - 1;

            for (int i = 0; i < text.length(); i++) {
                char c = text.charAt(i);
                index[i] = charSet.indexOf(c);
            }

            int carry = 1;
            for (int i = index.length - 1; i >= 0; i--) {
                index[i] += carry;
                carry = 0;
                if (index[i] > end) {
                    index[i] = 0;
                    carry = 1;
                } else {
                    break;
                }
            }

            if (carry == 1) {
                return null;
            }

            String result = "";
            for (int i = 0; i < index.length; i++) {
                result += charSet.charAt(index[i]);
            }

            return result.toString();
        }


        // can be used as an alternative to getNextString, but it will be infinitely slower to break longer hashes
        public String getRandomString(int length) {
            String text = "";

            for (int i = 0; i < length; i++) {
                Random rand =  new Random();
                char c = charSet.charAt(rand.nextInt(charSet.length()));
                text += c;
            }

            return text;
        }
    }

}