package cryptographyproject;

import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.security.SecureRandom;
import java.util.Scanner;

class ElGamal {

    private BigInteger p, g, x, y;
    private SecureRandom random = new SecureRandom();

    public ElGamal(int bitLength) {
        p = BigInteger.probablePrime(bitLength, random);
        g = new BigInteger(bitLength - 1, random).mod(p);
        x = new BigInteger(bitLength - 1, random).mod(p);
        y = g.modPow(x, p);
    }

    public BigInteger getP() {
        return p;
    }

    public BigInteger getG() {
        return g;
    }

    public BigInteger getY() {
        return y;
    }

    public BigInteger getX() {
        return x;
    }

    public BigInteger[] encrypt(BigInteger message, BigInteger y, BigInteger g, BigInteger p) {
        BigInteger k = new BigInteger(p.bitLength() - 1, random).mod(p);
        BigInteger c1 = g.modPow(k, p);
        BigInteger c2 = message.multiply(y.modPow(k, p)).mod(p);
        return new BigInteger[]{c1, c2};
    }

    public BigInteger decrypt(BigInteger c1, BigInteger c2) {
        BigInteger s = c1.modPow(x, p);
        BigInteger sInv = s.modInverse(p);
        return c2.multiply(sInv).mod(p);
    }
}
