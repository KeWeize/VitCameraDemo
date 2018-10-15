package com.vit.scanlibs.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.vit.scanlibs.R;

/**
 * <p> 扫描 Activity <p/>
 *
 * @author kewz
 */

public class CommScanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comm_scan);

        CommScanFragment mScanFragment = new CommScanFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fl_scan_container, mScanFragment)
                .commit();

    }

}
