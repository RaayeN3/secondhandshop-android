package ie.bask.niftysecondhandshop.activities;

import android.content.Intent;
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

public class MainActivity extends Base implements View.OnClickListener {

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
     * Load adverts from Firebase Database when onStart state is triggered
     */
    @Override
    protected void onStart() {
        super.onStart();
        loadAdverts();
        loadFashionAdverts();
        loadCarAdverts();
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