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
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding;
import com.openclassrooms.realestatemanager.utils.REMHelper;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "TAG_MAIN";

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
        } else {
            return super.onOptionsItemSelected(pItem);
        }
        return true;
    }
}
