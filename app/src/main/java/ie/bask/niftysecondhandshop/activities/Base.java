package ie.bask.niftysecondhandshop.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.preference.PreferenceManager;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Type;
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

    public static List<Advert> adverts = new ArrayList<>();
    public static List<AdvertFashion> fashionAdverts = new ArrayList<>();
    public static List<AdvertCar> carAdverts = new ArrayList<>();
    public EditText productTitle;
    public ScrollableNumberPicker snp_horizontal;
    public EditText priceManual;
    public Spinner locationSpinner;
    public ImageView advertImage;
    public Bitmap bitmap;
    public static final int CAMERA_PIC_REQUEST = 1111;
    public Button submitButton;
    public EditText productDetails;

    public void newAdvert(Advert advert) {
        adverts.add(advert);
        Toast.makeText(this, "You added a new Advert object!", Toast.LENGTH_SHORT).show();
    }

    public void newAdvertFashion(AdvertFashion fashionAdvert) {
        fashionAdverts.add(fashionAdvert);
        Toast.makeText(this, "You added a new Fashion Advert object!", Toast.LENGTH_SHORT).show();
    }

    public void newAdvertCar(AdvertCar carAdvert) {
        carAdverts.add(carAdvert);
        Toast.makeText(this, "You added a new Car Advert object!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem home = menu.findItem(R.id.homeMenu);
        MenuItem sell = menu.findItem(R.id.sellMenu);
        MenuItem browse = menu.findItem(R.id.browseMenu);

        if (adverts.isEmpty() && fashionAdverts.isEmpty() && carAdverts.isEmpty()) {
            browse.setEnabled(false);
        } else
            browse.setEnabled(true);

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

    public void browse(MenuItem item) {
        startActivity(new Intent(this, BrowseActivity.class));
    }

    public void settings(MenuItem item) {
        Toast.makeText(this, "Settings Selected", Toast.LENGTH_SHORT).show();
    }

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
        if (adverts.isEmpty() && fashionAdverts.isEmpty() && carAdverts.isEmpty()) {
            view.setEnabled(false);
        } else {
            startActivity(new Intent(this, BrowseActivity.class));
        }
    }

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

    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_PIC_REQUEST);
    }


    public String getImageUri(Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(), inImage, "Title", null);
        return path;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST && resultCode == RESULT_OK) {
            bitmap = (Bitmap) data.getExtras().get("data");
            advertImage.setImageBitmap(bitmap);
            getImageUri(bitmap);
        } else {
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    public void saveAdvertList() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(adverts);
        editor.putString("1", json);
        editor.apply();
    }

    public List<Advert> loadAdvertList() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = prefs.getString("1", null);
        Type type = new TypeToken<List<Advert>>() {
        }.getType();
        adverts = gson.fromJson(json, type);
        return adverts;
    }

    public void saveAdvertFashionList() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(fashionAdverts);
        editor.putString("2", json);
        editor.apply();
    }

    public List<AdvertFashion> loadAdvertFashionList() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = prefs.getString("2", null);
        Type type = new TypeToken<List<AdvertFashion>>() {
        }.getType();
        fashionAdverts = gson.fromJson(json, type);
        return fashionAdverts;
    }

    public void saveAdvertCarList() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = prefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(carAdverts);
        editor.putString("3", json);
        editor.apply();
    }

    public List<AdvertCar> loadAdvertCarList() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        Gson gson = new Gson();
        String json = prefs.getString("3", null);
        Type type = new TypeToken<List<AdvertCar>>() {
        }.getType();
        carAdverts = gson.fromJson(json, type);
        return carAdverts;
    }

}