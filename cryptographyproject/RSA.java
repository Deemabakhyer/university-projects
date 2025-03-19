/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cryptographyproject;

import java.math.BigInteger;

/**
 *
 * @author deema
 */
public class RSA {

    public int RSAencrypt(int key) {

        int p, q, n, totient, d = 0, e, i;

        BigInteger msg = BigInteger.valueOf(key);
        BigInteger C;

        p = 7;
        q = 11;
        n = p * q;
        BigInteger N = BigInteger.valueOf(n);
        totient = (p - 1) * (q - 1);

        // choosing e --------------
        for (e = 2; e < totient; e++) {

            if (gcd(e, totient) == 1) {
                break;
            }
        }

        // Calculating d -----------
        for (i = 0; i <= 9; i++) {
            int x = 1 + (i * totient);

            if (x % e == 0) {
                d = x / e;
                break;
            }
        }

        // Enncryption -----------
        C = (msg.pow(e)).mod(N);
        System.out.println("The encrypted key is : " + C);
        int c = C.intValue();
        return c;

    }

    public int RSAdecrypt(int encryptedKey) {
        int p, q, n, totient, d = 0, e, i;

        BigInteger C = BigInteger.valueOf(encryptedKey);
        BigInteger msgback;

        p = 7;
        q = 11;
        n = p * q;
        BigInteger N = BigInteger.valueOf(n);
        totient = (p - 1) * (q - 1);

        // choosing e --------------
        for (e = 2; e < totient; e++) {

            if (gcd(e, totient) == 1) {
                break;
            }
        }

        // Calculating d -----------
        for (i = 0; i <= 9; i++) {
            int x = 1 + (i * totient);

            if (x % e == 0) {
                d = x / e;
                break;
            }
        }
        // Decryption -------------
        msgback = (C.pow(d)).mod(N);
        System.out.println("The decrypted key is : " + msgback);
        int msg = msgback.intValue();
        return msg;

    }

    public int gcd(int e, int z) {
        if (e == 0) {
            return z;
        } else {
            return gcd(z % e, e);
        }
    }

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
}
