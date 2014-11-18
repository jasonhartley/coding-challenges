import java.io.*;

public class Solution {

	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

			int n = Integer.valueOf(br.readLine());

			// Disallowed method
			/*String binary = Integer.toBinaryString(n);
			char[] charArray = binary.toCharArray();

			for (int i = 0; i < charArray.length; i++) {
				if (charArray[i] == '1') {
					charArray[i] = '0';
				} else {
					charArray[i] = '1';
				}
			}

			String complementString = new String(charArray);
			int complement = Integer.parseInt(complementString, 2);

			System.out.println(complement);*/

			// Numeric method
			double log2 = Math.log(n)/Math.log(2);
			int floor = (int) Math.floor(log2);
			int power = (int) Math.pow(2, floor + 1);
			int complement2 = (power - 1) - n;

			System.out.println(complement2);

		} catch (IOException io) {
			io.printStackTrace();
		}
	}
}