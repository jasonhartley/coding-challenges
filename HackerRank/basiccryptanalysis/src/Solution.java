import java.io.*;
import java.util.*;

public class Solution {

	public static class Node implements Comparable{
		private Node mParent;
		private Set<Node> mChildren;
		private Map<Character, Character> mMap;

		public Node() {
			mParent = null;// Is all this really necessary?
			mMap = new TreeMap<Character, Character>();
			mChildren = new TreeSet<Node>();
		}

		public Node(Node parent, Map<Character, Character> map) {
			mParent = parent;
			mMap = map;// I think I can do this without copying the map, but we'll see.
			mChildren = new TreeSet<Node>();
		}

		public Node getParent() {
			return mParent;
		}

		public Map<Character, Character> getMap() {
			return mMap;
		}

		public Set<Node> getChildren() {
			return mChildren;
		}

		public void addChild(Node child) {
			mChildren.add(child);
		}

		@Override
		public int compareTo(Object o) {
			Node node = (Node) o;
			for (Character c : mMap.keySet()) {
				if (node.getMap().get(c) != mMap.get(c)) {
					return 1;// Or -1?  I dunno.
				}
			}
			return 0;
		}
	}

	public static void main(String[] args) {
		try {
			String input;// A line of input from a stream (the dictionary)
			String message = "";// The encrypted message
			Set<String> dictSet = new HashSet<String>();// The dictionary
			List<String> dictListSorted;// The dictionary, sorted
			Set<String> cipherWordSet;// Words used in the message
			List<String> cipherWordListSorted;// Words used in the message, sorted
			LengthFirstComparator comparator = new LengthFirstComparator();// Used to sort by word length
			Node head = new Node();// The node at the top of the tree
			Map<Character, Character> tempMap;// Temp map to hold a discovered cipher-to-plain map
			Set<Node> leaves = new HashSet<Node>();// Keeps track of all the ends of the branches

			// Read the dictionary
			InputStream inputStream = new FileInputStream("dictionary.lst");
			BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
			while ((input = reader.readLine()) != null) {
				dictSet.add(input);
			}
//			System.out.println();
//			System.out.println("Dictionary: ");
//			System.out.println(dictSet);
//			System.out.println();

			// Sort the dictionary by word length
			dictListSorted = new ArrayList<String>(dictSet);
			Collections.sort(dictListSorted, comparator);
//			System.out.println("Dictionary, sorted: ");
//			System.out.println(dictListSorted);
//			System.out.println();

			// Read the encrypted message
			reader = new BufferedReader(new InputStreamReader(System.in));
			while ((input = reader.readLine()) != null) {
				message += (input + " ");
			}
//			System.out.println("Message:");
//			System.out.println(message);
//			System.out.println();

			// Sort message words by size
			cipherWordSet = new HashSet<String>(Arrays.asList(message.split("\\s+")));// Removes duplicates
			cipherWordListSorted = new ArrayList<String>(cipherWordSet);// Allows sorting
			Collections.sort(cipherWordListSorted, comparator);

			// Starting from the longest word in the message, "reduce" the words
			int wordCount = cipherWordListSorted.size();
			int dictCount = dictListSorted.size();
			char[] word, wordReduced, dictWord, dictWordReduced;
			for (int i = 0; i < wordCount; i++) {
				word = cipherWordListSorted.get(wordCount - i - 1).toCharArray();
				wordReduced = reduce(word);
				//System.out.println(word.length + ". Ciph word: " + String.valueOf(word) + ", reduced: " + String.valueOf(wordReduced));
				leavesLoop:
				for (int j = 0; j < dictCount; j++) {
					dictWord = dictListSorted.get(dictCount - j - 1).toCharArray();
					// First, ensure we're comparing two words of the same length
					if (word.length == dictWord.length) {
						dictWordReduced = reduce(dictWord);
						//System.out.println(word.length + ". Dict word: " + String.valueOf(dictWord) + ", reduced: " + String.valueOf(dictWordReduced));
						if (String.valueOf(wordReduced).equals(String.valueOf(dictWordReduced))) {
//							System.out.println(String.valueOf(word) + " ?= " + String.valueOf(dictWord));
							//todo: maybe refactor so there is only one addToTree
							// If this is the first comparison we're doing, just go and add it
							if (leaves.size() == 0) {
								/**** Add this map to the tree *****/
								tempMap = createMap(word, dictWord);
//								System.out.println("initial add to tree: " + tempMap.toString());
								addToTree(head, leaves, tempMap);
								/***********************************/
							}
							// Otherwise go find the branch this map belongs in
							else {
								for (Node n : leaves) {
									// Is is compatible with any of our branches?
									if (areCompatable(createMap(word, dictWord), getBranch(n))) {
										/**** Add this map to the tree *****/
										tempMap = createMap(word, dictWord);
//										System.out.println("adding to tree: " + tempMap.toString());
										addToTree(head, leaves, tempMap);
										/***********************************/
										break leavesLoop;
									}
									else {
										// For now, we will ignore maps not compatible with our current branch(es).
//										System.out.println("Ignoring non-compatible match for now. ");
									}
								}
							}
						}
					}
				}
//				System.out.println();
			}

			// Let's show all the branches we have
//			System.out.println("Branches (" + leaves.size() + "):");
//			for (Node n : leaves) {
//				System.out.println(getBranch(n).toString());
//			}

			// What letters have we not figured out yet?
			Set<Character> unkCiphers = new TreeSet<Character>();
			Set<Character> unkPlains = new TreeSet<Character>();
			Set<String> unkCipherWords = new HashSet<String>();
			tempMap = new TreeMap<Character, Character>();
			// Let's only pay attention to the one branch we have, though there could be many
			for (Node n : leaves) {
				 tempMap = getBranch(n);// If there were more than one branch, we'd use an ArrayList<Map>
			}
			// What letters so we have left to pair together?
			for (char c = 'a'; c <= 'z'; c++) {
				if (tempMap.get(c) == null) {
					unkCiphers.add(c);
				}
			}
			for (char c = 'a'; c <= 'z'; c++) {
				if (!tempMap.values().contains(c)) {
					unkPlains.add(c);
				}
			}
//			System.out.println("unknown ciphers: " + unkCiphers);
//			System.out.println("unknown plains: " + unkPlains);

			// Create a set of words we can't decipher yet
			for (String cipherWord : cipherWordSet) {
				for (char c : cipherWord.toCharArray()) {
					if (unkCiphers.contains(c)) {
						unkCipherWords.add(cipherWord);
					}
				}
			}

//			System.out.println("unknown cipher words: " + unkCipherWords);

			// Are then any words we can't decipher yet?
			if (unkCipherWords.size() > 0 ) {
				// note: There's no way to validate that our guesses are right if there are no undeciphered words left
//				System.out.println("This is where we'd do some letter guessing.");
			}

			// Print the deciphered message
			tempMap.put(' ', ' ');//todo: make this less fragile a way to detect whitespace
			for (char c : message.toCharArray()) {
				System.out.print(tempMap.get(c));
			}
			System.out.println();


		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	private static Map<Character, Character> getBranch(Node node) {
		if (node.getParent() != null) {
			Map<Character, Character> branch = getBranch(node.getParent());
			branch.putAll(node.getMap());
			return branch;
		} else {
			return node.getMap();
		}
	}

	private static void addToTree(Node node, Set<Node> leaves, Map<Character, Character> map) {
		// Is mMap a subset of map?
		if (areCompatable(map, node.mMap)) {
			// Strip mMap from map
			map.keySet().removeAll(node.mMap.keySet());
		}
		if (node.getChildren() != null) {
			// Are any of the children maps subsets of the extra values?
			for (Node childNode : node.getChildren()) {
				if (areCompatable(childNode.getMap(), map)) {
					// Recursively call addToTree using the child node and the extra values as the map
					addToTree(childNode, leaves, map);
					return;
				}
			}
		}
		// So the extra values are not subsets of any children (or there are no children)
			// Add a child node using this node as the parent and the extra values as the map
		if (map != null) {
			Node newChild = new Node(node, map);
			node.addChild(newChild);
			// Add this node to the leaves set
			leaves.add(newChild);
			// Remove this parent from the leaves set
			leaves.remove(node);
		}
	}

	private static boolean areCompatable(Map<Character, Character> parentMap, Map<Character, Character> childMap) {
		// Both null is okay, but one null is not
		if (parentMap == null || childMap == null) {
			return (childMap == null && parentMap == null);
		}
		// Return false if any conflicts are found
		for (char c : parentMap.keySet()) {
			if (parentMap.get(c) != childMap.get(c) && childMap.get(c) != null) {
				return false;
			}
		}
		for (char c : childMap.keySet()) {
			if (childMap.get(c) != parentMap.get(c) && parentMap.get(c) != null) {
				return false;
			}
		}

		return true;
	}

	private static boolean isASubset(Map<Character, Character> superSet, Map<Character, Character> subSet) {
		if (subSet == null || superSet == null) {
			return (subSet == null && superSet == null);
		}

		for (char c : subSet.keySet()) {
			if (subSet.get(c) != superSet.get(c)) {
				return false;
			}
		}
		return true;
	}

	private static Map<Character, Character> createMap(char[] cipherText, char[] plainText) {
		Map<Character, Character> map = new TreeMap<Character, Character>();
		for(int i = 0; i < cipherText.length; i++) {
			map.put(cipherText[i], plainText[i]);
		}
		return map;
	}

	private static char[] reduce(char[] word) {
		int wordLen = word.length;
		//System.out.println("wordLen: " + wordLen);
		char letterCounter = 'a';
		Map<Character, Character> letterMap = new HashMap<Character, Character>();
		char[] wordReduced = new char[wordLen];
		char letter;
		for (int j = 0; j < wordLen; j++) {
			letter = word[j];
			//System.out.println("letter: " + letter);
			//System.out.println("letterMap.get("+letter+"): " + letterMap.get(letter));
			if (letterMap.get(letter) == null) {
				letterMap.put(letter, letterCounter);
				wordReduced[j] = letterCounter;
				letterCounter++;
			}
			else {
				wordReduced[j] = letterMap.get(letter);
			}
			//System.out.println("is mapped to:" + letterMap.get(letter));

		}
		return wordReduced;
	}

	// Borrowed from: http://stackoverflow.com/a/18885688/1301891
	private static class LengthFirstComparator implements Comparator<String> {
		@Override
		public int compare(String o1, String o2) {
			if (o1.length() != o2.length()) {
				return o1.length() - o2.length();// Overflow impossible since lengths are non-negative
			}
			return o1.compareTo(o2);
		}
	}
}
