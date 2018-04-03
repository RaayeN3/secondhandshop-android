package ie.bask.niftysecondhandshop.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.net.MalformedURLException;
import java.net.URL;

import ie.bask.niftysecondhandshop.R;
import ie.bask.niftysecondhandshop.adapters.AdvertAdapter;
import ie.bask.niftysecondhandshop.adapters.AdvertCarAdapter;
import ie.bask.niftysecondhandshop.adapters.AdvertFashionAdapter;
import ie.bask.niftysecondhandshop.models.Advert;
import ie.bask.niftysecondhandshop.models.AdvertCar;
import ie.bask.niftysecondhandshop.models.AdvertFashion;

public class BrowseActivity extends Base {

    // Widgets
    ListView productsView;
    ListView fashionProductsView;
    ListView carsView;
    TextView browseEmptyDefaultText;
    RadioGroup choice_radio_group;
    TextView emptyAdvertCategory;
    ImageButton searchButton;
    EditText EditTextSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialise widgets
        emptyAdvertCategory = findViewById(R.id.emptyAdvertCategory);
        emptyAdvertCategory.setVisibility(View.GONE);
        productsView = findViewById(R.id.productsView);
        fashionProductsView = findViewById(R.id.fashionProductsView);
        carsView = findViewById(R.id.carsView);
        choice_radio_group = findViewById(R.id.choice_radio_group);
        browseEmptyDefaultText = findViewById(R.id.browseEmptyDefaultText);
        searchButton = findViewById(R.id.searchButton);
        EditTextSearch = findViewById(R.id.EditTextSearch);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchText = EditTextSearch.getText().toString();
                String type;
                int radioID = choice_radio_group.getCheckedRadioButtonId();
                if (radioID == R.id.advert_radioButton) {
                    type = "General";
                } else if (radioID == R.id.fashionAd_radioButton) {
                    type = "Fashion";
                } else {
                    type = "Car";
                }
                firebaseUserSearch(searchText, type);
            }
        });


        // Bind adapter to ListView
        final AdvertAdapter adapter = new AdvertAdapter(this, adverts);
        productsView.setAdapter(adapter);
        // Display AlertDialog with CRUD operations when the user long clicks on any position
        productsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BrowseActivity.this);
                alertDialogBuilder.setTitle("CRUD operations:");
                String[] items = {"Delete product", "Delete all"};
                alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Delete the Advert at clicked position
                        if (i == 0) {
                            // Get advert at clicked position from database
                            DatabaseReference clickedPos = databaseAds.child(adverts.get(position).getProductID());
                            // Removing advert
                            clickedPos.removeValue();
                            adverts.remove(position);
                            // Notify adapter of changed data
                            adapter.notifyDataSetChanged();
                        }
                        // Delete all Adverts
                        else {
                            // Get reference to ads table
                            databaseAds = FirebaseDatabase.getInstance().getReference("ads");
                            // Delete all nodes in the table
                            databaseAds.setValue(null);
                            adverts.clear();
                            // Notify adapter of changed data
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                return true;
            }
        });
        // Open up activity to view individual advert
        // passing the data with Bundle
        productsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent ViewAdvertIntent = new Intent(getApplicationContext(), ViewAdvertActivity.class);
                // Put extras into the Bundle
                Bundle b = new Bundle();
                Advert advert = (Advert) parent.getItemAtPosition(position);
                b.putInt("pos", position);
                b.putString("id", advert.getProductID());
                b.putString("image", advert.getImageUri());
                b.putString("title", advert.getProductTitle());
                b.putString("price", Double.toString(advert.getProductPrice()));
                b.putString("location", advert.getProductLocation());
                b.putString("description", advert.getProductDescription());
                ViewAdvertIntent.putExtras(b);
                // Start ViewAdvertActivity
                startActivityForResult(ViewAdvertIntent, 0);
            }
        });


        // Bind adapter to ListView
        final AdvertFashionAdapter adapterFashion = new AdvertFashionAdapter(this, fashionAdverts);
        fashionProductsView.setAdapter(adapterFashion);
        // Display AlertDialog with CRUD operations when the user long clicks on any position
        fashionProductsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BrowseActivity.this);
                alertDialogBuilder.setTitle("CRUD operations:");
                String[] items = {"Update", "Delete product", "Delete all"};
                alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Update AdvertFashion at clicked position
                        if (i == 0) {
                            Intent AdvertFashionIntent = new Intent(getApplicationContext(), AdvertFashionActivity.class);
                            // Put extras into the Bundle
                            Bundle b = new Bundle();
                            b.putInt("pos", position);
                            b.putString("id", fashionAdverts.get(position).getProductID());
                            b.putString("title", fashionAdverts.get(position).getProductTitle());
                            b.putString("price", Double.toString(fashionAdverts.get(position).getProductPrice()));
                            b.putString("type", fashionAdverts.get(position).getProductType());
                            b.putString("size", fashionAdverts.get(position).getProductSize());
                            b.putString("location", fashionAdverts.get(position).getProductLocation());
                            b.putString("description", fashionAdverts.get(position).getProductDescription());
                            AdvertFashionIntent.putExtras(b);
                            startActivityForResult(AdvertFashionIntent, 0);
                        }
                        // Delete the AdvertFashion at clicked position
                        else if (i == 1) {
                            // Get advert at clicked position from database
                            DatabaseReference clickedPos = databaseFashionAds.child(fashionAdverts.get(position).getProductID());

                            // Removing advert
                            clickedPos.removeValue();
                            fashionAdverts.remove(position);

                            // Notify adapter of changed data
                            adapterFashion.notifyDataSetChanged();
                        }
                        // Delete all AdvertFashions
                        else {
                            // Get reference to fashionAds table
                            databaseFashionAds = FirebaseDatabase.getInstance().getReference("fashionAds");

                            // Delete all nodes in the table
                            databaseFashionAds.setValue(null);
                            fashionAdverts.clear();

                            // Notify adapter of changed data
                            adapterFashion.notifyDataSetChanged();
                        }
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                return true;
            }
        });
        // Hide ListView by default
        fashionProductsView.setVisibility(View.GONE);

        // Open up activity to view individual advert
        // passing the data with Bundle
        fashionProductsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent ViewAdvertFashionIntent = new Intent(getApplicationContext(), ViewAdvertFashionActivity.class);
                // Put extras into the Bundle
                Bundle b = new Bundle();
                AdvertFashion advertFashion = (AdvertFashion) parent.getItemAtPosition(position);
                b.putInt("pos", position);
                b.putString("id", advertFashion.getProductID());
                b.putString("image", advertFashion.getImageUri());
                b.putString("title", advertFashion.getProductTitle());
                b.putString("price", Double.toString(advertFashion.getProductPrice()));
                b.putString("type", advertFashion.getProductType());
                b.putString("size", advertFashion.getProductSize());
                b.putString("location", advertFashion.getProductLocation());
                b.putString("description", advertFashion.getProductDescription());
                ViewAdvertFashionIntent.putExtras(b);
                // Start ViewAdvertActivity
                startActivityForResult(ViewAdvertFashionIntent, 0);
            }
        });


        final AdvertCarAdapter adapterCar = new AdvertCarAdapter(this, carAdverts);
        carsView.setAdapter(adapterCar);
        carsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BrowseActivity.this);
                alertDialogBuilder.setTitle("CRUD operations:");
                String[] items = {"Update", "Delete car", "Delete all"};
                alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            Intent AdvertCarIntent = new Intent(getApplicationContext(), AdvertCarActivity.class);
                            // Put extras into the Bundle
                            Bundle b = new Bundle();
                            b.putInt("pos", position);
                            b.putString("id", carAdverts.get(position).getCarID());
                            b.putString("make", carAdverts.get(position).getCarMake());
                            b.putString("model", carAdverts.get(position).getCarModel());
                            b.putInt("year", carAdverts.get(position).getCarYear());
                            b.putString("price", Double.toString(carAdverts.get(position).getCarPrice()));
                            b.putString("location", carAdverts.get(position).getCarLocation());
                            b.putString("description", carAdverts.get(position).getCarDescription());
                            AdvertCarIntent.putExtras(b);
                            startActivityForResult(AdvertCarIntent, 0);
                        } else if (i == 1) {
                            // Get advert at clicked position from database
                            DatabaseReference clickedPos = databaseCarAds.child(carAdverts.get(position).getCarID());

                            // Removing advert
                            Log.v("MyLogs", databaseCarAds.child(carAdverts.get(position).getCarID()) + "\n");
                            clickedPos.setValue(null);
                            carAdverts.remove(position);

                            // Notify adapter of changed data
                            adapterCar.notifyDataSetChanged();
                        } else {
                            // Get reference to fashionAds table
                            databaseCarAds = FirebaseDatabase.getInstance().getReference("carAds");

                            // Delete all nodes in the table
                            databaseCarAds.setValue(null);
                            carAdverts.clear();

                            // Notify adapter of changed data
                            adapterCar.notifyDataSetChanged();
                        }
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();

                return true;
            }
        });
        carsView.setVisibility(View.GONE);

        // Open up activity to view individual advert
        // passing the data with Bundle
        carsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent ViewAdvertCarIntent = new Intent(getApplicationContext(), ViewAdvertCarActivity.class);
                // Put extras into the Bundle
                Bundle b = new Bundle();
                AdvertCar advertCar = (AdvertCar) parent.getItemAtPosition(position);
                b.putInt("pos", position);
                b.putString("id", advertCar.getCarID());
                b.putString("image", advertCar.getImageUri());
                b.putString("make", advertCar.getCarMake());
                b.putString("model", advertCar.getCarModel());
                b.putInt("year", advertCar.getCarYear());
                b.putString("price", Double.toString(advertCar.getCarPrice()));
                b.putString("location", advertCar.getCarLocation());
                b.putString("description", advertCar.getCarDescription());
                ViewAdvertCarIntent.putExtras(b);
                // Start ViewAdvertActivity
                startActivityForResult(ViewAdvertCarIntent, 0);
            }
        });

        // Manage which ListView is visible depending on selected radio button
        choice_radio_group.check(R.id.advert_radioButton);
        choice_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.advert_radioButton) {
                    // Clear search box
                    EditTextSearch.setText(null);

                    // if there are no adverts, hide all ListViews
                    // and only show the emptyCategory TextView
                    if (adverts.isEmpty()) {
                        emptyAdvertCategory.setVisibility(View.VISIBLE);
                        productsView.setVisibility(View.GONE);
                        fashionProductsView.setVisibility(View.GONE);
                        carsView.setVisibility(View.GONE);
                    }
                    // if adverts is not empty, show respective ListView
                    // and hide emptyCategory TextView
                    else {
                        emptyAdvertCategory.setVisibility(View.GONE);
                        productsView.setVisibility(View.VISIBLE);
                        fashionProductsView.setVisibility(View.GONE);
                        carsView.setVisibility(View.GONE);
                    }
                } else if (checkedId == R.id.fashionAd_radioButton) {
                    EditTextSearch.setText(null);

                    if (fashionAdverts.isEmpty()) {
                        emptyAdvertCategory.setVisibility(View.VISIBLE);
                        productsView.setVisibility(View.GONE);
                        fashionProductsView.setVisibility(View.GONE);
                        carsView.setVisibility(View.GONE);
                    } else {
                        emptyAdvertCategory.setVisibility(View.GONE);
                        productsView.setVisibility(View.GONE);
                        fashionProductsView.setVisibility(View.VISIBLE);
                        carsView.setVisibility(View.GONE);
                    }
                } else if (checkedId == R.id.carAd_radioButton) {
                    EditTextSearch.setText(null);

                    if (carAdverts.isEmpty()) {
                        emptyAdvertCategory.setVisibility(View.VISIBLE);
                        productsView.setVisibility(View.GONE);
                        fashionProductsView.setVisibility(View.GONE);
                        carsView.setVisibility(View.GONE);
                    } else {
                        emptyAdvertCategory.setVisibility(View.GONE);
                        productsView.setVisibility(View.GONE);
                        fashionProductsView.setVisibility(View.GONE);
                        carsView.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        if (!adverts.isEmpty() || !fashionAdverts.isEmpty() || !carAdverts.isEmpty()) {
            browseEmptyDefaultText.setVisibility(View.GONE);
            choice_radio_group.setVisibility(View.VISIBLE);
        } else {
            choice_radio_group.setVisibility(View.GONE);
        }
    }

    private void firebaseUserSearch(String searchText, String radioSelection) {

        // Firebase search query variable
        Query firebaseSearchQuery;

        switch (radioSelection) {
            case ("General"):
                // Search based on productTitle
                firebaseSearchQuery = databaseAds.orderByChild("productTitle").startAt(searchText).endAt(searchText + "\uf8ff");

                // Set layout and query for FirebaseListAdapter
                FirebaseListOptions<Advert> optionsAd = new FirebaseListOptions.Builder<Advert>()
                        .setLayout(R.layout.row_advert)
                        .setQuery(firebaseSearchQuery, Advert.class)
                        .build();

                // Populate ListView with the search results
                FirebaseListAdapter<Advert> firebaseListAdapterGeneral = new FirebaseListAdapter<Advert>(optionsAd) {
                    @Override
                    protected void populateView(View v, Advert model, int position) {
                        ImageView productImage = v.findViewById(R.id.row_image);
                        TextView tvTitle = v.findViewById(R.id.row_title);
                        TextView tvPrice = v.findViewById(R.id.row_price);
                        TextView tvLocation = v.findViewById(R.id.row_location);

                        try {
                            // Download URL for image from Firebase Storage
                            URL downloadURL = new URL(model.getImageUri());
                            // Load image URL into ImageView
                            Glide
                                    .with(BrowseActivity.this)
                                    .load(downloadURL)
                                    .apply(new RequestOptions()
                                            .centerCrop()
                                            .placeholder(R.mipmap.ic_launcher_round)
                                            .error(R.mipmap.ic_launcher_round))
                                    .into(productImage);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }

                        tvTitle.setText(model.getProductTitle());
                        String productPrice = "€" + model.getProductPrice();
                        tvPrice.setText(productPrice);
                        tvLocation.setText(model.getProductLocation());
                    }
                };
                firebaseListAdapterGeneral.startListening();
                // Bind adapter to ListView
                productsView.setAdapter(firebaseListAdapterGeneral);
                break;

            case ("Fashion"):
                firebaseSearchQuery = databaseFashionAds.orderByChild("productTitle").startAt(searchText).endAt(searchText + "\uf8ff");

                FirebaseListOptions<AdvertFashion> optionsFashion = new FirebaseListOptions.Builder<AdvertFashion>()
                        .setLayout(R.layout.row_advert_fashion)
                        .setQuery(firebaseSearchQuery, AdvertFashion.class)
                        .build();

                FirebaseListAdapter<AdvertFashion> firebaseListAdapterFashion = new FirebaseListAdapter<AdvertFashion>(optionsFashion) {
                    @Override
                    protected void populateView(View v, AdvertFashion model, int position) {
                        ImageView productImage = v.findViewById(R.id.row_image);
                        TextView tvTitle = v.findViewById(R.id.row_title);
                        TextView tvPrice = v.findViewById(R.id.row_price);
                        TextView tvLocation = v.findViewById(R.id.row_location);

                        try {
                            // Download URL for image from Firebase Storage
                            URL downloadURL = new URL(model.getImageUri());
                            // Load image URL into ImageView
                            Glide
                                    .with(BrowseActivity.this)
                                    .load(downloadURL)
                                    .apply(new RequestOptions()
                                            .centerCrop()
                                            .placeholder(R.mipmap.ic_launcher_round)
                                            .error(R.mipmap.ic_launcher_round))
                                    .into(productImage);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }

                        tvTitle.setText(model.getProductTitle());
                        String productPrice = "€" + model.getProductPrice();
                        tvPrice.setText(productPrice);
                        tvLocation.setText(model.getProductLocation());
                    }
                };
                firebaseListAdapterFashion.startListening();
                fashionProductsView.setAdapter(firebaseListAdapterFashion);
                break;

            default:
                firebaseSearchQuery = databaseCarAds.orderByChild("carMake").startAt(searchText).endAt(searchText + "\uf8ff");

                FirebaseListOptions<AdvertCar> optionsCar = new FirebaseListOptions.Builder<AdvertCar>()
                        .setLayout(R.layout.row_advert_car)
                        .setQuery(firebaseSearchQuery, AdvertCar.class)
                        .build();

                FirebaseListAdapter<AdvertCar> firebaseListAdapterCar = new FirebaseListAdapter<AdvertCar>(optionsCar) {
                    @Override
                    protected void populateView(View v, AdvertCar model, int position) {
                        ImageView productImage = v.findViewById(R.id.row_image);
                        TextView tvMake = v.findViewById(R.id.row_make);
                        TextView tvModel = v.findViewById(R.id.row_model);
                        TextView tvYear = v.findViewById(R.id.row_year);
                        TextView tvPrice = v.findViewById(R.id.row_price);

                        try {
                            // Download URL for image from Firebase Storage
                            URL downloadURL = new URL(model.getImageUri());
                            // Load image URL into ImageView
                            Glide
                                    .with(BrowseActivity.this)
                                    .load(downloadURL)
                                    .apply(new RequestOptions()
                                            .centerCrop()
                                            .placeholder(R.mipmap.ic_launcher_round)
                                            .error(R.mipmap.ic_launcher_round))
                                    .into(productImage);
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        }

                        tvMake.setText(model.getCarMake());
                        tvModel.setText(model.getCarModel());
                        tvYear.setText(String.valueOf(model.getCarYear()));
                        String productPrice = "€" + model.getCarPrice();
                        tvPrice.setText(productPrice);
                    }
                };
                firebaseListAdapterCar.startListening();
                carsView.setAdapter(firebaseListAdapterCar);
        }
    }

}
