package com.openclassrooms.realestatemanager.view.fragments;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentReListBinding;
import com.openclassrooms.realestatemanager.di.Injection;
import com.openclassrooms.realestatemanager.di.ReViewModelFactory;
import com.openclassrooms.realestatemanager.model.RealEstateComplete;
import com.openclassrooms.realestatemanager.view.adapters.ReListAdapter;
import com.openclassrooms.realestatemanager.viewmodel.ReListViewModel;

import java.util.List;

import static com.openclassrooms.realestatemanager.AppRem.sApi;
import static com.openclassrooms.realestatemanager.view.adapters.ReListAdapter.IS_EDIT_KEY;
import static com.openclassrooms.realestatemanager.view.adapters.ReListAdapter.RE_ID_KEY;

public class ReListFragment extends BaseFragment<FragmentReListBinding> {

    private static final String TAG = "TAG_ReListFragment";
    private ReListViewModel mViewModel;
    private View mFragView;
    private FragmentReListBinding mBinding;

    private ReListAdapter mAdapter;
    private Context mContext;
    private boolean mIsTabletLandscape;
    private NavController mNavController;
    private MutableLiveData<List<RealEstateComplete>> mMLDReCompList;

    @Override
    protected int getMenuAttached() {return R.menu.menu_general;}

    @Override
    protected void configureDesign(FragmentReListBinding pBinding, NavController pNavController, boolean pIsTablet, boolean pIsTabletLandscape) {
        mBinding = pBinding;
        mFragView = mBinding.getRoot();
        mContext = mFragView.getContext();
        mIsTabletLandscape = pIsTabletLandscape;
        mNavController = pNavController;
        initRecyclerView();
    }

    private void configureViewModel() {
        ReViewModelFactory lFactory = Injection.reViewModelFactory(mContext);
        mViewModel = new ViewModelProvider(this,lFactory).get(ReListViewModel.class);
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configureViewModel();

        if(sApi.getSearchResult() == null) {
            mViewModel.selectAllReComplete().observe(getViewLifecycleOwner(), new Observer<List<RealEstateComplete>>() {
                @Override
                public void onChanged(List<RealEstateComplete> pAllRe) {
                    //                Log.d(TAG, "onActivityCreated allReComplete: " + pAllRe.size());
                    mAdapter.setReList(pAllRe);
                    mAdapter.notifyDataSetChanged();
                }
            });
        } else {
            mAdapter.setReList(sApi.getSearchResult());
            mAdapter.notifyDataSetChanged();
        }
/*
        mViewModel.getSearchResult().observe(getViewLifecycleOwner(), new Observer<List<RealEstateComplete>>() {
            @Override
            public void onChanged(List<RealEstateComplete> pRealEstateCompletes) {
//                Log.d(TAG, "onChanged searchresult: " + pRealEstateCompletes.size());
                mAdapter.setReList(pRealEstateCompletes);
                mAdapter.notifyDataSetChanged();
            }
        });
*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        if (pItem.getItemId() == R.id.reAddEditFragment) {
            Bundle lBundle = new Bundle();
            lBundle.putLong(RE_ID_KEY,0);
            lBundle.putBoolean(IS_EDIT_KEY,false);
//            if (mIsTabletLandscape) {
//                mNavController.navigate(R.id.reAddEditFragment,lBundle);
//                Log.d(TAG, "onOptionsItemSelected: detail fragment tablet detail to edit");
//            } else {
                mNavController.navigate(R.id.action_reListFragment_to_reAddFragment,lBundle);
//            }
            return super.onOptionsItemSelected(pItem);
        } else {
            return NavigationUI.onNavDestinationSelected(pItem, mNavController) || super.onOptionsItemSelected(pItem);
        }
    }

    private void initRecyclerView() {
        mAdapter = new ReListAdapter();
        mBinding.fragReListRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        mBinding.fragReListRv.setAdapter(mAdapter);
    }
}