package ie.bask.niftysecondhandshop.activities;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

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
        snp_horizontal = findViewById(R.id.snp_horizontal);
        priceManual = findViewById(R.id.priceManual);
        locationSpinner = findViewById(R.id.locationSpinner);
        productDetails = findViewById(R.id.productDetails);
        submitButton = findViewById(R.id.submitButton);
        progressDialog = new ProgressDialog(this);

        // Set the max input length of title to 25 characters
        InputFilter[] filter = new InputFilter[1];
        filter[0] = new InputFilter.LengthFilter(25);
        productTitle.setFilters(filter);

        // Set the max length of price to 5
        filter[0] = new InputFilter.LengthFilter(5);
        priceManual.setFilters(filter);

        filter[0] = new InputFilter.LengthFilter(50);
        productDetails.setFilters(filter);

        // Use string-array from the res/values/strings to populate in the spinner
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(AdvertActivity.this,
                R.layout.spinner_item, getResources().getStringArray(R.array.locations));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        locationSpinner.setAdapter(myAdapter);

        permissionCheck();
        takePhoto();

        // Initialise Firebase Storage
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
    }


    public void submitButtonPressed(View view) {
        // Get input from widgets
        final String title = productTitle.getText().toString();
        final double price;
        final String location = locationSpinner.getSelectedItem().toString();
        final String details = productDetails.getText().toString();

        // Use the number picker if manual price is empty, default value of np is 0
        if (priceManual.getText().toString().isEmpty()) {
            price = (double) snp_horizontal.getValue();
        } else {
            price = Double.parseDouble(priceManual.getText().toString());
        }

        // Get the URI of captured image
        String imageUri = getImageUri(bitmap);

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
            // Start progress dialog
            progressDialog.setMessage("Uploading advert...");
            progressDialog.show();

            // Get reference to Firebase Storage and put photo into images folder
            final StorageReference ref = storageReference.child("images/" + UUID.randomUUID().toString());
            ref.putFile(Uri.parse(imageUri)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // Close progress dialog
                    progressDialog.dismiss();

                    String downloadURL = String.valueOf(taskSnapshot.getDownloadUrl());
                    Log.v("MyLogs", "Value of ref is " + taskSnapshot.getDownloadUrl().toString());
                    // Create a new advert with the data
                    newAdvert(new Advert(downloadURL, title, price, location, details));
                    Log.v("MyLogs", "Submit pressed! Data: 1) Title: " + title + " (2) Price: " + price + " (3) Location: " + location + " (4) Details: " + details);

                }
            });
        }
    }

}