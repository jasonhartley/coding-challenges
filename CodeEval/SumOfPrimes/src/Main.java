/**
 * Sum of First 1000 Primes
 *
 * Challenge description: https://www.codeeval.com/open_challenges/4/
 */
public class Main {
    public static void main(String[] args) {
        final int LIMIT = 1000;
        int count = 0;
        int sum = 0;
        int n = 7919;//this is the 1000th prime
        boolean[] isPrime = new boolean[n + 1];

        // Begin with all integers as prime
        for (int i = 2; i <= n; i++) {
            isPrime[i] = true;
        }

        // Sieve of Eratosthenes
        for (int i = 2; i * i <= n; i++) {
            if (isPrime[i]) {
                for (int j = i; i * j <= n; j++) {
                    isPrime[i * j] = false;
                }
            }
        }

        // Sum
        for (int i = 2; i <= n && count < LIMIT; i++) {
            if (isPrime[i]) {
                sum += i;
                count++;
            }
        }
        System.out.println(sum);
    }
}