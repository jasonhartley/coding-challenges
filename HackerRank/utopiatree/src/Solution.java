import java.io.*;

public class Solution {

	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			String input;// Each line of input (should be an integer)
			int count;// The number of test cases
			int n;// The number of growth cycles

			input = br.readLine();
			count = Integer.valueOf(input);

			while ( (input = br.readLine()) != null && count > 0) {
				n = Integer.valueOf(input);
				int sum = 1;
				for (int i = 1; i <= n; i++) {
					if (i % 2 != 0) {
						sum *= 2;
					}
					else {
						sum++;
					}
				}

				System.out.println(sum);

				count--;
			}

		} catch (IOException io) {
			io.printStackTrace();
		}
	}
}