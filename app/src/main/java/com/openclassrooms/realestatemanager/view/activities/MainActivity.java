package com.openclassrooms.realestatemanager.view.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "TAG_MAIN";

    private ActivityMainBinding mBinding;
    private View mActView;

    private NavController mNavController;
    private long mBackPressedTime;
    private Toast mBackToast;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        NavHostFragment lNavHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (lNavHostFragment != null) {
            mNavController = lNavHostFragment.getNavController();
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
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
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

}
