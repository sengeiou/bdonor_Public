
package com.sq.sohel.blooddonor.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.sq.sohel.blooddonor.BR;
import com.sq.sohel.blooddonor.BuildConfig;
import com.sq.sohel.blooddonor.R;
import com.sq.sohel.blooddonor.databinding.ActivityMainBinding;
import com.sq.sohel.blooddonor.databinding.NavHeaderMainBinding;
import com.sq.sohel.blooddonor.ui.about.AboutFragment;
import com.sq.sohel.blooddonor.ui.base.BaseActivity;
import com.sq.sohel.blooddonor.ui.base.BasePagerAdapter;
import com.sq.sohel.blooddonor.ui.becomeDonor.BecomeDonorActivity;
import com.sq.sohel.blooddonor.ui.login.LoginActivity;
import com.sq.sohel.blooddonor.ui.main.advanceSearch.AdvanceSearchDialog;
import com.sq.sohel.blooddonor.ui.main.donor.DonorViewModel;

import com.sq.sohel.blooddonor.ui.main.newRequestForBlood.NewRequestActivity;
import com.sq.sohel.blooddonor.ui.main.rating.RateUsDialog;
import com.sq.sohel.blooddonor.ui.requestAndResponseForBlood.RequestAndResponseForBloodActivity;
import com.sq.sohel.blooddonor.utils.AppUtils;
import com.sq.sohel.blooddonor.utils.MinMaxFilter;
import com.sq.sohel.blooddonor.utils.PermissionUtils;
//import com.sq.sohel.blooddonor.utils.ScreenUtils;


import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

