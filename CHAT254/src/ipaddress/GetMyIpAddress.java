/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipaddress;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.regex.Pattern;

/**
 *
 * @author varun
 */
public class GetMyIpAddress {
    private static final Pattern PATTERN = Pattern.compile(
            "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");//to validate ip address
    String ipAddresses[] = new String[5], temp;
    int j = 0;
    public static boolean validateIP(final String ip) {
        return PATTERN.matcher(ip).matches();
    }
    public boolean validatePort(String portNumber) {
        if ((portNumber != null) && (portNumber.length() == 4) && (portNumber.matches(".*\\d.*"))) {
            if( (Integer.parseInt(portNumber) > 1023))
                return true;
            else
                return false;
        }
        else 
            return false;
    }
    public String[] ipAddress() {
        Enumeration e = null;
        String s = "";
        try {
            e = NetworkInterface.getNetworkInterfaces();
        }
        catch (Exception exception) {
            exception.printStackTrace();
        }
        while (e.hasMoreElements()) {
            NetworkInterface n = (NetworkInterface) e.nextElement();
            Enumeration ee = n.getInetAddresses();
            while (ee.hasMoreElements()) {
                InetAddress i = (InetAddress) ee.nextElement();
                temp = i.getHostAddress();
                System.out.println(temp);
                if((temp.charAt(1) == '7' || temp.charAt(1) == '9') && (temp.charAt(2) == '2')) {
                    ipAddresses[j] = temp;
                    j++;
                }
            }
        }
        if (ipAddresses[0] == null) {
            ipAddresses[0] = "127.0.0.1";
            ipAddresses[1] = "";
        } else if (ipAddresses[1] == null) {
            ipAddresses[1] = " ";
        }
        return ipAddresses;
    }
    public static void main(String args[]) {
        new GetMyIpAddress().ipAddress();
    }
    //10.0.0.1 to 10.255.255.254
    //172.16.0.1 to 172.31.255.254
    //192.168.0.1 to 192.168.255.254
}
