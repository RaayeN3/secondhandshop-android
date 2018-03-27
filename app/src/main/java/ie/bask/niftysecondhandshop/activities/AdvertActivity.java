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

        // Set the max length of price to 5
        priceManual = findViewById(R.id.priceManual);
        InputFilter[] priceFilter = new InputFilter[1];
        priceFilter[0] = new InputFilter.LengthFilter(5);
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

        // Populate fields with values from selected position from ListView for update
        // Passed values with Bundle from BrowseActivity
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            productTitle.setText(bundle.getString("title"));
            priceManual.setText(bundle.getString("price"));
            String location = bundle.getString("location");
            int spinnerPosition = myAdapter.getPosition(location);
            locationSpinner.setSelection(spinnerPosition);
            productDetails.setText(bundle.getString("description"));
        }

        permissionCheck();
        takePhoto();
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
            Bundle bundle = getIntent().getExtras();
            // Used for updating objects if Bundle has extras
            if (bundle != null) {
                // Get extras from Bundle
                int position = bundle.getInt("pos");
                String id = bundle.getString("id");
                Advert ad = new Advert(id, imageUri, title, price, location, details);
                databaseAds.child(id).setValue(ad);
                adverts.set(position, ad);

                Toast.makeText(this, "Successfully updated position " + position, Toast.LENGTH_SHORT).show();
            }
            // Create a new Advert object if Bundle has no extras in it
            else {
                newAdvert(new Advert(imageUri, title, price, location, details));
                Log.v("MyLogs", "Submit pressed! Data: 1) Title: " + title + " (2) Price: " + price + " (3) Location: " + location + " (4) Details: " + details);
            }
        }
    }

}