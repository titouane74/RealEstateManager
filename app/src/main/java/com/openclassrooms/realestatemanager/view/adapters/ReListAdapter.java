package com.openclassrooms.realestatemanager.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentReListItemBinding;
import com.openclassrooms.realestatemanager.model.RealEstateComplete;
import com.openclassrooms.realestatemanager.utils.REMHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florence LE BOURNOT on 30/11/2020
 */
public class ReListAdapter extends RecyclerView.Adapter<ReListAdapter.ReListHolder> {

    public static final String RE_ID_KEY = "RE_ID";
    public static final String IS_EDIT_KEY = "IS_EDIT";

    private static final String TAG = "TAG_ReListAdapter";
    private List<RealEstateComplete> mReList = new ArrayList<>();
    private com.openclassrooms.realestatemanager.databinding.FragmentReListItemBinding mBinding;
    private NavController mNavController;
    private Context mContext;
    private boolean mIsTablet;


    public void setReList(List<RealEstateComplete> pReList) {
        mReList = pReList;
    }

    @NonNull
    @Override
    public ReListAdapter.ReListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lLayoutInflater = LayoutInflater.from(parent.getContext());
        mBinding = FragmentReListItemBinding.inflate(lLayoutInflater, parent, false);
        mContext = mBinding.getRoot().getContext();
        mIsTablet =  mContext.getResources().getBoolean(R.bool.isTablet);
        int lIntNavHost = REMHelper.getNavHostId(mContext, mIsTablet);
        mNavController = Navigation.findNavController((Activity) parent.getContext(), lIntNavHost);
        return new ReListAdapter.ReListHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReListHolder pHolder, int position) {
        pHolder.bindView(mReList.get(position));

        pHolder.itemView.setOnClickListener(v -> {
            Bundle lBundle = new Bundle();
            lBundle.putLong(RE_ID_KEY,mReList.get(position).getRealEstate().getReId());

            if (REMHelper.isTabletLandscape(mContext,mIsTablet)) {
                mNavController.navigate(R.id.reDetailFragment,lBundle);
            } else {
//TODO
// Deactivation of the safe args with the navigation component causing a build warning
//                ReListFragmentDirections.ActionReListFragmentToReDetailFragment lAction =ReListFragmentDirections.actionReListFragmentToReDetailFragment();
//                lAction.setReid(mReList.get(position).getReId());
//                mNavController.navigate(lAction);
                mNavController.navigate(R.id.action_reListFragment_to_reDetailFragment,lBundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mReList == null) {
            return 0;
        } else {
            return mReList.size();
        }
    }


    static class ReListHolder extends RecyclerView.ViewHolder {
        com.openclassrooms.realestatemanager.databinding.FragmentReListItemBinding mBindingHolder;

        public ReListHolder(@NonNull FragmentReListItemBinding pBindingHolder) {
            super(pBindingHolder.getRoot());
            mBindingHolder = pBindingHolder;
        }

        public void bindView(RealEstateComplete pReComp) {
            String lCity = pReComp.getReLocation().getLocCity() != null ? pReComp.getReLocation().getLocCity() : "";
            mBindingHolder.itemCity.setText(lCity);
            mBindingHolder.itemPrice.setText(REMHelper.formatNumberWithCommaAndCurrency(pReComp.getRealEstate().getRePrice()));
            mBindingHolder.itemType.setText(pReComp.getRealEstate().getReType());
            if(pReComp.getRealEstate().isReIsSold()) {
                mBindingHolder.fragReListItemImgSold.setVisibility(View.VISIBLE);
            } else {
                mBindingHolder.fragReListItemImgSold.setVisibility(View.INVISIBLE);
            }
            if(!pReComp.getRealEstate().isReIsMandatoryDataComplete()) {
                mBindingHolder.fragReListItemImgDataMissing.setVisibility(View.VISIBLE);
            } else {
                mBindingHolder.fragReListItemImgDataMissing.setVisibility(View.INVISIBLE);
            }
            if (pReComp.getRePhotoList().size()>0) {
                if (pReComp.getRePhotoList().get(0).getPhPath() != null) {
                    Glide.with(mBindingHolder.fragReListItemImgPhoto.getContext())
                            .load(pReComp.getRePhotoList().get(0).getPhPath())
                            .into(mBindingHolder.fragReListItemImgPhoto);
                }
            }

        }
    }
}
