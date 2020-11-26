package com.openclassrooms.realestatemanager.view.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.viewmodel.REListViewModel;

public class REListFragment extends Fragment {

    private REListViewModel mViewModel;
    private Button mBtnAddRe;
    private View mFragView;

    public static REListFragment newInstance() {
        return new REListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mFragView= inflater.inflate(R.layout.fragment_re_list, container, false);

        mBtnAddRe = mFragView.findViewById(R.id.btn_add_re);


        mBtnAddRe.setOnClickListener(v-> Navigation.findNavController(v).navigate(R.id.action_nav_re_list_to_nav_re_add));

        return mFragView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(REListViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_general, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        NavController lNavController = Navigation.findNavController(mFragView);
        switch (pItem.getItemId()) {
            case R.id.nav_agent_add:
            case R.id.nav_re_add:
                NavigationUI.onNavDestinationSelected(pItem,lNavController);
                return true;
            case R.id.menu_search_agent:
                Toast.makeText(getContext(), "EDIT AGENT", Toast.LENGTH_SHORT).show();
                NavigationUI.onNavDestinationSelected(pItem,lNavController);
                return true;
            case R.id.menu_search_re:
                Toast.makeText(getContext(), "SEARCH RE", Toast.LENGTH_SHORT).show();
                NavigationUI.onNavDestinationSelected(pItem,lNavController);
                return true;
            default:
                return super.onOptionsItemSelected(pItem);
        }
    }
}