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
import android.widget.Toast;

import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;

import ie.bask.niftysecondhandshop.R;
import ie.bask.niftysecondhandshop.models.Advert;
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

        loadAdvertCarList();

        advertImage = findViewById(R.id.advertImage);

        carMake = findViewById(R.id.carMake);
        InputFilter[] carMakeFilter = new InputFilter[1];
        carMakeFilter[0] = new InputFilter.LengthFilter(20);
        carMake.setFilters(carMakeFilter);
        final ArrayAdapter<String> arrayAdapterCarMakes = new ArrayAdapter<>(AdvertCarActivity.this, android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.carMakes));
        carMake.setAdapter(arrayAdapterCarMakes);
        carMake.setThreshold(1);

        carModel = findViewById(R.id.carModel);
        InputFilter[] carModelFilter = new InputFilter[1];
        carModelFilter[0] = new InputFilter.LengthFilter(20);
        carModel.setFilters(carModelFilter);

        carYear = findViewById(R.id.snp_carYear);

        snp_horizontal = findViewById(R.id.snp_horizontal);

        priceManual = findViewById(R.id.priceManual);
        InputFilter[] priceFilter = new InputFilter[1];
        priceFilter[0] = new InputFilter.LengthFilter(4);
        priceManual.setFilters(priceFilter);

        locationSpinner = findViewById(R.id.locationSpinner);
        ArrayAdapter<String> myAdapter = new ArrayAdapter<>(AdvertCarActivity.this,
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

    protected void onPause() {
        super.onPause();
        saveAdvertCarList();
    }

    public void submitButtonPressed(View view) {
        String make = carMake.getText().toString();
        String model = carModel.getText().toString();
        int year = carYear.getValue();
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

        if (TextUtils.isEmpty(carMake.getText())) {
            carMake.setError("Car make is required!");
            carMake.requestFocus();
        } else if (TextUtils.isEmpty(carModel.getText())) {
            carModel.setError("Car model is required");
            carModel.requestFocus();
        } else if (TextUtils.isEmpty(productDetails.getText())) {
            productDetails.setError("Details field is required!");
            productDetails.requestFocus();
        } else {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                int position = extras.getInt("pos");
                carAdverts.set(position, new AdvertCar(imageUri, make, model, year, price, location, details));
                saveAdvertCarList();
                Toast.makeText(this, "Successfully updated position " + position, Toast.LENGTH_SHORT).show();
                Log.v("MyLogs", String.valueOf(position));
            } else {
                newAdvertCar(new AdvertCar(imageUri, make, model, year, price, location, details));
                saveAdvertCarList();
                Log.v("MyLogs", "Submit pressed! Data: 1) Make: " + model + " (2) Model: " + model + " (3) Year: " + year + " (4) Price: " + price +
                        " (5) Location: " + location + " (6) Details: " + details);
            }
        }
    }

}
