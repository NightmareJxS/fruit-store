<%-- 
    Document   : create
    Created on : Feb 22, 2022, 2:29:25 PM
    Author     : ASUS
--%>

<%@page import="sample.user.UserError"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create user Page</title>
    </head>
    <body>
        <h1>Create user</h1>
        <%
            UserError userError= (UserError)request.getAttribute("USER_ERROR");
            if (userError==null) {
                    userError=new UserError();
                }
        %>
        <form action="MainController" method="POST">
            User ID<input type="text" name="userID" required=""/>
            <%= userError.getUserID() %></br>
            Full Name<input type="text" name="fullName" required=""/>
            <%= userError.getFullName()%></br>
            Role ID<input type="text" name="roleID" value="US" readonly=" "/></br>
            Password<input type="password" name="password" required=""/></br>
            Confirm<input type="password" name="confirm" required=""/></br>
            <%= userError.getConfirm()%></br>
            <input type="submit" name="action" value="Create"/>
            <input type="reset" value="Reset"/>
        </form>
    </body>
</html>
