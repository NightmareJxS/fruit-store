/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sample.user.UserDAO;
import sample.user.UserDTO;
import sample.user.UserError;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "CreateController", urlPatterns = {"/CreateController"})
public class CreateController extends HttpServlet {

    private static final String ERROR = "create.jsp";
    private static final String SUCCESS = "login.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;
        UserError userError = new UserError();
        try {
            String userID = request.getParameter("userID");
            String fullName = request.getParameter("fullName");
            String roleID = request.getParameter("roleID");
            String password = request.getParameter("password");
            String confirm = request.getParameter("confirm");
            
            boolean check = true;
            UserDAO dao= new UserDAO();
            boolean checkDuplicate= dao.checkDuplicate(userID);
            if (checkDuplicate) {
                check=false;
                userError.setUserID("Duplicate userID roi !");
            }
            if (userID.length() < 3 || userID.length() > 10) {
                check = false;
                userError.setUserID("User id must be in [3,10]");
            }
            if (fullName.length() < 5 || fullName.length() > 20) {
                check = false;
                userError.setFullName("Full Name must be in [5,20]");
            }
            if (!password.equals(confirm)) {
                check = false;
                userError.setConfirm("hai password ko giong nhau");
            }
            if (check) {
                UserDTO user= new UserDTO(userID, fullName, roleID, password);
                boolean checkCreate= dao.createUser(user);
                if (checkCreate) {
                    url=SUCCESS;
                }
                
            }else{
                request.setAttribute("USER_ERROR", userError);
            }
            
            
            
        } catch (Exception e) {
            if (e.toString().contains("duplicate")) {
                userError.setUserID("trung khoa chin roi!");
                request.setAttribute("USER_ERROR", userError);
            }
            
            
            log("Error at CreateController: " + e.toString());
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
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
