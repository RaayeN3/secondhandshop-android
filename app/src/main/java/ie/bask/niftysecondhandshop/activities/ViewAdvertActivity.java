package ie.bask.niftysecondhandshop.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;

import ie.bask.niftysecondhandshop.R;

public class ViewAdvertActivity extends Base implements View.OnClickListener {

    ImageView imageViewProduct;
    TextView textViewTitle, tvTitle, textViewPrice, tvPrice, textViewLocation, tvLocation, textViewDetails, tvDetails;
    Button buttonUpdate, buttonDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_advert);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageViewProduct = findViewById(R.id.imageViewProduct);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewPrice = findViewById(R.id.textViewPrice);
        textViewLocation = findViewById(R.id.textViewLocation);
        textViewDetails = findViewById(R.id.textViewDetails);
        tvTitle = findViewById(R.id.tvTitle);
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
            tvTitle.setText(bundle.getString("title"));
            tvPrice.setText(bundle.getString("price"));
            tvLocation.setText(bundle.getString("location"));
            tvDetails.setText(bundle.getString("description"));
        }

        // Adding scroller to Details field
        tvDetails.setMovementMethod(new ScrollingMovementMethod());

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
            String title = tvTitle.getText().toString();
            String price = tvPrice.getText().toString();
            String location = tvLocation.getText().toString();
            String description = tvDetails.getText().toString();

            // Open AdvertActivity for update of current advert
            Intent AdvertIntent = new Intent(getApplicationContext(), AdvertActivity.class);
            // Put extras into the Bundle
            Bundle b = new Bundle();
            b.putInt("pos", position);
            b.putString("id", id);
            b.putString("title", title);
            b.putString("price", price);
            b.putString("location", location);
            b.putString("description", description);
            AdvertIntent.putExtras(b);
            startActivityForResult(AdvertIntent, 0);
        } else {
            Bundle bundle = getIntent().getExtras();
            // Get position
            int position = bundle.getInt("pos");
            // Get advert at clicked position from database
            DatabaseReference clickedPos = databaseAds.child(adverts.get(position).getProductID());
            // Removing advert
            clickedPos.removeValue();
            adverts.remove(position);
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
