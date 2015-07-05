import java.io.*;

public class Solution {

    static int isPangram(String n) {
        int[] alphabet = new int[26];

        char[] charArray = n.toCharArray();
        int aToInt = Character.getNumericValue('a');
        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];
            if (!Character.isLetter(c)) {
                continue;
            }
            char toLower = Character.toLowerCase(c);
            int toInt = Character.getNumericValue(toLower) - aToInt;
            alphabet[toInt] = 1;
        }

        for (int i = 0; i < 26; i++) {
            if (alphabet[i] == 0) {
                return 0;
            }
        }

        return 1;
    }

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String input;
r
            while ((input = br.readLine()) != null) {
                System.out.println(isPangram(input));
            }

        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
