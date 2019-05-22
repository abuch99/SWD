package in.ac.bits_hyderabad.swd.swd.user.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.helper.Goodies;
import in.ac.bits_hyderabad.swd.swd.helper.GoodiesAdapter;

public class User_GoodiesFragment extends Fragment implements GoodiesAdapter.itemClicked {

    RecyclerView rvGoodiesList;
    RecyclerView.Adapter mAdaptor;
    RecyclerView.LayoutManager mLayoutManager;

    ArrayList<Goodies> goodies;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        // Defines the xml file for the fragment
        View view = inflater.inflate(R.layout.goodies_fragment, parent, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        goodies=new ArrayList<Goodies>();                                   //define your goodies array list here

        goodies.add(0,new Goodies("0","ATMOS HOODIE","CRUx BPHC","250","PRASOON BAGHEL","9133260431"));
        rvGoodiesList=view.findViewById(R.id.rvGoodiesList);
        rvGoodiesList.setHasFixedSize(false);
        mLayoutManager =new LinearLayoutManager(this.getActivity());
        rvGoodiesList.setLayoutManager(mLayoutManager);
        mAdaptor=new GoodiesAdapter(this,goodies);
        rvGoodiesList.setAdapter(mAdaptor);
        mAdaptor.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(int index) {
        Log.e("hello","hello");

    }
}
