/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.product;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import sample.utils.DBUtils;

/**
 *
 * @author ASUS
 */
public class ProductDAO {
    private static final String SEARCH="SELECT productID, productName, image, price, quantity, categoryID, importDate, usingDate, status FROM tblProduct WHERE productName like ?";
    private static final String SEARCH_ALL="SELECT productID, productName, image, price, quantity, categoryID, importDate, usingDate, status FROM tblProduct";
    private static final String DELETE="UPDATE tblProduct SET status=0 WHERE productID=?";
    private static final String UPDATE="UPDATE tblProduct SET productName=?, image=?, price=?, quantity=?, categoryID=?, importDate=?, usingDate=?, status=? WHERE productID=?";
    private static final String CHECK_DUPLICATE="SELECT productName FROM tblProduct where productID=?";
    private static final String CREATE="INSERT INTO tblProduct(productID, productName, image, price, quantity, categoryID, importDate, usingDate, status) VALUES(?,?,?,?,?,?,?,?,?)";
    private static final String CHECK_CATEGORY="SELECT categoryName FROM tblCategory where categoryID=?";
    private static final String CHECK_PRODUCT_QUANTITY="SELECT productName FROM tblProduct WHERE productID=? and quantity>=?";
    private static final String UPDATE_QUANTITY="UPDATE tblProduct SET quantity=quantity-? WHERE productID=?";
    private static final String GET_ORDER_NUMBER="SELECT TOP 1 orderID FROM tblOrder ORDER BY orderID DESC";
    private static final String ADD_NEW_ORDER="INSERT INTO tblOrder(orderID, orderDate, total, userID) VALUES(?,getdate(),?,?)";
    private static final String GET_DETAIL_NUMBER="SELECT TOP 1 detailID FROM tblOrderDetail ORDER BY detailID DESC";
    private static final String ADD_NEW_DETAIL="INSERT INTO tblOrderDetail(detailID, price, quantity, orderID, productID) VALUES(?,?,?,?,?)";
    
