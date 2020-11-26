package com.openclassrooms.realestatemanager.view.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.view.fragments.POIDialogFragment;
import com.openclassrooms.realestatemanager.view.fragments.REAddFragment;

import java.util.List;

public class MainActivity extends AppCompatActivity implements POIDialogFragment.OnDialogPOIListener {

    public static final String TAG = "TAG_MAIN";

    //DESIGN
    private Toolbar mToolbar;
    private View mMainLayout;
    private NavController mNavController;
    private long mBackPressedTime;
    private Toast mBackToast;

    //LIFECYCLE
    private Context mContext;
    private REAddFragment mREAddFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();

        bindView();

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
        setSupportActionBar(mToolbar);
        NavigationUI.setupActionBarWithNavController(this, mNavController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        mNavController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return mNavController.navigateUp() || super.onSupportNavigateUp();
    }

    private void bindView() {
        mMainLayout = findViewById(R.id.act_main);
        mToolbar = findViewById(R.id.act_main_toolbar);
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        Log.d(TAG, "onOptionsItemSelected: " + pItem.getItemId());
        switch (pItem.getItemId()) {
            case R.id.nav_agent_add:
            case R.id.nav_re_add:
                NavigationUI.onNavDestinationSelected(pItem,mNavController);
                return true;
            case R.id.menu_action_edit_agent:
                return true;
            case R.id.menu_action_save_agent:
                return true;
            case R.id.menu_action_edit_re:
                return true;
            case R.id.menu_action_save_re:
                return true;
            default:
                return super.onOptionsItemSelected(pItem);
        }

    }
*/

    @Override
    public void onBackPressed() {
        if (mBackPressedTime + 2000 > System.currentTimeMillis()) {
            mBackToast.cancel();
            super.onBackPressed();
            return;
        }
        mBackPressedTime = System.currentTimeMillis();
        //TODO to reactivate with the toolbar managment on the REAddFragment
        mNavController.navigate(R.id.nav_re_list);
    }

    @Override
    public void onPOIOkClicked(List<String> pList) {
        Fragment lFragment = getSupportFragmentManager().getPrimaryNavigationFragment();
        if (lFragment != null) {
            Log.d(TAG, "onPOIOkClicked: " + lFragment.getId() + lFragment.getTag());
/*            if (lFragment instanceof REAddFragment) {
                REAddFragment mREAddFragment = (REAddFragment) lFragment;
                mREAddFragment.onPOIOkClicked(pList);
            }*/
        }

/*
        mREAddFragment = (REAddFragment) getSupportFragmentManager().findFragmentById(R.id.lay_frag_add);
        if (mREAddFragment == null) {
            mREAddFragment = new REAddFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.lay_frag_add, mREAddFragment)
                    .commit();
            mREAddFragment.onPOIOkClicked(pList);
        }
*/
    }
}
