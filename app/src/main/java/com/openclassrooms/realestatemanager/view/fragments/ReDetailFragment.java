package com.openclassrooms.realestatemanager.view.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentReDetailBinding;
import com.openclassrooms.realestatemanager.di.Injection;
import com.openclassrooms.realestatemanager.di.ReViewModelFactory;
import com.openclassrooms.realestatemanager.model.RePoi;
import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.model.RealEstateComplete;
import com.openclassrooms.realestatemanager.utils.REMHelper;
import com.openclassrooms.realestatemanager.view.adapters.DetailPhotoAdapter;
import com.openclassrooms.realestatemanager.viewmodel.ReDetailViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.openclassrooms.realestatemanager.view.adapters.ReListAdapter.IS_EDIT_KEY;
import static com.openclassrooms.realestatemanager.view.adapters.ReListAdapter.RE_ID_KEY;

public class ReDetailFragment extends BaseFragment<FragmentReDetailBinding> {

    private static final String TAG = "REDetailFragment";
    private FragmentReDetailBinding mBinding;
    private DetailPhotoAdapter mAdapter;
    private View mFragView;
    private ReDetailViewModel mViewModel;
    private Context mContext;
    private NavController mNavController;
    private boolean mIsTabletLandscape;
    private RealEstate mRE;
    private int mReId;

    @Override
    protected int getMenuAttached() {
//        if (mIsTabletLandscape) {
//            return R.menu.menu_general_tablet;
//        } else {
        return R.menu.menu_edit;
//        }
    }

    @Override
    protected void configureDesign(FragmentReDetailBinding pBinding, NavController pNavController, boolean pIsTablet, boolean pIsTabletLandscape) {
        mBinding = pBinding;
        mFragView = mBinding.getRoot();
        mContext = getContext();
        mNavController = pNavController;
        mIsTabletLandscape = pIsTabletLandscape;
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new DetailPhotoAdapter();
        mBinding.fragReDetRvPhoto.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mBinding.fragReDetRvPhoto.setAdapter(mAdapter);
        initPhotoList();
    }

