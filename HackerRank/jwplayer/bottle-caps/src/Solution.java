import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Solution {

    public static void main(String[] args) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            String input;
            int count;

            count = Integer.valueOf(br.readLine());

            int cash, cost, caps;
            int fromCash, total;

            for (int i = 0; i < count; i++) {
                input = br.readLine();
                String[] numbers = input.split("\\s+");
                cash = Integer.valueOf(numbers[0]);
                cost = Integer.valueOf(numbers[1]);
                caps = Integer.valueOf(numbers[2]);
                fromCash = cash / cost;
                total = fromCash + beersForCaps(fromCash, caps);

                System.out.println(total);

            }

        } catch (IOException io) {
            io.printStackTrace();
        }
    }

    public static int beersForCaps(int onHand, int needed) {
        if (onHand >= needed) {
            int gonnaGet = onHand / needed;
            int extraCaps = onHand % needed;

            return gonnaGet + beersForCaps(extraCaps + gonnaGet, needed);
        } else {
            return 0;
        }
    }
}
