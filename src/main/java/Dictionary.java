import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface Dictionary {
    @NotNull String translate(@NotNull final String word) throws WordNotFound;
    @NotNull List<String> nearest(@NotNull final String word);
}
