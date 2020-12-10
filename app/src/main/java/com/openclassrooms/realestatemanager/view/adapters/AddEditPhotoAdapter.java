package com.openclassrooms.realestatemanager.view.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.databinding.FragmentReAddEditRvPhotoItemBinding;
import com.openclassrooms.realestatemanager.model.RePhoto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florence LE BOURNOT on 29/11/2020
 */
public class AddEditPhotoAdapter extends RecyclerView.Adapter<AddEditPhotoAdapter.AddEditPhotoHolder> {

    private List<RePhoto> mPhotoList = new ArrayList<>();
    private FragmentReAddEditRvPhotoItemBinding mBinding;

    private OnRecyclerViewListener mCallback;

    public interface OnRecyclerViewListener {
        void listToSave(List<RePhoto> pPhotoList);
    }

    public void setPhotoList(List<RePhoto> pPhotoList) { mPhotoList = pPhotoList; }

    @NonNull
    @Override
    public AddEditPhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater lLayoutInflater = LayoutInflater.from(parent.getContext());
        mBinding = FragmentReAddEditRvPhotoItemBinding.inflate(lLayoutInflater,parent,false);
//        mCallback = (OnRecyclerViewListener) mBinding.getRoot().getContext();
        return new AddEditPhotoHolder(mBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AddEditPhotoHolder holder, int position) {
        holder.bindView(mPhotoList.get(position));
/*
        mBinding.fragReAddEditEtPhoto.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                RePhoto lPhoto = mPhotoList.get(position);
                lPhoto.setPhDescription(mBinding.fragReAddEditEtPhoto.getText().toString());
                mPhotoList.set(position,lPhoto);
            }
        });
*/

        mBinding.fragReAddEditEtPhoto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                RePhoto lPhoto = mPhotoList.get(position);
                lPhoto.setPhDescription(mBinding.fragReAddEditEtPhoto.getText().toString());
                mPhotoList.set(position,lPhoto);
//                mCallback.listToSave(mPhotoList);
            }
            @Override
            public void afterTextChanged(Editable s) { }
        });
        mBinding.fragReAddEditImgDelete.setOnClickListener(v-> {
            mPhotoList.remove(position);
            notifyItemRemoved(position);
//            mCallback.listToSave(mPhotoList);
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
