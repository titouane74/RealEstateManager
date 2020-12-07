package com.openclassrooms.realestatemanager.view.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentReDetailBinding;
import com.openclassrooms.realestatemanager.di.Injection;
import com.openclassrooms.realestatemanager.di.ReViewModelFactory;
import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.view.adapters.DetailPhotoAdapter;
import com.openclassrooms.realestatemanager.viewmodel.ReDetailViewModel;
import com.openclassrooms.realestatemanager.viewmodel.ReListViewModel;

import java.util.ArrayList;
import java.util.List;

public class ReDetailFragment extends BaseFragment<FragmentReDetailBinding> {

    private static final String TAG = "REDetailFragment";
    private FragmentReDetailBinding mBinding;
    private DetailPhotoAdapter mAdapter;
    private View mFragView;
    private ReDetailViewModel mViewModel;
    private Context mContext;
    private NavController mNavController;
    private boolean mIsTablet;
    private RealEstate mRE;
    private int mReId;

    @Override
    protected int getMenuAttached() {
//        if (mIsTablet) {
//            return R.menu.menu_general_tablet;
//        } else {
            return R.menu.menu_edit;
//        }
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_re_detail;
    }

    @Override
    protected void configureDesign(FragmentReDetailBinding pBinding, NavController pNavController, boolean pIsTablet) {
        mBinding = pBinding;
        mFragView = mBinding.getRoot();
        mContext = getContext();
        mNavController = pNavController;
        mIsTablet = pIsTablet;
        initRecyclerView();
    }

    private void initRecyclerView() {
        mAdapter = new DetailPhotoAdapter();
        mBinding.fragReDetRvPhoto.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
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

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        if (pItem.getItemId() == R.id.menu_action_edit) {
//            Navigation.findNavController(mFragView).navigate(R.id.action_reDetailFragment_to_reAddEditFragment);
            if (mIsTablet) {
                mNavController.navigate(R.id.reAddEditFragment);
                Log.d(TAG, "onOptionsItemSelected: detail fragment tablet detail to edit");
            } else {
                mNavController.navigate(R.id.action_reDetailFragment_to_reAddEditFragment);
                Log.d(TAG, "onOptionsItemSelected: detail fragment phone detail to edit");
            }
            return true;
        }
        return super.onOptionsItemSelected(pItem);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            ReDetailFragmentArgs lArgs = ReDetailFragmentArgs.fromBundle(getArguments());

//            RealEstate lRE = lArgs.getRealestate();
            mReId = lArgs.getReid();
            Log.i(TAG, "onViewCreated: " + mReId);
            //displayRealEstate(lRE);
        }
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

    private void configureViewModel() {
        ReViewModelFactory lFactory = Injection.reViewModelFactory(mContext);
        mViewModel = new ViewModelProvider(this,lFactory).get(ReDetailViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configureViewModel();
        mViewModel.getRealEstate(mReId).observe(getViewLifecycleOwner(),pRealEstate -> {
            displayRealEstate(pRealEstate);
        });
    }

}