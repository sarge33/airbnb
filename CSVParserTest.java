class CSVParser {
    public String parse(String str) {
        List<String> res = new ArrayList<>();
        boolean inQuote = false;
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i<str.length(); i++) {
            if(inQuote) {
                if(str.charAt(i) == '\"') {
                    if(i + 1 < str.length() && str.charAt(i+1) == '\"') {
                        sb.append('\"'); // skip
                        i++;
                    } else {
                        inQuote = false;
                    }
                } else {
                    sb.append(str.charAt(i));
                }
            } else {
                if(str.charAt(i) == '\"') {
                    inQuote = true;
                } else if (str.charAt(i) == ',') {
                    res.add(sb.toString());
                    sb.setLength(0);
                } else {
                    sb.append(str.charAt(i));
                }
            }
        }
        if(sb.length() > 0) {
            res.add(sb.toString());
        }

        return String.join("|", res);
    }
}
public class CSVParserTest {

    public static void main(String[] args) {

        String original = "John,Smith,john.smith@gmail.com,Los Angeles,1";
        String expected = "John|Smith|john.smith@gmail.com|Los Angeles|1";

        CSVParser parser = new CSVParser();
        String s = parser.parse(original);
        System.out.println("original: " + original);
        System.out.println("expected: " + expected);
        System.out.println("test    : " + s);

        original = "\"Alexandra \"\"Alex\"\"\",Menendez,alex.menendez@gmail.com,Miami,1";
        expected = "Alexandra \"Alex\"|Menendez|alex.menendez@gmail.com|Miami|1";

        s = parser.parse(original);
        System.out.println("original: " + original);
        System.out.println("expected: " + expected);
        System.out.println("test    : " + s);
   }
 }
