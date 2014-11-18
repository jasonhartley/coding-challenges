import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * The absolute value of the difference between the first half of the word the reversed last half of the word
 * is how many reduction operations are required to palindromize the word.
 *
 * Example:
 *  A B C D A B C <-- The word to palindromize
 * -C B A         <-- The reverse tail
 * ==========
 * -2 0 2         <-- Take the absolute value of each of these numbers then sum them
 *
 * |-2| + |0| + |2| =  4 reductions
 */

public class Solution {
    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String input;// A line of input (the word)
            char[] word;// The word we will palindromize
            int count;// The number of test cases
            int len;// The length of of the word
            char[] reverseTail;// The last half of the word reversed
            int sum;// The number of "reduction" operations needed to create the palindrome

            count = Integer.valueOf(br.readLine());

            while (count > 0 && (input = br.readLine()) != null) {
                word = input.toCharArray();
                len = word.length;
                reverseTail = new char[len / 2];
                sum = 0;

                // Get last half of word and reverse it
                for (int i = 0; i < len / 2; i++) {
                    reverseTail[i] = word[len - 1 - i];
                }

                // Sum the absolute value of each difference
                for (int i = 0; i < reverseTail.length; i++) {
                    sum += Math.abs(word[i] - reverseTail[i]);
                }
                System.out.println(sum);

                count--;
            }

        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
