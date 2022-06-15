<%-- 
    Document   : viewCart
    Created on : Feb 24, 2022, 1:16:02 PM
    Author     : ASUS
--%>

<%@page import="sample.user.UserDTO"%>
<%@page import="sample.shopping.Product"%>
<%@page import="sample.shopping.Cart"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>View Cart Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="css/styleViewCart.css">

    </head>
    <body>

        <div class="row">
            <div class="center">
                <h1>Your cart</h1>
            </div>
        </div>

        <%
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            if (loginUser == null || !loginUser.getRoleID().equals("US")) {
                response.sendRedirect("login.jsp");
                return;
            }
            double total = 0;
        %>

        <%
            String message = (String) request.getAttribute("CART_MESSAGE");
            if (message == null) {
                message = "";
            }
        %>
        <div class="row">
            <div class="center">
                <%= message%>
            </div>
        </div>

        <%
            Cart cart = (Cart) session.getAttribute("CART");
            if (cart != null) {
//            add vo day neu cart trong rong thi ko tao table
        %>

        <!--table-->
        <div class="table-wrapper">
            <table class="fl-table">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Total</th>
                        <th>Remove</th>
                        <th>Edit</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        int count = 1;

                        for (Product product : cart.getCart().values()) {
                            total += product.getPrice() * product.getQuantity();
                    %>
                <form action="MainController">
                    <tr>
                        <td><%= count++%></td>
                        <td><%= product.getProductID()%>
                            <input type="hidden" name="id" value="<%= product.getProductID()%>"/>
                        </td>
                        <td><%= product.getProductName()%></td>
                        <td><%= product.getPrice()%>VND</td>
                        <td>
                            <input type="number" name="quantity" value="<%=  product.getQuantity()%>" min="1" required=""/>
                        </td>

                        <td><%= product.getPrice() * product.getQuantity()%></td>
                        <td>
                            <input type="submit" name="action" value="Remove"/>
                        </td>
                        <td>
                            <input type="submit" name="action" value="Edit"/>
                        </td>
                    </tr>  
                </form>

                <%
                    }
                %>

                </tbody>
            </table>

        </div>        

        <!--total-->
        <div class="row">
            <div class="center">
                <h1> Total: <%= total%> VND</h1>
            </div>
        </div>
        

        <%
            }
        %>


        <!--buttons-->
        <div class="row">
            <div class="left col-lg-6 col-md-6">
                <form action="MainController">
                    <button onclick="document.location = 'shopping.jsp'" class="btn btn-2 btn-sep">Add more</button>
                    <input type="hidden" name="action" value="Search"/>
                    <input type="hidden" name="roleID" value="<%= loginUser.getRoleID()%>"/>
                    <input type="hidden" name="searchType" value="ALL"/>
                </form>
            </div>
            <div class="right col-lg-6 col-md-6">
                <form action="MainController">
                    Please enter your email to check out <input type="text" name="userEmail" required=""/>
                    <input type="submit" name="action" value="CheckOut" class="btn btn-3 btn-sep"/>
                    <input type="hidden" name="total" value="<%= total%>"/>
                    <input type="hidden" name="userID" value="<%= loginUser.getUserID()%>"/>
                </form>
            </div>
        </div>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>    
    </body>
</html>
