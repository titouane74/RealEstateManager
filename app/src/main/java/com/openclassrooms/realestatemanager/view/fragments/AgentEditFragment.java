package com.openclassrooms.realestatemanager.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.viewmodel.AgentAddViewModel;

public class AgentEditFragment extends Fragment {

    private static final String TAG = "AgentAddFragment";
    private TextView mText;

    private AgentAddViewModel mViewModel;

    public static AgentEditFragment newInstance() {
        return new AgentEditFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View lView = inflater.inflate(R.layout.fragment_agent_add, container, false);

        bindView(lView);
        setHasOptionsMenu(true);

        return lView;
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