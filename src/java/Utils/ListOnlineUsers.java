/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import java.util.ArrayList;
import java.util.List;
import javax.websocket.Session;

/**
 *
 * @author user
 */
public class ListOnlineUsers {
    public static List<OnlineUsers> online = new ArrayList<>();
    public void setOnlineUser(String username, Session s){
        OnlineUsers thisone = new OnlineUsers(username,s);
        online.add(thisone);
    }
    public Session getSessionUser(String username){
        Session s = null;
        int i = 0;
        for(i=0;i<online.size();i++){
            s = online.get(i).getSession(username);
        }
        return s;
   }
    
}
