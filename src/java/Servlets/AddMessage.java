/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
@WebServlet(name = "AddMessage", urlPatterns = {"/AddMessage"})
public class AddMessage extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String uid1 = request.getParameter("uid1");
        String friend_uid = request.getParameter("friend_uid");
        String message = request.getParameter("message");
        System.out.println(uid1+"   "+friend_uid+"  "+message);
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/similis", "root","MysqlRoot@007")){
                String sql = "INSERT INTO messages(uid1,friend_uid,message) values(?,?,?)";
                String getUID= "SELECT uid from users WHERE username=?";
                String getFriendId = "SELECT uid FROM users where username=?";
                PreparedStatement ps = con.prepareStatement(getUID);
                ps.setString(1,uid1);
                ResultSet outer = ps.executeQuery();
                int uid=0;
                int friendid=0;
                while(outer.next()){
                    uid = outer.getInt("uid");
                    System.out.println("UID: "+uid);
                }
                    PreparedStatement friendps = con.prepareStatement(getFriendId);
                    friendps.setString(1,friend_uid);
                    ResultSet inner = friendps.executeQuery();
                    while(inner.next()){
                        friendid = inner.getInt("uid");
                        System.out.println("FriendID: "+friendid);
                    }
                    if(uid!=0 && friendid!=0){
                    PreparedStatement insert = con.prepareStatement(sql);
                    insert.setInt(1, uid);
                    insert.setInt(2, friendid);
                    insert.setString(3,message);
                    insert.execute();
                    }
                    
                
            } catch (SQLException ex) {
                Logger.getLogger(AddMessage.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AddMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            Logger.getLogger(AddMessage.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(AddMessage.class.getName()).log(Level.SEVERE, null, ex);
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
