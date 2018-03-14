package ie.bask.niftysecondhandshop.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;

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
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BrowseActivity.this);
                alertDialogBuilder.setTitle("CRUD operations:");
                String[] items = {"Create", "Update", "Delete product", "Delete all"};
                alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Create new Advert
                        if (i == 0) {
                            startActivity(new Intent(getApplicationContext(), AdvertActivity.class));
                        }
                        // Update Advert at clicked position
                        else if (i == 1) {
                            Intent AdvertIntent = new Intent(getApplicationContext(), AdvertActivity.class);
                            // Put position as an extra into the Bundle
                            AdvertIntent.putExtra("pos", position);
                            startActivityForResult(AdvertIntent, 0);
                        }
                        // Delete the Advert at clicked position
                        else if (i == 2) {
                            adverts.remove(position);
                            // Notify adapter of changed data
                            adapter.notifyDataSetChanged();
                        }
                        // Delete all Adverts
                        else {
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
                String[] items = {"Create", "Update", "Delete product", "Delete all"};
                alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Create new AdvertFashion
                        if (i == 0) {
                            startActivity(new Intent(getApplicationContext(), AdvertFashionActivity.class));
                        }
                        // Update AdvertFashion at clicked position
                        else if (i == 1) {
                            Intent AdvertFashionIntent = new Intent(getApplicationContext(), AdvertFashionActivity.class);
                            // Put position as an extra into the Bundle
                            AdvertFashionIntent.putExtra("pos", position);
                            startActivityForResult(AdvertFashionIntent, 0);
                        }
                        // Delete the AdvertFashion at clicked position
                        else if (i == 2) {
                            fashionAdverts.remove(position);
                            // Notify adapter of changed data
                            adapterFashion.notifyDataSetChanged();
                        }
                        // Delete all AdvertFashion's
                        else {
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
                String[] items = {"Create", "Update", "Delete car", "Delete all"};
                alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            startActivity(new Intent(getApplicationContext(), AdvertCarActivity.class));
                        } else if (i == 1) {
                            Intent AdvertCarIntent = new Intent(getApplicationContext(), AdvertCarActivity.class);
                            AdvertCarIntent.putExtra("pos", position);
                            startActivityForResult(AdvertCarIntent, 0);
                        } else if (i == 2) {
                            carAdverts.remove(position);
                            adapterCar.notifyDataSetChanged();
                        } else {
                            carAdverts.clear();
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


    /**
     * Save adverts to SharedPreferences when onPause state is triggered
     */
    @Override
    protected void onPause() {
        super.onPause();
        saveAdvertList();
        saveAdvertFashionList();
        saveAdvertCarList();
    }

}
