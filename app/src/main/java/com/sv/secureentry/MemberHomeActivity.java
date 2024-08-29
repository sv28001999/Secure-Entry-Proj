package com.sv.secureentry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.sv.secureentry.bottomnavfragments.MemberListFragment;
import com.sv.secureentry.bottomnavfragments.MemberSecretaryHomeFrag;
import com.sv.secureentry.bottomnavfragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MemberHomeActivity extends AppCompatActivity {


    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_home);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = new MemberSecretaryHomeFrag();
                    break;
                case R.id.navigation_member:
                    selectedFragment = new MemberListFragment();
                    break;
                case R.id.navigation_profile:
                    selectedFragment = new ProfileFragment();
                    break;
            }
            assert selectedFragment != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.member_fragment_container, selectedFragment).commit();
            return true;
        });

        // Set default selection
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        }
    }
}