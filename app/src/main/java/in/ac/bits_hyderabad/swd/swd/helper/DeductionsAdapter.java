package in.ac.bits_hyderabad.swd.swd.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.ac.bits_hyderabad.swd.swd.R;

public class DeductionsAdapter extends RecyclerView.Adapter<DeductionsAdapter.ViewHolder> {

    TextView tvDedName,tvDedPrice ,tvSizeText,tvSize;

    public ArrayList<Deduction> deductions;
    Context context;
    public DeductionsAdapter(Context context, ArrayList<Deduction> deductions){
        this.deductions=deductions;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.deduction_card, parent, false);
        return new DeductionsAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        tvDedName.setText(deductions.get(position).name);
        tvDedPrice.setText("₹ "+deductions.get(position).amount);

        if(deductions.get(position).type.equals("3")){
            tvSizeText.setText("Quantity :");
            tvSize.setText(deductions.get(position).netqut);
            tvSizeText.setVisibility(View.VISIBLE);
            tvSize.setVisibility(View.VISIBLE);
        }
        else if(deductions.get(position).type.equals("2")){
            tvSizeText.setText("Sizes Ordered :");
            int xs=Integer.parseInt(deductions.get(position).xs);
            int s=Integer.parseInt(deductions.get(position).s);
            int m=Integer.parseInt(deductions.get(position).m);
            int l=Integer.parseInt(deductions.get(position).l);
            int xl=Integer.parseInt(deductions.get(position).xl);
            int xxl=Integer.parseInt(deductions.get(position).xxl);
            int xxxl=Integer.parseInt(deductions.get(position).xxxl);

            String sizestring="";

            if(xs!=0){
                sizestring += (xs+ " XS \n");
            }
            if(s!=0){
                sizestring += (s+ " S \n");
            }

            if(m!=0){
                sizestring += (m+ " M \n");
            }
            if(l!=0){
                sizestring += (l+ " L \n");
            }

            if(xl!=0){
                sizestring += (xl+ " XL \n");
            }

            if(xxl!=0){
                sizestring += (xxl+ " XXL \n");
            }

            if(xxxl!=0){
                sizestring += (xxxl+ " XXXL \n");
            }

            tvSize.setText(sizestring.trim());
            tvSize.setVisibility(View.VISIBLE);
            tvSizeText.setVisibility(View.VISIBLE);
        }


    }

    @Override
    public int getItemCount() {
        return deductions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        public ViewHolder(@NonNull final View itemView) {
            super(itemView);


            tvDedName=itemView.findViewById(R.id.tvdeductionName);
            tvDedPrice=itemView.findViewById(R.id.tvdeductionPrice);
            tvSizeText=itemView.findViewById(R.id.tvSizesOrderedText);
            tvSize=itemView.findViewById(R.id.tvSizesShow);

            tvSizeText.setVisibility(View.GONE);
            tvSize.setVisibility(View.GONE);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

        }

    }

}