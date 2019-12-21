package in.ac.bits_hyderabad.swd.swd.helper;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.jsibbold.zoomage.ZoomageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.ac.bits_hyderabad.swd.swd.R;

public class NoticesAdapter extends RecyclerView.Adapter<NoticesAdapter.ViewHolder>{

    private ArrayList<Notice> notices;
    private  String DEBUG_TAG="touch event";
    NoticesAdapter.itemClicked activity;
    Context context;
    ZoomageView image;
    boolean error_loading=false;

    public interface itemClicked{
        void onItemClicked(int index);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public NoticesAdapter(Context context, Fragment fragment, ArrayList<Notice> notices)
    {
        this.context=context;
        this.notices=notices;
        activity=(itemClicked)fragment;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_notices, parent, false);
        return new NoticesAdapter.ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final String ImageUrl= "http://swd.bits-hyderabad.ac.in/img/uploads/"+notices.get(position).image;//"All.jpg"  or   notices.get(i).getImage()


        final Context context =holder.ivNotice.getContext();


        Picasso.get().load(ImageUrl)
                .resize(700,600)
                .placeholder(R.drawable.ic_loading)
                .centerInside()
                .error(R.drawable.ic_error)
                .into(holder.ivNotice);

        holder.itemView.setTag(notices.get(position));
        holder.tvNoticeTitle.setText(notices.get(position).getTitle());
        if(notices.get(position).body.trim().isEmpty()){
            holder.tvNoticeText.setVisibility(View.GONE);
        }
        else {
            holder.tvNoticeText.setText(notices.get(position).getBody());
        }
        if(notices.get(position).link.trim().isEmpty()){
            holder.tvLinks.setVisibility(View.GONE);
        }
        else {
            holder.tvLinks.setText(notices.get(position).getLink());
        }



        holder.ivNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Dialog myDialog=new Dialog(context,android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                myDialog.setContentView(R.layout.dialog_my);
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

    }

    @Override
    public int getItemCount() {
        return notices.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView ivNotice;
        TextView tvNoticeTitle,tvNoticeText,tvLinks;
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            ivNotice=itemView.findViewById(R.id.ivNotice);
            tvNoticeTitle=itemView.findViewById(R.id.tvNoticeTitle);
            tvNoticeText=itemView.findViewById(R.id.tvNoticetext);
            tvLinks=itemView.findViewById(R.id.tvLinks);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    activity.onItemClicked(notices.indexOf(v.getTag()));

                }
            });

        }

    }
}
