package in.ac.bits_hyderabad.swd.swd.helper;

import android.app.Dialog;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.view.GestureDetectorCompat;
import androidx.core.view.MotionEventCompat;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.ac.bits_hyderabad.swd.swd.R;

public class GoodiesAdapter extends RecyclerView.Adapter<GoodiesAdapter.ViewHolder> {

    private ArrayList<Goodies> goodies;
    private  String DEBUG_TAG="touch event";
    itemClicked activity;
    Context context;
    ZoomageView image;
    boolean error_loading=false;

    GestureDetector gestureDetector;

    public interface itemClicked{
        void onItemClicked(int index);
    }

    public GoodiesAdapter(Context context,Fragment fragment, ArrayList<Goodies> goodies)
    {
        this.context=context;
        this.goodies=goodies;
        activity=(itemClicked)fragment;
    }

    @NonNull
    @Override
    public GoodiesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.goodies_card,viewGroup,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final GoodiesAdapter.ViewHolder viewHolder, final int i) {

        final String ImageUrl= "http://swd.bits-hyderabad.ac.in/goodies/img/"+goodies.get(i).getImage();//"All.jpg"  or   goodies.get(i).getImage()


        final Context context =viewHolder.ivGoodie.getContext();
        Log.e("Image url",ImageUrl);

        /*Picasso.get().load(ImageUrl)
                .resize(700,700)
                .placeholder(R.drawable.ic_loading)
                .centerInside().error(R.drawable.ic_error)
                .into(viewHolder.ivGoodie);
        */

        Picasso.get().load(ImageUrl)
                .resize(600,600)
                .placeholder(R.drawable.ic_loading)
                .centerInside().error(R.drawable.ic_error)
                .into(viewHolder.ivGoodie);

        viewHolder.itemView.setTag(goodies.get(i));
        viewHolder.tvGoodieName.setText(goodies.get(i).getName());
        viewHolder.tvGoodieHost.setText(goodies.get(i).getHost());
        viewHolder.tvGoodiePrice.setText(goodies.get(i).getPrice());
        viewHolder.tvGoodieHosterName.setText(goodies.get(i).getHost_name());
        viewHolder.tvGoodieHosterMobile.setText(goodies.get(i).getMobile());


            viewHolder.ivGoodie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                        Dialog myDialog=new Dialog(context,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                        myDialog.setContentView(R.layout.my_dialog);
                        myDialog.getWindow().setBackgroundDrawableResource(R.color.semiTransparentColor99black);
                        image=myDialog.findViewById(R.id.ivFullGoodieImage);


                        Picasso.get().load(ImageUrl)
                                .resize(1500,1500)
                                .centerInside()
                                .placeholder(R.drawable.ic_loading)
                                .error(R.drawable.ic_error)
                                .into(image);
                        myDialog.show();


                }
            });

        };

    @Override
    public int getItemCount() {
        return goodies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivGoodie;
        TextView tvGoodieName,tvGoodieHost,tvGoodiePrice,tvGoodieHosterName,tvGoodieHosterMobile;

        public ViewHolder(@NonNull final View itemView) {
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
                    activity.onItemClicked(goodies.indexOf(v.getTag()));

                }
            });

        }

    }

}
