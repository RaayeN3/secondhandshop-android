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
import ie.bask.niftysecondhandshop.models.Advert;
import ie.bask.niftysecondhandshop.models.AdvertCar;
import ie.bask.niftysecondhandshop.models.AdvertFashion;

public class BrowseActivity extends Base {

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

        adverts = loadAdvertList();
        fashionAdverts = loadAdvertFashionList();
        carAdverts = loadAdvertCarList();


        emptyAdvertCategory = findViewById(R.id.emptyAdvertCategory);
        emptyAdvertCategory.setVisibility(View.GONE);

        productsView = findViewById(R.id.productsView);
        final AdvertAdapter adapter = new AdvertAdapter(this, adverts);
        productsView.setAdapter(adapter);
        productsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BrowseActivity.this);
                alertDialogBuilder.setTitle("CRUD operations:");
                String[] items = {"Create", "Update", "Delete product", "Delete all"};
                alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            startActivity(new Intent(getApplicationContext(), AdvertActivity.class));
                        } else if (i == 1) {
                            Intent AdvertIntent = new Intent(getApplicationContext(), AdvertActivity.class);
                            AdvertIntent.putExtra("pos", position);
                            startActivityForResult(AdvertIntent, 0);
                            adapter.notifyDataSetChanged();
                        } else if (i == 2) {
                            adverts.remove(position);
                            saveAdvertList();
                            adapter.notifyDataSetChanged();
                        } else {
                            adverts.clear();
                            saveAdvertList();
                            adapter.notifyDataSetChanged();
                        }
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        productsView.setVisibility(View.GONE);

        fashionProductsView = findViewById(R.id.fashionProductsView);
        final AdvertFashionAdapter adapterFashion = new AdvertFashionAdapter(this, fashionAdverts);
        fashionProductsView.setAdapter(adapterFashion);
        fashionProductsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(BrowseActivity.this);
                alertDialogBuilder.setTitle("CRUD operations:");
                String[] items = {"Create", "Update", "Delete product", "Delete all"};
                alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            startActivity(new Intent(getApplicationContext(), AdvertFashionActivity.class));
                        } else if (i == 1) {
                            Intent AdvertFashionIntent = new Intent(getApplicationContext(), AdvertFashionActivity.class);
                            AdvertFashionIntent.putExtra("pos", position);
                            startActivityForResult(AdvertFashionIntent, 0);
                            adapterFashion.notifyDataSetChanged();
                        } else if (i == 2) {
                            fashionAdverts.remove(position);
                            saveAdvertFashionList();
                            adapterFashion.notifyDataSetChanged();
                        } else {
                            fashionAdverts.clear();
                            saveAdvertFashionList();
                            adapterFashion.notifyDataSetChanged();
                        }
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
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
                            adapterCar.notifyDataSetChanged();
                        } else if (i == 2) {
                            carAdverts.remove(position);
                            saveAdvertCarList();
                            adapterCar.notifyDataSetChanged();
                        } else {
                            carAdverts.clear();
                            saveAdvertCarList();
                            adapterCar.notifyDataSetChanged();
                        }
                    }
                });
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
            }
        });
        carsView.setVisibility(View.GONE);

        choice_radio_group = findViewById(R.id.choice_radio_group);
        choice_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.advert_radioButton) {
                    if (adverts.isEmpty()) {
                        emptyAdvertCategory.setVisibility(View.VISIBLE);
                        productsView.setVisibility(View.GONE);
                        fashionProductsView.setVisibility(View.GONE);
                        carsView.setVisibility(View.GONE);
                    } else {
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

    protected void onPause() {
        super.onPause();
        saveAdvertList();
        saveAdvertFashionList();
        saveAdvertCarList();
    }
}
