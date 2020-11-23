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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.view.fragments.REAddFragment;
import com.openclassrooms.realestatemanager.view.fragments.REListFragment;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;
import com.wangjie.rapidfloatingactionbutton.util.RFABTextUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener {

    public static final String TAG = "TAG_MAIN";

    //DESIGN
    private Toolbar mToolbar;
    private RapidFloatingActionLayout mRFALayout;
    private RapidFloatingActionButton mRFAButton;
    private RapidFloatingActionContentLabelList mRFAContent;
    private RapidFloatingActionHelper mRFAHelper;
    private REListFragment mREListFragment;
    private REAddFragment mREAddFragment;
    private View mMainLayout;


    //LIFECYCLE
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = getApplicationContext();

        bindView();
        configureToolBar();
        configureRapidFloatButton();
        configureAndShowListFragment();
    }

    /**
     * Initialize and display the fragment with the real estate list
     */
    private void configureAndShowListFragment() {
        mREListFragment = (REListFragment) getSupportFragmentManager().findFragmentById(R.id.act_main_frame_list);
        if (mREListFragment == null) {
            mREListFragment = new REListFragment();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.act_main_frame_list, mREListFragment)
                    .commit();
        }
    }

    /**
     * Replace the displayed fragment
     * Take in account if it's a tablet or not
     * @param pFragment : fragment : fragment to display
     */
    private void replaceFragment(final Fragment pFragment) {
        final FragmentManager lFragmentManager = getSupportFragmentManager();
        final FragmentTransaction lFragmentTransaction = lFragmentManager.beginTransaction();
//        lFragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right);
//        if (mMainLayout.getTag() == getString(R.string.app_support_tablet)) {
//            lFragmentTransaction.replace(R.id.frame_right, pFragment);
//        } else {
            lFragmentTransaction.replace(R.id.act_main_frame_list, pFragment);
//        }
        lFragmentTransaction.commit();
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
        mRFAContent = new RapidFloatingActionContentLabelList(mContext);
        mRFAContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> lItems = new ArrayList<>();
        lItems.add(new RFACLabelItem<Integer>()
                .setLabel(getString(R.string.rfab_txt_item_add_re))
                .setResId(R.drawable.ic_type)
                .setIconNormalColor(ContextCompat.getColor(mContext,R.color.rfab_color_bckgrd_normal_add_re))
                .setIconPressedColor(ContextCompat.getColor(mContext,R.color.rfab_color_bckgrd_pressed_add_re))
                .setWrapper(0)
        );
        lItems.add(new RFACLabelItem<Integer>()
                .setLabel(getString(R.string.rfab_txt_item_add_agent))
                .setResId(R.drawable.ic_agent)
                .setIconNormalColor(ContextCompat.getColor(mContext,R.color.rfab_color_bckgrd_normal_add_agent))
                .setIconPressedColor(ContextCompat.getColor(mContext,R.color.rfab_color_bckgrd_pressed_add_agent))
                .setWrapper(1)
        );
        mRFAContent
                .setItems(lItems)
                .setIconShadowRadius(RFABTextUtil.px2dip(mContext,3F))
                .setIconShadowColor(ContextCompat.getColor(mContext,R.color.colorAccent))
                .setIconShadowDy(RFABTextUtil.px2dip(mContext,3F))
        ;
        mRFAHelper = new RapidFloatingActionHelper(
                mContext,
                mRFALayout,
                mRFAButton,
                mRFAContent
        ).build();
    }

    private void bindView() {
        mToolbar = findViewById(R.id.act_main_toolbar);
        mRFALayout = findViewById(R.id.act_main_rfal);
        mRFAButton = findViewById(R.id.act_main_rfab);
        mMainLayout = findViewById(R.id.act_main);
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
                mREAddFragment = new REAddFragment();
                replaceFragment(mREAddFragment);
                mRFAButton.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        //TODO to reactivate with the toolbar managment on the REAddFragment
        replaceFragment(mREListFragment);
        mRFAButton.setVisibility(View.VISIBLE);
    }
}
