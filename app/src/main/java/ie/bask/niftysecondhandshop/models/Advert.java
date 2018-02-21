package ie.bask.niftysecondhandshop.models;

import android.media.Image;
import android.widget.ImageView;

/**
 * Created by cecobask on 18-Feb-18.
 */

public class Advert {

    private Long counter = (long) 1;
    private Long productID;
    private ImageView productImage;
    private String productTitle;
    private double productPrice;
    private String productLocation;
    private String productDescription;


    public Advert(ImageView productImage, String productTitle, double productPrice, String productLocation, String productDescription) {
        this.productID = counter++;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.productPrice = productPrice;
        this.productLocation = productLocation;
        this.productDescription = productDescription;
    }

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public ImageView getProductImage() {
        return productImage;
    }

    public void setProductImage(ImageView productImage) {
        this.productImage = productImage;
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

}