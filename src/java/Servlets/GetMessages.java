/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Utils.MessageType;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author user
 */
@WebServlet(name = "GetMessages", urlPatterns = {"/GetMessages"})
public class GetMessages extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        response.setContentType("application/json;charset=UTF-8");
        String uid1 = request.getParameter("loggedin");
        String friend = request.getParameter("friend");
        String id = "SELECT uid FROM users WHERE username=?";
        int uid = 0;
        int friendid = 0;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/similis","root","MysqlRoot@007")){
                PreparedStatement ps = con.prepareStatement(id);
                 ps.setString(1, uid1);
                 ResultSet rs = ps.executeQuery();
                 while(rs.next()){
                     uid = rs.getInt("uid");
                 }
                 PreparedStatement ps1 = con.prepareStatement(id);
                 ps1.setString(1, friend);
                  ResultSet rs1 = ps1.executeQuery();
                  while(rs1.next()){
                      friendid= rs1.getInt("uid");
                  }
                  if(uid!=0 && friendid!=0){
                  CallableStatement cs = con.prepareCall("{ call similis.message_procedure(?,?)}");
                  cs.setInt(1, uid);
                  cs.setInt(2,friendid);
                  ResultSet mess= cs.executeQuery();
                  List<MessageType> messages= new ArrayList<>();
                  while(mess.next()){
                      String message = mess.getString("message");
                      int uid2 = mess.getInt("uid1");
                      int friendid2 = mess.getInt("friend_uid");
                      int type = 0;
                      if(uid2 == uid){
                          type =1;
                      }
                      if(friendid2 == uid){
                          type =2;
                      }
                      MessageType first_message = new MessageType(message,type);
                      messages.add(first_message);
                  }
                  response.getWriter().write(new Gson().toJson(messages));
                  }
            } catch (SQLException ex) {
                Logger.getLogger(GetMessages.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GetMessages.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(GetMessages.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(GetMessages.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
