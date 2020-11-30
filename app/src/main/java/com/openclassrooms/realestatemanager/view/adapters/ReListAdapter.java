package com.openclassrooms.realestatemanager.view.adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentReListItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florence LE BOURNOT on 30/11/2020
 */
public class ReListAdapter extends RecyclerView.Adapter<ReListAdapter.ReListHolder> {

    private static final String TAG = "TAG_ReListAdapter";
    private List<String> mReList = new ArrayList<>();
    private FragmentReListItemBinding mBinding;
    private NavController mNavController;

    public void setReList(List<String> pReList) {
        mReList = pReList;
    }

    @NonNull
    @Override
    public ReListAdapter.ReListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lLayoutInflater = LayoutInflater.from(parent.getContext());
        mBinding = FragmentReListItemBinding.inflate(lLayoutInflater, parent, false);
        mNavController = Navigation.findNavController((Activity) parent.getContext(), R.id.nav_host_fragment);
        return new ReListAdapter.ReListHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReListHolder pHolder, int position) {
        pHolder.bindView(mReList.get(position));
        pHolder.itemView.setOnClickListener(v -> {
            mNavController.navigate(R.id.action_nav_re_list_to_nav_re_detail);
        });
    }

    @Override
    public int getItemCount() {
        if (mReList == null) {
            return 0;
        } else {
            Log.d(TAG, "getItemCount: " + mReList.size());
            return mReList.size();
        }
    }

    static class ReListHolder extends RecyclerView.ViewHolder {
        FragmentReListItemBinding mBindingHolder;

        public ReListHolder(@NonNull FragmentReListItemBinding pBindingHolder) {
            super(pBindingHolder.getRoot());
            mBindingHolder = pBindingHolder;

        }

        public void bindView(String pPhotoUrl) {
/*
            mBindingHolder.itemCity.setText(pPhotoUrl);
            mBindingHolder.itemPrice.setText(pPhotoUrl);
            mBindingHolder.itemType.setText(pPhotoUrl);
*/

/*            Glide.with(mBindingHolder.fragReListItemImgPhoto.getContext())
                    .load(pPhotoUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(mBindingHolder.fragReListItemImgPhoto);*/
        }
    }

}
