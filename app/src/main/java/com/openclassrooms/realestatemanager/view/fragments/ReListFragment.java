package com.openclassrooms.realestatemanager.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.openclassrooms.realestatemanager.databinding.FragmentReListBinding;
import com.openclassrooms.realestatemanager.di.Injection;
import com.openclassrooms.realestatemanager.di.ReViewModelFactory;
import com.openclassrooms.realestatemanager.model.RealEstateComplete;
import com.openclassrooms.realestatemanager.view.adapters.ReListAdapter;
import com.openclassrooms.realestatemanager.viewmodel.ReListViewModel;

import java.util.List;

import static com.openclassrooms.realestatemanager.AppRem.sApi;

public class ReListFragment extends BaseFragment<FragmentReListBinding> implements ReSearchFragment.OnSearchListener  {

    private static final String TAG = "TAG_ReListFragment";
    private FragmentReListBinding mBinding;
    private ReListViewModel mViewModel;
    private ReListAdapter mAdapter;
    private Context mContext;

    public ReListFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void configureDesign(FragmentReListBinding pBinding, boolean pIsTablet) {
        mBinding = pBinding;
        mContext = mBinding.getRoot().getContext();
        Log.d(TAG, "configureDesign: init RV");
        initRecyclerView();
        Log.d(TAG, "configureDesign: conf VM");
        configureViewModel();

        if(sApi.getSearchResult() == null) {
            Log.d(TAG, "configureDesign: search null");
            getListDataFromDatabase();
        } else {
            Log.d(TAG, "configureDesign: search not null");
            updateRecyclerView(sApi.getSearchResult());
        }

    }

    private void configureViewModel() {
        ReViewModelFactory lFactory = Injection.reViewModelFactory(mContext);
        mViewModel = new ViewModelProvider(this,lFactory).get(ReListViewModel.class);
    }

    private void getListDataFromDatabase() {
//        mViewModel.selectAllReCompleteMandatoryDataComplete().observe(getViewLifecycleOwner(), this::updateRecyclerView);
        mViewModel.selectAllReComplete().observe(getViewLifecycleOwner(), this::updateRecyclerView);
    }

    private void initRecyclerView() {
        mAdapter = new ReListAdapter();
        mBinding.fragReListRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        mBinding.fragReListRv.setAdapter(mAdapter);
    }

    private void updateRecyclerView(List<RealEstateComplete> pReCompList) {
        Log.d(TAG, "updateRecyclerView: " + pReCompList.size());
        mAdapter.setReList(pReCompList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void updateList(List<RealEstateComplete> pReCompList) {
        if (pReCompList.size() == 0) {
            Log.d(TAG, "updateList: liste vide  : ");
            getListDataFromDatabase();
        } else {
            Log.d(TAG, "updateList: " + pReCompList.size());
            updateRecyclerView(pReCompList);
        }
    }
}