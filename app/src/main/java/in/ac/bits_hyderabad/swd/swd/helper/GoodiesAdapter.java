package in.ac.bits_hyderabad.swd.swd.helper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.Permission;
import java.util.ArrayList;

import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.databases.GoodiesTable;

public class GoodiesAdapter extends RecyclerView.Adapter<GoodiesAdapter.ViewHolder> {

    private ArrayList<Goodies> goodies;

    itemClicked activity;
    public interface itemClicked{
        void onItemClicked(int index);
    }

    public GoodiesAdapter(Context context,ArrayList<Goodies> goodies)
    {
        this.goodies=goodies;
        activity=(itemClicked)context;
    }

    @NonNull
    @Override
    public GoodiesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.goodies_card,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull GoodiesAdapter.ViewHolder viewHolder, int i) {
        viewHolder.itemView.setTag(goodies.get(i));
        viewHolder.tvGoodieName.setText(goodies.get(i).getName());
        viewHolder.tvGoodieHost.setText(goodies.get(i).getHost());
        viewHolder.tvGoodiePrice.setText(goodies.get(i).getPrice());
        viewHolder.tvGoodieHosterName.setText(goodies.get(i).getHost_name());
        viewHolder.tvGoodieHosterMobile.setText(goodies.get(i).getMobile());
    }

    @Override
    public int getItemCount() {
        return goodies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivGoodie;
        TextView tvGoodieName,tvGoodieHost,tvGoodiePrice,tvGoodieHosterName,tvGoodieHosterMobile;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            ivGoodie=itemView.findViewById(R.id.ivGoodie);
            tvGoodieName=itemView.findViewById(R.id.tvGoodieName);
            tvGoodieHost=itemView.findViewById(R.id.tvGoodieHost);
            tvGoodiePrice=itemView.findViewById(R.id.tvGoodiePrice);
            tvGoodieHosterName=itemView.findViewById(R.id.tvGoodieHosterName);
            tvGoodieHosterMobile=itemView.findViewById(R.id.tvGoodieHosterMobile);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }

}
