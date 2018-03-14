package ie.bask.niftysecondhandshop.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;

import ie.bask.niftysecondhandshop.R;
import ie.bask.niftysecondhandshop.models.AdvertFashion;

public class AdvertFashionActivity extends Base {

    private RadioGroup productType;
    private Spinner clothingSizeSpinner;
    private ScrollableNumberPicker shoeSize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advert_fashion);
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

        productType = findViewById(R.id.typeRadioGroup);
        // Check clothing by default
        productType.check(R.id.clothing_radioButton);

        // If shoes radio button is clicked, hide clothing sizes
        // and vice versa
        productType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.clothing_radioButton) {
                    clothingSizeSpinner.setVisibility(View.VISIBLE);
                    shoeSize.setVisibility(View.GONE);
                } else {
                    clothingSizeSpinner.setVisibility(View.GONE);
                    shoeSize.setVisibility(View.VISIBLE);
                }
            }
        });

        clothingSizeSpinner = findViewById(R.id.clothingSizeSpinner);
        // Populate string-array with clothing sizes to the spinner
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(AdvertFashionActivity.this,
                R.layout.spinner_item, getResources().getStringArray(R.array.clothingSizes));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clothingSizeSpinner.setAdapter(myAdapter);

        shoeSize = findViewById(R.id.snp_shoeSizes);

        locationSpinner = findViewById(R.id.locationSpinner);

        // Use string-array from the res/values/strings to populate in the spinner
        myAdapter = new ArrayAdapter<>(AdvertFashionActivity.this,
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
        saveAdvertFashionList();
    }


    public void submitButtonPressed(View view) {
        // Get input from widgets
        String title = productTitle.getText().toString();
        double price;
        String location = locationSpinner.getSelectedItem().toString();
        String details = productDetails.getText().toString();
        int radioId = productType.getCheckedRadioButtonId();
        String type;
        String size;

        // Use the number picker if manual price is empty, default value of np is 0
        if (priceManual.getText().toString().isEmpty()) {
            price = (double) snp_horizontal.getValue();
        } else {
            price = Double.parseDouble(priceManual.getText().toString());
        }

        // Set value of type for each radio button clicked
        if (radioId == R.id.clothing_radioButton) {
            type = "Clothing";
        } else {
            type = "Shoes";
        }

        // Set size = value from spinner if checked radio button is Clothing
        if (radioId == R.id.clothing_radioButton) {
            size = clothingSizeSpinner.getSelectedItem().toString();
        }
        // Set size = value from EditText if checked radio button is Shoes
        else {
            size = String.valueOf(shoeSize.getValue());
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
                fashionAdverts.set(position, new AdvertFashion(imageUri, title, price, type, size, location, details));
                Toast.makeText(this, "Successfully updated position " + position, Toast.LENGTH_SHORT).show();
                Log.v("MyLogs", "Updated position " + String.valueOf(position));
            }
            // Create a new AdvertFashion object if Bundle has no extras in it
            else {
                newAdvertFashion(new AdvertFashion(imageUri, title, price, type, size, location, details));
                Log.v("MyLogs", "Submit pressed! Data: 1) Title: " + title + " (2) Price: " + price + " (3) Type: " + type + " (4) Size: " + size +
                        " (5) Location: " + location + " (6) Details: " + details);
            }
        }
    }

}
