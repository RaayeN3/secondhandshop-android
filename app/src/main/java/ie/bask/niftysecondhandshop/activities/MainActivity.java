package ie.bask.niftysecondhandshop.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;

import ie.bask.niftysecondhandshop.R;

public class MainActivity extends Base {

    private Button sellButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sellButton = findViewById(R.id.sellButton);
        if (sellButton != null) {
            Log.v("MyLogs", "Sell button initialised!");
        }
    }

}
