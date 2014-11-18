import java.util.*;

public class Main {

	public static void main(String[] args) {
		String[] braces = new String[] { ")(){}", "[]({})", "([])", "{()[]}", "([)]" };

		validBraces(braces);
    }

	public static void validBraces(String[] expressions) {
		List<Character> charList;

		for (String expression : expressions) {
			// Convert a String to an ArrayList of Characters
			charList = new ArrayList<Character>();
			for (char c : expression.toCharArray()) {
				charList.add(c);
			}

			checkBraces(charList);
		}
	}

	/**
	 * Validate the braces recursively.
	 * The first character should be an opening brace and it should have a matching closing brace
	 * either as the second character, or the last character
	 */
	private static void checkBraces(List<Character> exp) {
		if (exp.size() == 0) {
			System.out.println("1");
			return;
		}

		// The index of the last character
		int last = exp.size() - 1;

		// first char must be an opening brace
		if (map.keySet().contains(exp.get(0))) {
			if (exp.get(1) == map.get(exp.get(0))) {
				// Remove first and second characters
				exp.remove(0);
				exp.remove(0);
				checkBraces(exp);
			}
			else if (exp.get(last) == map.get(exp.get(0))){
				// Remove first and last characters
				exp.remove(last);
				exp.remove(0);
				checkBraces(exp);
			}
			else {
				// No matching brace for the opening brace
				System.out.println("0");
			}
		}
		else {
			// First brace is a closing brace
			System.out.println("0");
		}
	}

	// A map of the different braces pairs
	// How to initialize a static map: http://stackoverflow.com/a/507658/1301891
	private static Map<Character, Character> map;
	static {
		Map<Character, Character> aMap = new HashMap<Character, Character>();
		aMap.put('(', ')');
		aMap.put('{', '}');
		aMap.put('[', ']');
		map = Collections.unmodifiableMap(aMap);
	}

	// An implementation of Insertion Sort, instead of using Array.sort()
	private static char[] insertionSort(char[] a) {
		int j;
		char temp;

		for (int i = 0; i < a.length; i++) {
			temp = a[i];
			j = i;
			while (j > 0 && a[j-1] > temp) {
				a[j] = a[j-1];
				j--;
			}
			a[j] = temp;
		}

		return a;
	}

}
