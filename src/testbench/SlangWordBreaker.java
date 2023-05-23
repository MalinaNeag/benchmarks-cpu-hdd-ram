package testbench;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

public class SlangWordBreaker {

    private final int targetHash = 132368363;
    private final Pattern wordPattern = Pattern.compile("^[a-z]{1,9}$");

    public static void main(String[] args) throws IOException, InterruptedException {
        SlangWordBreaker breaker = new SlangWordBreaker();
        breaker.findSlangWord();
    }

    private void findSlangWord() throws IOException, InterruptedException {
        List<String> words = scrapeSlangWords();
        int cpuCount = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(cpuCount);

        for (String word : words) {
            executor.submit(() -> {
                if (hash(word) == targetHash) {
                    System.out.println("Found: " + word);
                    executor.shutdownNow();
                }
            });
        }

        executor.shutdown();
        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
    }

    private List<String> scrapeSlangWords() throws IOException {
        List<String> words = new ArrayList<>();
        int totalPages = 288;

        for (int i = 1; i <= totalPages; i++) {
            Document doc = Jsoup.connect("https://www.oxfordinternationalenglish.com/dictionary-of-british-slang/" + i).get();
            Elements slangWordElements = doc.select("div.row > div > a");
            for (Element element : slangWordElements) {
                //System.out.println(element);
                String word = element.text().trim().toLowerCase();
                if (wordPattern.matcher(word).matches()) {
                    words.add(word);
                }
            }
        }

        return words;
    }

    private int hash(String text) {
        int a = 0;
        int b = 0;
        for (char c : text.toCharArray()) {
            String charSet = "abcdefghijklmnopqrstuvwxyz";
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
}