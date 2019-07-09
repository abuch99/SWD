package in.ac.bits_hyderabad.swd.swd.helper;

import android.content.Context;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.ac.bits_hyderabad.swd.swd.R;

public class DocumentsAdapter extends RecyclerView.Adapter <DocumentsAdapter.ViewHolder>{

    public ArrayList<Document> documents;
    public Context context;
    itemClicked activity;

    public interface itemClicked{
        void onItemClicked(int index);
    }

    public DocumentsAdapter(Context context, Fragment fragment, ArrayList<Document> documents){
        this.documents=documents;
        this.context=context;
        activity=(itemClicked)fragment;
    }

    @NonNull
    @Override
    public DocumentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.doc_card,parent,false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(@NonNull DocumentsAdapter.ViewHolder holder, int position) {


        holder.itemView.setTag(documents.get(position));
        holder.tvDocsName.setText(documents.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return documents.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvDocsName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvDocsName = itemView.findViewById(R.id.tvDoc_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    activity.onItemClicked(documents.indexOf(v.getTag()));
                }
            });
        }
    }
}
