import org.jetbrains.annotations.NotNull;

public class WordNotFound extends Exception {
    private final String word;
    public WordNotFound(@NotNull final String word) {
        super(word + ": not found");
        this.word = word;
    }

    public @NotNull String missingWord() {
        return word;
    }
}
