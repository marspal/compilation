package parser.util;

import java.util.ArrayList;

public class StringUtils {
    public static String LeftPad(String s, int size){
        String result = "";
        for(int i = 0; i < size; ++i){
            result += s;
        }
        return result;
    }

    public static String join(ArrayList<String> list, String s) {
        StringBuilder string = new StringBuilder();

        for(int i = 0; i < list.size(); ++i){
            string.append(list.get(i));
            if(i != list.size() - 1){
                string.append(s);
            }
        }
        return string.toString();
    }
}
