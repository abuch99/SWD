package in.ac.bits_hyderabad.swd.swd.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.ac.bits_hyderabad.swd.swd.R;

public class GoodiesAdapter extends BaseAdapter {

    private ArrayList<Goodies> goodies;
    LayoutInflater mInflater;

    public GoodiesAdapter(Context context, ArrayList<Goodies> goodies) {
        mInflater = LayoutInflater.from(context);
        this.goodies = goodies;
    }

    @Override
    public int getCount() {
        return goodies.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
            convertView = mInflater.inflate(R.layout.card_goodies, parent, false);

        //TODO: Implement on click listener to take user to goodie details and place an order

        String ImageUrl = "http://swd.bits-hyderabad.ac.in/goodies/img/" + goodies.get(position).getImage();//"All.jpg"  or   goodies.get(i).getImage()

        TextView tvGoodieName = convertView.findViewById(R.id.tvGoodieName);
        ImageView ivGoodie = convertView.findViewById(R.id.ivGoodie);
        TextView tvGoodiePrice = convertView.findViewById(R.id.tvGoodiePrice);
        TextView tvGoodieHost = convertView.findViewById(R.id.tvGoodieHost);

        Picasso.get().load(ImageUrl)
                .placeholder(R.drawable.ic_loading)
                .error(R.drawable.ic_error)
                .fit().centerInside()
                .into(ivGoodie);

        tvGoodieName.setText(goodies.get(position).getName());
        tvGoodieHost.setText(goodies.get(position).getHost());
        tvGoodiePrice.setText(goodies.get(position).getPrice());

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

}
