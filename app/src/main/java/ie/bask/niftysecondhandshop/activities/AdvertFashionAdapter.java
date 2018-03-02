package ie.bask.niftysecondhandshop.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ie.bask.niftysecondhandshop.R;
import ie.bask.niftysecondhandshop.models.AdvertFashion;

/**
 * Created by cecobask on 28-Feb-18.
 */

public class AdvertFashionAdapter extends ArrayAdapter<AdvertFashion> {

    private Context context;
    private List<AdvertFashion> fashionAdverts;
    private ViewHolder v;

    public AdvertFashionAdapter(Context context, List<AdvertFashion> fashionAdverts) {
        super(context, R.layout.row_advert_fashion, fashionAdverts);
        this.context = context;
        this.fashionAdverts = fashionAdverts;
    }

    static class ViewHolder {
        ImageView productImage;
        TextView productTitle;
        TextView productPrice;
        TextView productType;
        TextView productSize;
        TextView productLocation;
        TextView productDescription;
    }

    @Override
    public AdvertFashion getItem(int position) {
        return fashionAdverts.get(position);
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
            view = li.inflate(R.layout.row_advert_fashion, parent, false);
        } else {
            view = convertView;
        }

        v = new AdvertFashionAdapter.ViewHolder();
        v.productImage = view.findViewById(R.id.row_image);
        v.productTitle = view.findViewById(R.id.row_title);
        v.productPrice = view.findViewById(R.id.row_price);
        v.productType = view.findViewById(R.id.row_type);
        v.productSize = view.findViewById(R.id.row_size);
        v.productLocation = view.findViewById(R.id.row_location);
        v.productDescription = view.findViewById(R.id.row_details);

        final AdvertFashion dataSet = fashionAdverts.get(position);
        v.productImage.setImageURI(dataSet.getImageUri());
        v.productTitle.setText(dataSet.getProductTitle());
        v.productPrice.setText("€" + dataSet.getProductPrice());
        v.productType.setText(dataSet.getProductType());
        v.productSize.setText(dataSet.getProductSize());
        v.productLocation.setText(dataSet.getProductLocation());
        v.productDescription.setText(dataSet.getProductDescription());

        return view;
    }

    @Override
    public int getCount() {
        return fashionAdverts.size();
    }
}
