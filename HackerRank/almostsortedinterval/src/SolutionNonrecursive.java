import java.io.*;
import java.util.*;

public class SolutionNonrecursive {
	public static void main(String[] args) {
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

			System.out.println(countIntervals(values, max, 0));


		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	// This solution takes O(n^2) time, which is slow as hell
	private static long countIntervals(List<Integer> values, int max, long count) {
		int bottom, top;
//		long subtotal = 0;
//		long prev = 0;

//		System.out.println("Subtract values:");

		while (values.size() > 0) {
//			prev = count;

			bottom = values.get(0);// The bottom value is the first value
			top = bottom;// The top value is currently this first value
			values.remove(0);// Might as well remove the value now
			count++;// This first value counts as one

//			System.out.print("doing: " + bottom + " ");

			// Count all the tops
			for (int i : values) {
				// Is a subtract
				if (i < top) {
//					System.out.print(i + " ");
				}
				// Is a brake
				if (i < bottom) {
//					System.out.print(i + "x");
					break;// i is smaller than bottom, so break
				}
				// Is the new top
				else if (i > top) {
					count++;
					top = i;
					if (top == max) {
						break;// i is the max value, so break
					}
				}
			}

//			subtotal = count - prev;
//			System.out.println("subtotal: " + subtotal);

//			System.out.println("count: " + count);
//			System.out.println();
		}

		return count;
	}
}
