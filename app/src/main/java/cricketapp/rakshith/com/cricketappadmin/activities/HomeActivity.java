package cricketapp.rakshith.com.cricketappadmin.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.FrameLayout;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.appinvite.AppInvite;
import com.google.android.gms.appinvite.AppInviteInvitationResult;
import com.google.android.gms.appinvite.AppInviteReferral;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import cricketapp.rakshith.com.cricketappadmin.R;
import cricketapp.rakshith.com.cricketappadmin.fragments.HomeFragment;

public class HomeActivity extends BaseActivity implements GoogleApiClient.OnConnectionFailedListener {
    FrameLayout flMainContainer;
    ArrayList<Fragment> mFragmentList = new ArrayList<Fragment>();
    AppCompatActivity mContext;
    Fragment mFragment;
    private Toolbar toolbar;
    private AdView mAdView;
    private GoogleApiClient mGoogleApiClient;

    private StorageReference mStorageRef;
//    ViewPager vpPager;
//    ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (Toolbar) findViewById(R.id.activity_home_detail_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mContext = this;

        mStorageRef = FirebaseStorage.getInstance().getReference();

        // Build GoogleApiClient with AppInvite API for receiving deep links
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(AppInvite.API)
                .build();

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

//        vpPager = (ViewPager) findViewById(R.id.activity_main_vp_pager);
//        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
//        vpPager.setAdapter(viewPagerAdapter);

        // Check if this app was launched from a deep link. Setting autoLaunchDeepLink to true
        // would automatically launch the deep link if one is found.
        boolean autoLaunchDeepLink = false;
        AppInvite.AppInviteApi.getInvitation(mGoogleApiClient, this, autoLaunchDeepLink)
                .setResultCallback(
                        new ResultCallback<AppInviteInvitationResult>() {
                            @Override
                            public void onResult(@NonNull AppInviteInvitationResult result) {
                                if (result.getStatus().isSuccess()) {
                                    // Extract deep link from Intent
                                    Intent intent = result.getInvitationIntent();
                                    String deepLink = AppInviteReferral.getDeepLink(intent);

                                    // Handle the deep link. For example, open the linked
                                    // content, or apply promotional credit to the user's
                                    // account.

                                    // ...
                                } else {
                                    Log.d("Rakshith", "getInvitation: no deep link found.");
                                }
                            }
                        });
        addFragment(new HomeFragment(), null);
    }


    public void addFragment(Fragment fragment, String mBackStack) {
        if (mContext == null) {
            return;
        }
        mFragmentList.add(fragment);

        FragmentTransaction fragmentTransaction = mContext.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.contentMainflMainContainer, fragment);

        if (mBackStack != null) {
            fragmentTransaction.addToBackStack(mBackStack);
        }
        mFragment = fragment;
        fragmentTransaction.commit();
    }

    public void replaceFragment(Fragment fragment, String mBackStack, Bundle bundle) {
        if (mContext == null) {
            return;
        }
        mFragmentList.add(fragment);

        FragmentTransaction fragmentTransaction = mContext.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contentMainflMainContainer, fragment);

        if (mBackStack != null) {
            fragmentTransaction.addToBackStack(mBackStack);
        }
        mFragment = fragment;
        if (bundle != null) {
            mFragment.setArguments(bundle);
        }
        fragmentTransaction.commit();
    }

    public Fragment getmFragment() {
        if (mFragmentList.size() > 0) {
            return mFragmentList.get(mFragmentList.size() - 1);
        }
        return null;
    }

    public void popCurrentFragment() {
        if (mFragmentList.size() > 0) {
            mFragmentList.remove(mFragmentList.size() - 1);
            if (mFragmentList.size() > 0) {
                mFragmentList.remove(mFragmentList.size() - 1);
            }
        }
        mContext.getSupportFragmentManager().popBackStack();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mContext = null;
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }
}
