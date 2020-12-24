package com.openclassrooms.realestatemanager.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentReDetailPhotoItemBinding;
import com.openclassrooms.realestatemanager.model.RePhoto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florence LE BOURNOT on 29/11/2020
 */
public class DetailPhotoAdapter extends RecyclerView.Adapter<DetailPhotoAdapter.DetailPhotoHolder> {

    private List<RePhoto> mPhotoList = new ArrayList<>();
    private FragmentReDetailPhotoItemBinding mBinding;

    public void setPhotoList(List<RePhoto> pPhotoList) { mPhotoList = pPhotoList; }

    @NonNull
    @Override
    public DetailPhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lLayoutInflater = LayoutInflater.from(parent.getContext());
        mBinding = FragmentReDetailPhotoItemBinding.inflate(lLayoutInflater,parent,false);
        return new DetailPhotoHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailPhotoHolder holder, int position) {
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

    static class DetailPhotoHolder extends RecyclerView.ViewHolder {

        FragmentReDetailPhotoItemBinding mBindingHolder;

        public DetailPhotoHolder(@NonNull FragmentReDetailPhotoItemBinding pBindingHolder) {
            super(pBindingHolder.getRoot());
                mBindingHolder = pBindingHolder;
        }

        public void bindView(RePhoto pPhoto) {
            mBindingHolder.fragReDetTvPhoto.setText(pPhoto.getPhDescription());
            RequestOptions requestOptions = new RequestOptions();
            requestOptions=requestOptions.placeholder(R.drawable.ic_no_photo);
            Glide.with(mBindingHolder.fragReDetImgPhoto.getContext())
                    .load(pPhoto.getPhPath())
                    .apply(requestOptions)
                    .into(mBindingHolder.fragReDetImgPhoto);
        }
    }
}