    public List<ProductDTO> getListProduct(String search) throws SQLException{
        List<ProductDTO> listProduct= new ArrayList<>();
        Connection conn=null;
        PreparedStatement ptm=null;
        ResultSet rs=null;
        
        try {
            conn=DBUtils.getConnection();
            if (conn!=null) {
                ptm=conn.prepareStatement(SEARCH);
                ptm.setString(1, "%"+search+"%");
                rs=ptm.executeQuery();
                while(rs.next()){
                    int productID=rs.getInt("productID");
                    String productName=rs.getString("productName");
                    String image=rs.getString("image");
                    double price=rs.getDouble("price");
                    int quantity=rs.getInt("quantity");
                    String categoryID=rs.getString("categoryID");
                    Date importDate=rs.getDate("importDate");
                    Date usingDate=rs.getDate("usingDate");
                    Boolean status=rs.getBoolean("status");
                    listProduct.add(new ProductDTO(productID, productName, image, price, quantity, categoryID, importDate, usingDate, status));
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(rs!=null)rs.close();
            if(ptm!=null)ptm.close();
            if(conn!=null)conn.close();
        }
        
        return listProduct;
    }
    
    public boolean deleteProduct(String productID) throws SQLException{
        boolean check= false;
        Connection conn=null;
        PreparedStatement ptm=null;
        try {
            conn=DBUtils.getConnection();
            if (conn!=null) {
                ptm=conn.prepareStatement(DELETE);
                ptm.setString(1, productID);
                check=ptm.executeUpdate()>0?true:false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(ptm!=null)ptm.close();
            if(conn!=null)conn.close();
        }
        
        return check;
    }
    
    public boolean updateProduct(ProductDTO product) throws SQLException{
        boolean check= false;
        Connection conn=null;
        PreparedStatement ptm=null;
        try {
            conn=DBUtils.getConnection();
            if (conn!=null) {
                ptm=conn.prepareStatement(UPDATE);
                ptm.setString(1, product.getProductName());
                ptm.setString(2, product.getImage());
                ptm.setDouble(3, product.getPrice());
                ptm.setInt(4, product.getQuantity());
                ptm.setString(5, product.getCategoryID());
                ptm.setDate(6,  product.getImportDate());
                ptm.setDate(7,  product.getUsingDate());
                ptm.setBoolean(8, product.isStatus());
                ptm.setInt(9, product.getProductID());
                check=ptm.executeUpdate()>0?true:false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(ptm!=null)ptm.close();
            if(conn!=null)conn.close();
        }
        
        return check;
    }
    
    public boolean checkDuplicate(int productID) throws SQLException{
        boolean check= false;
        Connection conn=null;
        PreparedStatement ptm=null;
        ResultSet rs=null;
        
        try {
            conn=DBUtils.getConnection();
            if(conn!=null){
                ptm=conn.prepareStatement(CHECK_DUPLICATE);
                ptm.setInt(1, productID);
                rs=ptm.executeQuery();
                if(rs.next()){
                    check=true;
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(rs!=null)rs.close();
            if(ptm!=null)ptm.close();
            if(conn!=null)conn.close();
        }
        
        return check;
    }

    public boolean addNewProduct(ProductDTO product) throws SQLException{
        boolean check= false;
        Connection conn=null;
        PreparedStatement ptm=null;
        try {
            conn=DBUtils.getConnection();
            if (conn!=null) {
                ptm=conn.prepareStatement(CREATE);
                ptm.setInt(1, product.getProductID());
                ptm.setString(2, product.getProductName());
                ptm.setString(3, product.getImage());
                ptm.setDouble(4, product.getPrice());
                ptm.setInt(5, product.getQuantity());
                ptm.setString(6, product.getCategoryID());
                ptm.setDate(7,  product.getImportDate());
                ptm.setDate(8,  product.getUsingDate());
                ptm.setBoolean(9, product.isStatus());
                check=ptm.executeUpdate()>0?true:false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(ptm!=null)ptm.close();
            if(conn!=null)conn.close();
        }
        
        return check;
    }
 
    public boolean checkCategory(String categoryID) throws SQLException{
        boolean check= false;
        Connection conn=null;
        PreparedStatement ptm=null;
        ResultSet rs=null;
        
        try {
            conn=DBUtils.getConnection();
            if(conn!=null){
                ptm=conn.prepareStatement(CHECK_CATEGORY);
                ptm.setString(1, categoryID);
                rs=ptm.executeQuery();
                if(rs.next()){
                    check=true;
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(rs!=null)rs.close();
            if(ptm!=null)ptm.close();
            if(conn!=null)conn.close();
        }
        
        return check;
    }
    
    public List<ProductDTO> getAllProduct () throws SQLException{
        List<ProductDTO> listProduct= new ArrayList<>();
        Connection conn=null;
        PreparedStatement ptm=null;
        ResultSet rs=null;
        
        try {
            conn=DBUtils.getConnection();
            if (conn!=null) {
                ptm=conn.prepareStatement(SEARCH_ALL);
//                ptm.setString(1, "%"+search+"%");
                rs=ptm.executeQuery();
                while(rs.next()){
                    int productID=rs.getInt("productID");
                    String productName=rs.getString("productName");
                    String image=rs.getString("image");
                    double price=rs.getDouble("price");
                    int quantity=rs.getInt("quantity");
                    String categoryID=rs.getString("categoryID");
                    Date importDate=rs.getDate("importDate");
                    Date usingDate=rs.getDate("usingDate");
                    Boolean status=rs.getBoolean("status");
                    listProduct.add(new ProductDTO(productID, productName, image, price, quantity, categoryID, importDate, usingDate, status));
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(rs!=null)rs.close();
            if(ptm!=null)ptm.close();
            if(conn!=null)conn.close();
        }
        
        return listProduct;
    }
    
    public boolean checkQuantity(int productID,int quantity) throws SQLException{
        boolean check= false;
        Connection conn=null;
        PreparedStatement ptm=null;
        ResultSet rs=null;
        
        try {
            conn=DBUtils.getConnection();
            if(conn!=null){
                ptm=conn.prepareStatement(CHECK_PRODUCT_QUANTITY);
                ptm.setInt(1, productID);
                ptm.setInt(2, quantity);
                rs=ptm.executeQuery();
                if(rs.next()){
                    check=true;
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(rs!=null)rs.close();
            if(ptm!=null)ptm.close();
            if(conn!=null)conn.close();
        }
        
        return check;
    }
    
    public boolean updateQuantity(int quantity,int productID) throws SQLException{
        boolean check= false;
        Connection conn=null;
        PreparedStatement ptm=null;
        try {
            conn=DBUtils.getConnection();
            if (conn!=null) {
                ptm=conn.prepareStatement(UPDATE_QUANTITY);
                ptm.setInt(1, productID);
                ptm.setInt(2, quantity);
                check=ptm.executeUpdate()>0?true:false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(ptm!=null)ptm.close();
            if(conn!=null)conn.close();
        }
        
        return check;
    }
    
    public int getNewOrderID() throws SQLException{
        int result=0;
        
        Connection conn=null;
        PreparedStatement ptm=null;
        ResultSet rs=null;
        
        try {
            conn=DBUtils.getConnection();
            if (conn!=null) {
                ptm=conn.prepareStatement(GET_ORDER_NUMBER);
                rs=ptm.executeQuery();
                while(rs.next()){
                    int orderID=rs.getInt("orderID");
                    result = orderID;
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(rs!=null)rs.close();
            if(ptm!=null)ptm.close();
            if(conn!=null)conn.close();
        }
        
        return result;
    }
    
    public boolean createNewOrder(int orderID, Double total,String userID) throws SQLException{
        boolean check= false;
        Connection conn=null;
        PreparedStatement ptm=null;
        try {
            conn=DBUtils.getConnection();
            if (conn!=null) {
                ptm=conn.prepareStatement(ADD_NEW_ORDER);
                ptm.setInt(1, orderID);
                ptm.setDouble(2, total);
                ptm.setString(3, userID);
                check=ptm.executeUpdate()>0?true:false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(ptm!=null)ptm.close();
            if(conn!=null)conn.close();
        }
        
        return check;
    }
    
    public int getNewDetailID() throws SQLException{
        int result=0;
        
        Connection conn=null;
        PreparedStatement ptm=null;
        ResultSet rs=null;
        
        try {
            conn=DBUtils.getConnection();
            if (conn!=null) {
                ptm=conn.prepareStatement(GET_DETAIL_NUMBER);
                rs=ptm.executeQuery();
                while(rs.next()){
                    int detailID=rs.getInt("detailID");
                    result = detailID;
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(rs!=null)rs.close();
            if(ptm!=null)ptm.close();
            if(conn!=null)conn.close();
        }
        
        return result;
    }
    
    public boolean createNewDetail(int detailID, Double total,int quantity,int orderID, int productID) throws SQLException{
        boolean check= false;
        Connection conn=null;
        PreparedStatement ptm=null;
        try {
            conn=DBUtils.getConnection();
            if (conn!=null) {
                ptm=conn.prepareStatement(ADD_NEW_DETAIL);
                ptm.setInt(1, detailID);
                ptm.setDouble(2, total);
                ptm.setInt(3, quantity);
                ptm.setInt(4, orderID);
                ptm.setInt(5, productID);
                check=ptm.executeUpdate()>0?true:false;
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(ptm!=null)ptm.close();
            if(conn!=null)conn.close();
        }
        
        return check;
    }
    
}
