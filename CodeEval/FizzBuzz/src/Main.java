import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * FizzBuzz - a classic
 *
 * Challenge description: https://www.codeeval.com/open_challenges/1/
 */
public class Main {
	public static void main(String[] args) throws IOException {
		InputStream input = new FileInputStream(args[0]);
		BufferedReader buffer = new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8")));
		String line;
		int a, b, n;
		int counter = 0;
		final int MAX_LINES = 20;
		final int MAX_AB = 20;
		final int MAX_N = 100;
		StringBuilder output;

		while (counter < MAX_LINES && (line = buffer.readLine()) != null) {
			String[] elements = line.split(" ");
			a = Integer.parseInt(elements[0]);
			b = Integer.parseInt(elements[1]);
			n = Integer.parseInt(elements[2]);

			// Skip this line if a, b, or n are out of range
			if (a > MAX_AB || b > MAX_AB || n > MAX_N) {
				continue;
			}
			output = new StringBuilder();

			for (int i = 1; i <= n; i++) {
				if (i > 1) {
					output.append(" ");
				}
				if (i % a != 0 && i % b != 0) {
					output.append(i);
				} else {
					if (i % a == 0) {
						output.append("F");
					}
					if (i % b == 0) {
						output.append("B");
					}
				}
			}

			System.out.println(output);
			counter++;
		}
		buffer.close();
	}
}
