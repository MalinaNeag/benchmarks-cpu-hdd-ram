package bench.cpu;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import bench.IBenchmark;

public class CPUThreadedHashing2 implements IBenchmark {

    private String result;
    private final AtomicBoolean running = new AtomicBoolean(true);

    @Override
    public void initialize(Object... params) {
    }

    @Override
    public void warmUp() {
    }

    @Override
    public void run() {
        throw new UnsupportedOperationException("Method not implemented. Use run(Object) instead");
    }

    @Override
    public void run(Object... options) {

        // maximum text length
        int maxTextLength = (Integer) options[0];
        // thread pool size
        int nThreads = (Integer) options[1];
        // hash code
        int hashCode = (Integer) options[2];

        // try to break these hash codes (in ascending order of difficulty):
        // 524381996
        // 52703576
        // 605107138

        int length = 2;

        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        HashManager hasher = new HashManager();
        String text = "aa";

        while (running.get()) {
            int counter = 0;
            while (counter < nThreads) {
                HashBreakerTask worker = new HashBreakerTask(hasher, text, hashCode);
                // assign new runnable to executor
                executor.execute(worker);
                // get next string (new task) OR NULL if final combination "zzz..z" reached
                text = hasher.getNextString(text);

                if (text == null) {
                    length++;
                    text = "aa";
                    for (int i = 2; i < length; ++i)
                        text += "a";
                }
                counter++;
            }

            try {
                executor.shutdown();
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (!result.equals("")) {
                running.set(false);
                break;
            }

            executor = Executors.newFixedThreadPool(nThreads);
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
        running.set(false);
    }

    @Override
    public String getResult() {
        return result;
    }

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
                //save password text as result to be printed on screen
                result = text;
                cancel();
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
            for (int i = 0; i < text.length(); i++) {
                a = (a + charSet.indexOf(text.charAt(i))) % 65521;
                b = (b + a) % 65521;
            }
            return (b << 16) | a;
        }

        /**
         * Returns the next string in the sequence, following the lexicographical order.
         * If the last string "zz...z" is reached, returns null.
         *
         * @param text The current string
         * @return The next string in the sequence or null if end of sequence is reached
         */
        public String getNextString(String text) {
            int n = text.length();
            char[] charArray = text.toCharArray();
            for (int i = n - 1; i >= 0; i--) {
                if (charArray[i] == 'z') {
                    charArray[i] = 'a';
                } else {
                    charArray[i] = (char) (charArray[i] + 1);
                    return new String(charArray);
                }
            }
            return null;
        }
    }
}