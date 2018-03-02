package ie.bask.niftysecondhandshop.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import ie.bask.niftysecondhandshop.R;

public class BrowseActivity extends Base {

    ListView productsView;
    ListView fashionProductsView;
    TextView browseEmptyDefaultText;
    RadioGroup choice_radio_group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        productsView = findViewById(R.id.productsView);
        AdvertAdapter adapter = new AdvertAdapter(this, adverts);
        productsView.setAdapter(adapter);
        productsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(BrowseActivity.this, "You Selected Row [" + position + "] For Advert Data "
                        + adverts.get(position), Toast.LENGTH_LONG).show();
            }
        });
        productsView.setVisibility(View.GONE);

        fashionProductsView = findViewById(R.id.fashionProductsView);
        AdvertFashionAdapter adapterFashion = new AdvertFashionAdapter(this, fashionAdverts);
        fashionProductsView.setAdapter(adapterFashion);
        fashionProductsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(BrowseActivity.this, "You Selected Row [" + position + "] For AdvertFashion Data "
                        + fashionAdverts.get(position), Toast.LENGTH_LONG).show();
            }
        });
        fashionProductsView.setVisibility(View.GONE);

        choice_radio_group = findViewById(R.id.choice_radio_group);
        choice_radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.advert_radioButton) {
                    productsView.setVisibility(View.VISIBLE);
                    fashionProductsView.setVisibility(View.GONE);
                } else if (checkedId == R.id.fashionAd_radioButton) {
                    productsView.setVisibility(View.GONE);
                    fashionProductsView.setVisibility(View.VISIBLE);
                }
            }
        });


        browseEmptyDefaultText = findViewById(R.id.browseEmptyDefaultText);
        if (!adverts.isEmpty() || !fashionAdverts.isEmpty()) {
            browseEmptyDefaultText.setVisibility(View.GONE);
            choice_radio_group.setVisibility(View.VISIBLE);
        } else {
            choice_radio_group.setVisibility(View.GONE);
        }
    }
}


