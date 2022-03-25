import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class HashMapDictionary implements Dictionary {
    @NotNull
    private final Map<String, String> dictionary = new HashMap<>();
    @NotNull
    private final Map<String, List<String>> levenshteinCache = new HashMap<>();

    public HashMapDictionary(@NotNull final File file) {
        try (var scanner = new Scanner(new BufferedReader(new InputStreamReader(new FileInputStream(file))))){
            while (scanner.hasNextLine()) {
                String[] strings = scanner.nextLine().split("-");
                addTranslation(strings[0].trim(), strings[1].trim());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(file + ": not found", e);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new RuntimeException("Wrong dictionary format", e);
        }
    }

    public void addTranslation(@NotNull final String word, @NotNull final String translation) {
        dictionary.put(word, translation);
    }

    public @NotNull String translate(@NotNull final String word) throws WordNotFound {
        final var value = dictionary.get(word);

        if (value == null) {
            throw new WordNotFound(word);
        }

        return value;
    }

    private static int min(int n1, int n2, int n3) {
        return Math.min(Math.min(n1, n2), n3);
    }

    private static int levenshtein(@NotNull String str1, @NotNull String str2) {
        int[] Di_1 = new int[str2.length() + 1];
        int[] Di = new int[str2.length() + 1];

        for (int j = 0; j <= str2.length(); j++) {
            Di[j] = j;
        }

        for (int i = 1; i <= str1.length(); i++) {
            System.arraycopy(Di, 0, Di_1, 0, Di_1.length);

            Di[0] = i;
            for (int j = 1; j <= str2.length(); j++) {
                int cost = (str1.charAt(i - 1) != str2.charAt(j - 1)) ? 1 : 0;
                Di[j] = min(
                        Di_1[j] + 1,
                        Di[j - 1] + 1,
                        Di_1[j - 1] + cost
                );
            }
        }

        return Di[Di.length - 1];
    }

    @Override
    public @NotNull List<String> nearest(@NotNull String word) {
        final var test = levenshteinCache.get(word);
        if (test != null) {
            return test;
        }

        if (dictionary.isEmpty()) {
            throw new RuntimeException("Empty dictionary");
        }

        class LevenshteinStruct {
            LevenshteinStruct(int dist, String str) {
                distance = dist;
                word = str;
            }

            final int distance;
            final String word;
        }

        List<LevenshteinStruct> array = new ArrayList<>(dictionary.size());

        for (var key :
                dictionary.keySet()) {
            array.add(new LevenshteinStruct(levenshtein(word, key), key));
        }

        array.sort(Comparator.comparingInt(a -> a.distance));
        var nearestWords = array.stream().map(a -> a.word).limit(5).collect(Collectors.toList());
        levenshteinCache.put(word, nearestWords);

        return nearestWords;
    }
}
