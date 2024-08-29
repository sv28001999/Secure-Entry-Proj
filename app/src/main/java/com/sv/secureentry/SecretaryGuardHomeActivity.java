package com.sv.secureentry;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.sv.secureentry.bottomnavfragments.EntryRecordFragment;
import com.sv.secureentry.bottomnavfragments.GuardHomeFragment;
import com.sv.secureentry.bottomnavfragments.MemberListFragment;
import com.sv.secureentry.bottomnavfragments.MemberSecretaryHomeFrag;
import com.sv.secureentry.bottomnavfragments.ProfileFragment;
import com.sv.secureentry.models.ProjConstants;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Objects;

public class SecretaryGuardHomeActivity extends AppCompatActivity {

    private String roleType;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        roleType = getIntent().getStringExtra(ProjConstants.CLIENT_ROLE);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            int itemId = item.getItemId();
            if (itemId == R.id.navigation_home_1 && Objects.equals(roleType, ProjConstants.SECRETARY_ROLE)) {
                selectedFragment = new MemberSecretaryHomeFrag();
            } else if (itemId == R.id.navigation_home_1 && Objects.equals(roleType, ProjConstants.GUARD_ROLE)) {
                selectedFragment = new GuardHomeFragment();
            } else if (itemId == R.id.navigation_member_1) {
                selectedFragment = new MemberListFragment();
            } else if (itemId == R.id.navigation_report_1) {
                selectedFragment = new EntryRecordFragment();
            } else if (itemId == R.id.navigation_profile_1) {
                selectedFragment = new ProfileFragment();
            }
            assert selectedFragment != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.guard_secretary_fragment_container, selectedFragment).commit();
            return true;
        });

        // Set default selection
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.navigation_home_1);
        }
    }
}