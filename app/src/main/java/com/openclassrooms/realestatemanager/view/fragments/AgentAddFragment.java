package com.openclassrooms.realestatemanager.view.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.viewmodel.AgentAddViewModel;
import com.openclassrooms.realestatemanager.R;

public class AgentAddFragment extends Fragment {

    private static final String TAG = "AgentAddFragment";
    private TextView mText;

    private AgentAddViewModel mViewModel;

    public static AgentAddFragment newInstance() {
        return new AgentAddFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View lView = inflater.inflate(R.layout.fragment_agent_add, container, false);

        bindView(lView);

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