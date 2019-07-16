package in.ac.bits_hyderabad.swd.swd.user.fragment;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.helper.Document;
import in.ac.bits_hyderabad.swd.swd.helper.DocumentsAdapter;
import in.ac.bits_hyderabad.swd.swd.helper.PdfDocumentSample;
import in.ac.bits_hyderabad.swd.swd.helper.Person;

public class User_DocFragment extends Fragment implements DocumentsAdapter.itemClicked {


    RecyclerView rvDocs;
    ArrayList<Document> documents=new ArrayList<>();
    RecyclerView.Adapter mAdapter;
    RecyclerView.LayoutManager mLayoutManager;
    String uid, id_no;
    TextView tvDialogmsg, tvDialogTitle;
    String name_of_item_clicked;
    String type_of_item_clicked;
    ProgressDialog dialog;
    int PERMISSION =1000;

    public static User_DocFragment newInstance(String uid, String id_no){
        User_DocFragment f = new User_DocFragment();
        Bundle args=new Bundle();
        args.putString("uid",uid);
        args.putString("id_no",id_no);
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

        uid=this.getArguments().getString("uid");
        id_no=this.getArguments().getString("id_no");
        dialog=new ProgressDialog(getActivity());
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.create();




        documents.add(new Document("Bonafide Certificate","bonafide"));
        documents.add(new Document("Good Character Certificate","good_char"));
        //documents.add(new Document("Medical Insurance Claim Form","claimform.pdf"));
        documents.add(new Document("No Objection Certificate","noc"));
        documents.add(new Document("Vacation Letter","vacation"));


        rvDocs=view.findViewById(R.id.rvDocs);
        mLayoutManager=new GridLayoutManager(getActivity(),2, RecyclerView.VERTICAL,false);
        rvDocs.setLayoutManager(mLayoutManager);
        mAdapter=new DocumentsAdapter(getActivity(),this,documents);
        rvDocs.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(int index) {

        name_of_item_clicked=documents.get(index).getName().toUpperCase();
        type_of_item_clicked=documents.get(index).getUrl();


        boolean ext= ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED;

        if(ext) {
            dialog.show();
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.DOCS_URL), new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("Response", response);

                    try {
                        JSONObject object = new JSONObject(response);

                        if (!object.getString("error").equalsIgnoreCase("true")) {
                            String content = object.getString("content");
                            PdfDocumentSample document = new PdfDocumentSample(id_no, name_of_item_clicked,
                                    content, getString(R.string.dean), getString(R.string.post),
                                    getString(R.string.doc_address), getString(R.string.doc_contact), getActivity());
                            dialog.cancel();
                            document.downloadFile();
                        } else {
                            Toast.makeText(getContext(), "Something went wrong! Please contact SWD", Toast.LENGTH_SHORT).show();
                            dialog.cancel();
                        }

                    } catch (JSONException e) {
                        Toast.makeText(getContext(), "Something went wrong! Please contact SWD", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("Error", error.toString());
                    Toast.makeText(getContext(), "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();
                    params.put("tag", "docs");
                    params.put("uid", uid);
                    params.put("doc_type", type_of_item_clicked);
                    return params;

                }
            };


            queue.add(request);
        }
        else
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION);
        }

    }


    @Override
    public void onResume() {
        super.onResume();
    }

}
