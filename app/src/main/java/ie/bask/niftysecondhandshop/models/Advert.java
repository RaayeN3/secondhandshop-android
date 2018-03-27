package ie.bask.niftysecondhandshop.models;

import ie.bask.niftysecondhandshop.activities.Base;

/**
 * Created by cecobask on 18-Feb-18.
 */

public class Advert {

    private String productID;
    private String imageUri;
    private String productTitle;
    private double productPrice;
    private String productLocation;
    private String productDescription;

    public Advert() {
    }

    public Advert(String productID, String imageUri, String productTitle, double productPrice, String productLocation, String productDescription) {
        this.productID = productID;
        this.imageUri = imageUri;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.productLocation = productLocation;
        this.productDescription = productDescription;
    }

    public Advert(String imageUri, String productTitle, double productPrice, String productLocation, String productDescription) {
        this.productID = Base.databaseAds.push().getKey();
        this.imageUri = imageUri;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.productLocation = productLocation;
        this.productDescription = productDescription;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
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

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }


    @Override
    public String toString() {
        return "Advert{" +
                "productID=" + productID +
                ", imageUri='" + imageUri + '\'' +
                ", productTitle='" + productTitle + '\'' +
                ", productPrice=" + productPrice +
                ", productLocation='" + productLocation + '\'' +
                ", productDescription='" + productDescription + '\'' +
                '}';
    }
}