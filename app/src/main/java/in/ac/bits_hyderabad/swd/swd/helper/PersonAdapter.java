package in.ac.bits_hyderabad.swd.swd.helper;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.ac.bits_hyderabad.swd.swd.R;

public class PersonAdapter  extends RecyclerView.Adapter<PersonAdapter.ViewHolder>{

    public ArrayList <Person> person;
    Context context;

    public PersonAdapter(ArrayList<Person> person, Context context)
    {
        this.person = person;
        this.context = context;
    }

    @NonNull
    @Override
    public PersonAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_contact, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull final PersonAdapter.ViewHolder holder, final int position) {
        holder.itemView.setTag(person.get(position));

        holder.tvContactName.setText(person.get(position).name);
        holder.tvDesignation.setText(person.get(position).designation);

        holder.ivMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = person.get(position).uid + "@hyderabad.bits-pilani.ac.in";
                Intent intentMail = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null));
                context.startActivity(intentMail);
            }
        });
        holder.ivCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = person.get(position).phone;
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:" + number));//change the number
                context.startActivity(callIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return person.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvContactName, tvDesignation;
        ImageView ivCall, ivMail;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvContactName=itemView.findViewById(R.id.tvcontact_name);
            tvDesignation=itemView.findViewById(R.id.tvDesignation);
            ivCall=itemView.findViewById(R.id.ivCall);
            ivMail=itemView.findViewById(R.id.ivMail);
        }
    }
}
