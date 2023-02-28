/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package privateChat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import ipaddress.GetMyIpAddress;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

/**
 *
 * @author varun
 */
public class PrivateChat extends javax.swing.JFrame implements ActionListener{
    public String ipAddresses[] = {};
    public JTextArea chatTextArea;
    public JTextField chatInputTextField;
    public JButton sendButton;
    public PrintWriter out;
   
    @Override
    public void actionPerformed(ActionEvent e) {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        String inputMessage = chatInputTextField.getText();
        chatTextArea.append("You: "+ inputMessage + "\n");
        chatInputTextField.setText("");
        if(out != null) {
            out.println(inputMessage);
        }
        else {
            System.out.println("Not connected till now");
        }
    }
    public void showOptionsCreateServerJoinServer() throws IOException {
        Object options[] = {"Create server", "Join server"};
        int choice = JOptionPane.showOptionDialog(this,
            "What would you like to do?",
            "Choose an option",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null, //do not use a custom Icon
            options, //the titles of buttons
            options[0]); //default button title
        if(choice == JOptionPane.YES_OPTION) {
            showCreateServerDialog();
        }
        else if(choice == JOptionPane.NO_OPTION) {
            showJoinServerDialog();
        }
        else {
            //System.exit(0);
        }
    }
    public void joinServerChatBox(String ipAddress, int portNumber) throws IOException {
        ipAddresses = new GetMyIpAddress().ipAddress();
        JLabel ipAddressLabel = new JLabel();
        ipAddressLabel.setBounds(5, 25, 150, 30);
        ipAddressLabel.setText("Server IpAddress: ");
        add(ipAddressLabel);
        JLabel ipAddressValueLabel = new JLabel();
        ipAddressValueLabel.setBounds(160, 25, 120, 30);
        //ipAddressValueLabel.setText(ipAddresses[0] + "  " + ipAddresses[1]);//here is the problem
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
        out = new P2PClient().joinServer(chatTextArea, ipAddress, portNumber);
    }
    public void createServerChatBox(int portNumber) throws IOException {
        //System.out.println(portNumber);
        JLabel ipAddressLabel = new JLabel();
        ipAddressLabel.setBounds(5,25,140,30);
        ipAddressLabel.setText("Your IpAddress: ");
        add(ipAddressLabel);
        JLabel ipAddressValueLabel = new JLabel();
        ipAddressValueLabel.setBounds(150,25,240,30);
        ipAddressValueLabel.setText(ipAddresses[0] + "  " + ipAddresses[1]);
        add(ipAddressValueLabel);
        JLabel portLabel = new JLabel();
        portLabel.setBounds(470,25,50,30);
        portLabel.setText("Port: ");
        add(portLabel);
        JLabel portValueLabel = new JLabel();
        portValueLabel.setBounds(510,25,100,30);
        portValueLabel.setText(String.valueOf(portNumber));
        add(portValueLabel);
        chatTextArea = new JTextArea();
        chatTextArea.setBounds(5,70,550,200);
        chatTextArea.setEditable(false);
        //add(chatTextArea);
        JScrollPane scroll = new JScrollPane(chatTextArea);
        scroll.setBounds(5,70,550,200);
        scroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(scroll);
        chatInputTextField = new JTextField();
        chatInputTextField.setBounds(5,285,460,30);
        chatInputTextField.addActionListener(this);
        add(chatInputTextField);
        sendButton = new JButton("send");
        sendButton.setBounds(470,285,85,30);
        sendButton.addActionListener(this);
        add(sendButton);
        //setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(570,360);
        setLayout(null);
        setVisible(true);
        out = new P2PServer().createServer(chatTextArea, portNumber);
    }
    public void showJoinServerDialog() throws IOException {
        JTextField ipAddressValue = new JTextField(16);
        JTextField portNumberValue = new JTextField(4);
        JPanel myPanel = new JPanel();
        myPanel.add(new JLabel("Enter your friend's IpAddress:"));
        myPanel.add(ipAddressValue);
        myPanel.add(Box.createHorizontalStrut(15)); // a spacer
        myPanel.add(new JLabel("Enter your friend's Port Number:"));
        myPanel.add(portNumberValue);

        int result = JOptionPane.showConfirmDialog(null, myPanel,
                "Ask your friend for these values", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            String ipAddress = ipAddressValue.getText();
            String portNumber = portNumberValue.getText();
            System.out.println(ipAddress);
            System.out.println(portNumber);
            GetMyIpAddress getMyIpAddress = new GetMyIpAddress();
            if(getMyIpAddress.validateIP(ipAddress) && getMyIpAddress.validatePort(portNumber)) {
                joinServerChatBox(ipAddress, Integer.parseInt(portNumber));
            }
            else {
                JOptionPane.showMessageDialog(null, "Invalid Ip-Address or port number");
                showOptionsCreateServerJoinServer(); 
            }
        }
        else {
            //System.exit(0);
        }
    }
    public void showCreateServerDialog() throws IOException {
        ipAddresses = new GetMyIpAddress().ipAddress();
        
        String portNumber;
        portNumber = (String) JOptionPane.showInputDialog(
                this,
                "Your IP Address:" + ipAddresses[0] + " " + ipAddresses[1] +" \n"
                        + "choose port number like 4444, 4445, 4446",
                "Enter 4 digit port number",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "4444");
        if(new GetMyIpAddress().validatePort(portNumber)) {
            createServerChatBox(Integer.parseInt(portNumber));
        }
        else {
            JOptionPane.showMessageDialog(null, "Invalid port number");
            //showOptionsCreateServerJoinServer();
        }
    }
    public static void main(String args[]) throws IOException {
        new PrivateChat().showOptionsCreateServerJoinServer();
    }
    
}
