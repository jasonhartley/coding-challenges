Number Complement (Programming)
===============================

A complement of a number is defined as the inversion (if the bit value = 0, change it to 1 and vice-versa) of all bits 
of the number starting from the leftmost bit that is set to 1. For example, if N = 5, N is represented as 101 in binary. 
The complement of N is 010, which is 2 in decimal notation. Similarly if N = 50, then the complement of N is 13. 
Complete the function **getIntegerComplement()**. This function accepts N as parameter. The function should return the 
complement of N. The section of the program which parses the input and displays the output will be handled by us. Your 
task is to complete the body of the function or method provided, and to return the correct output. Do not convert the 
number to a string or characters during the complement calculation. Example, do not use : Integer.toBinaryString.

###Reminder:
The test is designed to press the time limits, so please submit any code written, even if not fully finished with the 
problem. A good start with unfinished code is much better than no code at all for a question.

###Constraints:
0 ≤ N ≤ 100000.
Do not convert N to a string or character array at any point in the calculation of the complement.

Sample Test Cases:
------------------
###Input #00:
```
50
```
###Output #00:
```
13
```
###Explanation:
50 in decimal form equals: **110010** when converted to binary.
Inverting the bit sequence: 001101. This is the binary equivalent of decimal **13**.

###Input #01:
```
100
```
###Output #01:
```
27
```
###Explanation:
The bit sequence for 100 is 1100100. Inverting the sequence gives 0011011 which is the binary equivalent of **decimal 27**.
