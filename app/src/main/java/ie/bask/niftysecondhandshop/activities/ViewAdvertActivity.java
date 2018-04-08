package ie.bask.niftysecondhandshop.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.firebase.database.DatabaseReference;

import java.net.MalformedURLException;
import java.net.URL;

import ie.bask.niftysecondhandshop.R;
import ie.bask.niftysecondhandshop.models.Advert;

public class ViewAdvertActivity extends Base implements View.OnClickListener {

    // Widgets
    ImageView imageViewProduct;
    TextView textViewTitle, textViewPrice, textViewLocation, textViewDetails;
    EditText EditTextTitle, EditTextPrice, EditTextLocation, EditTextDetails;
    Button buttonUpdate, buttonDelete, buttonSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_advert);

        // Initialise widgets
        imageViewProduct = findViewById(R.id.imageViewProduct);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewPrice = findViewById(R.id.textViewPrice);
        textViewLocation = findViewById(R.id.textViewLocation);
        textViewDetails = findViewById(R.id.textViewDetails);
        EditTextTitle = findViewById(R.id.EditTextTitle);
        EditTextPrice = findViewById(R.id.EditTextPrice);
        EditTextLocation = findViewById(R.id.EditTextLocation);
        EditTextDetails = findViewById(R.id.EditTextDetails);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonDelete = findViewById(R.id.buttonDelete);
        buttonSave = findViewById(R.id.buttonSave);

        // Prevent keyboard from automatically popping up
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        // Populate fields with values from selected position from ListView
        // Passed values with Bundle from BrowseActivity
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            try {
                // Download URL for image from Firebase Storage
                URL downloadURL = new URL(bundle.getString("image"));
                // Load image URL into ImageView
                Glide
                        .with(ViewAdvertActivity.this)
                        .load(downloadURL)
                        .apply(new RequestOptions()
                                .centerCrop()
                                .placeholder(R.mipmap.ic_launcher_round)
                                .error(R.mipmap.ic_launcher_round))
                        .into(imageViewProduct);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            EditTextTitle.setText(bundle.getString("title"));
            EditTextPrice.setText(bundle.getString("price"));
            EditTextLocation.setText(bundle.getString("location"));
            EditTextDetails.setText(bundle.getString("description"));
        }

        // Adding scroller to Details field
        EditTextDetails.setMovementMethod(new ScrollingMovementMethod());

        // Set onClickListeners for buttons
        buttonUpdate.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        // Hide save button by default
        buttonSave.setVisibility(View.GONE);

        // Make EditTexts disabled by default
        EditTextTitle.setEnabled(false);
        EditTextPrice.setEnabled(false);
        EditTextLocation.setEnabled(false);
        EditTextDetails.setEnabled(false);

        // Set the max input length of title to 25 characters
        InputFilter[] filter = new InputFilter[1];
        filter[0] = new InputFilter.LengthFilter(25);
        EditTextTitle.setFilters(filter);

        // Set the max length of price to 5
        filter[0] = new InputFilter.LengthFilter(5);
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

            // Enable editing text
            EditTextTitle.setEnabled(true);
            EditTextPrice.setEnabled(true);
            EditTextLocation.setEnabled(true);
            EditTextDetails.setEnabled(true);

            // Set onClickListener for save button
            buttonSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = getIntent().getExtras();
                    // Get values from fields
                    String id = bundle.getString("id");
                    int position = bundle.getInt("pos");
                    String image = bundle.getString("image");
                    String title = EditTextTitle.getText().toString();
                    String priceStr = EditTextPrice.getText().toString();
                    double price = Double.valueOf(priceStr);
                    String location = EditTextLocation.getText().toString();
                    String description = EditTextDetails.getText().toString();

                    // Create the updated Advert
                    Advert ad = new Advert(id, image, title, price, location, description);
                    // Update database and arrayList
                    databaseAds.child(id).setValue(ad);
                    adverts.set(position, ad);
                    Toast.makeText(getApplicationContext(), "Successfully updated position " + position, Toast.LENGTH_SHORT).show();

                    // Hide save and show update button
                    buttonSave.setVisibility(View.GONE);
                    buttonUpdate.setVisibility(View.VISIBLE);
                    // Disable editing text
                    EditTextTitle.setEnabled(false);
                    EditTextPrice.setEnabled(false);
                    EditTextLocation.setEnabled(false);
                    EditTextDetails.setEnabled(false);
                }
            });
        } else {
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ViewAdvertActivity.this);
            alertDialogBuilder.setTitle("You are about to delete an advert!");
            alertDialogBuilder.setMessage("Really delete this advert?");
            alertDialogBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Bundle bundle = getIntent().getExtras();
                    // Get position
                    String id = bundle.getString("id");
                    // Get advert at clicked position from database
                    DatabaseReference clickedPos = databaseAds.child(id);
                    // Removing advert from database and arrayList
                    clickedPos.removeValue();

                    // Iterate through array to find element with specific ID
                    for (int j = 0; j < adverts.size(); j++) {
                        Advert obj = adverts.get(j);

                        if (obj.getProductID().equals(id)) {
                            // Found, delete.
                            adverts.remove(j);
                            break;
                        }
                    }

                    // Close all previous activities and open BrowseActivity
                    finishAffinity();
                    Intent BrowseIntent = new Intent(getApplicationContext(), BrowseActivity.class);
                    BrowseIntent.putExtra("selectRadioButton", R.id.advert_radioButton);
                    startActivity(BrowseIntent);
                }
            });
            alertDialogBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            alertDialogBuilder.show();
        }
    }


    @Override
    public void onBackPressed() {
        Intent backToBrowse = new Intent(getApplicationContext(), BrowseActivity.class);
        backToBrowse.putExtra("selectRadioButton", R.id.advert_radioButton);
        startActivity(backToBrowse);
    }

}
