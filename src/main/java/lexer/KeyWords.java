package lexer;

import java.util.*;

public class KeyWords {
    static String[] keywords = {
        "var",
        "if",
        "else",
        "for",
        "while",
        "break",
        "func",
        "return"
    };
    static HashSet<String> set = new HashSet<>(Arrays.asList(keywords));
    public static boolean isKeyword(String word) {
        return set.contains(word);
    }
}
