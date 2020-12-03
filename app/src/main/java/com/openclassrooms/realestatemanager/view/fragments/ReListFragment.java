package com.openclassrooms.realestatemanager.view.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.NavHost;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentReListBinding;
import com.openclassrooms.realestatemanager.view.adapters.ReListAdapter;
import com.openclassrooms.realestatemanager.viewmodel.ReListViewModel;

import java.util.ArrayList;
import java.util.List;

public class ReListFragment extends BaseFragment<FragmentReListBinding> {

    private ReListViewModel mViewModel;
    private View mFragView;
    private FragmentReListBinding mBinding;

    private ReListAdapter mAdapter;
    private Context mContext;
    private boolean mIsTablet;
    private NavController mNavController;

    @Override
    protected int getFragmentLayout() { return R.layout.fragment_re_list; }

    @Override
    protected int getMenuAttached() {return R.menu.menu_general;}

    @Override
    protected void configureDesign(FragmentReListBinding pBinding, NavController pNavController, boolean pIsTablet) {
        mBinding = pBinding;
        mFragView = mBinding.getRoot();
        mContext = mFragView.getContext();
        mIsTablet = pIsTablet;
        mNavController = pNavController;
        initRecyclerView();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReListViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
/*
        if (mIsTablet) {
                switch (pItem.getItemId()) {
                    case R.menu.menu_edit:
                        Toast.makeText(mContext, "menu edit", Toast.LENGTH_SHORT).show();
                        mNavController.navigate(R.id.action_reDetailFragment_to_reAddEditFragment);
                        break;
                    case R.menu.menu_search:
                        Toast.makeText(mContext, "menu search", Toast.LENGTH_SHORT).show();
                        mNavController.navigate(R.id.action_reDetailFragment_to_reSearchFragment);
                        break;
                    case R.menu.menu_general:
                        Toast.makeText(mContext, "menu loan", Toast.LENGTH_SHORT).show();
                        mNavController.navigate(R.id.action_reDetailFragment_to_loanFragment);
                        break;
                    default:
                        break;
                }
//            } else {
//                Toast.makeText(mContext, "PAS DE NAVHOST !", Toast.LENGTH_SHORT).show();
//            }
            return true;
        } else {*/
            return NavigationUI.onNavDestinationSelected(pItem,mNavController) || super.onOptionsItemSelected(pItem);
//        }
    }

    private void initRecyclerView() {
        mAdapter = new ReListAdapter();
        mBinding.fragReListRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        mBinding.fragReListRv.setAdapter(mAdapter);
        initPhotoList();
    }
    private void initPhotoList() {
        List<String> lPhotoList = new ArrayList<>();
        lPhotoList.add("https://lh3.googleusercontent.com/a-/AOh14Gh7GajhYVm2T1esN8y8XZF7Iz6HzjC0ugJkk2dN7g=s96-c");
        lPhotoList.add("https://lh3.googleusercontent.com/a-/AOh14Gj0Y3MR2L_u0rFtMCji9r5CmQzR5PDKlZkB9zc9");
        mAdapter.setReList(lPhotoList);
    }
}