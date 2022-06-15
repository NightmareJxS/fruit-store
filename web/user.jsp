<%-- 
    Document   : user
    Created on : Feb 15, 2022, 3:16:58 PM
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
        <title>User Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <link rel="stylesheet" type="text/css" href="css/styleUser.css">
        <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
    </head>
    <body>
        <%
            UserDTO loginUser = (UserDTO) session.getAttribute("LOGIN_USER");
            if (loginUser == null || !loginUser.getRoleID().equals("US")) {
                response.sendRedirect("login.jsp");
                return;
            }
        %>


        <div class="center">
            <div>
                <h1>Welcome: <%= loginUser.getFullName()%></h1><br>
                <form action="MainController">
                    <button onclick="document.location = 'shopping.jsp'" class="btn btn-2 btn-sep icon-cart">Go to fruit store</button>
                    <input type="hidden" name="action" value="Search"/>
                    <input type="hidden" name="roleID" value="<%= loginUser.getRoleID()%>"/>
                    <input type="hidden" name="searchType" value="ALL"/>
                </form>
                <form action="MainController" method="POST">
                    <input type="submit" name="action" value="Logout" class="btn btn-3 btn-sep"/>
                </form>
            </div>
        </div>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
    </body>
</html>
