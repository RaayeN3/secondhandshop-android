package ie.bask.niftysecondhandshop.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ie.bask.niftysecondhandshop.R;
import ie.bask.niftysecondhandshop.models.AdvertCar;

/**
 * Created by cecobask on 04-Mar-18.
 */

public class AdvertCarAdapter extends ArrayAdapter<AdvertCar> {
    private Context context;
    private List<AdvertCar> carAdverts;
    private ViewHolder v;


    public AdvertCarAdapter(Context context, List<AdvertCar> carAdverts) {
        super(context, R.layout.row_advert_car, carAdverts);
        this.context = context;
        this.carAdverts = carAdverts;
    }

    static class ViewHolder {
        ImageView carImage;
        TextView carMake;
        TextView carModel;
        TextView carYear;
        TextView carPrice;
        TextView carLocation;
        TextView carDescription;
    }

    @Override
    public AdvertCar getItem(int position) {
        return carAdverts.get(position);
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
            view = li.inflate(R.layout.row_advert_car, parent, false);
        } else {
            view = convertView;
        }

        v = new ViewHolder();
        v.carImage = view.findViewById(R.id.row_image);
        v.carMake = view.findViewById(R.id.row_make);
        v.carModel = view.findViewById(R.id.row_model);
        v.carYear = view.findViewById(R.id.row_year);
        v.carPrice = view.findViewById(R.id.row_price);
        v.carLocation = view.findViewById(R.id.row_location);
        v.carDescription = view.findViewById(R.id.row_details);

        final AdvertCar dataSet = carAdverts.get(position);
        if (dataSet.getImageUri().equals("default")) {
            v.carImage.setImageResource(R.drawable.ic_car);
        } else {
            v.carImage.setImageURI(Uri.parse(dataSet.getImageUri()));
        }
        v.carMake.setText(dataSet.getCarMake());
        v.carModel.setText(dataSet.getCarModel());
        v.carYear.setText(String.valueOf(dataSet.getCarYear()));
        String carPrice = "€" + dataSet.getCarPrice();
        v.carPrice.setText(carPrice);
        v.carLocation.setText(dataSet.getCarLocation());
        v.carDescription.setText(dataSet.getCarDescription());

        return view;
    }

    @Override
    public int getCount() {
        return carAdverts.size();
    }

}

