package com.openclassrooms.realestatemanager.view.adapters;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentReListItemBinding;
import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.utils.REMHelper;
import com.openclassrooms.realestatemanager.view.fragments.ReListFragmentDirections;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florence LE BOURNOT on 30/11/2020
 */
public class ReListAdapter extends RecyclerView.Adapter<ReListAdapter.ReListHolder> {

    public static final String RE_ID_KEY = "RE_ID";
    public static final String IS_EDIT_KEY = "IS_EDIT";

    private static final String TAG = "TAG_ReListAdapter";
    private List<RealEstate> mReList = new ArrayList<>();
    private com.openclassrooms.realestatemanager.databinding.FragmentReListItemBinding mBinding;
    private NavController mNavController;
    private Context mContext;
    private boolean mIsTablet;


    public void setReList(List<RealEstate> pReList) {
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
            lBundle.putInt(RE_ID_KEY,mReList.get(position).getReId());

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

        public void bindView(RealEstate pRealEstate) {
            mBindingHolder.itemCity.setText(pRealEstate.getReAgentFirstName());
            mBindingHolder.itemPrice.setText(REMHelper.formatNumberWithCommaAndCurrency(pRealEstate.getRePrice()));
            mBindingHolder.itemType.setText(pRealEstate.getReType());
            if(pRealEstate.isReIsSold()) {
                mBindingHolder.fragReListItemImgSold.setVisibility(View.VISIBLE);
            } else {
                mBindingHolder.fragReListItemImgSold.setVisibility(View.INVISIBLE);
            }

            /*
            Glide.with(mBindingHolder.fragReListItemImgPhoto.getContext())
                    .load(pPhotoUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(mBindingHolder.fragReListItemImgPhoto);
*/

        }
    }
}
