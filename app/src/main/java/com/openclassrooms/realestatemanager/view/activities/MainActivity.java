package com.openclassrooms.realestatemanager.view.activities;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.view.fragments.POIDialogFragment;
import com.openclassrooms.realestatemanager.view.fragments.REAddFragment;
import com.openclassrooms.realestatemanager.view.fragments.REListFragmentDirections;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;
import com.wangjie.rapidfloatingactionbutton.util.RFABTextUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener ,
        POIDialogFragment.OnDialogPOIListener {

    public static final String TAG = "TAG_MAIN";

    //DESIGN
    private Toolbar mToolbar;
    private RapidFloatingActionLayout mRFALayout;
    private RapidFloatingActionButton mRFAButton;
    private RapidFloatingActionHelper mRFAHelper;
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
        configureToolBar();
        configureNavController();
        configureRapidFloatButton();

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
    }

    /**
     * Configure the rapide floating action button
     */
    @SuppressLint("ResourceType")
    private void configureRapidFloatButton() {
        RapidFloatingActionContentLabelList lRFAContent = new RapidFloatingActionContentLabelList(mContext);
        lRFAContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> lItems = new ArrayList<>();
        lItems.add(new RFACLabelItem<Integer>()
                .setLabel(getString(R.string.rfab_txt_item_add_re))
                .setResId(R.drawable.ic_type)
                .setIconNormalColor(ContextCompat.getColor(mContext,R.color.rfab_color_bckgrd_normal_add_re))
                .setIconPressedColor(ContextCompat.getColor(mContext,R.color.rfab_color_bckgrd_pressed_add_re))
                .setWrapper(0)
        );
        lItems.add(new RFACLabelItem<Integer>()
                .setLabel(getString(R.string.rfab_txt_item_view_det_re))
                .setResId(R.drawable.ic_type)
                .setIconNormalColor(ContextCompat.getColor(mContext,R.color.rfab_color_bckgrd_normal_add_re))
                .setIconPressedColor(ContextCompat.getColor(mContext,R.color.rfab_color_bckgrd_pressed_add_re))
                .setWrapper(1)
        );
        lItems.add(new RFACLabelItem<Integer>()
                .setLabel(getString(R.string.rfab_txt_item_add_agent))
                .setResId(R.drawable.ic_agent)
                .setIconNormalColor(ContextCompat.getColor(mContext,R.color.rfab_color_bckgrd_normal_add_agent))
                .setIconPressedColor(ContextCompat.getColor(mContext,R.color.rfab_color_bckgrd_pressed_add_agent))
                .setWrapper(2)
        );
        lRFAContent
                .setItems(lItems)
                .setIconShadowRadius(RFABTextUtil.px2dip(mContext,3F))
                .setIconShadowColor(ContextCompat.getColor(mContext,R.color.rfab_color_bckgrd))
                .setIconShadowDy(RFABTextUtil.px2dip(mContext,3F))
        ;
        mRFAHelper = new RapidFloatingActionHelper(
                mContext,
                mRFALayout,
                mRFAButton,
                lRFAContent
        ).build();
    }

    private void bindView() {
        mMainLayout = findViewById(R.id.act_main);
        mToolbar = findViewById(R.id.act_main_toolbar);
        mRFALayout = findViewById(R.id.act_main_rfal);
        mRFAButton = findViewById(R.id.act_main_rfab);
    }

    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        mRFAHelper.toggleContent();
        Log.d(TAG, "onRFACItemLabelClick: " + item.getLabel());
        manageRFABClick(position);
    }

    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        mRFAHelper.toggleContent();
        manageRFABClick(position);
    }

    private void manageRFABClick(int pPosition) {
        switch (pPosition) {
            case 0 :
                Log.d(TAG, "onNavToAdd: JE PASSE : " + mRFAButton.getVisibility());
                mNavController.navigate(R.id.nav_re_add);
                mRFAButton.setVisibility(View.INVISIBLE);
                break;
            case 1 :
                RealEstate lRE = new RealEstate(1,"apartment", 250000,100,5,2,1,"With garden and a box",false);
                REListFragmentDirections.ActionNavReListToNavReDetail lAction = REListFragmentDirections.actionNavReListToNavReDetail(lRE);
                lAction.setReid(1);
                mNavController.navigate(lAction);
                mRFAButton.setVisibility(View.INVISIBLE);
                break;
            case 2 :
                mNavController.navigate(R.id.nav_agent_add);
                mRFAButton.setVisibility(View.INVISIBLE);
                break;        }
    }


    @Override
    public void onBackPressed() {
        if (mBackPressedTime + 2000 > System.currentTimeMillis()) {
            mBackToast.cancel();
            super.onBackPressed();
            return;
        } else {
            mBackToast = Toast.makeText(mContext, R.string.exit_app_back_pressed, Toast.LENGTH_SHORT);
            mBackToast.show();
        }
        mBackPressedTime = System.currentTimeMillis();
        //TODO to reactivate with the toolbar managment on the REAddFragment
        mNavController.navigate(R.id.nav_re_list);
        mRFAButton.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRfabVisible();
    }

    private void setRfabVisible() {
        mRFAButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPOIOkClicked(List<String> pList) {
        Fragment lFragment = getSupportFragmentManager().getPrimaryNavigationFragment();
        if (lFragment != null) {
            Log.d(TAG, "onPOIOkClicked: " + lFragment.getId() + lFragment.getTag() );
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
