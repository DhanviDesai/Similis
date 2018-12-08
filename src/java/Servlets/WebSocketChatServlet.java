/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Utils.ListOnlineUsers;
import static Utils.ListOnlineUsers.online;
import Utils.OnlineUsers;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import java.util.*;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author user
 */
@ServerEndpoint(value = "/websocketendpoint")
public class WebSocketChatServlet {
//@PathParam("Username") String username,
    //ListOnlineUsers on = new ListOnlineUsers();
@OnOpen
public void onOpen(Session s){
    int flag = 0;
    System.out.println(s.getId());
    System.out.println("Web socket open");
    Map<String, List<String>> params = s.getRequestParameterMap();
    System.out.println(params.get("username").get(0));
    String username = params.get("username").get(0);
    //System.out.println(username);
    for(int i=0;i<online.size();i++){
        if(username.equals(online.get(i).getUsername())){
            flag = 1;
            break;
        }
    }
    if(flag!=1){
    OnlineUsers user = new OnlineUsers(username,s);
    online.add(user);
    }
    for(int i =0;i<online.size();i++){
        System.out.println("Online users are: "+online.get(i).getUsername());
    }
}

@OnClose
public void onClose(Session s){
    System.out.println("Web socket closed");
    for(int i=0;i<online.size();i++){
        if(online.get(i).getSession() == s){
            online.remove(i);
            break;
        }
    }
}

@OnMessage
public void onMessage(String completeData){
    System.out.println("Online size: "+online.size());
        try {
            //    String echomsg = "Echo from the server";
//    return eco
System.out.println(completeData);
JSONObject obj = new JSONObject(completeData);
String friend = obj.getString("Friend");
String message = obj.getString("Message");
Session s = null;
for(int i = 0;i<online.size();i++){
    s = online.get(i).getSession(friend);
    System.out.println(s);
if(s!=null){
    System.out.println(s);
    System.out.println(online.get(i).getUsername(s));
    s.getBasicRemote().sendText(message);
    break;
}
}
        } catch (JSONException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
}

}
