package in.ac.bits_hyderabad.swd.swd.user.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.helper.Document;
import in.ac.bits_hyderabad.swd.swd.helper.DocumentsAdapter;

public class User_DocFragment extends Fragment implements DocumentsAdapter.itemClicked {


    RecyclerView rvDocs;
    ArrayList<Document> documents=new ArrayList<>();
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    String id;
    String url;
    String name_of_item_clicked;

    public static User_DocFragment newInstance(String id){
        User_DocFragment f = new User_DocFragment();
        Bundle args=new Bundle();
        args.putString("id",id);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View view = inflater.inflate(R.layout.doc_fragment, parent, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        id=this.getArguments().getString("id");


        documents.add(new Document("Bonafide Certificate","gen_bonafide.php"));
        documents.add(new Document("Good Character Certificate","gen_goodchar.php"));
        documents.add(new Document("Medical Insurance Claim Form","claimform.pdf"));
        documents.add(new Document("No Objection Certificate","gen_noc.php"));
        documents.add(new Document("Vacation Letter","gen_vacation.php"));


        rvDocs=view.findViewById(R.id.rvDocs);
        mLayoutManager=new GridLayoutManager(getActivity(),2, RecyclerView.VERTICAL,false);
        rvDocs.setLayoutManager(mLayoutManager);
        mAdapter=new DocumentsAdapter(getActivity(),this,documents);
        rvDocs.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(int index) {


        name_of_item_clicked=documents.get(index).getName();
        if(!documents.get(index).getName().contains("Medical")) {
            url="http://swd.bits-hyderabad.ac.in/components/navbar/" + documents.get(index).getUrl() + "?myid=" + id;
        }
        else{
            url="http://swd.bits-hyderabad.ac.in/components/navbar/" + documents.get(index).getUrl();
        }

    }


}
