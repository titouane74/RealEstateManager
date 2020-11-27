package com.openclassrooms.realestatemanager.view.fragments;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentReListBinding;
import com.openclassrooms.realestatemanager.viewmodel.REListViewModel;

public class REListFragment extends BaseFragment {

    private REListViewModel mViewModel;
    private View mFragView;
    private FragmentReListBinding mBinding;


    @Override
    protected int getFragmentLayout() { return R.layout.fragment_re_list; }

    @Override
    protected int getMenuAttached() {return R.menu.menu_general;}


    @Override
    protected void configureDesign(View pView) {

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentReListBinding.inflate(inflater, container, false);
        mFragView = mBinding.getRoot();
        return mFragView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(REListViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        NavController lNavController = Navigation.findNavController(mFragView);
        return NavigationUI.onNavDestinationSelected(pItem,lNavController) || super.onOptionsItemSelected(pItem);
    }
}