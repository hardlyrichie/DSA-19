import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class FrequencyPrint {

    // Inspired by counting sort
    static String frequencyPrint(String s) {
        String[] words = s.split(" ");

        HashMap<String, Integer> freq = new HashMap<>();

        int max = 0;

        // Put words in hashmap with word as key, freq as value, O(n)
        for (String word : words) {
            if (freq.containsKey(word)) {
                freq.put(word, freq.get(word) + 1);
            } else {
                freq.put(word, 1);
            }

            if (freq.get(word) > max) {
                max = freq.get(word);
            }
        }

        // From hashmap to array of size of max freq, O(n)
        Stack<String>[] order = new Stack[max];

        for (Map.Entry<String, Integer> entry : freq.entrySet()){
            int i = entry.getValue() - 1;

            if (order[i] == null) {
                order[i] = new Stack<String>();
            }
            order[i].add(entry.getKey());
        }

        StringBuilder sentence = new StringBuilder();
        // From array of words to a string, O(n + n)
        for (int i = 0; i < order.length; i++) {
            if (order[i] == null) continue;
            for (String word : order[i]) {
                // j is up to i + 1 b/c index of order starts at 0, while frequencies start at 1
                for (int j = 0; j < i + 1; j++) {
                    sentence.append(word + " ");
                }
            }
        }

        return sentence.toString();
    }

}
