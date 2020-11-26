package com.openclassrooms.realestatemanager.view.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.viewmodel.REListViewModel;

public class REListFragment extends BaseFragment {

    private REListViewModel mViewModel;
    private View mFragView;

    public static REListFragment newInstance() {
        return new REListFragment();
    }


    @Override
    protected int getFragmentLayout() { return R.layout.fragment_re_list; }

    @Override
    protected int getMenuAttached() {return R.menu.menu_general;}


    @Override
    protected void configureDesign(View pView) {
        mFragView = pView;
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
        switch (pItem.getItemId()) {
            case R.id.nav_agent_add:
            case R.id.nav_re_add:
                NavigationUI.onNavDestinationSelected(pItem,lNavController);
                return true;
            case R.id.nav_agent_edit:
                Toast.makeText(getContext(), "EDIT AGENT", Toast.LENGTH_SHORT).show();
                NavigationUI.onNavDestinationSelected(pItem,lNavController);
                return true;
            case R.id.nav_re_edit:
                Toast.makeText(getContext(), "SEARCH RE", Toast.LENGTH_SHORT).show();
                NavigationUI.onNavDestinationSelected(pItem,lNavController);
                return true;
            default:
                return super.onOptionsItemSelected(pItem);
        }
    }
}