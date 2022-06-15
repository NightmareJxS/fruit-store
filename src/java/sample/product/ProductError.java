/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sample.product;


/**
 *
 * @author ASUS
 */
public class ProductError {
    private String productID ;
    private String productName ;
    private String image ;
    private String price ;
    private String quantity ;
    private String categoryID ;
    private String importDate ;
    private String usingDate ;
    private String status ;
    private String errorMessage;

    public ProductError() {
        this.productID = "";
        this.productName = "";
        this.image = "";
        this.price = "";
        this.quantity = "";
        this.categoryID = "";
        this.importDate = "";
        this.usingDate = "";
        this.status = "";
        this.errorMessage = "";
    }

    public ProductError(String productID, String productName, String image, String price, String quantity, String categoryID, String importDate, String usingDate, String status, String errorMessage) {
        this.productID = productID;
        this.productName = productName;
        this.image = image;
        this.price = price;
        this.quantity = quantity;
        this.categoryID = categoryID;
        this.importDate = importDate;
        this.usingDate = usingDate;
        this.status = status;
        this.errorMessage = errorMessage;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getImportDate() {
        return importDate;
    }

    public void setImportDate(String importDate) {
        this.importDate = importDate;
    }

    public String getUsingDate() {
        return usingDate;
    }

    public void setUsingDate(String usingDate) {
        this.usingDate = usingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    
    
    
}
