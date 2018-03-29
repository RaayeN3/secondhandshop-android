package ie.bask.niftysecondhandshop.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import ie.bask.niftysecondhandshop.R;

public class ViewAdvertCarActivity extends Base implements View.OnClickListener {

    ImageView imageViewProduct;
    TextView textViewMake, textViewModel, textViewYear, textViewPrice, textViewLocation, textViewDetails, tvMake, tvModel, tvYear, tvPrice, tvLocation, tvDetails;
    Button buttonUpdate, buttonDelete;

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
        tvMake = findViewById(R.id.tvMake);
        tvModel = findViewById(R.id.tvModel);
        tvYear = findViewById(R.id.tvYear);
        tvPrice = findViewById(R.id.tvPrice);
        tvLocation = findViewById(R.id.tvLocation);
        tvDetails = findViewById(R.id.tvDetails);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);

        // Populate fields with values from selected position from ListView
        // Passed values with Bundle from BrowseActivity
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            imageViewProduct.setImageURI(Uri.parse(bundle.getString("image")));
            tvMake.setText(bundle.getString("make"));
            tvModel.setText(bundle.getString("model"));
            tvYear.setText(String.valueOf(bundle.getInt("year")));
            tvPrice.setText(bundle.getString("price"));
            tvLocation.setText(bundle.getString("location"));
            tvDetails.setText(bundle.getString("description"));
        }

        // Adding scroller to fields
        tvDetails.setMovementMethod(new ScrollingMovementMethod());
        tvMake.setMovementMethod(new ScrollingMovementMethod());
        tvModel.setMovementMethod(new ScrollingMovementMethod());

        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);
    }

    /**
     * Handle onClick events
     */
    @Override
    public void onClick(View v) {
        if (v == buttonUpdate) {
            Bundle bundle = getIntent().getExtras();
            // Set values for fields
            String id = bundle.getString("id");
            int position = bundle.getInt("pos");
            String make = tvMake.getText().toString();
            String model = tvModel.getText().toString();
            int year = Integer.valueOf(tvYear.getText().toString());
            String price = tvPrice.getText().toString();
            String location = tvLocation.getText().toString();
            String description = tvDetails.getText().toString();

            // Open AdvertActivity for update of current advert
            Intent AdvertCarIntent = new Intent(getApplicationContext(), AdvertCarActivity.class);
            // Put extras into the Bundle
            Bundle b = new Bundle();
            b.putInt("pos", position);
            b.putString("id", id);
            b.putString("make", make);
            b.putString("model", model);
            b.putInt("year", year);
            b.putString("price", price);
            b.putString("location", location);
            b.putString("description", description);
            AdvertCarIntent.putExtras(b);
            startActivityForResult(AdvertCarIntent, 0);
        } else {
            Bundle bundle = getIntent().getExtras();
            // Get position
            int position = bundle.getInt("pos");
            // Get advert at clicked position from database
            DatabaseReference clickedPos = databaseCarAds.child(carAdverts.get(position).getCarID());
            // Removing advert
            clickedPos.removeValue();
            carAdverts.remove(position);
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
