/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.controller;

import java.io.IOException;
import java.sql.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sample.product.ProductDAO;
import sample.product.ProductDTO;
import sample.product.ProductError;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "AddProductController", urlPatterns = {"/AddProductController"})
public class AddProductController extends HttpServlet {

    private static final String ERROR = "addProduct.jsp";
    private static final String SUCCESS = "admin.jsp";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url=ERROR;
        ProductError productError = new ProductError();
        
        try {
            int productID=Integer.parseInt(request.getParameter("productID")) ;
            String productName=request.getParameter("productName");
            String image=request.getParameter("image");
            Double price=Double.parseDouble(request.getParameter("price"));
            int quantity=Integer.parseInt(request.getParameter("quantity"));
            String categoryID=request.getParameter("categoryID");
            Date importDate=Date.valueOf((request.getParameter("importDate"))) ;
            Date usingDate=Date.valueOf(request.getParameter("usingDate"));
            boolean status=Boolean.parseBoolean(request.getParameter("status"));
            
            boolean check=true;
            ProductDAO dao = new ProductDAO();
            boolean checkDuplicate= dao.checkDuplicate(productID);
            if (checkDuplicate) {
                check=false;
                productError.setProductID("Dupllicate ProductID!");
            }
            if (productName.length() < 5 || productName.length() > 50) {
                check = false;
                productError.setProductName("Product Name must be in [5,20]");
            }
            if (image.length()<5 || image.length()>100) {
                check = false;
                productError.setImage("Image Link must be in [5,100]");
            }
            if (price < 0) {
                check = false;
                productError.setPrice("price must be > 0");
            }
            if (quantity < 0) {
                check = false;
                productError.setQuantity("quantity must be > 0");
            }
            boolean checkCategory=dao.checkCategory(categoryID);
            if (checkCategory == false) {
                check=false;
                productError.setCategoryID("CategoryID not in Database!");
            }
            if (check) {
                ProductDTO product= new ProductDTO(productID, productName, image, price, quantity, categoryID, importDate, usingDate, status);
                boolean checkCreate = dao.addNewProduct(product);
                if (checkCreate) {
                    url=SUCCESS;
                }
            }else{
                request.setAttribute("PRODUCT_ERROR", productError);
            }
            
        } catch (Exception e) {
            if (e.toString().contains("duplicate")) {
                productError.setProductID("trung khoa chin roi!");
                request.setAttribute("PRODUCT_ERROR", productError);
            }
            
            log("Error at AddProductController : "+e.toString());
        }finally{
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
