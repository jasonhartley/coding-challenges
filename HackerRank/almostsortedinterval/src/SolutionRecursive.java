import java.io.*;
import java.util.*;

public class SolutionRecursive {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();

		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String input;// a line of input
			int max;// The max value as given by the fist line of input

			max = Integer.valueOf(br.readLine());// First line of input should give the maximum value
			input = br.readLine();// The input values,
			List<Integer> values = new ArrayList<Integer>();

			// Store the values into an integer array list
			for(String s : input.split("\\s+")) {
				values.add(Integer.valueOf(s));
			}

			System.out.println("max:  " + max);
			System.out.println("size: " + values.size());

			System.out.println(countIntervals(values, max, 0));


		} catch (IOException io) {
			io.printStackTrace();
		}

		long time = System.currentTimeMillis() - start;
		System.out.println("time: " + time);

	}

	// This solution causes a stack overflow with large input
	private static int countIntervals(List<Integer> values, int max, int count) {
		int bottom, top;

		if (values.size() > 0) {
			count++;// This first value counts as one
			bottom = values.get(0);// The bottom value is the first value
			top = bottom;// The top value is currently this first value
			values.remove(0);// Might as well remove the value now

			// Count all the tops
			for (int i : values) {
				if (i < bottom) {
					break;// i is smaller than bottom, so break
				}
				else if (i > top) {
					count++;
					top = i;
					if (top == max) {
						break;// i is the max value, so break
					}
				}
			}

			count = countIntervals(values, max, count);
		}

		return count;
	}
}
