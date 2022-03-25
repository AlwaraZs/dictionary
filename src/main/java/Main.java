import java.io.File;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Locale locale = Locale.getDefault();
        ResourceBundle bundle;
        try {
            bundle  = ResourceBundle.getBundle("UI", locale);
        } catch (MissingResourceException e) {
            System.out.println("Default locale is not supported. Default locale: us");
            locale = new Locale("en");
            bundle  = ResourceBundle.getBundle("UI", locale);
        }

        final Scanner in = new Scanner(System.in);
        String filePath;
        HashMapDictionary dictionary = null;
        TranslatableText text = null;

        if (args.length < 1) {
            System.out.print(bundle.getString("get_dictionary"));
            filePath = in.nextLine().trim();
        } else {
            filePath = args[0];
        }


        while (dictionary == null) {
            try {
                dictionary = new HashMapDictionary(new File(filePath));
            } catch (RuntimeException e) {
                System.out.println(bundle.getString("file_not_found_wrong_format"));
                System.out.print(bundle.getString("get_an_dict"));
                filePath = in.nextLine().trim();
            }
        }

        while (true) {
            System.out.print(bundle.getString("translate_file"));
            try {
                text = new TranslatableText(new File(in.nextLine().trim()));
            } catch (RuntimeException e) {
                System.out.println(bundle.getString("error_in_file_read"));
                continue;
            }

            boolean translated = true;

            do {
                try {
                    final String translatedText = text.translate(dictionary);
                    System.out.println(bundle.getString("translation_divider"));
                    System.out.println(translatedText);
                    translated = true;
                } catch (WordNotFound wordNotFound) {
                    System.out.printf(bundle.getString("word_not_found_format"), wordNotFound.missingWord());
                    try {
                        System.out.println(bundle.getString("nearest_word_format"));
                        for (var word :
                                dictionary.nearest(wordNotFound.missingWord())) {
                            System.out.println(word);
                        }
                    } catch (RuntimeException e) {
                        System.out.println(bundle.getString("empty_dict"));
                    }
                    System.out.printf(bundle.getString("replace_word"), wordNotFound.missingWord());
                    text.replaceWord(wordNotFound.missingWord(), in.nextLine().trim());
                    translated = false;
                }
            } while (!translated);
        }
    }
}
