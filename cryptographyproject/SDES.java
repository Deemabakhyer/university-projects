/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cryptographyproject;

import java.util.Scanner;

/**
 *
 * @author deema
 */
public class SDES {

    int P10[] = {3, 5, 2, 7, 4, 10, 1, 9, 8, 6};
    int P8[] = {6, 3, 7, 4, 8, 5, 10, 9};

    int key1[] = new int[8];
    int key2[] = new int[8];

    int[] IP = {2, 6, 3, 1, 4, 8, 5, 7};
    int[] EP = {4, 1, 2, 3, 2, 3, 4, 1};
    int[] P4 = {2, 4, 3, 1};
    int[] IP_inv = {4, 1, 3, 5, 7, 2, 8, 6};

    int[][] S0 = {{1, 0, 3, 2}, {3, 2, 1, 0}, {0, 2, 1, 3}, {3, 1, 3, 2}};
    int[][] S1 = {{0, 1, 2, 3}, {2, 0, 1, 3}, {3, 0, 1, 0}, {2, 1, 0, 3}};

    void key_generation(int[] key) {
        try {
            if (key.length < 10) {
                throw new IllegalArgumentException("Key must have exactly 10 bits.");
            }

            int key_[] = new int[10];

            // Apply P10 permutation
            for (int i = 0; i < 10; i++) {
                if (P10[i] - 1 >= key.length) {
                    throw new ArrayIndexOutOfBoundsException("P10 index out of bounds");
                }
                key_[i] = key[P10[i] - 1];
            }

            int[] Ls = new int[5];
            int[] Rs = new int[5];

            // Split into two halves
            for (int i = 0; i < 5; i++) {
                Ls[i] = key_[i];
                Rs[i] = key_[i + 5];
            }

            // First key (K1)
            Ls = shift(Ls, 1);
            Rs = shift(Rs, 1);
            for (int i = 0; i < 5; i++) {
                key_[i] = Ls[i];
                key_[i + 5] = Rs[i];
            }
            for (int i = 0; i < 8; i++) {
                key1[i] = key_[P8[i] - 1];
            }

            // Second key (K2)
            Ls = shift(Ls, 2);
            Rs = shift(Rs, 2);
            for (int i = 0; i < 5; i++) {
                key_[i] = Ls[i];
                key_[i + 5] = Rs[i];
            }
            for (int i = 0; i < 8; i++) {
                key2[i] = key_[P8[i] - 1];
            }

        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error: Key array index out of bounds!");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Unexpected error in key_generation: " + e.getMessage());
        }
    }

    int[] shift(int[] ar, int n) {
        while (n > 0) {
            int temp = ar[0];
            for (int i = 0; i < ar.length - 1; i++) {
                ar[i] = ar[i + 1];
            }
            ar[ar.length - 1] = temp;
            n--;
        }
        return ar;
    }

    int[] encrypt(int[] plaintext) {
        int[] arr = new int[8];

        // Initial permutation
        for (int i = 0; i < 8; i++) {
            arr[i] = plaintext[IP[i] - 1];
        }

        int[] arr1 = f(arr, key1);
        int[] after_swap = swap(arr1);
        int[] arr2 = f(after_swap, key2);

        int[] ciphertext = new int[8];
        for (int i = 0; i < 8; i++) {
            ciphertext[i] = arr2[IP_inv[i] - 1];
        }
        return ciphertext;
    }

    int[] f(int[] ar, int[] key_) {
        int[] l = new int[4];
        int[] r = new int[4];

        // Split into two halves
        for (int i = 0; i < 4; i++) {
            l[i] = ar[i];
            r[i] = ar[i + 4];
        }

        // Expansion permutation
        int[] ep = new int[8];
        for (int i = 0; i < 8; i++) {
            ep[i] = r[EP[i] - 1];
        }

        // XOR with key
        for (int i = 0; i < 8; i++) {
            ep[i] ^= key_[i];
        }

        // S-Boxes
        int row, col;
        row = (ep[0] << 1) | ep[3]; // First and last bits
        col = (ep[1] << 1) | ep[2]; // Middle two bits
        int s0Value = S0[row][col];

        row = (ep[4] << 1) | ep[7];
        col = (ep[5] << 1) | ep[6];
        int s1Value = S1[row][col];

        // Combine S-Box outputs
        int[] sOutput = new int[4];
        sOutput[0] = (s0Value >> 1) & 1;
        sOutput[1] = s0Value & 1;
        sOutput[2] = (s1Value >> 1) & 1;
        sOutput[3] = s1Value & 1;

        // P4 permutation
        int[] r_p4 = new int[4];
        for (int i = 0; i < 4; i++) {
            r_p4[i] = sOutput[P4[i] - 1];
        }

        // XOR with left half
        for (int i = 0; i < 4; i++) {
            l[i] ^= r_p4[i];
        }

        // Produce output
        int[] output = new int[8];
        for (int i = 0; i < 4; i++) {
            output[i] = l[i];
            output[i + 4] = r[i];
        }
        return output;
    }

    int[] swap(int[] array) {
        int[] output = new int[8];
        for (int i = 0; i < 4; i++) {
            output[i] = array[i + 4];
            output[i + 4] = array[i];
        }
        return output;
    }

    int[] decrypt(int[] ar) {
        int[] arr = new int[8];

        // Initial permutation
        for (int i = 0; i < 8; i++) {
            arr[i] = ar[IP[i] - 1];
        }

        // Decryption process
        int[] arr1 = f(arr, key2);
        int[] after_swap = swap(arr1);
        int[] arr2 = f(after_swap, key1);

        int[] decrypted = new int[8];
        for (int i = 0; i < 8; i++) {
            decrypted[i] = arr2[IP_inv[i] - 1];
        }
        return decrypted;
    }

    public static void main(String[] args) {

        SDES s_des = new SDES();
        Scanner inp = new Scanner(System.in);

        System.out.println("\nChoose an operation:");
        System.out.println("1. Encryption");
        System.out.println("2. Decryption");
        System.out.println("3. Both Encryption and Decryption");
        System.out.print("Enter your choice (1, 2, or 3): ");
        int choice = inp.nextInt();

        switch (choice) {
            case 1:
            case 2:
            case 3:
                System.out.println("Enter the 10 Bit Key (binary):");
                String k = inp.next();
                if (k.length() != 10) {
                    System.out.println("Invalid key! Please enter exactly 10 bits.");
                    return;
                }

                if (!k.matches("[01]{10}")) {
                    System.out.println("Invalid key! Please enter exactly 10 bits (0s and 1s).");
                    return;
                }
                int[] key = new int[10];
                for (int i = 0; i < k.length(); i++) {
                    key[i] = Integer.parseInt(k.substring(i, i + 1));
                }

                int[] plaintext = new int[8];
                if (choice == 1 || choice == 3) {
                    System.out.println("Enter the 8 Bit plaintext message (binary):");
                    String input = inp.next();
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
                    System.out.println();

                }

                if (choice == 2 || choice == 3) {
                    System.out.println("Enter the 8 Bit ciphertext message (binary):");
                    String input2 = inp.next();
                    if (!input2.matches("[01]{8}")) {
                        System.out.println("Invalid ciphertext! Please enter exactly 8 bits (0s and 1s).");
                        return;
                    }
                    int[] cipherText = new int[8];
                    for (int i = 0; i < input2.length(); i++) {
                        cipherText[i] = Integer.parseInt(input2.substring(i, i + 1));
                    }
                    s_des.key_generation(key);
                    int[] decrypted = s_des.decrypt(cipherText);
                    System.out.println("Decrypted Text:");
                    for (int i : decrypted) {
                        System.out.print(i);
                    }
                    System.out.println();

                }
                break;

            default:
                System.out.println("Invalid choice. Please select 1, 2, or 3.");
                break;
        }

        inp.close();
    }
}
