/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chat254;

import javax.swing.SwingWorker;
import privateChat.PrivateChat;

/**
 *
 * @author varun
 */
public class HandlePrivateChat extends SwingWorker<Void, Void> {

    @Override
    protected Void doInBackground() throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        new PrivateChat().showOptionsCreateServerJoinServer();
        return null;
    }
    
}
