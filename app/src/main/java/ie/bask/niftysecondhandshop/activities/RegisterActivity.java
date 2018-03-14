package ie.bask.niftysecondhandshop.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import ie.bask.niftysecondhandshop.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    // Widgets
    private EditText editTextEmail;
    private EditText editTextPassword;
    private Button buttonRegister;
    private Button buttonLogin;
    private ProgressDialog progressDialog;

    // Defining Firebaseauth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialising firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            // That means user is already logged in
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        // Initialising widgets
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonLogin = findViewById(R.id.buttonLogin);
        buttonRegister = findViewById(R.id.buttonRegister);
        progressDialog = new ProgressDialog(this);

        // Set onClick listeners for the buttons
        buttonRegister.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);
    }

    private void registerUser() {

        // Get input values from widgets
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        // Checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            editTextEmail.setError("Email is required!");
            editTextEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Password is required!");
            editTextPassword.requestFocus();
            return;
        }

        // If the email and password are not empty
        // display a progress dialog

        progressDialog.setMessage("Registering, please wait...");
        progressDialog.show();

        // Creating a new user
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // Checking if successful
                        if (task.isSuccessful()) {
                            finish();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            Toast.makeText(RegisterActivity.this, "Registration error", Toast.LENGTH_LONG).show();
                        }
                        progressDialog.dismiss();
                    }
                });
    }


    /**
     * Handle onClick events
     */
    @Override
    public void onClick(View view) {

        if (view == buttonRegister) {
            registerUser();
        }

        if (view == buttonLogin) {
            // Open login activity when user clicks on Login button
            startActivity(new Intent(this, LoginActivity.class));
        }

    }
}