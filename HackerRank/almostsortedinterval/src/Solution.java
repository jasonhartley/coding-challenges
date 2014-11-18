import java.io.*;
import java.util.*;

public class Solution {

	public static void main(String[] args) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String input;// a line of input
			int max;// The max value as given by the fist line of input.  It's not need and I don't use it.

			List<Integer> values = new ArrayList<Integer>();// The values read from the input
			//NavigableSet<Integer> values1 = new TreeSet<Integer>();// Storing values in a TreeSet might make getting a subset faster than from an ArrayList
			max = Integer.valueOf(br.readLine());// First line of input should give the maximum value
			input = br.readLine();// The input values

			// Store the values into an Long array list
			for(String s : input.split("\\s+")) {
				values.add(Integer.valueOf(s));
				//values1.add(Integer.valueOf(s));
			}

			System.out.println(countIntervals(values, max));
//			System.out.println(countIntervals(values1, max));


		} catch (IOException io) {
			io.printStackTrace();
		}
	}

//	private static long countIntervals(NavigableSet<Integer> values, int max) {
	private static long countIntervals(List<Integer> values, int max) {
		long start = System.currentTimeMillis();

		// Note: a "drop value" is the value in the sequence that is less than the once before it
		// A "subtraction" is the number of positions including and after the drop value before a value is greater than the one before the drop value
		// For example:
		// values[] = { 1, 5, 6, 2, 3, 4, 7, 8 }
		// drop value = 2, drop position = 3, e.g. values[3] = 2
		// last max = 6, last max position = 2, e.g. values[2] = 6
		// Subtractions = 3 (because 2, 3, & 4 are in the sequence before 7 which is the first value greater than 6)
		// A brake is a value that is less than the start value where we can stop counting and move onto the next value.
		// In this example there is no brake, so therefore the brake is after the value 8 (the end of the set)

		// Note: There are two next loops in Step #2 that could easily make the time complexity be O(n^2).
		// The whole trick here was to use TreeMaps for the tables of "drops" and "subtracts", then only iterate
		// over the entries that are needed.  Removing the unnecessary iterations changed the execution time from
		// 7-8 minutes down to 5 seconds on large data sets.

		TreeMap<Integer, Integer> dropValues = new TreeMap<Integer, Integer>();// A drop's value by its position
		//TreeMap<Integer, Integer> dropSubtracts = new TreeMap<Integer, Integer>();// A drop's "subtraction" by its position
		TreeMap<Integer, Integer> dropLengths = new TreeMap<Integer, Integer>();
		Map<Integer, Integer> maxValues = new TreeMap<Integer, Integer>();// Each new max value by its position
		NavigableSet<Integer> keySet;
		int len = values.size();
		int val, segmentLen;
		int lastMaxPos = 0;
		int lastMax = values.get(lastMaxPos);// lastMax should be the first value, esp. since we start iterating on the second value
		int lastDropPos = 0;
		int brakePos;
		int subtracts = 0;
		int subtotal;// ??
		long total = 0;
		int subtractMax = 0;
		int subtractCount = 0;
		int length = 0;

		// Step 1. Build the drops table and subtracts table
		// =================================================
		System.out.print("^");// First one is always a new max
		maxValues.put(0, values.get(0));

		for (int pos = 1; pos < len; pos++) {
			val = values.get(pos);// The current value we're looking at
//			System.out.print(pos + ":" + val);

			// IF WE FOUND A DROP
			if (values.get(pos - 1) > val) {
				System.out.print("o");// A drop
				// Record this drop
				dropValues.put(pos, val);
				// Record drop length
				if (length > 0) {
//					System.out.print(lastDropPos + ":" + length);
					dropLengths.put(lastDropPos, length);
					length = 0;
				}
				// Record the subtracts, then reset the subtracts count
				if (subtracts > 0) {
					subtractCount++;
					// This is for stats purposes
					if (subtracts > subtractMax) {
						subtractMax = subtracts;
					}
					//dropSubtracts.put(lastDropPos, subtracts);
//					System.out.print(" pos: " + pos + " lastDropPos: " + lastDropPos + " subtracts: " + subtracts);
					subtracts = 0;
				}
				lastDropPos = pos;
//				System.out.print(" new lastDropPos: " + lastDropPos);
				// Count it as a subtract
				subtracts++;// subtracts should now be set back to 1 for this drop
				length++;// length should now be 1 for this drop
			}
			// The value increased
			else {
				// IF WE FOUND A NEW MAX
				if (val > lastMax) {
					System.out.print("^");// A new max
					lastMaxPos = pos;
					lastMax = values.get(lastMaxPos);
					// Record the new max
					maxValues.put(lastMaxPos, lastMax);
					// Record the subtracts if any, then reset the count
					if (subtracts > 0) {
						subtractCount++;
						if (subtracts > subtractMax) {
							subtractMax = subtracts;
						}
						//dropSubtracts.put(lastDropPos, subtracts);
//						System.out.print(" pos: " + pos + " lastCliffPos: " + lastCliffPos + " subtracts: " + subtracts);
						subtracts = 0;
					}
				}
				// IF WE FOUND A SUBTRACT
				else {
					System.out.print(".");// A subtract (the number is ascending, but it is not a new max)
					subtracts++;
				}
				// Any increase is an increase in drop length
				length++;
			}
			// Record the last of the subtracts if any (also last of the drop lengths)
			if (pos + 1 == len) {
				// Record subtracts
				if (subtracts > 0) {
					subtractCount++;
					if (subtracts > subtractMax) {
						subtractMax = subtracts;
					}
					//dropSubtracts.put(lastDropPos, subtracts);
				}
				// Record lengths
				if (length > 0) {
					dropLengths.put(lastDropPos, length);
				}
			}
//			System.out.println();
		}
		System.out.println();


		System.out.println("drop values: " + dropValues);
		//System.out.println("drop subtracts: " + dropSubtracts);
		System.out.println("drop lengths: " + dropLengths);
		System.out.println("max values: " + maxValues);
		System.out.println("subtract max: " + subtractMax);
		System.out.println("subtract count: " + subtractCount);// + " " + dropSubtracts.size());
		long time1 = System.currentTimeMillis() - start;
		System.out.println("Step One time: " + time1);

		// Step 2. Count the almost-sorted intervals
		// =========================================
		for (int pos = 0; pos < len; pos++) {
			val = values.get(pos);
			keySet = (NavigableSet<Integer>) dropValues.tailMap(pos).keySet();// tailMap() trims all the drops from the beginning we don't need
			System.out.print("pos: " + pos);
//			System.out.print(" keySet: " + keySet);

			// Find the brake position - maximum time: O(n) todo: find a faster way to find the brake, like O(log(n))
			brakePos = len;// If no brake is found, the default will be after the final index
			for (int i : keySet) {
				if (dropValues.get(i) < val) {
					brakePos = i;
					break;
				}
			}
			System.out.print(" brake: " + brakePos);


			// Sum the subtracts  todo: combine this loop with the find-brake loop
			subtracts = 0;
			//keySet = dropSubtracts.tailMap(pos + 1).keySet();
			// Probably will want to do away with the pos+1 here.  We need to find the max value of the segment.
			//keySet = dropValues.tailMap(pos).keySet();// dropSubtracts is deprecated, plus they have the same key set
			System.out.println(" keySet: " + keySet);
			NavigableSet<Integer> subSet1;// To use subSet(), but be a NavigableSet
			//int prevI = 0;
			int subMax;
			int subSubtract = 0;
			lastMax = 0;
			// Find the initial lastMax
			if (keySet.size() > 0) {
				int p = pos;
				int q = keySet.first();
				while (p != q) {
					lastMax = values.get(p++);
				}
				System.out.print(" new");
			}
			System.out.print(" init lastMax: " + lastMax);

			for (int i : keySet) {
				if (i < brakePos) {

					// Find the max value and its position for that segment (for now we'll check to the end) todo: check segment only, not to end
					//NavigableSet<Integer> keySet1 = (NavigableSet<Integer>) dropValues.keySet();
					//keySet1 = keySet1.subSet(i, true, brakePos, false);
					//System.out.print(" keySet1: " + keySet1);

					subSet1 = new TreeSet<Integer>(values.subList(i, i + dropLengths.get(i)));// todo: make values be a set and use subSet() cuz faster?
					//subSet1 = new TreeSet<Integer>(values.subList(i, i + dropSubtracts.get(i)));// doing this with subtracts is incorrect
					System.out.print("  subSet1: " + subSet1);
					subMax = subSet1.last();
					System.out.print(" subMax: " + subMax);
					if (lastMax < subMax) {
						//Time to investigate!
						subSubtract = subSet1.headSet(lastMax).size();
						lastMax = subMax;
					}
					else if (lastMax > subMax) {
						// Subtract them all
						subSubtract = dropLengths.get(i);
					}
					else {
						System.out.println("Error.  lastMax == subMax.");
					}
					System.out.print(" subSubtract: " + subSubtract);
					System.out.println(" lastMax: " + lastMax);

					subtracts += subSubtract;
				}
				// We're at the brake pos
				else {
					break;// Once we get to the brake, stop iterating
				}
			}
			System.out.print("    subtracts: " + subtracts);

			// Calculate the length of this interval's segment
			segmentLen = brakePos - pos;
			System.out.print(" segmentLen: " + segmentLen);

			// Count the almost-sorted intervals for this iteration by subtracting the "subtracts" from this segment
			subtotal = segmentLen - subtracts;
			System.out.println(" subtotal: " + subtotal);
			total += subtotal;
		}

		long time2 = System.currentTimeMillis() - start - time1;
		System.out.println("Step Two time: " + time2);

		return total;
	}

	/*private void recordSubtracts(TreeMap<Integer, Integer> dropSubtracts, int lastDropPos, int subtracts) {
		// Maybe use this later
	}*/
}
