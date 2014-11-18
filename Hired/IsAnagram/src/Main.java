import java.util.*;

public class Main {

	public static void main(String[] args) {
		String[] firstWords = new String[] { "cinema", "host", "aba", "train" };
		String[] secondWords = new String[] { "iceman", "shot", "bab", "rain" };

		isAnagram(firstWords, secondWords);
    }

	public static void isAnagram(String[] firstWords, String[] secondWords) {
		for (int i = 0; i < firstWords.length; i++) {
			// Both words should at least be the same length
			if (firstWords[i].length() == secondWords[i].length()) {
				// Sort the characters
				char[] firstSorted = insertionSort(firstWords[i].toCharArray());
				char[] secondSorted = insertionSort(secondWords[i].toCharArray());
				// Compare the sorted characters
				for (int j = 0; j < firstSorted.length; j++) {
					if (firstSorted[j] != secondSorted[j]) {
						// They don't match: no-go
						System.out.println("0");
						break;
					}
					else if (j + 1 == firstSorted.length) {
						// We made it to the end, all characters match!
						System.out.println("1");
					}
				}
			}
			// The words are unequal in length: no-go
			else {
				System.out.println("0");
			}
		}
	}
}
