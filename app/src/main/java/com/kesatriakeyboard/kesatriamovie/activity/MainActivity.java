package com.kesatriakeyboard.kesatriamovie.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.kesatriakeyboard.kesatriamovie.BuildConfig;
import com.kesatriakeyboard.kesatriamovie.R;
import com.kesatriakeyboard.kesatriamovie.tool.Helper;
import com.uxcam.UXCam;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final int LOADER_ID_NOWPLAYING = 0x0001;
    public static final int LOADER_ID_UPCOMING = 0x0010;
    public static final int LOADER_ID_SEARCH = 0x0100;
    public static final int LOADER_ID_FAVOURITE = 0x1000;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        UXCam.startWithKey(BuildConfig.UXCAM_KEY);

        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            getSupportActionBar().setTitle(getString(R.string.title_nowplaying));
            Fragment currentFragment = new NowplayingFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, currentFragment)
                    .commit();
        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String title = "";
        Fragment fragment = null;
        Bundle bundle = new Bundle();

        int id = item.getItemId();
        switch (id) {
            case R.id.nav_nowplaying:
                title = getString(R.string.title_nowplaying);
                fragment = new NowplayingFragment();
                break;
            case R.id.nav_upcoming:
                title = getString(R.string.title_upcoming);
                fragment = new UpcomingFragment();
                break;
            case R.id.nav_search:
                title = getString(R.string.title_search);
                fragment = new SearchFragment();
                bundle.putString(SearchFragment.EXTRAS_QUERY, Helper.getInstance().getQuery());
                fragment.setArguments(bundle);
                break;
            case R.id.nav_favourite:
                title = getString(R.string.title_favourite);
                fragment = new FavouriteFragment();
                break;
            case R.id.action_settings:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;
        }

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, fragment)
                    .commit();
        }

        toolbar.setTitle(title);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
