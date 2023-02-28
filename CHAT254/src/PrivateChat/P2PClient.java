package privateChat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import javax.swing.JTextArea;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author varun
 */
public class P2PClient {
    public PrintWriter joinServer(final JTextArea chatTextArea, String ipAddress, int portNumber) throws IOException {
        chatTextArea.append("creating connection\n");
        Socket clientSocket = null;
        try {
            clientSocket = new Socket(ipAddress, portNumber);
            chatTextArea.append("connected. start chatting\n");
        } catch (ConnectException e) {
            chatTextArea.append("Connection refused by the server.\n");
            chatTextArea.append("Make sure that server is created before you join. Try again\n");
        }
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        final BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        new Thread() {
            public void run() {
                String message = null;
                while (true) {
                    try {
                        message = in.readLine();
                        if (message == null) {
                            chatTextArea.append("Connection closed \n");
                            break;
                        }
                        message = "Your friend: " + message;
                        chatTextArea.append(message + "\n");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
        return out;
    } 
}
