package com.openclassrooms.realestatemanager.view.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.databinding.FragmentReAddEditRvPhotoItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florence LE BOURNOT on 29/11/2020
 */
public class AddEditPhotoAdapter extends RecyclerView.Adapter<AddEditPhotoAdapter.AddEditPhotoHolder> {

    private List<String> mPhotoList = new ArrayList<>();
    private FragmentReAddEditRvPhotoItemBinding mBinding;

    public void setPhotoList(List<String> pPhotoList) { mPhotoList = pPhotoList; }

    @NonNull
    @Override
    public AddEditPhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lLayoutInflater = LayoutInflater.from(parent.getContext());
        mBinding = FragmentReAddEditRvPhotoItemBinding.inflate(lLayoutInflater,parent,false);
        return new AddEditPhotoHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AddEditPhotoHolder holder, int position) {
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

    static class AddEditPhotoHolder extends RecyclerView.ViewHolder {

        FragmentReAddEditRvPhotoItemBinding mBindingHolder;

        public AddEditPhotoHolder(@NonNull FragmentReAddEditRvPhotoItemBinding pBindingHolder) {
            super(pBindingHolder.getRoot());
                mBindingHolder = pBindingHolder;
        }

        public void bindView(String pPhotoUrl) {
            mBindingHolder.fragReAddEditTvPhoto.setText(pPhotoUrl);
/*            Glide.with(mBindingHolder.fragReAddEditImgPhoto.getContext())
                    .load(pPhotoUrl)
                    .apply(RequestOptions.circleCropTransform())
                    .into(mBindingHolder.fragReAddEditImgPhoto);*/
        }
    }
}