import static com.sq.sohel.blooddonor.utils.AppConstants.ALL_PERMISSION_LIST;
import static com.sq.sohel.blooddonor.utils.AppConstants.ALL_PERMISSION_LIST_CODE;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements MainNavigator, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    @Inject
    @Named("MainViewModel")
    ViewModelProvider.Factory mViewModelFactory;
    private ActivityMainBinding mActivityMainBinding;
    //private SwipePlaceHolderView mCardsContainerView;
    private DrawerLayout mDrawer;
    private MainViewModel mMainViewModel;
    private NavigationView mNavigationView;
    private Toolbar mToolbar;

    @Inject
    MainPagerAdapter mPagerAdapter;

    AdvanceSearchDialog mAdvanceSearchDialog;

    public static Intent newIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getViewModel() {
        mMainViewModel = ViewModelProviders.of(this, mViewModelFactory).get(MainViewModel.class);
        return mMainViewModel;
    }

    @Override
    public BasePagerAdapter getPagerAdapter() {
        return mPagerAdapter;
    }

    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(AboutFragment.TAG);
        if (fragment == null) {
            super.onBackPressed();
        } else {
            onFragmentDetached(AboutFragment.TAG);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);

        SearchManager manager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView search = (SearchView) menu.findItem(R.id.action_search).getActionView();
        search.setSearchableInfo(manager.getSearchableInfo(getComponentName()));
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //Toast.makeText(getApplicationContext(), "onQueryTextSubmit", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //loadHistory(query);
                mPagerAdapter.FilterData(1, newText);
                //Toast.makeText(getApplicationContext(), "onQueryTextChange", Toast.LENGTH_SHORT).show();
                return true;
                //return false;
            }
        });

        return true;
    }

    public void onFragmentDetached(String tag) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        if (fragment != null) {
            fragmentManager
                    .beginTransaction()
                    .disallowAddToBackStack()
                    .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                    .remove(fragment)
                    .commitNow();
            unlockDrawer();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Drawable drawable = item.getIcon();
        if (drawable instanceof Animatable) {
            ((Animatable) drawable).start();
        }
//        switch (item.getItemId()) {
//            case R.id.action_cut:
//                return true;
//            case R.id.action_copy:
//                return true;
//            case R.id.action_share:
//                return true;
//            case R.id.action_delete:
//                return true;
//            default:
        return super.onOptionsItemSelected(item);
//        }
    }

    @Override
    public void openLoginActivity() {
        startActivity(LoginActivity.newIntent(this));
        finish();
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivityMainBinding = getViewDataBinding();
        mMainViewModel.setNavigator(this);
        allPermissionAsk();
        setUp();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mDrawer != null) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    private void lockDrawer() {
        if (mDrawer != null) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }

    private void setUp() {
        mDrawer = mActivityMainBinding.drawerView;
        mToolbar = mActivityMainBinding.toolbar;
        mNavigationView = mActivityMainBinding.navigationView;
        //mCardsContainerView = mActivityMainBinding.cardsContainer;

        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawer,
                mToolbar,
                R.string.open_drawer,
                R.string.close_drawer) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hideKeyboard();
            }
        };
        mDrawer.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        setupNavMenu();
        String version = getString(R.string.version) + " " + BuildConfig.VERSION_NAME;
        if(BuildConfig.rELEASE){
            version = "Release 1";
        }else{
            version = "Debug 1";
        }
        mMainViewModel.updateAppVersion(version);
        mMainViewModel.onNavMenuCreated();
        setupCardContainerView();
        //subscribeToLiveData();


    }

    private void allPermissionAsk() {


        if (!PermissionUtils.hasPermissions(this, ALL_PERMISSION_LIST)) {
            ActivityCompat.requestPermissions(this, ALL_PERMISSION_LIST, ALL_PERMISSION_LIST_CODE);
        }
    }

    private void setupCardContainerView() {
        //mPagerAdapter.setCount(2);
        mActivityMainBinding.donorViewPager.setAdapter(mPagerAdapter);
    }

    private void setupNavMenu() {
        NavHeaderMainBinding navHeaderMainBinding = DataBindingUtil.inflate(getLayoutInflater(),
                R.layout.nav_header_main, mActivityMainBinding.navigationView, false);
        mActivityMainBinding.navigationView.addHeaderView(navHeaderMainBinding.getRoot());
        navHeaderMainBinding.setViewModel(mMainViewModel);

        mNavigationView.setNavigationItemSelectedListener(
                item -> {
                    mDrawer.closeDrawer(GravityCompat.START);
                    switch (item.getItemId()) {
                        case R.id.navItemBecomeDonor:
                            startActivity(BecomeDonorActivity.newIntent(MainActivity.this, AppUtils.DonorAddType.AddMySelf));
                            return true;
                        case R.id.navItemAddDonor:
                            startActivity(BecomeDonorActivity.newIntent(MainActivity.this, AppUtils.DonorAddType.AddOtherDonor));
                            return true;
                        case R.id.navItemAdvanceSearch:
                            mAdvanceSearchDialog = AdvanceSearchDialog.newInstance();
                            mAdvanceSearchDialog.show(getSupportFragmentManager(), mPagerAdapter);
                            return true;
                        case R.id.navAllRequestOfBlood:
                            startActivity(RequestAndResponseForBloodActivity.newIntent(MainActivity.this,false));
                            return true;
                        case R.id.navMyAllRequestOfBlood:
                            startActivity(RequestAndResponseForBloodActivity.newIntent(MainActivity.this,true));
                            return true;

                        case R.id.navnewRequestForBlood:
                            //startActivity(BecomeDonorActivity.newIntent(MainActivity.this, AppUtils.DonorAddType.AddOtherDonor));
                            //mActivityMainBinding.donorViewPager.setCurrentItem(1);
                            startActivity(NewRequestActivity.newIntent(MainActivity.this));
                            return true;
                        case R.id.navItemAbout:
                            showAboutFragment();
                            return true;
                        case R.id.navItemRateUs:
                            RateUsDialog.newInstance().show(getSupportFragmentManager());
                            return true;

                        case R.id.navItemLogout:
                            mMainViewModel.logout();
                            return true;
                        default:
                            return false;
                    }
                });
    }

    private void showAboutFragment() {
        lockDrawer();
        getSupportFragmentManager()
                .beginTransaction()
                .disallowAddToBackStack()
                .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                .add(R.id.clRootView, AboutFragment.newInstance(), AboutFragment.TAG)
                .commit();
    }

    private void subscribeToLiveData() {
        mMainViewModel.getDonorCardData().observe(this, d -> mMainViewModel.setQuestionDataList(d));
    }

    private void unlockDrawer() {
        if (mDrawer != null) {
            mDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == ALL_PERMISSION_LIST_CODE && grantResults.length > 2) {
            if (grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, "phone state permission granted", Toast.LENGTH_LONG).show();
                TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
                mMainViewModel.getDataManager().setCurrentUserContactNumber(tm.getLine1Number());

            } else {
                //Toast.makeText(this, "phone state permission denied", Toast.LENGTH_LONG).show();
            }
        }
        if (requestCode == ALL_PERMISSION_LIST_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                LocationFinder lf = new LocationFinder(this,this, mMainViewModel.getDataManager());
//                lf.createLocationRequest();
//                lf.startLocationUpdates();
            } else {
                //Toast.makeText(this, "Location permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }
}
