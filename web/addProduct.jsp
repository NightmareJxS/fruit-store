<%-- 
    Document   : addProduct
    Created on : Mar 5, 2022, 6:06:26 PM
    Author     : ASUS
--%>

<%@page import="sample.product.ProductError"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add new product Page</title>
    </head>
    <body>
        <h1>Add new product</h1>
        <%
          ProductError productError = (ProductError)request.getAttribute("PRODUCT_ERROR");
          if (productError==null) {
                  productError=new ProductError();
              }
        %>
        <form action="MainController" method="POST">
            Product ID <input type="number" name="productID" required=""/>
            <%= productError.getProductID()%><br>
            Product Name <input type="text" name="productName" required=""/>
            <%= productError.getProductName()%><br>
            Image Link <input type="text" name="image" required=""/>
            <%= productError.getImage()%><br>
            Price <input type="number" name="price" required=""/>
            <%= productError.getPrice()%><br>
            Quantity <input type="number" name="quantity" required=""/>
            <%= productError.getQuantity()%><br>
            Category ID <input type="text" name="categoryID" required=""/>
            <%= productError.getCategoryID()%><br>
            Import Date <input type="date" name="importDate" required=""/>
            <%= productError.getImportDate()%><br>
            Using Date <input type="date" name="usingDate" required=""/>
            <%= productError.getUsingDate()%><br>
            Status <input type="text" name="status" value="true" readonly=""/><br>
            <input type="submit" name="action" value="AddProduct"/>
            <input type="reset" value="Reset"/>
        </form>
    </body>
</html>
