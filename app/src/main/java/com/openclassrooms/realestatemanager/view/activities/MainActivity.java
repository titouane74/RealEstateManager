package com.openclassrooms.realestatemanager.view.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding;
import com.openclassrooms.realestatemanager.utils.REMHelper;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.view.fragments.ReListFragment;

import java.util.List;

import static com.openclassrooms.realestatemanager.AppRem.sApi;

//public class MainActivity extends AppCompatActivity implements ReSearchFragment.OnSearchResult {
    public class MainActivity extends AppCompatActivity  {

    public static final String TAG = "TAG_MAIN";

//    public static final REMApi sApi = Injection.getREMApiService();

    private ActivityMainBinding mBinding;
    private View mActView;

    private NavController mNavController;
    private NavHostFragment mNavHostFragment;
    private long mBackPressedTime;
    private Toast mBackToast;

    private Context mContext;
    private int mIntNavHost = 0;
    private boolean mIsTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mIsTablet = getApplicationContext().getResources().getBoolean(R.bool.isTablet);

        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        mActView = mBinding.getRoot();
        mContext = getApplicationContext();
        setContentView(mActView);

        configureNavController();
        configureToolBar();
        Log.d(TAG, "onCreate : current frag : " + mNavHostFragment.getChildFragmentManager());

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    private void configureNavController() {

        mIntNavHost = REMHelper.getNavHostId(mContext,mIsTablet);
        mNavHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(mIntNavHost);
        if (mNavHostFragment != null) {
            mNavController = mNavHostFragment.getNavController();
        } else {
            Toast.makeText(mContext, "NavHost null", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "configureNavController: navHost null");
        }
    }

    /**
     * Configure the toolbar
     */
    private void configureToolBar() {
        setSupportActionBar(mBinding.actMainToolbar);
        NavigationUI.setupActionBarWithNavController(this, mNavController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        return mNavController.navigateUp() || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (mBackPressedTime + 2000 > System.currentTimeMillis()) {
            mBackToast.cancel();
            super.onBackPressed();
            return;
        } else {
            mBackToast = Toast.makeText(mContext, getString(R.string.exit_app_back_pressed), Toast.LENGTH_SHORT);
            mBackToast.show();
        }
        mBackPressedTime = System.currentTimeMillis();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        if (REMHelper.isTabletLandscape(mContext,mIsTablet)) {
            switch (pItem.getItemId()) {
                case R.id.reAddEditFragment:
                    mNavController.navigate(R.id.action_reDetailFragment_to_reAddEditFragment);
                    break;
                case R.id.loanFragment:
                    mNavController.navigate(R.id.action_reDetailFragment_to_loanFragment);
                    break;
                case R.id.reSearchFragment:
                    mNavController.navigate(R.id.action_reDetailFragment_to_reSearchFragment);
                    break;
                default:
                    return super.onOptionsItemSelected(pItem);
            }
        } else if (pItem.getItemId() == R.id.menu_clear_search) {
            sApi.setSearchResult(null);
            mNavController.navigate(R.id.reListFragment);
        } else if (pItem.getItemId() == R.id.reMapsFragment) {
            if (!Utils.isInternetAvailable(this)) {
                Toast.makeText(this, getString(R.string.default_txt_no_internet_no_map), Toast.LENGTH_SHORT).show();
            } else {
                mNavController.navigate(R.id.action_reListFragment_to_mapsFragment);
            }
        } else {
            return super.onOptionsItemSelected(pItem);
        }
        return true;
    }

/*
    @Override
    public void onSearchResult(List<RealEstateComplete> pReCompList) {

        //ReListFragment lFrag = (ReListFragment) getSupportFragmentManager().findFragmentById(R.id.frag_re_list_rv);
//        ReListFragment lFrag = (ReListFragment) mNavHostFragment.getChildFragmentManager().findFragmentById(R.id.frag_re_list_rv);
//        lFrag.onSearchResult(pReCompList);
//        Log.d(TAG, "onSearchResult: current frag : " + mNavHostFragment.getChildFragmentManager().getPrimaryNavigationFragment());
//        Log.d(TAG, "onSearchResult: current frag : " + mNavHostFragment.getChildFragmentManager().getFragments().get(0));
//
//        ReListFragment lReListFragment = getReListFragmentActualInstance();
//        lReListFragment.onSearchResult(pReCompList);
    }

*/


    /**
     * Retrieve the actual instance of the ReListFragment
     *
     * @return : object : ReListFragment instance
     */
    private ReListFragment getReListFragmentActualInstance() {
        ReListFragment resultFragment = null;
        Fragment navHostFragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (navHostFragment != null) {
            List<Fragment> fragmentList = navHostFragment.getChildFragmentManager().getFragments();
            for (Fragment fragment : fragmentList) {
                if (fragment instanceof ReListFragment) {
                    resultFragment = (ReListFragment) fragment;
                    break;
                }
            }
        }
        return resultFragment;
    }


/*
// IN MAIN ACTIVITY MAREU

    private void replaceFragment(final Fragment pFragment) {
        final FragmentManager lFragmentManager = getSupportFragmentManager();
        final FragmentTransaction lFragmentTransaction = lFragmentManager.beginTransaction();
        lFragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        if (mMainLayout.getTag() == getString(R.string.tablet)) {
            lFragmentTransaction.replace(R.id.frame_right, pFragment);
        } else {
            lFragmentTransaction.replace(R.id.frame_list, pFragment);
        }
        lFragmentTransaction.commit();
    }*/

}
