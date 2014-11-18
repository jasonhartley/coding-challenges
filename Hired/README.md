Hired.com Code Challenges
=========================

Challenge 1: Are Two Words Anagrams?
-------------------------------------

Given two arrays of strings, are the strings at the corresponding index of each array anagrams to each other?

For each pair that are, output a 1, if not, output a 0.

Example:

Given the following inputs:
```
{ "cinema", "host", "aba", "train" }
{ "iceman", "shot", "bab", "rain" }
```
You should have the following output:
```
1
1
0
0
```

My Solution:
------------
1. Convert each string to a char array
2. Sort each char array
3. Compare each char array letter by letter

I implemented my own Insertion Sort method because I wasn't sure at the time if I was allowed to import the Array
library, otherwise I would have converted the string to an ArrayList<Character> and used Array.sort() for sorting.

Challenge 2: Brace Validation
------------------------------

Given a string of parenthesis, curly braces, and square braces, validate that the braces appear in valid order.

Output a 1 if the order is valid, otherwise output a 0 if not valid.

Example:

Given the following inputs:
```
{ ")(){}", "[]({})", "([])", "{()[]}", "([)]" }
```
You should have the following output:
```
0
1
1
1
0
```

My Solution:
------------
1. Create a static map of the matching braces: ( ), { }, [ ]
2. Convert the string to an ArrayList\<Character>
3. Recursively check that:
  3. The character in the first position is an opening brace and
  3. Has a corresponding closing brace either in the second position or last position.
4. If the above conditions are true, remove those two braces from the list and call the recursive method again using the
newly-pruned list.
5. Once all braces have been successfully removed, we know the string of braces was valid

Note:  When I did this challenge for real, I completely bombed it.  I used a series of nested conditionals that didn't
work and weren't scalable.  The map containing the corresponding braces made the code less messy and easily scalable,
but added some complexity that I still don't like.  Not until a few hours after doing the challenge (conducted on
talentguide.co) did I think of the recursive algorithm.  I had to use Stack Overflow to find a way to create a static
map (because I am still not sure if instantiating an object is allowed, and imho it's probably not the best programming
practice either).
