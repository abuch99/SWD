package in.ac.bits_hyderabad.swd.swd.user.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.helper.PdfDocumentSample;

public class MiscFragment extends Fragment {

    String uid, id_no, password;
    ProgressDialog dialog;
    int PERMISSION =1000;

    MaterialCardView cardTimings, cardBonafide, cardGoodChar, cardNoObjection, cardVacation;

    public MiscFragment(String uid, String id_no, String password) {
        this.uid = uid;
        this.id_no = id_no;
        this.password = password;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_misc, parent, false);

        cardTimings = rootView.findViewById(R.id.cardViewTimings);
        cardBonafide = rootView.findViewById(R.id.cardViewBonafide);
        cardGoodChar = rootView.findViewById(R.id.cardViewGoodCharacter);
        cardNoObjection = rootView.findViewById(R.id.cardViewNoObjection);
        cardVacation = rootView.findViewById(R.id.cardViewVacation);

        dialog=new ProgressDialog(getActivity());
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.create();

        cardTimings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction().replace(R.id.container, new BusTimingsFragment()).commit();
            }
        });

        cardBonafide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDocument("BONAFIDE CERTIFICATE", "bonafide");
            }
        });

        cardGoodChar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDocument("GOOD CHARACTER CERTIFICATE", "good_char");
            }
        });

        cardNoObjection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDocument("NO OBJECTION CERTIFICATE", "noc");
            }
        });

        cardVacation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDocument("VACATION LETTER CERTIFICATE", "vacation");
            }
        });

        return rootView;
    }

    public void getDocument(String name, String url) {

        boolean ext = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if(ext) {
            RequestQueue queue = Volley.newRequestQueue(getActivity());
            StringRequest request = new StringRequest(Request.Method.POST, getString(R.string.DOCS_URL), new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject object = new JSONObject(response);

                        if (!object.getString("error").equalsIgnoreCase("true")) {
                            String content = object.getString("content");
                            PdfDocumentSample document = new PdfDocumentSample(id_no, name,
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
                    Toast.makeText(getContext(), "Please check your Internet connection!", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {

                    Map<String, String> params = new HashMap<>();
                    params.put("tag", "docs");
                    params.put("uid", uid);
                    params.put("doc_type", url);
                    return params;

                }
            };


            queue.add(request);
        } else {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION);
        }

    }

}
