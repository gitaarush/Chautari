package com.sns.chautari.common;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.sns.chautari.R;
import com.sns.chautari.home.HomeFragment;
import com.sns.chautari.photo.PhotoFragment;
import com.sns.chautari.team.TeamFragment;
import com.sns.chautari.video.VideoFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;


public class MainActivity extends FragmentActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Unbinder unbinder;

    @BindView(R.id.bottom_nav_bar)
    BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, "onCreate:");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        unbinder = ButterKnife.bind(this);

        setFragment(new HomeFragment());
        bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Log.i(TAG, "clickTest: Navigation Button action");
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.nav_home:
                                selectedFragment = new HomeFragment();
                            break;
                            case R.id.nav_team:
                                selectedFragment = new TeamFragment();
                                break;
                            case R.id.nav_photo:
                                selectedFragment = new PhotoFragment();
                                break;
                            case R.id.nav_video:
                                selectedFragment = new VideoFragment();
                                break;
                            default:
                                return false;
                        }
                        setFragment(selectedFragment);
                        return true;
                    }
                });
    }

    private void setFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
