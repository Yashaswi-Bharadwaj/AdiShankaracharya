package com.shankaram.lokashankaram.adishankaracharya.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.shankaram.lokashankaram.adishankaracharya.Constants.ActivityScreenTransition;
import com.shankaram.lokashankaram.adishankaracharya.Constants.AppConstants;
import com.shankaram.lokashankaram.adishankaracharya.Constants.AppUtils;
import com.shankaram.lokashankaram.adishankaracharya.R;

public class HomeScreenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private final String TAG = AppConstants.TAG + HomeScreenActivity.class.getSimpleName();

    private TextView mIntroMsg, mTotalCount;

    private NavigationView mNavigationView;
    private Menu mMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Log.d(TAG, "onCreate");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mIntroMsg = (TextView) findViewById(R.id.tv_intro_msg);
        mTotalCount = (TextView) findViewById(R.id.tv_total_count);

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mMenu = mNavigationView.getMenu();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.count);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent countIntent = new Intent(
                        HomeScreenActivity.this, CounterActivity.class);
                startActivity(countIntent);
                ActivityScreenTransition.animateScreen(HomeScreenActivity.this,
                        ActivityScreenTransition.ANIM_TYPE.ENTER);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                Log.d("testing-" + TAG, "Opened");

                /*NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
                TextView message  = (TextView) navigationView.getHeaderView(0)
                        .findViewById(R.id.nav_count);
                message.setText("8123");*/

            }
        };

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            //updateTotalCount();

        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "onResume");

        /*MenuItem count = mMenu.getItem(R.id.nav_count);
        count.setTitle(AppUtils.getTotalCount(this));
        mNavigationView.setNavigationItemSelectedListener(this);*/

        /*final NavigationView navigationView= (NavigationView) findViewById(R.id.nav_view);
        final DrawerLayout drawer= (DrawerLayout) findViewById(R.id.drawer_layout);
        Menu menu=navigationView.getMenu();
        SubMenu sb=menu.addSubMenu("Courses");
        navigationView.invalidate();*/

        /*NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header=navigationView.getHeaderView(0);
        View view=navigationView.inflateHeaderView(R.layout.nav_header_main);
        TextView japa = (TextView)header.findViewById(R.id.nav_count);
        japa.setText("813");*/

        /*mTotalCount.setText("3");*/
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Intent nextActivityIntent = null;

        if (id == R.id.nav_details) {
            nextActivityIntent = new Intent(this, MyDetailsActivity.class);
        } else if (id == R.id.nav_japa_counter) {
            nextActivityIntent = new Intent(this, CounterActivity.class);
        } else if (id == R.id.nav_website) {
            if (!AppUtils.isInternetConnected(this)) {
                AppUtils.showOfflineDialog(this);
                return true;
            }
            nextActivityIntent = new Intent(this, WebviewActivity.class);
        } else if (id == R.id.nav_contact) {
            nextActivityIntent = new Intent(this, ContactActivity.class);
        } /*else if (id == R.id.nav_count) {
            nextActivityIntent = new Intent(this, CounterActivity.class);
        }*/

        startActivity(nextActivityIntent);
        ActivityScreenTransition.animateScreen(this,
                ActivityScreenTransition.ANIM_TYPE.ENTER);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
