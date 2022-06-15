/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controller;

import java.io.IOException;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sample.product.ProductDAO;
import sample.shopping.Cart;
import sample.shopping.Product;
import sample.utils.JavaMailUtils;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "CheckOutController", urlPatterns = {"/CheckOutController"})
public class CheckOutController extends HttpServlet {

    private static final String ERROR = "viewCart.jsp";
    private static final String SUCCESS = "viewCart.jsp";
    private static final String EMAIL_PATTERN = "^[A-Za-z0-9]*@" + "[A-Za-z0-9]*+(\\.[A-Za-z0-9]{2,})*$";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = ERROR;

        try {
            HttpSession session = request.getSession();
            Cart cart = (Cart) session.getAttribute("CART");
            //check cart
            if (cart != null) {
                ProductDAO dao = new ProductDAO();
                boolean checkQuantityTotal = false;
                boolean checkUpdateQuantity = false;
                boolean checkCreateNewOrder = false;
                boolean checkCreateNewDetail = false;
                String QuantityErrorMessage = "The product : ";
                int newOrderID = 0;
                int newDetailID = 0;
                String email = request.getParameter("userEmail");

                //check valid email
                if (Pattern.matches(EMAIL_PATTERN, email)) {

                    //check quantity
                    for (Product product : cart.getCart().values()) {
                        int id = product.getProductID();
                        int quantity = product.getQuantity();
                        boolean checkQuantity = dao.checkQuantity(id, quantity);
                        if (!checkQuantity) {
                            QuantityErrorMessage += product.getProductName() + ", ";
                            checkQuantityTotal = true;
                        }
                    }
                    if (checkQuantityTotal) {
                        QuantityErrorMessage += "You choose is larger than what we have in stock !";
                        request.setAttribute("CART_MESSAGE", QuantityErrorMessage);
                    } else {//update quantity
                        for (Product product : cart.getCart().values()) {
                            int id = product.getProductID();
                            int quantity = product.getQuantity();
                            checkUpdateQuantity = dao.updateQuantity(id, quantity);
                            if (!checkUpdateQuantity) {
                                request.setAttribute("CART_MESSAGE", "Fail to update DB quantity!");
                                break;
                            }
                        }
                        //create new order
                        if (checkUpdateQuantity) {
                            newOrderID = dao.getNewOrderID() + 1;
                            Double total = Double.parseDouble(request.getParameter("total"));
                            String userID = request.getParameter("userID");
                            checkCreateNewOrder = dao.createNewOrder(newOrderID, total, userID);
                            if (!checkCreateNewOrder) {
                                request.setAttribute("CART_MESSAGE", "Fail to create new order!");
                            }
                        }
                        //create new orderDetail
                        if (checkCreateNewOrder) {
                            for (Product product : cart.getCart().values()) {
                                int id = product.getProductID();
                                int quantity = product.getQuantity();
                                newDetailID = dao.getNewDetailID() + 1;
                                double tmpTotal = product.getPrice() * quantity;
                                checkCreateNewDetail = dao.createNewDetail(newDetailID, tmpTotal, quantity, newOrderID, id);
                                if (!checkCreateNewDetail) {
                                    request.setAttribute("CART_MESSAGE", "Fail to create new OrderDetail!");
                                    break;
                                }
                            }
                        }
                        //confirm message and remove cart
                        if (checkUpdateQuantity && checkCreateNewOrder && checkCreateNewDetail) {
                            request.setAttribute("CART_MESSAGE", "Ordered successfull! Your Order number is : " + newOrderID);
                            session.removeAttribute("CART");
                        }
                        url=SUCCESS;
                    }

                    JavaMailUtils.sendMail(email);
                    
                } else {
                    request.setAttribute("CART_MESSAGE", "Please enter a vaild email for checkout!");
                }

            } else {
                request.setAttribute("CART_MESSAGE", "Wow such empty!");
            }
        } catch (Exception e) {
            log("Error at CheckOutController : " + e.toString());
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
