package in.ac.bits_hyderabad.swd.swd;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.contact_list;

public class contact_adapter extends RecyclerView.Adapter<contact_adapter.MyViewHolder> {

    private Context context;
    private List<contact_list> resp_list= new ArrayList<>();

    public contact_adapter(Context context, ArrayList<contact_list> list)
    {
        this.context=context;
        resp_list=list;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        //holder.tv.setText(resp_list.get(position));
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.resp_list,parent,false));
    }

    @Override
    public int getItemCount() {
        return resp_list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        public MyViewHolder(View v){
            super(v);
            tv = v.findViewById(R.id.resp_name);
        }
    }
}
