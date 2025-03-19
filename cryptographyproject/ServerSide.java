package cryptographyproject;

import static cryptographyproject.ClientSide.modularExponentiation;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerSide {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        RSA r = new RSA();
        int port = 12345; // Choose a port number
        int b = 0, A = 0, P = 0, S_B = 0, number = 0;
        SDES s_des = new SDES();
        CeaserCipher cc = new CeaserCipher();

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server is listening on port " + port);

            while (true) {
                try (Socket socket = serverSocket.accept()) {
                    System.out.println("New client connected");
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                    String encryptionChoice = in.readLine();
                    String pubChoice = in.readLine();

                    switch (encryptionChoice) {
                        case "1":
                            switch (pubChoice) {
                                case "1": {
                                    String encryptedKEY = in.readLine();
                                    int keyAsInt = Integer.parseInt(encryptedKEY);
                                    System.out.println("Received the cipherText containing the key used in symmetric encryption: " + keyAsInt);
                                    int rsaKey = r.RSAdecrypt(keyAsInt);
                                    System.out.println("The key used in symmetric encryption: " + rsaKey);
                                    String cipherRSA = in.readLine();
                                    System.out.println("Received ciphertext for Caesar cipher: " + cipherRSA);
                                    String decryptedText = cc.decryptCC(cipherRSA, rsaKey);
                                    System.out.println("Decrypted text: " + decryptedText);
                                    out.println(decryptedText);
                                    break;
                                }
                                case "2": {
                                    String clientMessage = in.readLine();
                                    System.out.println("Received from client: " + clientMessage);
                                    if (clientMessage.equals("enter your private key")) {
                                        System.out.print("Enter your private key (b): ");
                                        b = scn.nextInt();
                                        out.println(b);
                                    }
                                    String publicKeyA = in.readLine();
                                    A = Integer.parseInt(publicKeyA);
                                    String receivedP = in.readLine();
                                    P = Integer.parseInt(receivedP);
                                    S_B = RSA.power(A, b, P);
                                    System.out.println("Calculated secret key S_B: " + S_B);
                                    String ciphertext = in.readLine();
                                    System.out.println("Received ciphertext for Caesar cipher: " + ciphertext);
                                    String decryptedText = cc.decryptCC(ciphertext, S_B);
                                    System.out.println("Decrypted text: " + decryptedText);
                                    out.println(decryptedText);
                                    break;
                                }
                                case "3": {
                                    int secretKey = Integer.parseInt(in.readLine());
                                    int t = Integer.parseInt(in.readLine());
                                    int y = Integer.parseInt(in.readLine());
                                    int c = Integer.parseInt(in.readLine());
                                    int r1 = Integer.parseInt(in.readLine());
                                    String enc = in.readLine();
                                    System.out.println("recive ceaser ciphertext" + enc);
                                    int EC = Integer.parseInt(in.readLine());
                                    System.out.println("recive encrypted key " + EC);

                                    int brmodp = modularExponentiation(y, r1, t); // b^r mod p
                                    // Decryption
                                    int crmodp = modularExponentiation(brmodp, secretKey, t); // c^r mod p
                                    int d = modularInverse(crmodp, t); // Calculate the modular inverse of c^r mod p
                                    int ad = (d * EC) % t;
                                    System.out.println("decrypted key" + ad);
                                    String ccc = cc.decryptCC(enc, ad);
                                    System.out.println("decrypted plainTexr" + ccc);

                                    break;
                                }
                                default:
                                    System.out.println("Invalid public key choice.");
                                    break;
                            }
                            break;

                        case "2":
                            switch (pubChoice) {
                                case "1": {
                                    String encryptedKEY = in.readLine();
                                    int keyAsInt = Integer.parseInt(encryptedKEY);
                                    System.out.println("The encrypted key is: " + keyAsInt);
                                    int rsaKey = r.RSAdecrypt(keyAsInt);
                                    String numberStr = Integer.toString(rsaKey);

                                    int[] digits = new int[numberStr.length()];
                                    for (int i = 0; i < numberStr.length(); i++) {
                                        digits[i] = Character.getNumericValue(numberStr.charAt(i));
                                    }

                                    System.out.print("Digits Array: ");
                                    for (int digit : digits) {
                                        System.out.print(digit + " ");
                                    }
                                    System.out.println("The key used in symmetric encryption: " + rsaKey);
                                    String sdesCiphertext = in.readLine();
                                    System.out.println("Received ciphertext for S-DES: " + sdesCiphertext);
                                    int[] ciphertextArray = new int[sdesCiphertext.length()];
                                    for (int i = 0; i < sdesCiphertext.length(); i++) {
                                        ciphertextArray[i] = Character.getNumericValue(sdesCiphertext.charAt(i));
                                    }
                                    s_des.key_generation(digits);
                                    int[] plnT = s_des.decrypt(ciphertextArray);
                                    System.out.println("The plainText is: ");
                                    for (int digit : plnT) {
                                        System.out.print(digit + "");
                                    }
                                    break;
                                }
                                case "2": {
                                    String clientMessage = in.readLine();
                                    System.out.println("Received from client: " + clientMessage);
                                    if (clientMessage.equals("enter your private key")) {
                                        System.out.print("Enter your private key (b): ");
                                        b = scn.nextInt();
                                        out.println(b);
                                    }
                                    String publicKeyA = in.readLine();
                                    A = Integer.parseInt(publicKeyA);
                                    String receivedP = in.readLine();
                                    P = Integer.parseInt(receivedP);
                                    S_B = RSA.power(A, b, P);
                                    System.out.println("Calculated secret key S_B: " + S_B);
                                    String sdesCiphertext = in.readLine();
                                    System.out.println("Received ciphertext for S-DES: " + sdesCiphertext);
                                    int[] ciphertextArray = new int[sdesCiphertext.length()];
                                    for (int i = 0; i < sdesCiphertext.length(); i++) {
                                        ciphertextArray[i] = Character.getNumericValue(sdesCiphertext.charAt(i));
                                    }
                                    number = S_B;
                                    String k = String.format("%10s", Integer.toBinaryString(number)).replace(' ', '0');
                                    int[] binaryArray = new int[k.length()];

                                    for (int i = 0; i < k.length(); i++) {
                                        binaryArray[i] = Character.getNumericValue(k.charAt(i));
                                    }

                                    int[] key = new int[10];
                                    System.arraycopy(binaryArray, 0, key, 0, binaryArray.length);
                                    s_des.key_generation(key);
                                    int[] decrypted = s_des.decrypt(ciphertextArray);
                                    System.out.println("Decrypted Text:");
                                    for (int i : decrypted) {
                                        System.out.print(i);
                                    }
                                    System.out.println();
                                    break;
                                }
                                default:
                                    System.out.println("Invalid public key choice.");
                                    break;
                            }
                            break;

                        default:
                            System.out.println("Invalid encryption choice.");
                            break;
                    }
                } catch (IOException | NumberFormatException e) {
                    System.err.println("Error in communication: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[] splitIntToArray(int number) {
        String numberStr = Integer.toString(number);
        int[] digits = new int[numberStr.length()];

        for (int i = 0; i < numberStr.length(); i++) {
            digits[i] = Character.getNumericValue(numberStr.charAt(i));
        }

        return digits;
    }

    public static int[] convertStringToIntArray(String str) {
        String[] stringArray = str.split(","); // Split by comma
        int[] intArray = new int[stringArray.length]; // Create int array

        for (int i = 0; i < stringArray.length; i++) {
            intArray[i] = Integer.parseInt(stringArray[i].trim()); // Convert and trim spaces
        }

        return intArray;
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
    // Method to calculate the modular inverse

    public static int modularInverse(int a, int m) {
        a = a % m;
        for (int x = 1; x < m; x++) {
            if ((a * x) % m == 1) {
                return x;
            }
        }
        return -1; // Return -1 if no modular inverse exists
    }
}
