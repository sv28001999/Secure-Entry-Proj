package com.sv.secureentry.bottomnavfragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sv.secureentry.EventsActivity;
import com.sv.secureentry.NoticeActivity;
import com.sv.secureentry.PollingActivity;
import com.sv.secureentry.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MemberSecretaryHomeFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MemberSecretaryHomeFrag extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MemberSecretaryHomeFrag() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MemberSecretaryHomeFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static MemberSecretaryHomeFrag newInstance(String param1, String param2) {
        MemberSecretaryHomeFrag fragment = new MemberSecretaryHomeFrag();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_member_secretary_home, container, false);
        CardView cdNoticeBtn, cdEventBtn, cdHelpBtn, cdPollingBtn;

        cdNoticeBtn = view.findViewById(R.id.cdNoticeBtn);
        cdEventBtn = view.findViewById(R.id.cdEventBtn);
        cdHelpBtn = view.findViewById(R.id.cdHelpBtn);
        cdPollingBtn = view.findViewById(R.id.cdPollingBtn);

        cdNoticeBtn.setOnClickListener(v -> startActivity(new Intent(getContext(), NoticeActivity.class)));
        cdEventBtn.setOnClickListener(v -> startActivity(new Intent(getContext(), EventsActivity.class)));
        cdPollingBtn.setOnClickListener(v -> startActivity(new Intent(getContext(), PollingActivity.class)));

        return view;
    }
}