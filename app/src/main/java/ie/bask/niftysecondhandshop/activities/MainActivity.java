package ie.bask.niftysecondhandshop.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ie.bask.niftysecondhandshop.R;
import ie.bask.niftysecondhandshop.models.Advert;
import ie.bask.niftysecondhandshop.models.AdvertCar;
import ie.bask.niftysecondhandshop.models.AdvertFashion;

public class MainActivity extends Base implements View.OnClickListener {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout;
    private Button sellButton;
    private SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        prefs = getSharedPreferences("myPrefs", MODE_PRIVATE);
        if (prefs.getBoolean("firstRun", true)) {
            fashionAdverts.add(new AdvertFashion("default", "Adidas Superstar", 70, "Shoes", "43", "Dunmore East", "Got them as a present, but they didn't fit me..."));
            adverts.add(new Advert("default", "iPhone 7", 600, "Tramore", "Perfect condition. Looks like new! :)"));
            carAdverts.add(new AdvertCar("default", "Toyota", "Prius", 2012, 5000, "Waterford", "Car is well-kept. Mileage is only 50,000!"));
            prefs.edit().putBoolean("firstRun", false).apply();
        } else {
            adverts = loadAdvertList();
            fashionAdverts = loadAdvertFashionList();
            carAdverts = loadAdvertCarList();
        }

        sellButton = findViewById(R.id.sellButton);
        sellButton.setOnClickListener(this);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //if the user is not logged in
        //that means current user will return null
        if (user == null) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

        //initializing views
        textViewUserEmail = findViewById(R.id.textViewUserEmail);
        buttonLogout = findViewById(R.id.buttonLogout);

        //displaying logged in user name
        String welcomeUser = "Welcome " + user.getEmail();
        textViewUserEmail.setText(welcomeUser);

        //adding listener to button
        buttonLogout.setOnClickListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveAdvertList();
        saveAdvertFashionList();
        saveAdvertCarList();
    }

    @Override
    public void onClick(View v) {
        if (v == buttonLogout) {
            //logging out the user
            firebaseAuth.signOut();
            //closing activity
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        } else if (v == sellButton) {
            sellButtonPressed(v);
        }
    }
}

