package com.openclassrooms.realestatemanager.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentReCardviewPhotoItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florence LE BOURNOT on 29/11/2020
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> {

    private List<String> mPhotoList = new ArrayList<>();
    private FragmentReCardviewPhotoItemBinding mBinding;

    public void setPhotoList(List<String> pPhotoList) {
        mPhotoList = pPhotoList;
    }

    @NonNull
    @Override
    public PhotoAdapter.PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lLayoutInflater = LayoutInflater.from(parent.getContext());
        mBinding = FragmentReCardviewPhotoItemBinding.inflate(lLayoutInflater,parent,false);
        return new PhotoHolder(mBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.PhotoHolder holder, int position) {
        holder.bindView(mPhotoList.get(position));
    }

    @Override
    public int getItemCount() {
        if (mPhotoList == null) {
            return 0;
        } else {
            return mPhotoList.size();
        }
    }

    static class PhotoHolder extends RecyclerView.ViewHolder {

        FragmentReCardviewPhotoItemBinding mBindingHolder;

        public PhotoHolder(@NonNull FragmentReCardviewPhotoItemBinding pBindingHolder) {
            super(pBindingHolder.getRoot());
                mBindingHolder = pBindingHolder;
        }

        public void bindView(String pPhotoUrl) {
            mBindingHolder.fragReAddEditTvPhoto.setText(pPhotoUrl);
        }
    }
}
