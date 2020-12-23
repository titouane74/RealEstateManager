package com.openclassrooms.realestatemanager.view.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
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
public class ReListAdapter extends RecyclerView.Adapter<ReListAdapter.ReListHolder> implements View.OnClickListener {

    private OnRecyclerViewListener mCallback;

    /**
     * Interface permettant de gérer les callbacks vers la MainActivity
     */
    public interface OnRecyclerViewListener {
        void onListAdapterItemClicked(Bundle pBundle);
    }

    public static final String RE_ID_KEY = "RE_ID";
    public static final String IS_EDIT_KEY = "IS_EDIT";

    private static final String TAG = "TAG_ReListAdapter";
    private List<RealEstateComplete> mReList = new ArrayList<>();
    private FragmentReListItemBinding mBinding;
    private Context mContext;
    private boolean mIsTablet;


    public void setReList(List<RealEstateComplete> pReList) {
        Log.d(TAG, "setReList: update list: " + pReList.size());
        mReList = pReList;
    }

    public ReListAdapter() {
        mCallback = (OnRecyclerViewListener) mContext;
    }

    @NonNull
    @Override
    public ReListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lLayoutInflater = LayoutInflater.from(parent.getContext());
        mBinding = FragmentReListItemBinding.inflate(lLayoutInflater, parent, false);
        mContext = mBinding.getRoot().getContext();
        mIsTablet = mContext.getResources().getBoolean(R.bool.isTablet);
        return new ReListHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReListHolder pHolder, int position) {
        pHolder.bindView(mReList.get(position));

        mCallback = (OnRecyclerViewListener) mContext;

        pHolder.itemView.setOnClickListener(v -> {
            Bundle lBundle = new Bundle();
            lBundle.putLong(RE_ID_KEY, mReList.get(position).getRealEstate().getReId());
            mCallback.onListAdapterItemClicked(lBundle);

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

    @Override
    public void onClick(View v) {

    }


    static class ReListHolder extends RecyclerView.ViewHolder {
        FragmentReListItemBinding mBindingHolder;

        public ReListHolder(@NonNull FragmentReListItemBinding pBindingHolder) {
            super(pBindingHolder.getRoot());
            mBindingHolder = pBindingHolder;
        }

        public void bindView(RealEstateComplete pReComp) {
            try {
                String lCity = pReComp.getReLocation().getLocCity() != null ? pReComp.getReLocation().getLocCity() : "";

                mBindingHolder.itemCity.setText(lCity);
                mBindingHolder.itemPrice.setText(REMHelper.formatNumberWithCommaAndCurrency(pReComp.getRealEstate().getRePrice()));
                mBindingHolder.itemType.setText(pReComp.getRealEstate().getReType());
                if (pReComp.getRealEstate().isReIsSold()) {
                    mBindingHolder.fragReListItemImgSold.setVisibility(View.VISIBLE);
                } else {
                    mBindingHolder.fragReListItemImgSold.setVisibility(View.INVISIBLE);
                }
                if (!pReComp.getRealEstate().isReIsMandatoryDataComplete()) {
                    mBindingHolder.fragReListItemImgDataMissing.setVisibility(View.VISIBLE);
                } else {
                    mBindingHolder.fragReListItemImgDataMissing.setVisibility(View.INVISIBLE);
                }
                if (pReComp.getRePhotoList().size() > 0) {
                    if (pReComp.getRePhotoList().get(0).getPhPath() != null) {
                        String lPath = pReComp.getRePhotoList().get(0).getPhPath();
                        Bitmap lBitmap = BitmapFactory.decodeFile(lPath);
                        mBindingHolder.fragReListItemImgPhoto.setImageBitmap(lBitmap);
                    } else {
                        displayNoPhoto();
                    }
                } else {
                    displayNoPhoto();
                }
            } catch (Exception pE) {
                pE.printStackTrace();
                Log.d(TAG, "bindView: stacktrace : " + pE.getMessage());
            }
        }

        private void displayNoPhoto() {
            Glide.with(mBindingHolder.fragReListItemImgPhoto.getContext())
                    .load(R.drawable.ic_no_photo)
                    .into(mBindingHolder.fragReListItemImgPhoto);
        }
    }
}
