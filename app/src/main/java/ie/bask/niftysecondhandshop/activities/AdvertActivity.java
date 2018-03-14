package ie.bask.niftysecondhandshop.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import ie.bask.niftysecondhandshop.R;
import ie.bask.niftysecondhandshop.models.Advert;


public class AdvertActivity extends Base {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialising widgets
        advertImage = findViewById(R.id.advertImage);

        productTitle = findViewById(R.id.productTitle);
        // Set the max input length of title to 25 characters
        InputFilter[] titleFilter = new InputFilter[1];
        titleFilter[0] = new InputFilter.LengthFilter(25);
        productTitle.setFilters(titleFilter);

        // Horizontal number picker used for price
        snp_horizontal = findViewById(R.id.snp_horizontal);

        // Set the max length of price to 3
        priceManual = findViewById(R.id.priceManual);
        InputFilter[] priceFilter = new InputFilter[1];
        priceFilter[0] = new InputFilter.LengthFilter(3);
        priceManual.setFilters(priceFilter);

        locationSpinner = findViewById(R.id.locationSpinner);

        // Use string-array from the res/values/strings to populate in the spinner
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(AdvertActivity.this,
                R.layout.spinner_item, getResources().getStringArray(R.array.locations));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(myAdapter);

        productDetails = findViewById(R.id.productDetails);
        InputFilter[] detailsFilter = new InputFilter[1];
        detailsFilter[0] = new InputFilter.LengthFilter(50);
        productDetails.setFilters(detailsFilter);

        submitButton = findViewById(R.id.submitButton);

        permissionCheck();

        takePhoto();
    }


    /**
     * Save adverts to SharedPreferences when onPause state is triggered
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveAdvertList();
    }


    public void submitButtonPressed(View view) {
        // Get input from widgets
        String title = productTitle.getText().toString();
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
        if (TextUtils.isEmpty(productTitle.getText())) {
            productTitle.setError("Product title is required!");
            productTitle.requestFocus();
        } else if (TextUtils.isEmpty(productDetails.getText())) {
            productDetails.setError("Product details is required!");
            productDetails.requestFocus();
        }
        // if none of the fields are empty
        else {
            // Getting the position of clicked element from ListView in BrowseActivity
            Bundle extras = getIntent().getExtras();

            // Used for updating objects if Bundle has extras
            if (extras != null) {
                int position = extras.getInt("pos");
                adverts.set(position, new Advert(imageUri, title, price, location, details));
                Toast.makeText(this, "Successfully updated position " + position, Toast.LENGTH_SHORT).show();
                Log.v("MyLogs", "Updated position " + String.valueOf(position));
            }
            // Create a new Advert object if Bundle has no extras in it
            else {
                newAdvert(new Advert(imageUri, title, price, location, details));
                Log.v("MyLogs", "Submit pressed! Data: 1) Title: " + title + " (2) Price: " + price + " (3) Location: " + location + " (4) Details: " + details);
            }
        }
    }

}