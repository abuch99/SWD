package in.ac.bits_hyderabad.swd.swd.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.ac.bits_hyderabad.swd.swd.APIConnection.Deduction;
import in.ac.bits_hyderabad.swd.swd.R;

public class DeductionsAdapter extends RecyclerView.Adapter<DeductionsAdapter.ViewHolder> {

    private TextView tvDedName, tvDedPrice, tvSize;

    private ArrayList<Deduction> deductions;
    private Context context;
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

        String deductionPrice = "₹" + deductions.get(position).getAmount();
        String subText = "";

        tvDedName.setText(deductions.get(position).getName().trim());
        tvDedPrice.setText(deductionPrice);

        if (deductions.get(position).getType().equals("3")) {
            subText = "Quantity: " + deductions.get(position).getNetqut();
        } else if (deductions.get(position).getType().equals("2")) {
            int xs = Integer.parseInt(deductions.get(position).getXs());
            int s = Integer.parseInt(deductions.get(position).getS());
            int m = Integer.parseInt(deductions.get(position).getM());
            int l = Integer.parseInt(deductions.get(position).getL());
            int xl = Integer.parseInt(deductions.get(position).getXl());
            int xxl = Integer.parseInt(deductions.get(position).getXxl());
            int xxxl = Integer.parseInt(deductions.get(position).getXxxl());
            int count = xs + s + m + l + xl + xxl + xxxl;

            if (count == 1) {
                if (xs != 0)
                    subText = "Size ordered: XS";
                else if (s != 0)
                    subText = "Size ordered: S";
                else if (m != 0)
                    subText = "Size ordered: M";
                else if (l != 0)
                    subText = "Size ordered: L";
                else if (xl != 0)
                    subText = "Size ordered: XL";
                else if (xxl != 0)
                    subText = "Size ordered: XXL";
                else
                    subText = "Size ordered: XXXL";
            } else {
                String sizes = "";
                if (xs != 0)
                    sizes = sizes + xs + " XS, ";
                if (s != 0)
                    sizes = sizes + s + " S, ";
                if (m != 0)
                    sizes = sizes + m + " M, ";
                if (l != 0)
                    sizes = sizes + l + " L, ";
                if (xl != 0)
                    sizes = sizes + xl + " XL, ";
                if (xxl != 0)
                    sizes = sizes + xxl + " XXL, ";
                if (xxl != 0)
                    sizes = sizes + xxxl + " XXL, ";

                if (sizes.isEmpty())
                    subText = "Size ordered is unknown";
                else
                    subText = "Sizes ordered: " + sizes.substring(0, sizes.length() - 2);
            }

        }

        tvSize.setText(subText);

    }

    @Override
    public int getItemCount() {
        return deductions.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvDedName=itemView.findViewById(R.id.tvdeductionName);
            tvDedPrice=itemView.findViewById(R.id.tvdeductionPrice);
            tvSize = itemView.findViewById(R.id.tvSize);
        }

    }

}