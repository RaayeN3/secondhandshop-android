package ie.bask.niftysecondhandshop.models;

import android.net.Uri;

/**
 * Created by cecobask on 28-Feb-18.
 */

public class AdvertCar {

    private Long counter = (long) 1;
    private Long carID;
    private Uri imageUri;
    private String carMake;
    private String carModel;
    private int carYear;
    private double carPrice;
    private String carLocation;
    private String carDescription;

    public AdvertCar(Uri imageUri, String carMake, String carModel, int carYear, double carPrice, String carLocation, String carDescription) {
        this.carID = counter++;
        this.imageUri = imageUri;
        this.carMake = carMake;
        this.carModel = carModel;
        this.carYear = carYear;
        this.carPrice = carPrice;
        this.carLocation = carLocation;
        this.carDescription = carDescription;
    }

    public Long getCarID() {
        return carID;
    }

    public void setCarID(Long carID) {
        this.carID = carID;
    }

    public Uri getImageUri() {
        return imageUri;
    }

    public void setImageUri(Uri imageUri) {
        this.imageUri = imageUri;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public int getCarYear() {
        return carYear;
    }

    public void setCarYear(int carYear) {
        this.carYear = carYear;
    }

    public double getCarPrice() {
        return carPrice;
    }

    public void setCarPrice(double carPrice) {
        this.carPrice = carPrice;
    }

    public String getCarLocation() {
        return carLocation;
    }

    public void setCarLocation(String carLocation) {
        this.carLocation = carLocation;
    }

    public String getCarDescription() {
        return carDescription;
    }

    public void setCarDescription(String carDescription) {
        this.carDescription = carDescription;
    }
}