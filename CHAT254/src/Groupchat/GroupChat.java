/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package groupChat;

import ipaddress.GetMyIpAddress;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import privateChat.PrivateChat;

/**
 *
 * @author varun
 */
public class GroupChat extends PrivateChat {
    
    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String inputMessage = chatInputTextField.getText();
        chatInputTextField.setText("");
        if(out != null) {
            out.println(inputMessage);
        }
        else {
            System.out.println("Not connected till now");
        }
    }
    @Override
    public void createServerChatBox(int portNumber) throws IOException {
        //System.out.println(portNumber);
        ipAddresses = new GetMyIpAddress().ipAddress();
        JLabel ipAddressLabel = new JLabel();
        ipAddressLabel.setBounds(5, 25, 140, 30);
        ipAddressLabel.setText("Your IpAddress: ");
        add(ipAddressLabel);
        JLabel ipAddressValueLabel = new JLabel();
        ipAddressValueLabel.setBounds(150, 25, 240, 30);
        ipAddressValueLabel.setText(ipAddresses[0] + "  " + ipAddresses[1]);
        add(ipAddressValueLabel);
        JLabel portLabel = new JLabel();
        portLabel.setBounds(470, 25, 50, 30);
        portLabel.setText("Port: ");
        add(portLabel);
        JLabel portValueLabel = new JLabel();
        portValueLabel.setBounds(510, 25, 100, 30);
        portValueLabel.setText(String.valueOf(portNumber));
        add(portValueLabel);
        chatTextArea = new JTextArea();
        chatTextArea.setBounds(5, 70, 550, 200);
        chatTextArea.setEditable(false);
        //add(chatTextArea);
        JScrollPane scroll = new JScrollPane(chatTextArea);
        scroll.setBounds(5, 70, 550, 200);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(scroll);
        chatInputTextField = new JTextField();
        chatInputTextField.setBounds(5, 285, 460, 30);
        chatInputTextField.addActionListener(this);
        add(chatInputTextField);
        sendButton = new JButton("send");
        sendButton.setBounds(470, 285, 85, 30);
        sendButton.addActionListener(this);
        add(sendButton);
        //setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(570, 360);
        setLayout(null);
        setVisible(true);
        new GroupServer().createServer(chatTextArea, portNumber);
        out = new GroupClient().joinServer(chatTextArea, ipAddresses[0], portNumber);
    }
    @Override
    public void joinServerChatBox(String ipAddress, int portNumber) throws IOException {
        ipAddresses = new GetMyIpAddress().ipAddress();
        JLabel ipAddressLabel = new JLabel();
        ipAddressLabel.setBounds(5, 25, 150, 30);
        ipAddressLabel.setText("Server IpAddress: ");
        add(ipAddressLabel);
        JLabel ipAddressValueLabel = new JLabel();
        ipAddressValueLabel.setBounds(160, 25, 120, 30);
        //ipAddressValueLabel.setText(ipAddresses[0] + "  " + ipAddresses[1]);
        ipAddressValueLabel.setText(ipAddress);
        add(ipAddressValueLabel);
        JLabel portLabel = new JLabel();
        portLabel.setBounds(350, 25, 150, 30);
        portLabel.setText("server Port no: ");
        add(portLabel);
        JLabel portValueLabel = new JLabel();
        portValueLabel.setBounds(510, 25, 100, 30);
        portValueLabel.setText(String.valueOf(portNumber));
        add(portValueLabel);
        chatTextArea = new JTextArea();
        chatTextArea.setBounds(5, 70, 550, 200);
        chatTextArea.setEditable(false);
        //add(chatTextArea);
        JScrollPane scroll = new JScrollPane(chatTextArea);
        scroll.setBounds(5,70,550,200);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(scroll);
        chatInputTextField = new JTextField();
        chatInputTextField.setBounds(5, 285, 460, 30);
        chatInputTextField.addActionListener(this);
        add(chatInputTextField);
        sendButton = new JButton("send");
        sendButton.setBounds(470, 285, 85, 30);
        sendButton.addActionListener(this);
        add(sendButton);
        //setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(570, 360);
        setLayout(null);
        setVisible(true);
        out = new GroupClient().joinServer(chatTextArea, ipAddress, portNumber);
    }
}




