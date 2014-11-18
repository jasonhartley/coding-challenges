import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Pass Triangle
 * 
 * Challenge description: https://www.codeeval.com/open_challenges/89/
 */
public class Main {
	public static void main(String[] args) throws IOException {
		int lastLineIndex = countLines(args[0]);
		String[] lines = new String[lastLineIndex + 1];
		InputStream input = new FileInputStream(args[0]);
		BufferedReader buffer = new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8")));

		// Parse each line of the file into a String array
		for (int i = 0; i <= lastLineIndex; i++) {
			lines[i] = buffer.readLine();
		}
		buffer.close();

		int[] elements = getIntArray(lines[lastLineIndex]);
		int[] elementsAbove;

		// Process the values from the bottom up
		for (int i = lastLineIndex - 1; i >= 0; i--) {
			elementsAbove = getIntArray(lines[i]);

			// This is where the magic happens.  Starting with the second-to-last line,
			// for each value, whichever of the two connected values below is bigger, add it.
			// Repeat until done.
			for (int j = 0; j < elementsAbove.length; j++) {
				elementsAbove[j] += elements[j] > elements[j+1] ? elements[j] : elements[j+1];
			}
			elements = elementsAbove;
		}

		System.out.println(elements[0]);
	}

	// Parse a line of numbers into an int array
	private static int[] getIntArray(String line) {
		String[] elementsString = line.split(" ");
		int[] elements = new int[elementsString.length];
		for (int i = 0; i < elementsString.length; i++) {
			elements[i] = Integer.parseInt(elementsString[i]);
		}
		return elements;
	}

	// Count the number of lines in a text file
	private static int countLines(String filename) throws IOException {
		InputStream inputStream = new BufferedInputStream(new FileInputStream(filename));
		byte[] c = new byte[1024];
		int count = 0;
		int readChars = 0;
		boolean empty = true;
		while ((readChars = inputStream.read(c)) != -1) {
			empty = false;
			for (int i = 0; i < readChars; ++i) {
				if (c[i] == '\n') {
					count++;
				}
			}
		}
		inputStream.close();

		return (count == 0 && !empty) ? 1 : count;
	}
}

