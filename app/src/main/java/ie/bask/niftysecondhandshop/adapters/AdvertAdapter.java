package ie.bask.niftysecondhandshop.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
import ie.bask.niftysecondhandshop.R;
import ie.bask.niftysecondhandshop.activities.AdvertActivity;
import ie.bask.niftysecondhandshop.activities.BrowseActivity;
import ie.bask.niftysecondhandshop.models.Advert;

public class AdvertAdapter extends ArrayAdapter<Advert> {
    private Context context;
    private List<Advert> adverts;
    private ViewHolder v;


    public AdvertAdapter(Context context, List<Advert> adverts) {
        super(context, R.layout.row_advert, adverts);
        this.context = context;
        this.adverts = adverts;
    }

    static class ViewHolder {
        ImageView productImage;
        TextView productTitle;
        TextView productPrice;
        TextView productLocation;
        TextView productDescription;
    }

    @Override
    public Advert getItem(int position) {
        return adverts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if (view == null) {
            LayoutInflater li = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.row_advert, parent, false);
        } else {
            view = convertView;
        }

        v = new ViewHolder();
        v.productImage = view.findViewById(R.id.row_image);
        v.productTitle = view.findViewById(R.id.row_title);
        v.productPrice = view.findViewById(R.id.row_price);
        v.productLocation = view.findViewById(R.id.row_location);
        v.productDescription = view.findViewById(R.id.row_details);

        final Advert dataSet = adverts.get(position);
        v.productImage.setImageURI(Uri.parse(dataSet.getImageUri()));
        v.productTitle.setText(dataSet.getProductTitle());
        String productPrice = "€" + dataSet.getProductPrice();
        v.productPrice.setText(productPrice);
        v.productLocation.setText(dataSet.getProductLocation());
        v.productDescription.setText(dataSet.getProductDescription());

        return view;
    }

    @Override
    public int getCount() {
        return adverts.size();
    }

}
