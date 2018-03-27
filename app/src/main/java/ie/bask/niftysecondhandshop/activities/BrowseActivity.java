package ie.bask.niftysecondhandshop.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ie.bask.niftysecondhandshop.R;
import ie.bask.niftysecondhandshop.adapters.AdvertAdapter;
import ie.bask.niftysecondhandshop.adapters.AdvertCarAdapter;
import ie.bask.niftysecondhandshop.adapters.AdvertFashionAdapter;

public class BrowseActivity extends Base {

    // Widgets
    ListView productsView;
    ListView fashionProductsView;
    ListView carsView;
    TextView browseEmptyDefaultText;
    RadioGroup choice_radio_group;
    TextView emptyAdvertCategory;

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
        // Bind adapter to ListView
        final AdvertAdapter adapter = new AdvertAdapter(this, adverts);
        productsView.setAdapter(adapter);
        // Display AlertDialog with CRUD operations when the user clicks on any position
        productsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, final View view, final int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BrowseActivity.this);
                alertDialogBuilder.setTitle("CRUD operations:");
                String[] items = {"Update", "Delete product", "Delete all"};
                alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Update Advert at clicked position
                        if (i == 0) {
                            Intent AdvertIntent = new Intent(getApplicationContext(), AdvertActivity.class);
                            // Put extras into the Bundle
                            Bundle b = new Bundle();
                            b.putInt("pos", position);
                            b.putString("id", adverts.get(position).getProductID());
                            b.putString("title", adverts.get(position).getProductTitle());
                            b.putString("price", Double.toString(adverts.get(position).getProductPrice()));
                            b.putString("location", adverts.get(position).getProductLocation());
                            b.putString("description", adverts.get(position).getProductDescription());
                            AdvertIntent.putExtras(b);
                            startActivityForResult(AdvertIntent, 0);
                        }
                        // Delete the Advert at clicked position
                        else if (i == 1) {
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
            }
        });
        // Hide ListView by default
        productsView.setVisibility(View.GONE);

        fashionProductsView = findViewById(R.id.fashionProductsView);
        // Bind adapter to ListView
        final AdvertFashionAdapter adapterFashion = new AdvertFashionAdapter(this, fashionAdverts);
        fashionProductsView.setAdapter(adapterFashion);
        // Display AlertDialog with CRUD operations when the user clicks on any position
        fashionProductsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
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
            }
        });
        // Hide ListView by default
        fashionProductsView.setVisibility(View.GONE);

        carsView = findViewById(R.id.carsView);
        final AdvertCarAdapter adapterCar = new AdvertCarAdapter(this, carAdverts);
        carsView.setAdapter(adapterCar);
        carsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
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
            }
        });
        carsView.setVisibility(View.GONE);

        // Manage which ListView is visible depending on selected radio button
        choice_radio_group = findViewById(R.id.choice_radio_group);
        choice_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.advert_radioButton) {
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

        browseEmptyDefaultText = findViewById(R.id.browseEmptyDefaultText);
        if (!adverts.isEmpty() || !fashionAdverts.isEmpty() || !carAdverts.isEmpty()) {
            browseEmptyDefaultText.setVisibility(View.GONE);
            choice_radio_group.setVisibility(View.VISIBLE);
        } else {
            choice_radio_group.setVisibility(View.GONE);
        }
    }

}
