/*
 * This project is licensed under the open source MPL V2.
 * See https://github.com/openMF/android-client/blob/master/LICENSE.md
 */

package com.mifos.mifosxdroid.online;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.mifos.mifosxdroid.OfflineCenterInputActivity;
import com.mifos.mifosxdroid.R;
import com.mifos.utils.Constants;
import com.mifos.utils.FragmentConstants;
import com.mifos.utils.MifosApplication;

import javax.annotation.Resource;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import butterknife.OnEditorAction;

/**
 * Created by ishankhanna on 09/02/14.
 */


public class DashboardFragmentActivity extends ActionBarActivity {


    public final static String TAG = DashboardFragmentActivity.class.getSimpleName();
    public static Context context;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        HomeFragment homeFragment=new HomeFragment();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.dashboard_global_container, homeFragment, "HomeFragment");
        fragmentTransaction.commit();
       //to enable home button
        getSupportActionBar().setDisplayOptions(android.support.v7.app.ActionBar.DISPLAY_HOME_AS_UP| android.support.v7.app.ActionBar.DISPLAY_SHOW_TITLE);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_launcher);
        getSupportActionBar().setSubtitle(R.string.home);


        /*actionBar.setLogo(R.mipmap.ic_launcher);*/

    }

    public void popStacEntryFragments()
    {
        if(getSupportFragmentManager().getBackStackEntryCount()!=0)
        {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.client_search, menu);

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected: " + item.getItemId());

        switch (item.getItemId()) {
            case R.id.item_offline_centers:
                startActivity(new Intent(this, OfflineCenterInputActivity.class));
                break;
            case R.id.logout:
                startActivity(new Intent(DashboardFragmentActivity.this, LogoutActivity.class));
                finish();
                break;
            case android.R.id.home:
                //android.r.id.home is used to home button click listner
                    startActivity(new Intent(this, DashboardFragmentActivity.class));
                    finish();
                break;

            default: //DO NOTHING
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return super.onSupportNavigateUp();
    }



    @Override
    public void onBackPressed() {
        EditText editText=(EditText)findViewById(R.id.et_search_by_id);
        if(editText!=null) {
            editText.setText("");

        }
        super.onBackPressed();
        if(getSupportFragmentManager().getBackStackEntryCount()==0)
        {
            getSupportActionBar().setSubtitle(R.string.home);
        }

    }
}


