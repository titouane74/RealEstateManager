package com.openclassrooms.realestatemanager.view.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.openclassrooms.realestatemanager.databinding.FragmentReAddEditRvPhotoItemBinding;
import com.openclassrooms.realestatemanager.model.RePhoto;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florence LE BOURNOT on 29/11/2020
 */
public class AddEditPhotoAdapter extends RecyclerView.Adapter<AddEditPhotoAdapter.AddEditPhotoHolder> {

    private static final String TAG = "TAG_AddEditPhotoAdapter";
    private List<RePhoto> mPhotoList = new ArrayList<>();
    private FragmentReAddEditRvPhotoItemBinding mBinding;

    public void setPhotoList(List<RePhoto> pPhotoList) { mPhotoList = pPhotoList; }

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
        final  RePhoto mPhoto = mPhotoList.get(position);
        mBinding.fragReAddEditEtPhoto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                mPhoto.setPhDescription(s.toString());
            }
        });
        mBinding.fragReAddEditImgDelete.setOnClickListener(v-> {
            mPhotoList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position,getItemCount());
        } );
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

        public void bindView(RePhoto pPhoto) {
            mBindingHolder.fragReAddEditEtPhoto.setText(pPhoto.getPhDescription());
            Glide.with(mBindingHolder.fragReAddEditImgPhoto.getContext())
                    .load(pPhoto.getPhPath())
                    .into(mBindingHolder.fragReAddEditImgPhoto);

        }
    }
}
