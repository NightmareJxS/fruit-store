<%-- 
    Document   : admin
    Created on : Feb 15, 2022, 3:26:03 PM
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
        <title>Admin Page</title>
    </head>
    <body>
        <%
            UserDTO loginUser= (UserDTO)session.getAttribute("LOGIN_USER");
            if (loginUser==null || !loginUser.getRoleID().equals("AD")) {
                    response.sendRedirect("login.jsp");
                    return;
                }
            String search= request.getParameter("search");
            if(search == null){
                search="";
            }
        %>
        Welcome: <h1><%= loginUser.getFullName() %></h1>
        <form action="MainController" method="POST">
            <input type="submit" name="action" value="Logout"/>
        </form>
        <form action="MainController">
            Search product : <input type="text" name="search" value="<%= search %>"/>
            <input type="submit" name="action" value="Search"/>
            <input type="hidden" name="roleID" value="<%= loginUser.getRoleID()%>"/>
        </form>
        <button onclick="document.location='addProduct.jsp'">add new product</button>
        <% 
            List<ProductDTO> listProduct= (List<ProductDTO>)request.getAttribute("LIST_PRODUCT");
            if (listProduct!= null) {
                    if (listProduct.size()>0) {
                        %>    
                        <table border="1">
                            <thead>
                                <tr>
                                    <th>No</th>
                                    <th>Product ID</th>
                                    <th>Product Name</th>
                                    <th>Link image</th>
                                    <th>Price</th>
                                    <th>quantity</th>
                                    <th>category ID</th>
                                    <th>import Date</th>
                                    <th>using Date</th>
                                    <th>status</th>
                                    <th>Delete</th>
                                    <th>Update</th>
                                </tr>
                            </thead>
                            <tbody>
                                <% 
                                    int count=1;
                                    for (ProductDTO product : listProduct) {
                                %>
                            <form action="MainController">
                                <tr>
                                    <td><%= count++ %></td>
                                    <td>
                                        <input type="number" name="productID" value="<%= product.getProductID()%>" readonly=""/>
                                    </td>
                                    <td>
                                        <input type="text" name="productName" value="<%= product.getProductName() %>"/>
                                    </td>
                                    <td>
                                        <input type="text" name="image" value="<%= product.getImage() %>"/> 
                                    </td>
                                    <td>
                                        <input type="number" step=0.001 name="price" value="<%= product.getPrice() %>"/> 
                                    </td>
                                    <td>
                                        <input type="number" name="quantity" value="<%= product.getQuantity() %>"/> 
                                    </td>
                                    <td>
                                        <input type="text" name="categoryID" value="<%= product.getCategoryID() %>"/> 
                                    </td>
                                    <td>
                                        <input type="date" name="importDate" value="<%= product.getImportDate() %>"/> 
                                    </td>
                                    <td>
                                        <input type="date" name="usingDate" value="<%= product.getUsingDate() %>"/> 
                                    </td>
                                    <td>
                                        <input type="text" name="status" value="<%= product.isStatus() %>"/> 
                                    </td>
                                    <!--delete-->
                                    <td>
                                        <a href="MainController?action=Delete&productID=<%=product.getProductID()%>&search=<%=search%>">Delete</a>
                                    </td>
                                    <!--update-->
                                    <td>
                                        <input type="submit" name="action" value="Update"/>
                                        <input type="hidden" name="search" value="<%= search %>"/>
                                        <input type="hidden" name="roleID" value="<%= loginUser.getRoleID()%>"/>
                                    </td>
                                </tr>
                            </form>
                                <% 
                                    }
                                %>
                            </tbody>
                        </table>
                        <%
                        }
                }
        %>
    </body>
</html>
