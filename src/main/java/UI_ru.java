import java.util.ListResourceBundle;

public class UI_ru extends ListResourceBundle {
    final Object[][] contents = new Object[][]{
            {"get_dictionary", "Введите путь к словарю: "},
            {"file_not_found_wrong_format", "Файл не найден или в файле неправильный формат словаря. (слово - перевод)"},
            {"get_an_dict", "Введите путь к другому словарю: "},
            {"translate_file", "Введите файл для перевода: "},
            {"error_in_file_read", "Ошибка в чтении файла"},
            {"translation_divider", "--------ПЕРЕВОД--------"},
            {"word_not_found_format", "Слово %s не найдено в словаре\n"},
            {"nearest_word_format", "Возможно подойдет: "},
            {"empty_dict", "Словарь пустой и нет возможных замен слова."},
            {"replace_word", "Введите строку для замены слова %s : "}
    };

    @Override
    protected Object[][] getContents() {
        return contents;
    }
}
