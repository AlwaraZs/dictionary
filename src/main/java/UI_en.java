import java.util.ListResourceBundle;

public class UI_en extends ListResourceBundle {
    final Object[][] contents = new Object[][]{
            {"get_dictionary", "Enter path to dictionary file: "},
            {"file_not_found_wrong_format", "File is not found or wrong format in it. (word - translation)"},
            {"get_an_dict", "Enter path to another dictionary: "},
            {"translate_file", "Enter file to translate: "},
            {"error_in_file_read", "Error in file reading"},
            {"translation_divider", "--------TRANSLATION--------"},
            {"word_not_found_format", "Word %s is not found in dictionary\n"},
            {"nearest_word_format", "Possible variants: "},
            {"empty_dict", "Dictionary is empty."},
            {"replace_word", "Enter word for %s replacement : "}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
