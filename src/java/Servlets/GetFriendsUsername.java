/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

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
@WebServlet(name = "GetFriendsUsername", urlPatterns = {"/GetFriendsUsername"})
public class GetFriendsUsername extends HttpServlet {
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
        response.setContentType("application/json;charset=UTF-8");
        System.out.println("In the get friends ");
    List<Integer> friends = new ArrayList<>();
    List<String> friendsUsername = new ArrayList<>();
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            try(Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/similis?useSSL=false","root","MysqlRoot@007")){
                String sql = "SELECT uid FROM users WHERE username=?";
                PreparedStatement ps = con.prepareStatement(sql);
                String username = (String)request.getSession().getAttribute("UserId");
                ps.setString(1, username);
                ResultSet set = ps.executeQuery();
                while(set.next()){
                    String callsql = "{call similis.overall_friends(?)}";
                    CallableStatement st = con.prepareCall(callsql);
                    st.setInt(1, set.getInt("uid"));
                    ResultSet rs = st.executeQuery();
                    while(rs.next()){
                        friends.add(rs.getInt("uid"));
                    }
                }
                String secondsql = "SELECT username FROM users where uid=?";
                PreparedStatement ps1 = con.prepareCall(secondsql);
                for(int uid: friends){
                    ps1.setInt(1, uid);
                    ResultSet rs1 = ps1.executeQuery();
                    while(rs1.next()){
                        friendsUsername.add(rs1.getString("username"));
                    }
                }
                response.getWriter().write(new Gson().toJson(friendsUsername));
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        } catch (ClassNotFoundException ex) {
                System.out.println(ex);
        } catch (InstantiationException ex) {
                System.out.println(ex);
        } catch (IllegalAccessException ex) {
                System.out.println(ex);
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
