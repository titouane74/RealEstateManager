package com.openclassrooms.realestatemanager.view.adapters;

import android.app.Activity;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florence LE BOURNOT on 30/11/2020
 */
public class ReListAdapter extends RecyclerView.Adapter<ReListAdapter.ReListHolder> {

    private static final String TAG = "TAG_ReListAdapter";
    private List<String> mReListString = new ArrayList<>();
    private List<RealEstate> mReList = new ArrayList<>();
    private com.openclassrooms.realestatemanager.databinding.FragmentReListItemBinding mBinding;
    private NavController mNavController;
    private Context mContext;
    private boolean mIsTablet;


    public void setReListString(List<String> pReList) {
        mReListString = pReList;
    }

    public void setReList(List<RealEstate> pReList) {
        mReList = pReList;
    }


    @NonNull
    @Override
    public ReListAdapter.ReListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lLayoutInflater = LayoutInflater.from(parent.getContext());
        mBinding = FragmentReListItemBinding.inflate(lLayoutInflater, parent, false);
        mContext = mBinding.getRoot().getContext();

        int lIntNavHost = REMHelper.getNavHostId(mContext, mIsTablet);
        mNavController = Navigation.findNavController((Activity) parent.getContext(), lIntNavHost);
        return new ReListAdapter.ReListHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReListHolder pHolder, int position) {
//        pHolder.bindView(mReListString.get(position));
        pHolder.bindView(mReList.get(position));
        pHolder.itemView.setOnClickListener(v -> {
            if (mIsTablet) {
                mNavController.navigate(R.id.reDetailFragment);
                Toast.makeText(mContext, "TABLET DETAIL", Toast.LENGTH_SHORT).show();
            } else {
                mNavController.navigate(R.id.action_reListFragment_to_reDetailFragment);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (mReList == null) {
            return 0;
        } else {
//            Log.d(TAG, "getItemCount: " + mReList.size());
            return mReList.size();
        }
        /*        if (mReListString == null) {
            return 0;
        } else {
//            Log.d(TAG, "getItemCount: " + mReList.size());
            return mReListString.size();
        }*/
    }


    static class ReListHolder extends RecyclerView.ViewHolder {
        FragmentReListItemBinding mBindingHolder;

        public ReListHolder(@NonNull FragmentReListItemBinding pBindingHolder) {
            super(pBindingHolder.getRoot());
            mBindingHolder = pBindingHolder;
        }

        public void bindView(RealEstate pRealEstate) {
            mBindingHolder.itemCity.setText(pRealEstate.getReAgentFirstName());
            mBindingHolder.itemPrice.setText(String.valueOf(pRealEstate.getRePrice()));
            mBindingHolder.itemType.setText(pRealEstate.getReType());
        }

            /*
        public void bindView(String pPhotoUrl) {

            mBindingHolder.itemCity.setText(pPhotoUrl);
            mBindingHolder.itemPrice.setText(pPhotoUrl);
            mBindingHolder.itemType.setText(pPhotoUrl);


            Glide.with(mBindingHolder.fragReListItemImgPhoto.getContext())
                    .load(pPhotoUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(mBindingHolder.fragReListItemImgPhoto);
        }
*/
    }


}
