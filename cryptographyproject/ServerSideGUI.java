/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package cryptographyproject;

/**
 *
 * @author deema
 */
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSideGUI extends JFrame {

    private JTextArea logArea;

    public ServerSideGUI() {
        setTitle("Server Side");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        logArea = new JTextArea();
        logArea.setEditable(false);
        add(new JScrollPane(logArea), BorderLayout.CENTER);

        startServer();
    }

    private void startServer() {
        int port = 12345;

        new Thread(() -> {
            try (ServerSocket serverSocket = new ServerSocket(port)) {
                logArea.append("Server is listening on port " + port + "\n");

                while (true) {
                    Socket socket = serverSocket.accept();
                    logArea.append("New client connected\n");

                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                    int choiceOfEncryption = Integer.parseInt(in.readLine());
                    int choicePuKeyAlgo = Integer.parseInt(in.readLine());

                    if (choiceOfEncryption == 1) {
                        String key = in.readLine();
                        String message = in.readLine();
                        logArea.append("Received Key: " + key + "\n");
                        logArea.append("Received Message: " + message + "\n");

                        CeaserCipher cc = new CeaserCipher();
                        String encryptedText = cc.encryptCC(message, Integer.parseInt(key));
                        out.println(encryptedText);
                        logArea.append("Sent Encrypted Text: " + encryptedText + "\n");
                    } else if (choiceOfEncryption == 2) {
                        String key = in.readLine();
                        String message = in.readLine();
                        logArea.append("Received Key: " + key + "\n");
                        logArea.append("Received Message: " + message + "\n");

                        SDES sdes = new SDES();
                        int[] keyArray = new int[10];
                        for (int i = 0; i < key.length(); i++) {
                            keyArray[i] = Integer.parseInt(key.substring(i, i + 1));
                        }
                        sdes.key_generation(keyArray);

                        int[] plaintext = new int[8];
                        for (int i = 0; i < message.length(); i++) {
                            plaintext[i] = Integer.parseInt(message.substring(i, i + 1));
                        }
                        int[] ciphertext = sdes.encrypt(plaintext);
                        StringBuilder sb = new StringBuilder();
                        for (int bit : ciphertext) {
                            sb.append(bit);
                        }
                        out.println(sb.toString());
                        logArea.append("Sent Encrypted Text: " + sb.toString() + "\n");
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ServerSideGUI serverGUI = new ServerSideGUI();
            serverGUI.setVisible(true);
        });
    }
}
