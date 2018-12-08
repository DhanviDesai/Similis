/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import Utils.MusicArtist;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author user
 */
@WebServlet(name = "SelectedMusicArtists", urlPatterns = {"/SelectedMusicArtists"})
public class SelectedMusicArtists extends HttpServlet {

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
        response.setContentType("text/plain;charset=UTF-8");
       // List<MusicArtist> artists = new Gson().fromJson(request.getParameter("selected"),MusicArtist.class);
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
           String artist_idsql = "SELECT artist_id FROM similis.music_artists WHERE name=?";
           PreparedStatement innerps = conn.prepareStatement(artist_idsql);
           String artist_name= obj.getString("name");
           innerps.setString(1, artist_name);
           ResultSet innerrs = innerps.executeQuery();
           if(innerrs.next()){
               int artist_id = innerrs.getInt("artist_id");
               String insert = "INSERT INTO similis.favourite_music VALUES (?,?)";
               PreparedStatement innermost = conn.prepareStatement(insert);
               innermost.setInt(1, uid);
               innermost.setInt(2,artist_id);
               innermost.execute();
               System.out.println("Inserting "+i+" value of "+artist_id);
               
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
