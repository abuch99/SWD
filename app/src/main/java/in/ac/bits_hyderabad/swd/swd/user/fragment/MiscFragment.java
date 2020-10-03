package in.ac.bits_hyderabad.swd.swd.user.fragment;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.card.MaterialCardView;

import in.ac.bits_hyderabad.swd.swd.APIConnection.DocContents;
import in.ac.bits_hyderabad.swd.swd.APIConnection.GetDataService;
import in.ac.bits_hyderabad.swd.swd.R;
import in.ac.bits_hyderabad.swd.swd.helper.PdfDocumentSample;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MiscFragment extends Fragment {

    String uid, id_no, password;
    ProgressDialog dialog;
    int PERMISSION =1000;

    MaterialCardView cardTimings, cardBonafide, cardGoodChar, cardNoObjection, cardVacation;

    private Retrofit mRetrofitClient;
    private GetDataService mRetrofitService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_misc, parent, false);

        SharedPreferences prefs = getContext().getSharedPreferences("USER_LOGIN_DETAILS", Context.MODE_PRIVATE);
        uid = prefs.getString("uid", null);
        password = prefs.getString("password", null);
        id_no = prefs.getString("id", null);

        cardTimings = rootView.findViewById(R.id.cardViewTimings);
        cardBonafide = rootView.findViewById(R.id.cardViewBonafide);
        cardGoodChar = rootView.findViewById(R.id.cardViewGoodCharacter);
        cardNoObjection = rootView.findViewById(R.id.cardViewNoObjection);
        cardVacation = rootView.findViewById(R.id.cardViewVacation);

        dialog = new ProgressDialog(getActivity());
        dialog.setMessage("Loading..");
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.create();

        mRetrofitClient = new Retrofit.Builder()
                .baseUrl(getString(R.string.URL))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mRetrofitService = mRetrofitClient.create(GetDataService.class);

        cardTimings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.action_miscFragment_to_busTimingsFragment);
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

    private void getDocument(String name, String url) {
        boolean ext = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        if (ext) {
            Call<DocContents> call = mRetrofitService.getDocContent("docs", uid, url);
            call.enqueue(new Callback<DocContents>() {
                @Override
                public void onResponse(Call<DocContents> call, Response<DocContents> response) {
                    if (!response.body().getError()) {
                        String content = response.body().getContent();
                        PdfDocumentSample document = new PdfDocumentSample(id_no, name,
                                content, getString(R.string.dean), getString(R.string.post),
                                getString(R.string.doc_address), getString(R.string.doc_contact), getActivity());
                        dialog.cancel();
                        document.downloadFile();
                    } else {
                        Toast.makeText(getContext(), "Something went wrong! Please contact SWD", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                }

                @Override
                public void onFailure(Call<DocContents> call, Throwable t) {
                    Toast.makeText(getContext(), "Something went wrong! Please contact SWD", Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    t.printStackTrace();
                }
            });
        }
    }
}
