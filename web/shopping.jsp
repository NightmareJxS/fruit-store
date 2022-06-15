<%-- 
    Document   : shopping
    Created on : Feb 24, 2022, 12:38:08 PM
    Author     : ASUS
--%>

<%@page import="sample.product.ProductDTO"%>
<%@page import="java.util.List"%>
<%@page import="sample.user.UserDTO"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Shopping Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="css/styleShopping.css">
    </head>
    <body>

        <%
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            if (loginUser == null || !loginUser.getRoleID().equals("US")) {
                response.sendRedirect("login.jsp");
                return;
            }
            String search = request.getParameter("search");
            if (search == null) {
                search = "";
            }
        %>
        <header>
            <h1>Welcome to our Fruit Store!</h1>
            <div class="row">
                <div class="col-lg-6 col-md-6 left">
                    <form action="MainController">
                        Search product : <input type="text" name="search" value="<%= search%>"/>
                        <input type="submit" name="action" value="Search"/>
                        <input type="hidden" name="roleID" value="<%= loginUser.getRoleID()%>"/>
                        <input type="hidden" name="searchType" value="SINGLE"/>
                    </form>
                    <form action="MainController" >
                        Search all : <input type="submit" name="action" value="Search"/>
                        <input type="hidden" name="roleID" value="<%= loginUser.getRoleID()%>"/>
                        <input type="hidden" name="searchType" value="ALL"/>
                    </form>
                </div>
                <div class="col-lg-6 col-md-6 right">
                    <form action="MainController">
                        View Cart <input type="submit" name="action" value="View"/>
                    </form>    

                    <form action="MainController" method="POST">
                        <input type="submit" name="action" value="Logout"/>
                    </form>
                </div>
            </div>
        </header>


        <!--Message-->
        <%
            String message = (String) request.getAttribute("MESSAGE");
            if (message == null) {
                message = "";
            }
        %>
        <div class="row">
            <div class="center">
                <%= message%>
            </div>
        </div>


        <!--items table-->                
        <div class="middle row container">
            <%
                List<ProductDTO> listProduct = (List<ProductDTO>) session.getAttribute("LIST_PRODUCT");
                if (listProduct != null) {
                    if (listProduct.size() > 0) {
                        for (ProductDTO product : listProduct) {
                            if (product.isStatus() == true && product.getQuantity() > 0) {
            %>
            <div class="col-lg-3">
                <div class="items">

                    <img src="<%= product.getImage()%>" alt="Product picture" class="img-responsive">
                    <%= product.getProductName()%><br>
                    <div class="productPrice">Price : <%= product.getPrice()%> VND</div><br>
                    <form action="MainController">
                        <select name="cmbQuantity">
                            <option value="1">1</option>
                            <option value="2">2</option>
                            <option value="3">3</option>
                            <option value="4">4</option>
                            <option value="5">5</option>
                            <option value="10">10</option>
                        </select>
                        <input type="submit" name="action" value="Add" class="addButton"/>
                        <input type="hidden" name="cmbProduct" value="<%= product.getProductID()%>_<%= product.getProductName()%>_<%= product.getPrice()%>"/>

                        <!--tried to show all product again when added a product to cart but fail-->
                        <input type="hidden" name="action" value="Search"/>
                        <input type="hidden" name="roleID" value="<%= loginUser.getRoleID()%>"/>
                        <input type="hidden" name="searchType" value="ALL"/>

                    </form>

                </div>
            </div>
            <%
                            }
                        }
                    }
                }
            %>
        </div>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>    
    </body>
</html>
