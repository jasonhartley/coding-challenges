Almost Sorted Intervals
=======================
https://www.hackerrank.com/challenges/almost-sorted-interval

Shik loves sorted intervals. But currently he does not have enough time to sort all the numbers. So he decided to use *Almost sorted intervals*. An *Almost sorted interval* is a consecutive subsequence in a sequence which satisfies the following property:

1. The first number is the smallest.
2. The last number is the largest.

Please help him count the number of almost sorted intervals in this permutation.

*Note:* Two intervals are different if at least one of the starting or ending indices are different.

####Input Format 
The first line contains an integer N. 
The second line contains a permutation from 1 to N.

####Output Format
Output the number of almost sorted intervals.

####Constraints 
1 ≤ N ≤ 10^6

####Sample Input
```
5
3 1 2 5 4
```
####Sample Output
```
8
```
####Explanation 
The subsequences [3], [1], [1 2], [1 2 5], [2], [2 5], [5], [4] are almost sorted intervals.

---
Note: My solution has a time complexity too great to complete large sets.
