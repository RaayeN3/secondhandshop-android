package ie.bask.niftysecondhandshop.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;

import ie.bask.niftysecondhandshop.R;
import ie.bask.niftysecondhandshop.models.AdvertCar;

public class ViewAdvertCarActivity extends Base implements View.OnClickListener {

    ImageView imageViewProduct;
    TextView textViewMake, textViewModel, textViewYear, textViewPrice, textViewLocation, textViewDetails;
    EditText EditTextModel, EditTextYear, EditTextPrice, EditTextLocation, EditTextDetails;
    AutoCompleteTextView autoCompleteMake;
    ScrollableNumberPicker snp_carYear;
    Button buttonUpdate, buttonDelete, buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_advert_car);

        imageViewProduct = findViewById(R.id.imageViewProduct);
        textViewMake = findViewById(R.id.textViewMake);
        textViewModel = findViewById(R.id.textViewModel);
        textViewYear = findViewById(R.id.textViewYear);
        textViewPrice = findViewById(R.id.textViewPrice);
        textViewLocation = findViewById(R.id.textViewLocation);
        textViewDetails = findViewById(R.id.textViewDetails);
        autoCompleteMake = findViewById(R.id.autoCompleteMake);
        EditTextModel = findViewById(R.id.EditTextModel);
        EditTextYear = findViewById(R.id.EditTextYear);
        EditTextPrice = findViewById(R.id.EditTextPrice);
        EditTextLocation = findViewById(R.id.EditTextLocation);
        EditTextDetails = findViewById(R.id.EditTextDetails);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonSave = findViewById(R.id.buttonSave);
        snp_carYear = findViewById(R.id.snp_carYear);

        // Prevent keyboard from automatically popping up
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Populate fields with values from selected position from ListView
        // Passed values with Bundle from BrowseActivity
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            imageViewProduct.setImageURI(Uri.parse(bundle.getString("image")));
            autoCompleteMake.setText(bundle.getString("make"));
            EditTextModel.setText(bundle.getString("model"));
            EditTextYear.setText(String.valueOf(bundle.getInt("year")));
            EditTextPrice.setText(bundle.getString("price"));
            EditTextLocation.setText(bundle.getString("location"));
            EditTextDetails.setText(bundle.getString("description"));
        }

        // Make fields disabled by default
        autoCompleteMake.setEnabled(false);
        EditTextModel.setEnabled(false);
        EditTextYear.setEnabled(false);
        EditTextPrice.setEnabled(false);
        EditTextLocation.setEnabled(false);
        EditTextDetails.setEnabled(false);

        // Set onClickListeners for buttons
        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        // Set the max input length of make to 25 characters
        InputFilter[] filter = new InputFilter[1];
        filter[0] = new InputFilter.LengthFilter(25);
        autoCompleteMake.setFilters(filter);

        // Set the max length of model to 20
        filter[0] = new InputFilter.LengthFilter(20);
        EditTextPrice.setFilters(filter);

        // Set the max length of model to 20
        filter[0] = new InputFilter.LengthFilter(20);
        EditTextPrice.setFilters(filter);

        // Set the max length of price to 6
        filter[0] = new InputFilter.LengthFilter(6);
        EditTextPrice.setFilters(filter);

        // Set the max length of location to 20
        filter[0] = new InputFilter.LengthFilter(20);
        EditTextLocation.setFilters(filter);

        // Set the max length of details to 50
        filter[0] = new InputFilter.LengthFilter(50);
        EditTextDetails.setFilters(filter);
    }

    /**
     * Handle onClick events
     */
    @Override
    public void onClick(View v) {
        if (v == buttonUpdate) {
            // Hide update and show save button
            buttonUpdate.setVisibility(View.GONE);
            buttonSave.setVisibility(View.VISIBLE);

            // Load string-array from resources to give suggestions
            // to the user when they start typing
            final ArrayAdapter<String> arrayAdapterCarMakes = new ArrayAdapter<>(ViewAdvertCarActivity.this, android.R.layout.simple_dropdown_item_1line,
                    getResources().getStringArray(R.array.carMakes));
            autoCompleteMake.setAdapter(arrayAdapterCarMakes);
            // Show suggestions after 1 symbol is typed
            autoCompleteMake.setThreshold(1);

            // Hide EditText and show number picker
            EditTextYear.setVisibility(View.GONE);
            snp_carYear.setVisibility(View.VISIBLE);
            snp_carYear.setValue(Integer.valueOf(EditTextYear.getText().toString()));

            // Enable editing text
            autoCompleteMake.setEnabled(true);
            EditTextModel.setEnabled(true);
            EditTextPrice.setEnabled(true);
            EditTextLocation.setEnabled(true);
            EditTextDetails.setEnabled(true);

            // Set onClickListener for save button
            buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Check if there are empty fields and set errors to alert the user
                    if (TextUtils.isEmpty(autoCompleteMake.getText())) {
                        autoCompleteMake.setError("Make is required!");
                        autoCompleteMake.requestFocus();
                    } else if (TextUtils.isEmpty(EditTextModel.getText())) {
                        EditTextModel.setError("Model is required");
                        EditTextModel.requestFocus();
                    } else if (TextUtils.isEmpty(EditTextDetails.getText())) {
                        EditTextDetails.setError("Details is required!");
                        EditTextDetails.requestFocus();
                    } else if (TextUtils.isEmpty(EditTextLocation.getText())) {
                        EditTextLocation.setError("Location is required!");
                        EditTextLocation.requestFocus();
                    } else if (TextUtils.isEmpty(EditTextPrice.getText())) {
                        EditTextPrice.setError("Details is required!");
                        EditTextPrice.requestFocus();
                    } else {
                        Bundle bundle = getIntent().getExtras();
                        // Get values from fields
                        String id = bundle.getString("id");
                        int position = bundle.getInt("pos");
                        String image = bundle.getString("image");
                        String make = autoCompleteMake.getText().toString();
                        String model = EditTextModel.getText().toString();
                        int year = snp_carYear.getValue();
                        String priceStr = EditTextPrice.getText().toString();
                        double price = Double.valueOf(priceStr);
                        String location = EditTextLocation.getText().toString();
                        String description = EditTextDetails.getText().toString();


                        // Create the updated Advert
                        AdvertCar carAd = new AdvertCar(id, image, make, model, year, price, location, description);
                        // Update database and arrayList
                        databaseCarAds.child(id).setValue(carAd);
                        carAdverts.set(position, carAd);
                        Toast.makeText(getApplicationContext(), "Successfully updated position " + position, Toast.LENGTH_SHORT).show();

                        // Hide save and show update button
                        buttonSave.setVisibility(View.GONE);
                        buttonUpdate.setVisibility(View.VISIBLE);

                        // Disable editing text
                        autoCompleteMake.setEnabled(false);
                        EditTextModel.setEnabled(false);
                        EditTextPrice.setEnabled(false);
                        EditTextLocation.setEnabled(false);
                        EditTextDetails.setEnabled(false);

                        // Hide/show widgets
                        snp_carYear.setVisibility(View.GONE);
                        EditTextYear.setVisibility(View.VISIBLE);
                        EditTextYear.setText(String.valueOf(year));
                    }
                }
            });
        } else {
            Bundle bundle = getIntent().getExtras();
            // Get position
            int position = bundle.getInt("pos");
            // Get advert at clicked position from database
            DatabaseReference clickedPos = databaseCarAds.child(carAdverts.get(position).getCarID());
            // Removing advert from database and arrayList
            clickedPos.removeValue();
            carAdverts.remove(position);
            // Close all previous activities and open BrowseActivity
            finishAffinity();
            Intent BrowseIntent = new Intent(getApplicationContext(), BrowseActivity.class);
            startActivity(BrowseIntent);
        }
    }

    @Override
    public void onBackPressed() {
        Intent backToBrowse = new Intent(getApplicationContext(), BrowseActivity.class);
        startActivity(backToBrowse);
    }

}
