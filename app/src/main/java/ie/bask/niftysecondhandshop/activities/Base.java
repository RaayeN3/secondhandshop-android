package ie.bask.niftysecondhandshop.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import ie.bask.niftysecondhandshop.R;

/**
 * Created by cecobask on 15-Feb-18.
 */

public class Base extends AppCompatActivity {


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

//        if(donations.isEmpty())
//            report.setEnabled(false);
//        else
//            report.setEnabled(true);

        if (this instanceof MainActivity) {
            home.setVisible(false);
        } else if (this instanceof SellActivity) {
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
        startActivity(new Intent(this, SellActivity.class));
    }

    public void browse(MenuItem item) {
        startActivity(new Intent(this, BrowseActivity.class));
    }

    public void settings(MenuItem item) {
        Toast.makeText(this, "Settings Selected", Toast.LENGTH_SHORT).show();
    }

    public void sellButtonPressed(View view) {
        startActivity(new Intent(this, SellActivity.class));
    }

    public void homeButtonPressed(View view) {
        startActivity(new Intent(this, MainActivity.class));
    }

    public void settingsButtonPressed(View view) {
        Toast.makeText(this, "Settings Selected", Toast.LENGTH_SHORT).show();
    }
}
