package com.openclassrooms.realestatemanager.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.viewmodel.AgentAddViewModel;

public class AgentEditFragment extends BaseFragment {

    private static final String TAG = "AgentAddFragment";
    private TextView mText;
    private View mFragView;
    private AgentAddViewModel mViewModel;

    public static AgentEditFragment newInstance() {
        return new AgentEditFragment();
    }

    @Override
    protected int getMenuAttached() { return R.menu.menu_save; }

    @Override
    protected int getFragmentLayout() { return R.layout.fragment_agent_add; }

    @Override
    protected void configureDesign(View pView) {
        mFragView = pView;
        bindView(mFragView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        if (pItem.getItemId() == R.id.menu_action_save) {
            Toast.makeText(getContext(), "SAVE", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(pItem);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AgentAddViewModel.class);
        // TODO: Use the ViewModel
    }
    private void bindView(View pView) {
        mText = pView.findViewById(R.id.frag_agent_add_txt);
    }

}