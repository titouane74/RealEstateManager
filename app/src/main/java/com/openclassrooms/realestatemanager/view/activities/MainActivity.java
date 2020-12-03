package com.openclassrooms.realestatemanager.view.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
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
        if (mIsTablet) {
            Toast.makeText(getApplicationContext(), "OUI, JE SUIS UNE TABLETTE !", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "OUI, JE SUIS UN TELEPHONE !", Toast.LENGTH_SHORT).show();
        }

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
        boolean lIsTablet = mContext.getResources().getBoolean(R.bool.isTablet);
        if (lIsTablet) {
            mIntNavHost = R.id.nav_right_fragment;
        } else {
            mIntNavHost = R.id.nav_host_fragment;
        }
//        mNavHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(mIntNavHost);
        mNavHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
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
        Log.d(TAG, "onOptionsItemSelected: " + pItem.getItemId());
        Log.d(TAG, "onOptionsItemSelected: main activity menu action edit : " + R.id.menu_action_edit);
        Log.d(TAG, "onOptionsItemSelected:  main activity detail to loan : " + R.id.action_reDetailFragment_to_loanFragment);
        Log.d(TAG, "onOptionsItemSelected:  main activity detail to search : " + R.id.action_reDetailFragment_to_reSearchFragment);
        Log.d(TAG, "onOptionsItemSelected: main activity detail to edit : " + R.id.action_reDetailFragment_to_reAddEditFragment);

        if (mIsTablet) {
            switch (pItem.getItemId()) {
                case R.id.menu_action_edit:
                    Log.d(TAG, "onOptionsItemSelected: main activity menu action edit : " + pItem.getItemId());
                    mNavController.navigate(R.id.reAddEditFragment);
                    break;
                case R.id.action_reDetailFragment_to_loanFragment:
                    Log.d(TAG, "onOptionsItemSelected:  main activity detail to loan : " + pItem.getItemId());
                    mNavController.navigate(R.id.action_reDetailFragment_to_loanFragment);
                    break;
                case R.id.action_reDetailFragment_to_reSearchFragment:
                    Log.d(TAG, "onOptionsItemSelected:  main activity detail to search : " + pItem.getItemId());
                    mNavController.navigate(R.id.action_reDetailFragment_to_reSearchFragment);
                    break;
                case R.id.action_reDetailFragment_to_reAddEditFragment:
                    Log.d(TAG, "onOptionsItemSelected: main activity detail to edit : " + pItem.getItemId());
                    mNavController.navigate(R.id.action_reDetailFragment_to_reAddEditFragment);
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
