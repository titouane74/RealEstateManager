package com.openclassrooms.realestatemanager.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.databinding.FragmentReDetailPhotoItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florence LE BOURNOT on 29/11/2020
 */
public class DetailPhotoAdapter extends RecyclerView.Adapter<DetailPhotoAdapter.DetailPhotoHolder> {

    private static final String TAG = "TAG_DetailPhotoAdapter";
    private List<String> mPhotoList = new ArrayList<>();
    private FragmentReDetailPhotoItemBinding mBinding;

    public void setPhotoList(List<String> pPhotoList) { mPhotoList = pPhotoList; }

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

        public void bindView(String pPhotoUrl) {
            mBindingHolder.fragReDetTvPhoto.setText(pPhotoUrl);
/*            Glide.with(mBindingHolder.fragReAddEditImgPhoto.getContext())
                    .load(pPhotoUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(mBindingHolder.fragReAddEditImgPhoto);*/
        }
    }
}
