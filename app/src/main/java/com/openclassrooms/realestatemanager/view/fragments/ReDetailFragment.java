package com.openclassrooms.realestatemanager.view.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.databinding.FragmentReDetailBinding;
import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.viewmodel.ReDetailViewModel;

public class ReDetailFragment extends Fragment {

    private static final String TAG = "REDetailFragment";
    private FragmentReDetailBinding mBinding;

    private View mFragView;
    private ReDetailViewModel mViewModel;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mBinding = FragmentReDetailBinding.inflate(inflater, container, false);
        mFragView = mBinding.getRoot();
        return mFragView;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

/*        if (getArguments() != null) {
            REDetailFragmentArgs lArgs = REDetailFragmentArgs.fromBundle(getArguments());

            RealEstate lRE = lArgs.getRealestate();
            Log.i(TAG, "onViewCreated: " + lRE.toString());
            displayRealEstate(lRE);
        }*/
    }

    @SuppressLint("SetTextI18n")
    private void displayRealEstate(RealEstate pRE) {
        mBinding.fragReDetTvPrice.setText(Integer.toString(pRE.getRePrice()));
        mBinding.fragReDetTvNbRooms.setText(Integer.toString(pRE.getReNbRooms()));
        mBinding.fragReDetTvNbBedrooms.setText(Integer.toString(pRE.getReNbBedrooms()));
        mBinding.fragReDetTvNbBathrooms.setText(Integer.toString(pRE.getReNbBathrooms()));
        mBinding.fragReDetTvType.setText(pRE.getReType());
        mBinding.fragReDetTvDescription.setText(pRE.getReDescription());
        mBinding.fragReDetTvArea.setText(Integer.toString(pRE.getReArea()));
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReDetailViewModel.class);
        // TODO: Use the ViewModel
    }

}