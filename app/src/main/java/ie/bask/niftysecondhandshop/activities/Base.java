package ie.bask.niftysecondhandshop.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import ie.bask.niftysecondhandshop.R;
import ie.bask.niftysecondhandshop.models.Advert;
import ie.bask.niftysecondhandshop.models.AdvertCar;
import ie.bask.niftysecondhandshop.models.AdvertFashion;
import pl.tajchert.nammu.Nammu;
import pl.tajchert.nammu.PermissionCallback;

/**
 * Created by cecobask on 15-Feb-18.
 */

public class Base extends AppCompatActivity {

    // Our database reference objects
    public static DatabaseReference databaseAds;
    public static DatabaseReference databaseFashionAds;
    public static DatabaseReference databaseCarAds;

    // Firebase auth object
    public static FirebaseAuth firebaseAuth;

    // ArrayLists storing objects of type Advert, AdvertFashion and AdvertCar
    public static List<Advert> adverts = new ArrayList<>();
    public static List<AdvertFashion> fashionAdverts = new ArrayList<>();
    public static List<AdvertCar> carAdverts = new ArrayList<>();

    // Widgets shared across multiple Activities
    public EditText productTitle;
    public ScrollableNumberPicker snp_horizontal;
    public EditText priceManual;
    public Spinner locationSpinner;
    public ImageView advertImage;
    public Button submitButton;
    public EditText productDetails;

    // Bitmap to temporary store image captured with the camera
    public Bitmap bitmap;
    public static final int CAMERA_PIC_REQUEST = 1111;


    /**
     * Adding new object to the ArrayList
     * of the respective type (Advert/AdvertFashion/AdvertCar)
     */
    public void newAdvert(Advert advert) {
        // Adding the advert to Firebase Database
        databaseAds.child(advert.getProductID()).setValue(advert);
        Toast.makeText(this, "You added a new Advert object!", Toast.LENGTH_SHORT).show();
    }

    public void newAdvertFashion(AdvertFashion fashionAdvert) {
        // Adding the advert to Firebase Database
        databaseFashionAds.child(fashionAdvert.getProductID()).setValue(fashionAdvert);
        Toast.makeText(this, "You added a new Fashion Advert object!", Toast.LENGTH_SHORT).show();
    }

    public void newAdvertCar(AdvertCar carAdvert) {
        // Adding the advert to Firebase Database
        databaseCarAds.child(carAdvert.getCarID()).setValue(carAdvert);
        Toast.makeText(this, "You added a new Car Advert object!", Toast.LENGTH_SHORT).show();
    }


    /**
     * Inflate the menu across every Activity
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    /**
     * Conditional Menu navigation
     */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem home = menu.findItem(R.id.homeMenu);
        MenuItem sell = menu.findItem(R.id.sellMenu);
        MenuItem browse = menu.findItem(R.id.browseMenu);
        MenuItem reset = menu.findItem(R.id.reset);

        if (adverts.isEmpty() && fashionAdverts.isEmpty() && carAdverts.isEmpty()) {
            browse.setEnabled(false);
            reset.setEnabled(false);
        } else {
            browse.setEnabled(true);
            reset.setEnabled(true);
        }