    private void initPhotoList() {
        List<String> lPhotoList = new ArrayList<>();
        lPhotoList.add("https://lh3.googleusercontent.com/a-/AOh14Gh7GajhYVm2T1esN8y8XZF7Iz6HzjC0ugJkk2dN7g=s96-c");
        lPhotoList.add("https://lh3.googleusercontent.com/a-/AOh14Gj0Y3MR2L_u0rFtMCji9r5CmQzR5PDKlZkB9zc9");
        lPhotoList.add("https://lh3.googleusercontent.com/a-/AOh14GgOP8seJeI1oZImU6EZHTL3WSJtWcUOMDzMvel07w=s96-c");
        lPhotoList.add("https://lh3.googleusercontent.com/a-/AOh14Gj0Y3MR2L_u0rFtMCji9r5CmQzR5PDKlZkB9zc9");
        lPhotoList.add("https://lh3.googleusercontent.com/a-/AOh14GgOP8seJeI1oZImU6EZHTL3WSJtWcUOMDzMvel07w=s96-c");
        lPhotoList.add("https://lh3.googleusercontent.com/a-/AOh14Gj0Y3MR2L_u0rFtMCji9r5CmQzR5PDKlZkB9zc9");
        mAdapter.setPhotoList(lPhotoList);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
//        if (pItem.getItemId() == R.id.menu_action_edit) {
        Log.d(TAG, "onOptionsItemSelected: detail :  " + pItem.getItemId());
        Log.d(TAG, "onOptionsItemSelected: detail : R.id.reAddEditFragment : " + R.id.reAddEditFragment);
        if (pItem.getItemId() == R.id.reAddEditFragment) {
            Bundle lBundle = new Bundle();
            lBundle.putInt(RE_ID_KEY, mReId);
            lBundle.putBoolean(IS_EDIT_KEY, true);
            Log.d(TAG, "onOptionsItemSelected: detail envoi reId : " + mReId);
//            if (mIsTabletLandscape) {
//                mNavController.navigate(R.id.reAddEditFragment,lBundle);
//                Log.d(TAG, "onOptionsItemSelected: detail fragment tablet detail to edit");
//            } else {
            mNavController.navigate(R.id.action_reDetailFragment_to_reAddEditFragment, lBundle);
//                Log.d(TAG, "onOptionsItemSelected: detail fragment phone detail to edit");
//            }
            return true;
        }
        return super.onOptionsItemSelected(pItem);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
//TODO
// Deactivation of the safe args with the navigation component causing a build warning
/*
            if (!mIsTabletLandscape) {
                ReDetailFragmentArgs lArgs = ReDetailFragmentArgs.fromBundle(getArguments());
                mReId = lArgs.getReid();
                Log.i(TAG, "onViewCreated: " + mReId);
            } else {
                mReId = getArguments().getInt(RE_ID_KEY);
                Log.d(TAG, "onViewCreated: detail mReId: " + mReId);
            }
*/
            mReId = getArguments().getInt(RE_ID_KEY);
            Log.d(TAG, "onViewCreated: detail mReId: " + mReId);
        }
    }

    @SuppressLint("SetTextI18n")
    private void displayRealEstate(RealEstate pRe) {
        if (pRe != null) {
            mBinding.fragReDetTvDescription.setText(pRe.getReDescription());
            mBinding.fragReDetTvAgent.setText(pRe.getReAgentFirstName() + " " + pRe.getReAgentLastName());
            mBinding.fragReDetTvPrice.setText(REMHelper.formatNumberWithCommaAndCurrency(pRe.getRePrice()));
            mBinding.fragReDetTvType.setText(pRe.getReType());
            mBinding.fragReDetTvArea.setText(Integer.toString(pRe.getReArea()));
            mBinding.fragReDetTvNbRooms.setText(Integer.toString(pRe.getReNbRooms()));
            mBinding.fragReDetTvNbBedrooms.setText(Integer.toString(pRe.getReNbBedrooms()));
            mBinding.fragReDetTvNbBathrooms.setText(Integer.toString(pRe.getReNbBathrooms()));
        }
    }

    private void configureViewModel() {
        ReViewModelFactory lFactory = Injection.reViewModelFactory(mContext);
        mViewModel = new ViewModelProvider(this, lFactory).get(ReDetailViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configureViewModel();
/*
        mViewModel.selectRealEstate(mReId).observe(getViewLifecycleOwner(), pRealEstate -> {
            displayRealEstate(pRealEstate);
        });
*/
        mViewModel.selectReComplete(mReId).observe(getViewLifecycleOwner(), pRealEstateComplete -> {
            Log.d(TAG, "onActivityCreated: " + pRealEstateComplete.mPoiList.size());
            Log.d(TAG, "onActivityCreated: " + pRealEstateComplete.getPoiList().size());
            displayReComplete(pRealEstateComplete);
        });
    }

    @SuppressLint("SetTextI18n")
    private void displayReComplete(RealEstateComplete pRe) {
        if (pRe != null) {
            Log.d(TAG, "displayReComplete: poi list : " + pRe.getPoiList() + " size : " + pRe.getPoiList().size());
            for (RePoi lPoi : pRe.getPoiList()) {
                Log.d(TAG, "displayReComplete: poi : " + lPoi.getPoiName());
                if(lPoi.getPoiName().equals(getString(R.string.poi_restaurant))) {
                    mBinding.fragReDetPoiRestaurant.setSelected(true);
                } else if(lPoi.getPoiName().equals(getString(R.string.poi_subway))) {
                    mBinding.fragReDetPoiSubway.setSelected(true);
                } else if(lPoi.getPoiName().equals(getString(R.string.poi_school))) {
                    mBinding.fragReDetPoiSchool.setSelected(true);
                } else if(lPoi.getPoiName().equals(getString(R.string.poi_health))) {
                    mBinding.fragReDetPoiHealth.setSelected(true);
                } else if(lPoi.getPoiName().equals(getString(R.string.poi_food))) {
                    mBinding.fragReDetPoiFood.setSelected(true);
                } else if(lPoi.getPoiName().equals(getString(R.string.poi_bank))) {
                    mBinding.fragReDetPoiBank.setSelected(true);
                } else if(lPoi.getPoiName().equals(getString(R.string.poi_store))) {
                    mBinding.fragReDetPoiStore.setSelected(true);
                } else if(lPoi.getPoiName().equals(getString(R.string.poi_park))) {
                    mBinding.fragReDetPoiPark.setSelected(true);
                }
/*
                //TODO switch bug sur le getString
                switch (lPoi.getPoiName()) {
                    case getString(R.string.poi_restaurant) :
                        mBinding.fragReDetPoiRestaurant.setSelected(true);
                        break;
                    case getString(R.string.poi_subway) :
                        mBinding.fragReDetPoiRestaurant.setSelected(true);
                        break;                    default:
                        throw new IllegalStateException("Unexpected value: " + lPoi.getPoiName());
                }
*/
            }
            Log.d(TAG, "displayReComplete: agent last name : " + pRe.getRealEstate().getReAgentLastName());
                mBinding.fragReDetTvAgent.setText(pRe.getRealEstate().getReAgentFirstName()
                        + " " + pRe.getRealEstate().getReAgentLastName());

//            mBinding.fragReDetTvDescription.setText(pRe.getReDescription());
//            mBinding.fragReDetTvAgent.setText(pRe.getReAgentFirstName() + " " + pRe.getReAgentLastName());
//            mBinding.fragReDetTvPrice.setText(REMHelper.formatNumberWithCommaAndCurrency(pRe.getRePrice()));
//            mBinding.fragReDetTvType.setText(pRe.getReType());
//            mBinding.fragReDetTvArea.setText(Integer.toString(pRe.getReArea()));
//            mBinding.fragReDetTvNbRooms.setText(Integer.toString(pRe.getReNbRooms()));
//            mBinding.fragReDetTvNbBedrooms.setText(Integer.toString(pRe.getReNbBedrooms()));
//            mBinding.fragReDetTvNbBathrooms.setText(Integer.toString(pRe.getReNbBathrooms()));

        } else {
            Log.d(TAG, "displayReComplete: pRe is null");
        }
    }
}