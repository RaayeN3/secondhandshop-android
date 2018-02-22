package ie.bask.niftysecondhandshop.activities;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ie.bask.niftysecondhandshop.R;
import ie.bask.niftysecondhandshop.models.Advert;

/**
 * Created by cecobask on 20-Feb-18.
 */

class AdvertAdapter extends ArrayAdapter<Advert> {
    private Context context;
    public List<Advert> adverts;


    public AdvertAdapter(Context context, List<Advert> adverts) {
        super(context, R.layout.row_advert, adverts);
        this.context = context;
        this.adverts = adverts;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        convertView = inflater.inflate(R.layout.row_advert, parent, false);
        Advert advert = adverts.get(position);
        ImageView productImage = convertView.findViewById(R.id.row_image);
        TextView productTitle = convertView.findViewById(R.id.row_title);
        TextView productPrice = convertView.findViewById(R.id.row_price);
        TextView productLocation = convertView.findViewById(R.id.row_location);
        TextView productDescription = convertView.findViewById(R.id.row_details);
// temporary fix for productImage, showing an arrow instead of photo taken with camera
        productImage.setImageResource(R.drawable.ic_arrow_down);
        productTitle.setText(advert.getProductTitle());
        productPrice.setText("€" + advert.getProductPrice());
        productLocation.setText(advert.getProductLocation());
        productDescription.setText(advert.getProductDescription());

        return convertView;
    }

    @Override
    public int getCount() {
        return adverts.size();
    }
}
