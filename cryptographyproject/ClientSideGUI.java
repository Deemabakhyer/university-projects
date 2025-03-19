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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSideGUI extends JFrame {

    private JComboBox<String> encryptionComboBox;
    private JComboBox<String> keyExchangeComboBox;
    private JTextField keyField;
    private JTextField messageField;
    private JTextArea outputArea;
    private JButton sendButton;

    public ClientSideGUI() {
        setTitle("Client Side");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new GridLayout(5, 2));
        inputPanel.add(new JLabel("Encryption Technique:"));
        encryptionComboBox = new JComboBox<>(new String[]{"Caesar Cipher", "S-DES"});
        inputPanel.add(encryptionComboBox);

        inputPanel.add(new JLabel("Key Exchange Technique:"));
        keyExchangeComboBox = new JComboBox<>(new String[]{"RSA", "Diffie-Hellman"});
        inputPanel.add(keyExchangeComboBox);

        inputPanel.add(new JLabel("Key:"));
        keyField = new JTextField();
        inputPanel.add(keyField);

        inputPanel.add(new JLabel("Message:"));
        messageField = new JTextField();
        inputPanel.add(messageField);

        sendButton = new JButton("Send");
        inputPanel.add(sendButton);

        add(inputPanel, BorderLayout.NORTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        add(new JScrollPane(outputArea), BorderLayout.CENTER);

        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sendMessage();
            }
        });
    }

    private void sendMessage() {
        String serverAddress = "localhost";
        int port = 12345;

        try (Socket socket = new Socket(serverAddress, port)) {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            int choiceOfEncryption = encryptionComboBox.getSelectedIndex() + 1;
            out.println(choiceOfEncryption);

            int choicePuKeyAlgo = keyExchangeComboBox.getSelectedIndex() + 1;
            out.println(choicePuKeyAlgo);

            String key = keyField.getText();
            String message = messageField.getText();

            if (choiceOfEncryption == 1) {
                out.println(key);
                out.println(message);
                String encryptedText = in.readLine();
                outputArea.append("Encrypted Text: " + encryptedText + "\n");
            } else if (choiceOfEncryption == 2) {
                out.println(key);
                out.println(message);
                String encryptedText = in.readLine();
                outputArea.append("Encrypted Text: " + encryptedText + "\n");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ClientSideGUI clientGUI = new ClientSideGUI();
            clientGUI.setVisible(true);
        });
    }
}
