package ie.bask.niftysecondhandshop.models;

import android.net.Uri;

/**
 * Created by cecobask on 26-Feb-18.
 */

public class AdvertFashion {

    private Long counter = (long) 1;
    private Long productID;
    private Uri imageUri;
    private String productTitle;
    private double productPrice;
    private String productType;
    private String productSize;
    private String productLocation;
    private String productDescription;

    public AdvertFashion(Uri imageUri, String productTitle, double productPrice, String productType, String productSize, String productLocation, String productDescription) {
        this.productID = counter++;
        this.imageUri = imageUri;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.productType = productType;
        this.productSize = productSize;
        this.productLocation = productLocation;
        this.productDescription = productDescription;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
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
}