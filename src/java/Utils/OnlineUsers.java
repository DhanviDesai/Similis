/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utils;

import javax.websocket.Session;

/**
 *
 * @author user
 */
public class OnlineUsers {
    
    String username;
    Session s;

    public OnlineUsers(String username, Session s) {
        this.username = username;
        this.s = s;
    }
    public Session getSession (String username){
        if(this.username.equals(username)){
            return this.s;
        }else{
            return null;
        }
    }
    public String getUsername(){
        return this.username;
    }
    
    public String getUsername(Session s){
        if(this.s == s ){
            return username;
        }else{
            return null;
        }
    }
    public Session getSession (){
            return this.s;
        }
  
}
