package parser.util;

public class StringUtils {
    public static String LeftPad(String s, int size){
        String result = "";
        for(int i = 0; i < size; ++i){
            result += s;
        }
        return result;
    }
}
