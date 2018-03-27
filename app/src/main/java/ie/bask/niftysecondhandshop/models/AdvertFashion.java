package ie.bask.niftysecondhandshop.models;

import ie.bask.niftysecondhandshop.activities.Base;

/**
 * Created by cecobask on 26-Feb-18.
 */

public class AdvertFashion {

    private String productID;
    private String imageUri;
    private String productTitle;
    private double productPrice;
    private String productType;
    private String productSize;
    private String productLocation;
    private String productDescription;

    public AdvertFashion() {

    }

    public AdvertFashion(String productID, String imageUri, String productTitle, double productPrice, String productType, String productSize, String productLocation, String productDescription) {
        this.productID = productID;
        this.imageUri = imageUri;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.productType = productType;
        this.productSize = productSize;
        this.productLocation = productLocation;
        this.productDescription = productDescription;
    }

    public AdvertFashion(String imageUri, String productTitle, double productPrice, String productType, String productSize, String productLocation, String productDescription) {
        this.productID = Base.databaseFashionAds.push().getKey();
        this.imageUri = imageUri;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.productType = productType;
        this.productSize = productSize;
        this.productLocation = productLocation;
        this.productDescription = productDescription;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getProductLocation() {
        return productLocation;
    }

    public void setProductLocation(String productLocation) {
        this.productLocation = productLocation;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    @Override
    public String toString() {
        return "AdvertFashion{" +
                "productID=" + productID +
                ", imageUri='" + imageUri + '\'' +
                ", productTitle='" + productTitle + '\'' +
                ", productPrice=" + productPrice +
                ", productType='" + productType + '\'' +
                ", productSize='" + productSize + '\'' +
                ", productLocation='" + productLocation + '\'' +
                ", productDescription='" + productDescription + '\'' +
                '}';
    }
}