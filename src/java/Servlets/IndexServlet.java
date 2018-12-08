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
@WebServlet(name = "IndexServlet", urlPatterns = {"/IndexServlet"})
public class IndexServlet extends HttpServlet {
    Connection conn;

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
        PrintWriter pw = response.getWriter();
        try{
        openConnection();
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        pw.write("connected to the database");
        System.out.println("Connected to the database");
        if(request.getParameter("command").equals("login")){
            if(loginUser(request.getParameter("username"),request.getParameter("password"))){
                request.getSession().setAttribute("UserId", request.getParameter("username"));
                response.sendRedirect("home.html");
            }
        }else if(request.getParameter("command").equals("signup")){
            pw.write("In the sign up part");
            System.out.println("In the sign up part");
            String sql = "INSERT INTO similis.users(username,password) VALUES(?,?)";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, request.getParameter("username"));
            statement.setString(2, request.getParameter("password"));
            statement.execute();
            if(loginUser(request.getParameter("username"),request.getParameter("password"))){
                request.getSession().setAttribute("UserId", request.getParameter("username"));
                response.sendRedirect("home.html");
                conn.close();
            }
        }
      }catch(SQLException e){
          e.printStackTrace();
          pw.println(e);
      } catch (ClassNotFoundException ex) {
          pw.println(ex);
            Logger.getLogger(IndexServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
          pw.println(ex);
            Logger.getLogger(IndexServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
          pw.println(ex);
            Logger.getLogger(IndexServlet.class.getName()).log(Level.SEVERE, null, ex);
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
        processRequest(request,response);
        
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

    private boolean loginUser(String username, String password) throws SQLException { 
//To change body of generated methods, choose Tools | Templates.
            System.out.println("In the login part");
            String sql = "SELECT * FROM similis.users WHERE username=? AND password=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,username);
            statement.setString(2,password);
            ResultSet rs = statement.executeQuery();
            return rs.next();
    }
    
    private void openConnection() throws ClassNotFoundException, InstantiationException, 
            IllegalAccessException, SQLException{
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/similis","root","MysqlRoot@007");
    }

}
