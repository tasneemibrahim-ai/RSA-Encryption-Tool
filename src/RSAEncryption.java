import java.util.ArrayList;
import java.util.Scanner;
public class RSAEncryption {
        public static void main(String[] args) {
            Scanner s = new Scanner(System.in);
            // Read input message and two numbers from user
            System.out.println(" enter your original message :");
            String message = s.nextLine();
            System.out.println(" Enter two distinct upper limits for the two primes p and q :");
            System.out.println(" Upper limit for p is :");
            int p = getPrimeNumber(s.nextInt());
            System.out.println(" Upper limit for q is :");
            int q = getPrimeNumber(s.nextInt());
            // Calculate values for encryption and decryption
            long n = p * q;
            long m = (p - 1) * (q - 1);
            int e = calculateE(m);
            int d = calculateD(e, m);
            // Encrypt message
            ArrayList<Double> encryptedMessage = encryptMessage(message, e, n);
            System.out.println("Your encrypted (cipher) message is :");
            printCharactersFromDoubles(encryptedMessage);
            // Decrypt message
            ArrayList<Double> decryptedMessage = decryptMessage(encryptedMessage, d, n);
            System.out.println("Your decrypted message is :");
            printCharactersFromDoubles(decryptedMessage);
        }
        // Finds the greatest common divisor between two numbers
        static long gcd(long a, int b) {
            long x = a;
            long y = b;
            while (y != 0) {
                long r = x % y;
                x = y;
                y = r;
            }
            return x;
        }
        // Calculates the power of a number using modular exponentiation
        static double power(double x, int y, long z) {
            double power = 1;
            x = x % z;

            while (y > 0) {
                if (y % 2 != 0) {
                    power = (x * power) % z;
                }

                x = (x * x) % z;
                y /= 2;
            }
            return power;
        }
        // Returns the first prime number less than or equal to the input number
        static int getPrimeNumber(int num) {
            for (int i = num; i >= 2; i--) {
                boolean isPrime = true;
                for (int j = 2; j <= Math.sqrt(i); j++) {
                    if (i % j == 0) {
                        isPrime = false;
                        break;
                    }
                }
                if (isPrime) {
                    return i;
                }
            }
            return 2;
        }
        // Calculates the value of e for encryption
        static int calculateE(long m) {
            int e = 2;
            while (gcd(m, e) != 1) {
                e++;
            }
            return e;
        }
        // Calculates the value of d for decryption
        static int calculateD(int e, long m) {
            int d = 1;
            while (((long) d * e) % m != 1) {
                d++;
            }
            return d;
        }
        // Encrypts a message using the given public key (e, n)
        static ArrayList<Double> encryptMessage(String message, int e, long n) {
            ArrayList<Double> encryptedMessage = new ArrayList<>();
            for (int i = 0; i < message.length(); i++) {
                double charValue = message.charAt(i);
                double encryptedValue = power(charValue, e, n);
                encryptedMessage.add(encryptedValue);
            }
            return encryptedMessage;
        }
        // Decrypts a message using the given private key (d, n)
        static ArrayList<Double> decryptMessage(ArrayList<Double> encryptedMessage, int d, long n) {
            ArrayList<Double> decryptedMessage = new ArrayList<>();
            for (double encryptedValue : encryptedMessage) {
                double decryptedValue = power(encryptedValue, d, n);
                decryptedMessage.add(decryptedValue);
            }
            return decryptedMessage;
        }
        // Prints the characters corresponding to the given doubles
        static void printCharactersFromDoubles(ArrayList<Double> doubles) {
            for (double d : doubles) {
                int intValue = (int) d;
                char c = (char) intValue;
                System.out.print(c);
            }
            System.out.println();
        }
    }