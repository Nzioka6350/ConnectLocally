/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package groupChat;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;
import javax.swing.JTextArea;

/**
 *
 * @author varun
 */
public class GroupServer {
    PrintWriter out;
    static int flag = 0;
    static JTextArea chatTextArea;
    static Vector connectedClients = new Vector(5, 5);
    public void createServer(final JTextArea chatTextArea, final int portNumber) {
        this.chatTextArea = chatTextArea;
        try {
            final ServerSocket serverSocket = new ServerSocket(portNumber);
            new Thread() {
                public void run() {
                    while (true) {
                        try {
                            Socket clientSocket = serverSocket.accept();
                            GroupServer.connectedClients.addElement(clientSocket);
                            GroupServer.chatTextArea.append("A new client connected.\n");
                            HandleClientThread handleClientThread = new HandleClientThread(portNumber, clientSocket);
                            Thread newThread = new Thread(handleClientThread);
                            //chatTextArea.append("Client Id: " + newThread.getId() + " Client Name: " + newThread.getName() + "\n");
                            newThread.start();
                        } catch (Exception e) {
                            e.printStackTrace();
                            break;
                        }
                    }
                }
            }.start();
        } catch (BindException e) {
            chatTextArea.append("Address already in use.\n");
            chatTextArea.append("Try using different port number\n");
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }
}

class HandleClientThread implements Runnable {

    int portNumber;
    Socket clientSocket;

    public HandleClientThread(int portNumber, Socket clientSocket) {
        this.portNumber = portNumber;
        this.clientSocket = clientSocket;
    }

    public void run() {
        Thread currentThread = Thread.currentThread();
        String currentThreadName = currentThread.getName();
        try {
            //PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                //GroupServer.chatTextArea.append("Client" + currentThreadName.charAt(7) + ": " + inputLine);
                //outPutLine = inputLine + " from server";
                //out.println(outPutLine);
                new Broadcast(clientSocket.getRemoteSocketAddress() + ": " + inputLine).send();
                if (inputLine.equalsIgnoreCase("bye")) {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
class Broadcast {

    String message;

    Broadcast(String message) {
        this.message = message;
    }

    void send() {
        Socket clientSocket = null;
        PrintWriter out = null;
        String messageToBeSent = null;
        for (int i = 0; i < GroupServer.connectedClients.size(); i++) {
            clientSocket = (Socket) GroupServer.connectedClients.get(i);
            try {
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                //messageToBeSent = clientSocket.getRemoteSocketAddress() + ": " + message;
                messageToBeSent = message;
                out.println(messageToBeSent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
