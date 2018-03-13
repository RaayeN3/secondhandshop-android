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

        advertImage = findViewById(R.id.advertImage);

        productTitle = findViewById(R.id.productTitle);
        InputFilter[] titleFilter = new InputFilter[1];
        titleFilter[0] = new InputFilter.LengthFilter(25);
        productTitle.setFilters(titleFilter);

        snp_horizontal = findViewById(R.id.snp_horizontal);

        priceManual = findViewById(R.id.priceManual);
        InputFilter[] priceFilter = new InputFilter[1];
        priceFilter[0] = new InputFilter.LengthFilter(3);
        priceManual.setFilters(priceFilter);

        locationSpinner = findViewById(R.id.locationSpinner);

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

    @Override
    protected void onPause() {
        super.onPause();
        saveAdvertList();
    }

    public void submitButtonPressed(View view) {
        String title = productTitle.getText().toString();
        double price;
        String location = locationSpinner.getSelectedItem().toString();
        String details = productDetails.getText().toString();

        if (priceManual.getText().toString().isEmpty()) {
            price = (double) snp_horizontal.getValue();
        } else {
            price = Double.parseDouble(priceManual.getText().toString());
        }


        String imageUri = getImageUri(bitmap);
        Log.v("MyLogs", "Value of imageUri is " + imageUri);


        if (TextUtils.isEmpty(productTitle.getText())) {
            productTitle.setError("Product title is required!");
            productTitle.requestFocus();
        } else if (TextUtils.isEmpty(productDetails.getText())) {
            productDetails.setError("Product details is required!");
            productDetails.requestFocus();
        } else {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                int position = extras.getInt("pos");
                adverts.set(position, new Advert(imageUri, title, price, location, details));
                Toast.makeText(this, "Successfully updated position " + position, Toast.LENGTH_SHORT).show();
                Log.v("MyLogs", "Updated position " + String.valueOf(position));
            } else {
                newAdvert(new Advert(imageUri, title, price, location, details));
                Log.v("MyLogs", "Submit pressed! Data: 1) Title: " + title + " (2) Price: " + price + " (3) Location: " + location + " (4) Details: " + details);
            }
        }
    }

}