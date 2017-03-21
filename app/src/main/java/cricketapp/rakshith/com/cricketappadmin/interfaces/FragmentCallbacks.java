package cricketapp.rakshith.com.cricketappadmin.interfaces;


import android.app.Fragment;

public interface FragmentCallbacks {

    public void addFragment(Fragment fragment, String mBackStack);

    public void replaceFragment(Fragment fragment, String mBackStack);

    public void clickAnalyticsEvent(String categoryId, String actionId, String labelId, long value);

    public Fragment getmFragment();

    public void popCurrentFragment();
}