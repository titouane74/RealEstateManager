package com.openclassrooms.realestatemanager.view.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding;
import com.openclassrooms.realestatemanager.model.RealEstateComplete;
import com.openclassrooms.realestatemanager.view.adapters.ReListAdapter;
import com.openclassrooms.realestatemanager.view.fragments.LoanFragment;
import com.openclassrooms.realestatemanager.view.fragments.MapsFragment;
import com.openclassrooms.realestatemanager.view.fragments.ReAddEditFragment;
import com.openclassrooms.realestatemanager.view.fragments.ReDetailFragment;
import com.openclassrooms.realestatemanager.view.fragments.ReListFragment;
import com.openclassrooms.realestatemanager.view.fragments.ReSearchFragment;
import com.openclassrooms.realestatemanager.view.fragments.RightFragment;

import java.util.ArrayList;
import java.util.List;

import static com.openclassrooms.realestatemanager.AppRem.sApi;

public class MainActivity extends AppCompatActivity implements ReAddEditFragment.OnSaveListener,
        ReSearchFragment.OnSearchListener, ReListAdapter.OnRecyclerViewListener,
        ReDetailFragment.OnClickListener, MapsFragment.OnMarkerClick {

    public static final String TAG = "TAG_";

    public static final String TAG_FRAG_LIST = "TAG_FRAG_LIST";

    private Context mContext;
    private ActivityMainBinding mBinding;
    private ReListFragment mListFragment;
    private RightFragment mRightFragment;
    private ReAddEditFragment mReAddEditFragment;
    private ReDetailFragment mReDetailFragment;
    private LoanFragment mLoanFragment;
    private ReSearchFragment mReSearchFragment;
    private MapsFragment mMapsFragment;

    private boolean mIsTablet;
    private long mBackPressedTime;
    private Toast mBackToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mBinding.getRoot());
        mContext = this;

        mIsTablet = getApplicationContext().getResources().getBoolean(R.bool.isTablet);
        setSupportActionBar(mBinding.actMainToolbar);

        if (savedInstanceState != null) {
            Fragment lListFragment = getSupportFragmentManager().getFragment(savedInstanceState, TAG_FRAG_LIST);

            if (lListFragment != null) {
                manageRestoreActiveFragment(lListFragment);
            }
        } else {
            if (mListFragment == null) configureAndShowListFragment();
        }
        initializeFragment();

        if (mIsTablet) {
            if (mRightFragment == null) configureAndShowRightFragment();
        }
    }

    private void initializeFragment() {
        if (mReAddEditFragment == null) mReAddEditFragment = new ReAddEditFragment();
        if (mReDetailFragment == null) mReDetailFragment = new ReDetailFragment();
        if (mLoanFragment == null) mLoanFragment = new LoanFragment();
        if (mReSearchFragment == null) mReSearchFragment = new ReSearchFragment();
        if (mMapsFragment == null) mMapsFragment = new MapsFragment();
    }

    private void manageRestoreActiveFragment(Fragment pFragment) {
        if (pFragment instanceof ReDetailFragment) {
            mReDetailFragment = (ReDetailFragment) pFragment;
            manageActionBar(true);
        } else if (pFragment instanceof ReAddEditFragment) {
            mReAddEditFragment = (ReAddEditFragment) pFragment;
            manageActionBar(true);
        } else if (pFragment instanceof ReSearchFragment) {
            mReSearchFragment = (ReSearchFragment) pFragment;
            manageActionBar(true);
        } else if (pFragment instanceof LoanFragment) {
            mLoanFragment = (LoanFragment) pFragment;
            manageActionBar(true);
        } else if (pFragment instanceof MapsFragment) {
            mMapsFragment = (MapsFragment) pFragment;
            manageActionBar(true);
        } else if (pFragment instanceof ReListFragment) {
            configureAndShowListFragment();
        }
        if (mListFragment == null) mListFragment = new ReListFragment();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Fragment lFragment = getSupportFragmentManager().findFragmentById(R.id.frame_list);
        if (lFragment != null) {
            getSupportFragmentManager().putFragment(outState, TAG_FRAG_LIST, lFragment);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu pMenu) {
        getMenuInflater().inflate(R.menu.menu_general, pMenu);
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        switch (pItem.getItemId()) {
            case android.R.id.home:
                if ((mReAddEditFragment != null && mReAddEditFragment.isVisible())
                        || (mReDetailFragment != null && mReDetailFragment.isVisible())
                        || (mLoanFragment != null && mLoanFragment.isVisible())
                        || (mReSearchFragment != null && mReSearchFragment.isVisible())
                        || (mMapsFragment != null && mMapsFragment.isVisible())
                ) {
                    if (mIsTablet) {
                        replaceFragment(mRightFragment);
                    } else {
                        replaceFragment(mListFragment);
                    }
                }
                manageActionBar(false);
                return true;
            case R.id.action_add:
                manageActionBar(true);
                if ((mListFragment != null && mListFragment.isVisible())) {
                    mReAddEditFragment = new ReAddEditFragment();
                    replaceFragment(mReAddEditFragment);
                    invalidateOptionsMenu();
                }
                return true;
            case R.id.action_loan:
                manageActionBar(true);
                if ((mListFragment != null && mListFragment.isVisible())) {
                    mLoanFragment = new LoanFragment();
                    replaceFragment(mLoanFragment);
                    invalidateOptionsMenu();
                }
                return true;
            case R.id.action_search:
                manageActionBar(true);
                if ((mListFragment != null && mListFragment.isVisible())) {
                    mReSearchFragment = new ReSearchFragment();
                    replaceFragment(mReSearchFragment);
                    invalidateOptionsMenu();
                }
                return true;
            case R.id.action_clear_search:
                sApi.setSearchResult(null);
                List<RealEstateComplete> lReComp = new ArrayList<>();
                mListFragment.updateList(lReComp);
                return true;
            case R.id.action_map:
                manageActionBar(true);
                if ((mListFragment != null && mListFragment.isVisible())) {
                    replaceFragment(mMapsFragment);
                    invalidateOptionsMenu();
                }
                return true;
            default:
                return super.onOptionsItemSelected(pItem);
        }
    }

    private void configureAndShowFrameFragment(int pIdFrame, Fragment pFragment) {
        getSupportFragmentManager().beginTransaction()
                .add(pIdFrame, pFragment)
                .commit();
    }

    private void configureAndShowListFragment() {
        mListFragment = (ReListFragment) getSupportFragmentManager().findFragmentById(R.id.frame_list);
        if (mListFragment == null) {
            mListFragment = new ReListFragment();
            configureAndShowFrameFragment(R.id.frame_list, mListFragment);
        }
    }

    private void configureAndShowRightFragment() {
        mRightFragment = (RightFragment) getSupportFragmentManager().findFragmentById(R.id.frame_right);
        if (mRightFragment == null && findViewById(R.id.frame_right) != null) {
            mRightFragment = new RightFragment();
            configureAndShowFrameFragment(R.id.frame_right, mRightFragment);
        }
    }

    private void replaceFragment(final Fragment pFragment) {
        final FragmentManager lFragmentManager = getSupportFragmentManager();
        final FragmentTransaction lFragmentTransaction = lFragmentManager.beginTransaction();
        lFragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
        if (mIsTablet) {
            lFragmentTransaction.replace(R.id.frame_right, pFragment);
        } else {
            lFragmentTransaction.replace(R.id.frame_list, pFragment);
        }
        lFragmentTransaction.commit();
    }

    private void manageActionBar(boolean isEnabled) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(isEnabled);
            getSupportActionBar().setDisplayShowHomeEnabled(isEnabled);
        }
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
    public void navigateToList() {
        if (mReAddEditFragment != null && mReAddEditFragment.isVisible()) {
            if (mIsTablet) {
                replaceFragment(mRightFragment);
            } else {
                replaceFragment(mListFragment);
            }
        }
        manageActionBar(false);
        invalidateOptionsMenu();
    }

    @Override
    public void updateList(List<RealEstateComplete> pReCompList) {
        if (mReSearchFragment != null && mReSearchFragment.isVisible()) {
            if (mIsTablet) {
                replaceFragment(mRightFragment);
            } else {
                replaceFragment(mListFragment);
            }
        }
        manageActionBar(false);
        mListFragment.updateList(pReCompList);
        invalidateOptionsMenu();
    }

    @Override
    public void onListAdapterItemClicked(Bundle pBundle) {
        manageActionBar(true);
        if (mReDetailFragment == null) mReDetailFragment = new ReDetailFragment();
        mReDetailFragment.setArguments(pBundle);
        replaceFragment(mReDetailFragment);
    }

    @Override
    public void navigateAddEdit(Bundle pBundle) {
        manageActionBar(true);
        if (mReAddEditFragment == null) mReAddEditFragment = new ReAddEditFragment();
        mReAddEditFragment.setArguments(pBundle);
        replaceFragment(mReAddEditFragment);
    }

    @Override
    public void navigateDetail(Bundle pBundle) {
        manageActionBar(true);
        if (mReDetailFragment == null) mReDetailFragment = new ReDetailFragment();
        mReDetailFragment.setArguments(pBundle);
        replaceFragment(mReDetailFragment);
    }
}
