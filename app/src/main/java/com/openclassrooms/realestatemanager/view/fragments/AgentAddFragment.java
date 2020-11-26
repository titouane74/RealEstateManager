package com.openclassrooms.realestatemanager.view.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.viewmodel.AgentAddViewModel;
import com.openclassrooms.realestatemanager.R;

public class AgentAddFragment extends BaseFragment {

    private static final String TAG = "AgentAddFragment";
    private TextView mText;
    private View mFragView;

    private AgentAddViewModel mViewModel;

    public static AgentAddFragment newInstance() {
        return new AgentAddFragment();
    }

    @Override
    protected int getFragmentLayout() { return R.layout.fragment_agent_add; }

    @Override
    protected int getMenuAttached() {return R.menu.menu_save;}


    @Override
    protected void configureDesign(View pView) {
        mFragView = pView;
        bindView(mFragView);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AgentAddViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        if (pItem.getItemId() == R.id.menu_action_save) {
            Toast.makeText(getContext(), "SAVE", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(pItem);
    }

    private void bindView(View pView) {
        mText = pView.findViewById(R.id.frag_agent_add_txt);
    }

}