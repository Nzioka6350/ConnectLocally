/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ipaddress;

import java.net.*;
import javax.swing.DefaultListModel;
/**
 *
 * @author varun
 */
public class NetworkPing {
    public void showConnectedComputers(final DefaultListModel model) throws Exception{
        final String ipAddresses[] = new GetMyIpAddress().ipAddress();
        InetAddress inetAddress = InetAddress.getByName(ipAddresses[0]);
        byte ip[] = inetAddress.getAddress();
        for (int i = 1; i < 255; i++) {
            ip[3] = (byte) i;
            inetAddress = InetAddress.getByAddress(ip);
            if (inetAddress.isReachable(1000)) {
                System.out.println(inetAddress + " machine is turned on and can be pinged");
                model.addElement(inetAddress + " " + inetAddress.getCanonicalHostName());
            } else if (!inetAddress.getHostAddress().equals(inetAddress.getHostName())) {
                //System.out.println(inetAddress + " machine is known in a DNS lookup");
            } else {
                //System.out.println(inetAddress + " the host address and host name are equal, meaning that host name could not be resolved");
            }
        }
        if((ipAddresses[1] != null) && (ipAddresses[1].length() > 1)) {
            new Thread() {
                public void run() {
                    try {
                        InetAddress inetAddress = InetAddress.getByName(ipAddresses[1]);
                        byte ip[] = inetAddress.getAddress();
                        for (int i = 1; i < 255; i++) {
                            ip[3] = (byte) i;
                            inetAddress = InetAddress.getByAddress(ip);
                            if (inetAddress.isReachable(1000)) {
                                System.out.println(inetAddress + " machine is turned on and can be pinged");
                                model.addElement(inetAddress + " " + inetAddress.getHostName());
                            } else if (!inetAddress.getHostAddress().equals(inetAddress.getHostName())) {
                                //System.out.println(inetAddress + " machine is known in a DNS lookup");
                            } else {
                                //System.out.println(inetAddress + " the host address and host name are equal, meaning that host name could not be resolved");
                            }
                        }
                    }
                    catch(Exception e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    } 
}
