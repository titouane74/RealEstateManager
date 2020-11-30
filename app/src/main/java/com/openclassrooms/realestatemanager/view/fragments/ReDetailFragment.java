package com.openclassrooms.realestatemanager.view.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentReDetailBinding;
import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.view.adapters.DetailPhotoAdapter;
import com.openclassrooms.realestatemanager.viewmodel.ReDetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class ReDetailFragment extends BaseFragment<FragmentReDetailBinding> {

    private static final String TAG = "REDetailFragment";
    private FragmentReDetailBinding mBinding;
    private DetailPhotoAdapter mAdapter;
    private View mFragView;
    private ReDetailViewModel mViewModel;
    private Context mContext;

    @Override
    protected int getMenuAttached() { return R.menu.menu_search; }

    @Override
    protected int getFragmentLayout() { return R.layout.fragment_re_detail; }

    @Override
    protected void configureDesign(FragmentReDetailBinding pBinding) {
        mBinding = pBinding;
        mFragView = mBinding.getRoot();
        mContext = getContext();
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new DetailPhotoAdapter();
        mBinding.fragReDetRvPhoto.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL,false));
        mBinding.fragReDetRvPhoto.setAdapter(mAdapter);
        initPhotoList();
    }

    private void initPhotoList() {
        List<String> lPhotoList = new ArrayList<>();
        lPhotoList.add("https://lh3.googleusercontent.com/a-/AOh14Gh7GajhYVm2T1esN8y8XZF7Iz6HzjC0ugJkk2dN7g=s96-c");
        lPhotoList.add("https://lh3.googleusercontent.com/a-/AOh14Gj0Y3MR2L_u0rFtMCji9r5CmQzR5PDKlZkB9zc9");
        lPhotoList.add("https://lh3.googleusercontent.com/a-/AOh14GgOP8seJeI1oZImU6EZHTL3WSJtWcUOMDzMvel07w=s96-c");
        lPhotoList.add("https://lh3.googleusercontent.com/a-/AOh14Gj0Y3MR2L_u0rFtMCji9r5CmQzR5PDKlZkB9zc9");
        lPhotoList.add("https://lh3.googleusercontent.com/a-/AOh14GgOP8seJeI1oZImU6EZHTL3WSJtWcUOMDzMvel07w=s96-c");
        lPhotoList.add("https://lh3.googleusercontent.com/a-/AOh14Gj0Y3MR2L_u0rFtMCji9r5CmQzR5PDKlZkB9zc9");
        mAdapter.setPhotoList(lPhotoList);
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