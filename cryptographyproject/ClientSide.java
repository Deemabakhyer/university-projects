/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package cryptographyproject;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.security.SecureRandom;
import java.util.Random;

/**
 *
 * @author deema
 */
public class ClientSide {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        RSA rsa = new RSA();
        int A = 0, a = 0, B = 0, b, S_A = 0, S_B, P = 0, alpha = 0, number = 0;
        SDES s_des = new SDES();
        CeaserCipher cc = new CeaserCipher();
        String serverAddress = "localhost"; // Server address (use IP or hostname)
        int port = 12345; // Same port number as server
        try (Socket socket = new Socket(serverAddress, port)) {
            // Create input and output streams
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("""
                               
                               Which encryption technique would you like?
                               1. Encrypt using Caesar Cipher
                               2. Encrypt using S-DES
                               Enter your choice: """);
            int choiceOfEncryption = scn.nextInt();
            out.println(choiceOfEncryption);
            System.out.println("""
                               
                               Which key exchange technique would you like?
                               1. RSA
                               2. Diffie-Hellman
                               3. ELgamal
                               Enter your choice: """);

            int choicePuKeyAlgo = scn.nextInt();
            out.println(choicePuKeyAlgo);

            //---------------------------------------------------------------------
            //---------------------------------------------------------------------
            switch (choicePuKeyAlgo) {
                case 1 -> {
                    System.out.println("\n \t----- RSA -----");
                    System.out.println("Public Key Algorithm 1 selected.");
                    switch (choiceOfEncryption) {
                        case 1: {
                            System.out.println("enter any integer from 1 - 76 to use as key");
                            int key = scn.nextInt();
                            int encryptedKey = rsa.RSAencrypt(key);
                            System.out.println("The cipgherText containing the key used in symmetric encryption: " + encryptedKey);
                            out.println(encryptedKey);

                            System.out.println("");
                            System.out.println("-----Now Enter a message please to encrypt-----:");
                            scn.nextLine();
                            String text = scn.nextLine();
                            String encryptedText = cc.encryptCC(text, key);
                            System.out.println("The cipgherText of the symmetric encryption: " + encryptedText);
                            out.println(encryptedText);

                            break;
                        }
                        case 2: {
                            System.out.println("Enter the 10 Bit Key (binary):");
                            String k = scn.next();
                            if (!k.matches("[01]{10}")) {
                                System.out.println("Invalid key! Please enter exactly 10 bits (0s and 1s).");
                                return;
                            }
                            int[] key = new int[10];
                            int combinedKey = combineArrayToInt(key);
                            int encryptedKey = rsa.RSAencrypt(combinedKey);
                            String encryptedKeyString = Integer.toString(combinedKey);
                            out.println(encryptedKeyString);
                            for (int i = 0; i < k.length(); i++) {
                                key[i] = Integer.parseInt(k.substring(i, i + 1));
                            }

                            int[] plaintext = new int[8];

                            System.out.println("Enter the 8 Bit plaintext message (binary):");
                            String input = scn.next();
                            if (!input.matches("[01]{8}")) {
                                System.out.println("Invalid plaintext! Please enter exactly 8 bits (0s and 1s).");
                                return;
                            }
                            for (int i = 0; i < input.length(); i++) {
                                plaintext[i] = Integer.parseInt(input.substring(i, i + 1));
                            }
                            s_des.key_generation(key);
                            int[] ciphertext = s_des.encrypt(plaintext);
                            System.out.println("Ciphertext:");
                            for (int i : ciphertext) {
                                System.out.print(i);
                            }
                            StringBuilder sb = new StringBuilder();
                            for (int bit : ciphertext) {
                                sb.append(bit);
                            }
                            out.println(sb.toString());
                            System.out.println();
                            break;
                        }
                    }
                }
//the end of RSA
                case 2 -> {
                    System.out.println("--------------You need to generate a shared key first-------------");
                    System.out.println("\n \t----- D-H -----");
                    System.out.println("Please enter a prime number for P:");

                    // P prime number 
                    P = scn.nextInt();
                    boolean bp = isPrime(P);

                    // Loop until a valid prime number is entered
                    for (int j = 0; j < 50; j++) {
                        if (bp == false) {
                            System.out.println("it is not a prime number please try again");
                            P = scn.nextInt();
                            bp = isPrime(P);

                        } else {
                            break;
                        }

                    }

                    System.out.println("Would you like to 1-enter a primitive root value or 2-calculate by the code?");
                    int choice = scn.nextInt();

                    switch (choice) {
                        case 1 -> {
                            System.out.println("Enter the alpha value:");
                            alpha = scn.nextInt();
                            for (int i = 0; alpha < P; i++) {
                                if (1 < alpha && alpha < P) {
                                    break;
                                }
                                alpha = scn.nextInt();

                            }
                        }

                        case 2 ->
                            alpha = findPrimitive(P);

                        default ->
                            System.out.println("Invalid choice for alpha selection.");
                    }

                    System.out.println("Enter your private key (a):");
                    a = scn.nextInt();
                    for (int i = 0; a < 500; i++) {
                        if (a < P) {
                            break;
                        }
                        a = scn.nextInt();

                    }

                    A = power(alpha, a, P);
                    System.out.println("A: " + A);

                    System.out.println("Waiting for the server response...");
                    out.println("enter your private key"); // Send request for private key from server
                    String response = in.readLine();
                    b = Integer.parseInt(response);

                    for (int i = 0; b < 500; i++) {
                        if (b < P) {
                            break;
                        }
                        response = in.readLine();
                        b = Integer.parseInt(response);

                    }

                    B = power(alpha, b, P);
                    S_A = power(B, a, P);
                    System.out.println("Shared secret (S_A) = " + S_A);

                    out.println(A); // Send public key A to server
                    out.println(P); // Send P to server

                    number = S_A;
                    String k = String.format("%10s", Integer.toBinaryString(number)).replace(' ', '0');
                    //changing the shred key into binary so it is can work with sdes
                    int[] binaryArray = new int[k.length()];

                    for (int i = 0; i < k.length(); i++) {
                        binaryArray[i] = Character.getNumericValue(k.charAt(i));
                    }

                    switch (choiceOfEncryption) {
                        case 1 -> {
                            System.out.println("");
                            System.out.println("-----Now Enter a message please to encrypt-----:");
                            scn.nextLine();
                            String text = scn.nextLine();
                            String encryptedText = cc.encryptCC(text, S_A);
                            System.out.println("Encrypted text: " + encryptedText);
                            out.println(encryptedText);
                        }

                        case 2 -> {
                            int[] key = new int[10];
                            if (choicePuKeyAlgo == 2) {
                                System.arraycopy(binaryArray, 0, key, 0, binaryArray.length);

                            }
                            System.out.println("\nEnter the 8 Bit plaintext message  : ");

                            String input = scn.next();

                            int[] plaintext = new int[8];

                            for (int i = 0; i < input.length(); i++) {
                                plaintext[i] = Integer.parseInt(input.substring(i, i + 1));
                            }

                            s_des.key_generation(key);
                            int[] ciphertext = s_des.encrypt(plaintext);
                            System.out.println("\nThe cipherText is:");
                            for (int i = 0; i < 8; i++) {
                                System.out.print(ciphertext[i]);
                            }
                            StringBuilder sb = new StringBuilder();
                            for (int bit : ciphertext) {
                                sb.append(bit);
                            }
                            out.println(sb.toString());
                            System.out.println("");
                            break;

                        }
                        //if the user's choice was DH and SDES

                        default -> {
                            System.out.println("Invalid choice!");
                            return;
                        }
                    }
                }
                case 3 -> {
                    System.out.println("\n \t----- ELGamal -----");
                    System.out.println("Public Key Algorithm 3 selected.");
                    switch (choiceOfEncryption) {
                        case 1: {
                            int t, y, c, secretKey;
                            Random sc = new SecureRandom();
                            secretKey = 70; // Use an integer value

                            // Public key calculation
                            System.out.println("secretKey = " + secretKey);
                            t = (int) (Math.random() * Integer.MAX_VALUE); // Generate a random integer for t
                            y = 3; // Public base
                            c = modularExponentiation(y, secretKey, t);
                            System.out.println("p = " + t);
                            System.out.println("b = " + y);
                            System.out.println("c = " + c);
                            out.println(secretKey); // Send t
                            out.println(t); // Send t
                            out.println(y); // Send y
                            out.println(c); // Send c

                            // Encryption
                            System.out.print("Enter your key message --> ");
                            int X = scn.nextInt(); // Read an integer
                            scn.nextLine();
                            System.out.print("Enter your message --> ");
                            String X1 = scn.nextLine(); // Read an integer
                            String enc = cc.encryptCC(X1, X);
                            System.out.println("Encrypted message using ceaser cipher: " + enc);
                            int r = (int) (Math.random() * Integer.MAX_VALUE); // Random integer for r
                            int EC = (X * modularExponentiation(c, r, t)) % t; // Encrypt the message
                            System.out.println("El gamal encyption" + EC);
                            int brmodp = modularExponentiation(y, r, t); // b^r mod p

                            // Sending necessary information
                            out.println(r); // Send r
                            out.println(enc);
                            out.println(EC); // Send encrypted message

                            break;
                        }
                        case 2: {/*
                            System.out.print("Enter your secret key (as a big number) --> ");
                            String keyInput = scn.nextLine();
                            BigInteger secretKey = new BigInteger(keyInput);

                            ELGamal elGamal2 = new ELGamal(secretKey);

                            // Encryption
                            System.out.print("Enter the 10 Bit Key (binary): ");
                            String k = scn.next();
                            if (!k.matches("[01]{10}")) {
                                System.out.println("Invalid key! Please enter exactly 10 bits (0s and 1s).");
                                return;
                            }

                            int[] key = new int[10];
                            for (int i = 0; i < k.length(); i++) {
                                key[i] = Integer.parseInt(k.substring(i, i + 1));
                            }

                            System.out.print("Enter the 8 Bit plaintext message (binary): ");
                            String input = scn.next();
                            if (!input.matches("[01]{8}")) {
                                System.out.println("Invalid plaintext! Please enter exactly 8 bits (0s and 1s).");
                                return;
                            }

                            int[] plaintext = new int[8];
                            for (int i = 0; i < input.length(); i++) {
                                plaintext[i] = Integer.parseInt(input.substring(i, i + 1));
                            }

                            // Encrypt using ElGamal
                            BigInteger message = new BigInteger(input, 2); // Convert binary string to BigInteger
                            BigInteger[] encryptedData = elGamal2.encrypt(message);
                            BigInteger EC = encryptedData[0];
                            BigInteger brmodp = encryptedData[1];

                            // Send the encrypted data to the server
                            out.println(EC);
                            out.println(brmodp);

                            // Encrypt plaintext using S-DES
                            s_des.key_generation(key);
                            int[] ciphertext = s_des.encrypt(plaintext);
                            System.out.println("Ciphertext from S-DES:");
                            for (int i : ciphertext) {
                                System.out.print(i);
                            }

                            StringBuilder sb = new StringBuilder();
                            for (int bit : ciphertext) {
                                sb.append(bit);
                            }
                            out.println(sb.toString());
                            break;
                             */ }
                    }
                }
//the end of DH
                default ->
                    System.out.println("Invalid choice for public key algorithm.");
            }

            //S_A = power(B, a, P);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int gcd(int e, int z) {
        if (e == 0) {
            return z;
        } else {
            return gcd(z % e, e);
        }
    }

