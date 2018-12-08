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
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author user
 */
@WebServlet(name = "SelectedMovies", urlPatterns = {"/SelectedMovies"})
public class SelectedMovies extends HttpServlet {

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
        System.out.println(request.getParameter("selected"));
       String json = request.getParameter("selected");
       System.out.println(request.getParameter("username"));
       String username = request.getParameter("username");
       if(json!=null){
           response.getWriter().write("Success");
       }
       else{
           response.getWriter().write("Fail");
       }
        try(Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/similis?useSSL=false","root","MysqlRoot@007")) {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
       String uidsql = "SELECT uid FROM users WHERE username=?";
       PreparedStatement ps = conn.prepareStatement(uidsql);
       ps.setString(1,username);
       ResultSet rs = ps.executeQuery();
       if(rs.next()){
           int uid = rs.getInt("uid");
           System.out.println(uid);
           JSONArray array = new JSONArray(json);
            for(int i=0;i<array.length();i++){
                JSONObject obj = array.getJSONObject(i);
           String movie_idsql = "SELECT movie_id FROM similis.movies WHERE movie_name=?";
           PreparedStatement innerps = conn.prepareStatement(movie_idsql);
           String movie_name= obj.getString("name");
           innerps.setString(1, movie_name);
           ResultSet innerrs = innerps.executeQuery();
           if(innerrs.next()){
               int movie_id = innerrs.getInt("movie_id");
               String insert = "INSERT INTO similis.favourite_movies VALUES (?,?)";
               PreparedStatement innermost = conn.prepareStatement(insert);
               innermost.setInt(1, uid);
               innermost.setInt(2,movie_id);
               innermost.execute();
               System.out.println("Inserting "+i+" value of "+movie_id);
               
           }
            }
       }
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (InstantiationException ex) {
            System.out.println(ex);
        } catch (IllegalAccessException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (JSONException ex) {
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