        if (this instanceof MainActivity) {
            home.setVisible(false);
        } else if (this instanceof AdvertActivity || this instanceof AdvertFashionActivity || this instanceof AdvertCarActivity) {
            sell.setVisible(false);
        } else {
            browse.setVisible(false);
            sell.setVisible(true);
            home.setVisible(true);
        }

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_logout) {
            //logging out the user
            firebaseAuth.signOut();
            //closing activity
            finishAffinity();
            Intent backToLogin = new Intent(getApplicationContext(), LoginActivity.class);
            backToLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(backToLogin);
        }
        return super.onOptionsItemSelected(item);
    }


    /** Methods used by side menu
     */
    public void home(MenuItem item) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void sell(MenuItem item) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Choose type of Advert");
        String[] items = {"General advert", "Fashion advert", "Car advert"};
        alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // if General Advert
                if (i == 0) {
                    startActivity(new Intent(getApplicationContext(), AdvertActivity.class));
                }
                // if Fashion Advert
                else if (i == 1) {
                    startActivity(new Intent(getApplicationContext(), AdvertFashionActivity.class));
                }
                // if Car Advert
                else {
                    startActivity(new Intent(getApplicationContext(), AdvertCarActivity.class));
                }
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void browse(MenuItem item) {
        startActivity(new Intent(this, BrowseActivity.class));
    }

    public void reset(MenuItem item) {
        databaseAds.removeValue();
        databaseFashionAds.removeValue();
        databaseCarAds.removeValue();
        Toast.makeText(this, "All adverts deleted", Toast.LENGTH_SHORT).show();
    }


    /** Methods used by bottom_menu onClick
     */
    public void sellButtonPressed(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Choose type of Advert");
        String[] items = {"General advert", "Fashion advert", "Car advert"};
        alertDialogBuilder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (i == 0) {
                    startActivity(new Intent(getApplicationContext(), AdvertActivity.class));
                } else if (i == 1) {
                    startActivity(new Intent(getApplicationContext(), AdvertFashionActivity.class));
                } else {
                    startActivity(new Intent(getApplicationContext(), AdvertCarActivity.class));
                }
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void homeButtonPressed(View view) {
        if (this instanceof MainActivity) {
            view.setEnabled(false);
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public void browseButtonPressed(View view) {
        // Check if ArrayLists are empty
        if (adverts.isEmpty() && fashionAdverts.isEmpty() && carAdverts.isEmpty()) {
            // if empty disable browse button
            view.setEnabled(false);
        } else {
            startActivity(new Intent(this, BrowseActivity.class));
        }
    }


    /** Request permission for writing on External Storage of device
     */
    public void permissionCheck() {
        int permissionCheckExtStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheckExtStorage != PackageManager.PERMISSION_GRANTED) {
            Nammu.askForPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionCallback() {
                @Override
                public void permissionGranted() {

                }

                @Override
                public void permissionRefused() {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                }
            });
        }
    }


    /** Start the camera
     */
    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_PIC_REQUEST);
    }


    /** Get the URI path of passed Bitmap image
     */
    public String getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
        return path;
    }


    /** Receive captured image from camera and store it as Bitmap
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            advertImage.setImageBitmap(bitmap);
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }


    /**
     * Firebase Database loading methods
     */
    public void loadAdverts() {
        // Getting the reference of Adverts node
        databaseAds = FirebaseDatabase.getInstance().getReference("ads");
        // Attaching value event listener
        databaseAds.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Clearing the previous Adverts list
                adverts.clear();

                // Iterating through all the nodes
                for (DataSnapshot advertSnapshot : dataSnapshot.getChildren()) {
                    // Getting Advert
                    Advert advert = advertSnapshot.getValue(Advert.class);
                    // Adding Advert to the list
                    adverts.add(advert);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadFashionAdverts() {
        // Getting the reference of Adverts node
        databaseFashionAds = FirebaseDatabase.getInstance().getReference("fashionAds");
        // Attaching value event listener
        databaseFashionAds.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Clearing the previous Adverts list
                fashionAdverts.clear();

                for (DataSnapshot fashionAdsSnapshot : dataSnapshot.getChildren()) {
                    AdvertFashion fAdvert = fashionAdsSnapshot.getValue(AdvertFashion.class);
                    fashionAdverts.add(fAdvert);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void loadCarAdverts() {
        // Getting the reference of Adverts node
        databaseCarAds = FirebaseDatabase.getInstance().getReference("carAds");
        // Attaching value event listener
        databaseCarAds.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // Clearing the previous Adverts list
                carAdverts.clear();

                for (DataSnapshot carAdsSnapshot : dataSnapshot.getChildren()) {
                    AdvertCar cAdvert = carAdsSnapshot.getValue(AdvertCar.class);
                    carAdverts.add(cAdvert);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}