    public static int E_E_A(int a, int b) {
        int num1 = a, num2 = b;
        int x = 0, y = 1, lastx = 1, lasty = 0, temp;
        while (b != 0) {
            int q = a / b;
            int r = a % b;

            a = b;
            b = r;

            temp = x;
            x = lastx - q * x;
            lastx = temp;

            temp = y;
            y = lasty - q * y;
            lasty = temp;
        }

        System.out.println("Extended Ecludian Algoritlm for a= " + num1 + " and b= " + num2);
        System.out.println(" GCD = a.x + b.y");
        System.out.println(a + " = " + num1 + ".(" + lastx + ") + " + num2 + ".(" + lasty + ")");

        return lasty;
    }

    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }

        }
        return true;
    }

    public static long power(long a, long b, long p) {
        if (b == 1) {
            return a;
        } else {
            return (((long) Math.pow(a, b)) % p);
        }
    }

    // Utility function to store prime factors of a number
    public static void findPrimefactors(HashSet<Integer> s, int n) {
        // Print the number of 2s that divide n
        while (n % 2 == 0) {
            s.add(2);
            n = n / 2;
        }

        // n must be odd at this point. So we can skip
        // one element (Note i = i +2)
        for (int i = 3; i <= Math.sqrt(n); i = i + 2) {
            // While i divides n, print i and divide n
            while (n % i == 0) {
                s.add(i);
                n = n / i;
            }
        }

        // This condition is to handle the case when
        // n is a prime number greater than 2
        if (n > 2) {
            s.add(n);
        }
    }

    /* Iterative Function to calculate (x^n)%p in
    O(logy) */
    public static int power(int x, int y, int p) {
        int res = 1;     // Initialize result

        x = x % p; // Update x if it is more than or
        // equal to p

        while (y > 0) {
            // If y is odd, multiply x with result
            if (y % 2 == 1) {
                res = (res * x) % p;
            }

            // y must be even now
            y = y >> 1; // y = y/2
            x = (x * x) % p;
        }
        return res;
    }

    // Function to find smallest primitive root of n
    public static int findPrimitive(int n) {
        HashSet<Integer> s = new HashSet<>();

        // Find value of Euler Totient function of n
        // Since n is a prime number, the value of Euler
        // Totient function is n-1 as there are n-1
        // relatively prime numbers.
        int phi = n - 1;

        // Find prime factors of phi and store in a set
        findPrimefactors(s, phi);

        // Check for every number from 2 to phi
        for (int r = 2; r <= phi; r++) {
            // Iterate through all prime factors of phi.
            // and check if we found a power with value 1
            boolean flag = false;
            for (Integer a : s) {

                // Check if r^((phi)/primefactors) mod n
                // is 1 or not
                if (power(r, phi / (a), n) == 1) {
                    flag = true;
                    break;
                }
            }

            // If there was no power with value 1.
            if (flag == false) {
                return r;
            }
        }

        // If no primitive root found
        return -1;
    }

    //the end of the key generaiton
    public int[] shift(int[] ar, int n) {
        while (n > 0) {
            int temp = ar[0];
            for (int i = 0; i < ar.length - 1; i++) {
                ar[i] = ar[i + 1];
            }
            ar[ar.length - 1] = temp;
            n--;
        }
        return ar;
    }//method for the shifting process

    public static int combineArrayToInt(int[] array) {
        int result = 0;
        for (int i = 0; i < array.length; i++) {
            result = result * 10 + array[i]; // دمج الأعداد
        }
        return result;
    }

    public static int modularExponentiation(int base, int exponent, int modulus) {
        int result = 1;
        base = base % modulus; // Update base if it's greater than or equal to modulus
        while (exponent > 0) {
            if ((exponent & 1) == 1) { // If exponent is odd, multiply base with result
                result = (result * base) % modulus;
            }
            exponent >>= 1; // Divide exponent by 2
            base = (base * base) % modulus; // Square the base
        }
        return result;
    }
}
