package ie.bask.niftysecondhandshop.activities;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;

import ie.bask.niftysecondhandshop.R;

public class MainActivity extends Base {

    private Button sellButton;
    private Button homeButton;
    private Button cameraButton;
    private Button settingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sellButton = findViewById(R.id.sellButton);
        homeButton = findViewById(R.id.homeButton);
        cameraButton = findViewById(R.id.cameraButton);
        settingsButton = findViewById(R.id.settingsButton);
        if (sellButton != null) {
            Log.v("ShopMainActivity", "Sell button initialised!");
        }
    }

}
