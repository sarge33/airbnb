import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

class CSVParser {

    public String parseCSV(String str) {
        List<String> res = new ArrayList<>();
        boolean inQuote = false;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<str.length(); ++i) {
            char ch = str.charAt(i);
            if(inQuote) {
                if(i+1<str.length() && ch == '\"' && str.charAt(i+1) == '\"') {
                    sb.append(ch);
                    i++; // skip the 2nd "
                } else if(ch == '\"'){
                    inQuote = false;
                } else {
                    sb.append(ch);
                }

            } else {
                if(ch == '\"') {
                    inQuote = true;
                } else if(ch == ',') {
                    res.add(sb.toString());
                    sb.setLength(0);
                } else {
                    sb.append(ch);
                }
            }
        }
        if(sb.length() > 0) res.add(sb.toString());
        return String.join("|", res);
    }

}

public class Test7 {
    @Test
    public void test1() {
        CSVParser sol = new CSVParser();
        String test = "John,Smith,john.smith@gmail.com,Los Angeles,1";
        String expected = "John|Smith|john.smith@gmail.com|Los Angeles|1";
        assertEquals(expected, sol.parseCSV(test));

        test = "Jane,Roberts,janer@msn.com,\"San Francisco, CA\",0";
        expected = "Jane|Roberts|janer@msn.com|San Francisco, CA|0";
        assertEquals(expected, sol.parseCSV(test));

        test = "\"Alexandra \"\"Alex\"\"\",Menendez,alex.menendez@gmail.com,Miami,1";
        expected = "Alexandra \"Alex\"|Menendez|alex.menendez@gmail.com|Miami|1";
        assertEquals(expected, sol.parseCSV(test));

        test = "\"\"\"Alexandra Alex\"\"\"";
        expected = "\"Alexandra Alex\"";
        assertEquals(expected, sol.parseCSV(test));
    }

    @Test
    public void test2() {
        CSVParser sol = new CSVParser();
        String test = "John,Smith,john.smith@gmail.com,Los Angeles,1";
        String expected = "John|Smith|john.smith@gmail.com|Los Angeles|1";
        assertEquals(expected, sol.parseCSV(test));

        test = "Jane,Roberts,janer@msn.com,\"San Francisco, CA\",0";
        expected = "Jane|Roberts|janer@msn.com|San Francisco, CA|0";
        assertEquals(expected, sol.parseCSV(test));

        test = "\"Alexandra \"\"Alex\"\"\",Menendez,alex.menendez@gmail.com,Miami,1";
        expected = "Alexandra \"Alex\"|Menendez|alex.menendez@gmail.com|Miami|1";
        assertEquals(expected, sol.parseCSV(test));

        test = "\"\"\"Alexandra Alex\"\"\"";
        expected = "\"Alexandra Alex\"";
        assertEquals(expected, sol.parseCSV(test));
    }

}
