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
import android.widget.TextView;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.viewmodel.AgentAddViewModel;
import com.openclassrooms.realestatemanager.R;

public class AgentAddFragment extends Fragment {

    private static final String TAG = "AgentAddFragment";
    private TextView mText;
    private View mFragView;

    private AgentAddViewModel mViewModel;

    public static AgentAddFragment newInstance() {
        return new AgentAddFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mFragView = inflater.inflate(R.layout.fragment_agent_add, container, false);

        bindView(mFragView);
        return mFragView;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AgentAddViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_save, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        NavController lNavController = Navigation.findNavController(mFragView);
        Log.d(TAG, "onOptionsItemSelected: " + pItem.getItemId());
        switch (pItem.getItemId()) {
            case R.id.menu_search_agent:
                Toast.makeText(getContext(), "EDIT AGENT", Toast.LENGTH_SHORT).show();
                NavigationUI.onNavDestinationSelected(pItem,lNavController);
                return true;
            case R.id.menu_action_save:
                Toast.makeText(getContext(), "SAVE AGENT", Toast.LENGTH_SHORT).show();
                return true;

            default:
                return super.onOptionsItemSelected(pItem);
        }
    }
    private void bindView(View pView) {
        mText = pView.findViewById(R.id.frag_agent_add_txt);
    }

}