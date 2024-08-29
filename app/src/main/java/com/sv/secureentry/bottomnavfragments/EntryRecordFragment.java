package com.sv.secureentry.bottomnavfragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sv.secureentry.ApiInterface;
import com.sv.secureentry.R;
import com.sv.secureentry.RetrofitClient;
import com.sv.secureentry.adapters.VisitorAdapter;
import com.sv.secureentry.adapters.VisitorList;
import com.sv.secureentry.models.GetEntriesReqBody;
import com.sv.secureentry.models.GetEntriesResBody;
import com.sv.secureentry.models.ProjConstants;
import com.leo.simplearcloader.ArcConfiguration;
import com.leo.simplearcloader.SimpleArcDialog;
import com.leo.simplearcloader.SimpleArcLoader;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EntryRecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EntryRecordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ApiInterface apiInterface;
    private RecyclerView gateEntryRecyclerView;
    private VisitorAdapter visitorAdapter;
    private List<VisitorList> visitorList;
    private SimpleArcDialog mDialog;

    public EntryRecordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EntryRecordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EntryRecordFragment newInstance(String param1, String param2) {
        EntryRecordFragment fragment = new EntryRecordFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("entryRecordFragment", "onCreateMethodCall");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_entry_record, container, false);
        gateEntryRecyclerView = view.findViewById(R.id.gateEntryRecyclerView);

        mDialog = new SimpleArcDialog(getContext());
        ArcConfiguration configuration = new ArcConfiguration(requireContext());
        configuration.setLoaderStyle(SimpleArcLoader.STYLE.SIMPLE_ARC);
        configuration.setColors(new int[]{Color.parseColor("#D8533FD3")});
        configuration.setText("Please wait..");
        mDialog.setConfiguration(configuration);
        mDialog.setCancelable(false);

        gateEntryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        Retrofit retrofit = RetrofitClient.getInstance();
        apiInterface = retrofit.create(ApiInterface.class);

        getVisitorsFromApi();

        return view;
    }

    private void getVisitorsFromApi() {
        mDialog.show();
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences(ProjConstants.USER_DATA_SF, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString("token", "");
        String secretCode = sharedPreferences.getString("orgUniqueCode", "");

        GetEntriesReqBody getEntriesReqBody = new GetEntriesReqBody(secretCode);
        Call<GetEntriesResBody> call = apiInterface.getEntryDetails("Bearer " + token, getEntriesReqBody);

        call.enqueue(new Callback<GetEntriesResBody>() {
            @Override
            public void onResponse(Call<GetEntriesResBody> call, Response<GetEntriesResBody> response) {
                GetEntriesResBody resBody = response.body();
                if (response.isSuccessful() && response.code() == 200) {
                    Toast.makeText(getContext(), "Successful", Toast.LENGTH_SHORT).show();
                    visitorList = getVisitors(resBody.getData());
                    visitorAdapter = new VisitorAdapter(getContext(), visitorList);
                    gateEntryRecyclerView.setAdapter(visitorAdapter);
                    mDialog.cancel();
                } else {
                    mDialog.cancel();
                    Toast.makeText(getContext(), "Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetEntriesResBody> call, Throwable t) {
                mDialog.cancel();
                Toast.makeText(getContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private List<VisitorList> getVisitors(ArrayList<GetEntriesResBody.Datum> datumArrayList) {
        List<VisitorList> visitors = new ArrayList<>();
        for (int i = 0; i < datumArrayList.size(); i++) {
            visitors.add(new VisitorList(datumArrayList.get(i).getPersonName(), datumArrayList.get(i).getMobileNumber(), datumArrayList.get(i).getWork(), datumArrayList.get(i).getPlace(), datumArrayList.get(i).getMemberRoomNo(), datumArrayList.get(i).getImageUrl(), datumArrayList.get(i).getDateIn()));
        }

        return visitors;
    }
}