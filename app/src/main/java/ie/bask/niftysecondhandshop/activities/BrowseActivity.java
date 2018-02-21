package ie.bask.niftysecondhandshop.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import ie.bask.niftysecondhandshop.R;

public class BrowseActivity extends Base {

    ListView productsView;

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
    }
}


