import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.nio.charset.Charset;

public class MainAlternate {
	public static void main(String[] args) throws IOException {
		//System.out.println("1. " + countLines(args[0]));
		//System.out.println("2. " + countLines2(args[0]));

		int lastLine = countLines(args[0]);
		String[] lines = new String[lastLine + 1];

		InputStream input = new FileInputStream(args[0]);
		BufferedReader buffer = new BufferedReader(new InputStreamReader(input, Charset.forName("UTF-8")));

		// Parse each line of file into a String array
		for (int i = 0; i <= lastLine; i++) {
			lines[i] = buffer.readLine();
			//System.out.println(i + ": " + lines[i]);
		}
		//System.out.println("...");
		buffer.close();

		int[] elements = getIntArray(lines[lastLine]);
		int[] elementsNow;

		for (int i = lastLine - 1; i >= 0; i--) {
			elementsNow = getIntArray(lines[i]);
			//System.out.println("elemsNow: " + printIntArray(elementsNow));
			//System.out.println("elements: " + printIntArray(elements));
			//System.out.println("...");

			for (int j = 0; j < elementsNow.length; j++) {
				if (elements[j] > elements[j+1]) {
					elementsNow[j] += elements[j];
				} else {
					elementsNow[j] += elements[j+1];
				}
			}
			elements = elementsNow;
		}
		//System.out.println("final length: " + elements.length);
		//System.out.println("final value: " + elements[0]);

	}

	private static String printIntArray(int[] intArray) {
		StringBuilder string = new StringBuilder();
		for (int i : intArray) {
			string.append(i + " ");
		}
		return string.toString();
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
		//long startTime = System.nanoTime();

		InputStream is = new BufferedInputStream(new FileInputStream(filename));
		byte[] c = new byte[1024];
		int count = 0;
		int readChars = 0;
		boolean empty = true;
		while ((readChars = is.read(c)) != -1) {
			empty = false;
			for (int i = 0; i < readChars; ++i) {
				if (c[i] == '\n') {
					count++;
				}
			}
		}
		is.close();

		//long elapsedTime = System.nanoTime() - startTime;
		//System.out.println(elapsedTime + "ns 1");

		return (count == 0 && !empty) ? 1 : count;
	}

	public static int countLines2(String filename) throws IOException {
		long startTime = System.nanoTime();

		LineNumberReader lnr = new LineNumberReader(new FileReader(new File(filename)));
		lnr.skip(Long.MAX_VALUE);
		lnr.close();

		long elapsedTime = System.nanoTime() - startTime;
		System.out.println(elapsedTime + "ns 2");

		return lnr.getLineNumber();
	}

}

