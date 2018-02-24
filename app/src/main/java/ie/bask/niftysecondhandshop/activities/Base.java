package ie.bask.niftysecondhandshop.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import ie.bask.niftysecondhandshop.R;
import ie.bask.niftysecondhandshop.models.Advert;

/**
 * Created by cecobask on 15-Feb-18.
 */

public class Base extends AppCompatActivity {

    public static List<Advert> adverts = new ArrayList<>();

    public void newAdvert(Advert advert) {
        adverts.add(advert);
        Toast.makeText(this, "You added a new Advert object!", Toast.LENGTH_SHORT).show();
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

        if (adverts.isEmpty()) {
            browse.setEnabled(false);
        } else
            browse.setEnabled(true);

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

    public void browseButtonPressed(View view) {
        startActivity(new Intent(this, BrowseActivity.class));
    }
}
