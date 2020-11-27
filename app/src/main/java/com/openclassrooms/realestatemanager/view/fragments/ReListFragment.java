package com.openclassrooms.realestatemanager.view.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.MenuItem;
import android.view.View;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentReListBinding;
import com.openclassrooms.realestatemanager.viewmodel.ReListViewModel;

public class ReListFragment extends BaseFragment<FragmentReListBinding> {

    private ReListViewModel mViewModel;
    private View mFragView;
    private FragmentReListBinding mBinding;


    @Override
    protected int getFragmentLayout() { return R.layout.fragment_re_list; }

    @Override
    protected int getMenuAttached() {return R.menu.menu_general;}

    @Override
    protected void configureDesign(FragmentReListBinding pBinding) {
        mBinding = pBinding;
        mFragView = mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReListViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        NavController lNavController = Navigation.findNavController(mFragView);
        return NavigationUI.onNavDestinationSelected(pItem,lNavController) || super.onOptionsItemSelected(pItem);
    }
}