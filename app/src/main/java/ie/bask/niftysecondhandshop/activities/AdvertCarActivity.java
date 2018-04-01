package ie.bask.niftysecondhandshop.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;

import ie.bask.niftysecondhandshop.R;
import ie.bask.niftysecondhandshop.models.AdvertCar;

public class AdvertCarActivity extends Base {

    private AutoCompleteTextView carMake;
    private EditText carModel;
    private ScrollableNumberPicker carYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert_car);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialising widgets
        advertImage = findViewById(R.id.advertImage);
        carMake = findViewById(R.id.carMake);
        carModel = findViewById(R.id.carModel);
        carYear = findViewById(R.id.snp_carYear);
        snp_horizontal = findViewById(R.id.snp_horizontal);
        priceManual = findViewById(R.id.priceManual);
        locationSpinner = findViewById(R.id.locationSpinner);
        productDetails = findViewById(R.id.productDetails);
        submitButton = findViewById(R.id.submitButton);

        // Load string-array from resources to give suggestions
        // to the user when they start typing
        ArrayAdapter<String> arrayAdapterCarMakes = new ArrayAdapter<>(AdvertCarActivity.this, android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.carMakes));
        carMake.setAdapter(arrayAdapterCarMakes);
        // Show suggestions after 1 symbol is typed
        carMake.setThreshold(1);

        // Set max input length of carMake to 20 chars
        InputFilter[] filter = new InputFilter[1];
        filter[0] = new InputFilter.LengthFilter(20);
        carMake.setFilters(filter);

        filter[0] = new InputFilter.LengthFilter(20);
        carModel.setFilters(filter);

        filter[0] = new InputFilter.LengthFilter(6);
        priceManual.setFilters(filter);

        filter[0] = new InputFilter.LengthFilter(50);
        productDetails.setFilters(filter);

        // Use string-array from the res/values/strings to populate in the spinner
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(AdvertCarActivity.this,
                R.layout.spinner_item, getResources().getStringArray(R.array.locations));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(myAdapter);

        permissionCheck();
        takePhoto();
    }


    public void submitButtonPressed(View view) {

        // Get input from widgets
        String make = carMake.getText().toString();
        String model = carModel.getText().toString();
        int year = carYear.getValue();
        double price;
        String location = locationSpinner.getSelectedItem().toString();
        String details = productDetails.getText().toString();

        // Use the number picker if manual price is empty, default value of np is 0
        if (priceManual.getText().toString().isEmpty()) {
            price = (double) snp_horizontal.getValue();
        } else {
            price = Double.parseDouble(priceManual.getText().toString());
        }

        // Get the URI of captured image
        String imageUri = getImageUri(bitmap);
        Log.v("MyLogs", "Value of imageUri is " + imageUri);

        // Check if there are empty fields and set errors to alert the user
        if (TextUtils.isEmpty(carMake.getText())) {
            carMake.setError("Car make is required!");
            carMake.requestFocus();
        } else if (TextUtils.isEmpty(carModel.getText())) {
            carModel.setError("Car model is required");
            carModel.requestFocus();
        } else if (TextUtils.isEmpty(productDetails.getText())) {
            productDetails.setError("Details field is required!");
            productDetails.requestFocus();
        }
        // If none of the field are empty
        else {
            newAdvertCar(new AdvertCar(imageUri, make, model, year, price, location, details));
            Log.v("MyLogs", "Submit pressed! Data: 1) Make: " + model + " (2) Model: " + model + " (3) Year: " + year + " (4) Price: " + price +
                    " (5) Location: " + location + " (6) Details: " + details);
        }
    }

}
