import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class TranslatableText {
    @NotNull String text;

    public TranslatableText(@NotNull final File file) {
        try {
            text = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException("IOException in reading from " + file);
        }
    }

    public TranslatableText(@NotNull final String text) {
        this.text = text;
    }

    private static void replaceAll(@NotNull final StringBuilder builder, @NotNull final String from, @NotNull final String to) {
        int index = builder.indexOf(from);
        while (index != -1) {
            builder.replace(index, index + from.length(), to);
            index += to.length();
            index = builder.indexOf(from, index);
        }
    }

    @NotNull String translate(@NotNull final Dictionary dictionary) throws WordNotFound {
        StringBuilder translatedText = new StringBuilder(text);
        final String[] words = text.split("[^\\p{L}]+");
        for (var word :
                words) {
            replaceAll(translatedText, word, dictionary.translate(word));
        }
        return translatedText.toString();
    }

    void replaceWord(@NotNull final String word, @NotNull final String replacement) {
        text = text.replaceAll(word, replacement);
    }
}
