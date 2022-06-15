<%-- 
    Document   : login
    Created on : Mar 4, 2022, 6:27:39 PM
    Author     : ASUS
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <script src="https://www.google.com/recaptcha/api.js" async defer></script>
    </head>
    <body>
        <h1>Input your information!</h1>
        <form action="MainController" method="POST">
            User ID<input type="text" name="userID" required="" placeholder="Input your ID"/><br>
            Password<input type="password" name="password" required="" placeholder="Input your password"/><br>
            <input type="submit" name="action" value="Login"/>
            <input type="reset" value="Reset"/>
            <div class="g-recaptcha" data-sitekey="6LeNH84eAAAAAM9iuRb5mr2vLdxgj5eLgRLnw2dq"></div>
        </form>
        <% 
            String error= (String)request.getAttribute("ERROR");
            if (error==null) {
                    error="";
                }
        %>
        <%= error %>
        <a href="create.jsp">Create new user</a><br/>
    </body>
</html>
