package ie.bask.niftysecondhandshop.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ie.bask.niftysecondhandshop.R;
import ie.bask.niftysecondhandshop.models.Advert;
import ie.bask.niftysecondhandshop.models.AdvertCar;
import ie.bask.niftysecondhandshop.models.AdvertFashion;

public class MainActivity extends Base implements View.OnClickListener {


    // Declaring SharedPreferences
    private SharedPreferences prefs = null;

    // Widgets
    private TextView textViewUserEmail;
    private Button sellButton;
    private ImageButton githubButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialising SharedPreferences
        prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);

        // Check if this is the first time the app is running
        if (prefs.getBoolean("firstRun", true)) {
            fashionAdverts.add(new AdvertFashion("default", "Adidas Superstar", 70, "Shoes", "43", "Dunmore East", "Got them as a present, but they didn't fit me..."));
            adverts.add(new Advert("default", "iPhone 7", 600, "Tramore", "Perfect condition. Looks like new! :)"));
            carAdverts.add(new AdvertCar("default", "Toyota", "Prius", 2012, 5000, "Waterford", "Car is well-kept. Mileage is only 50,000!"));
            // Set firstRun to false
            prefs.edit().putBoolean("firstRun", false).apply();
        }
        // If this isn't the first time the app is running, load data from SharedPreferences
        else {
            adverts = loadAdvertList();
            fashionAdverts = loadAdvertFashionList();
            carAdverts = loadAdvertCarList();
        }

        // Initialising widgets
        textViewUserEmail = findViewById(R.id.textViewUserEmail);
        sellButton = findViewById(R.id.sellButton);
        githubButton = findViewById(R.id.githubButton);
        githubButton.setOnClickListener(this);
        sellButton.setOnClickListener(this);

        // Initialising Firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        // Getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        // If the user is not logged in
        // that means current user will return null
        if (user == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        // Displaying logged in user name
        String welcomeUser = "Welcome, " + user.getEmail() + "!";
        textViewUserEmail.setText(welcomeUser);
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


    /**
     * Handle onClick events
     */
    @Override
    public void onClick(View v) {
        if (v == sellButton) {
            sellButtonPressed(v);
        } else {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/cecobask"));
            startActivity(browserIntent);
        }
    }

}

