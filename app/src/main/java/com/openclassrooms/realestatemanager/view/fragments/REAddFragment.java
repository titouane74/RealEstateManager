package com.openclassrooms.realestatemanager.view.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.viewmodel.REAddViewModel;

public class REAddFragment extends Fragment {

    private REAddViewModel mViewModel;

    public static REAddFragment newInstance() {
        return new REAddFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View lView = inflater.inflate(R.layout.fragment_add_re, container, false);
        return lView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(REAddViewModel.class);
        // TODO: Use the ViewModel
    }

}