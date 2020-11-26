package com.openclassrooms.realestatemanager.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
/**
 * Created by Florence LE BOURNOT on 26/11/2020
 */
abstract class BaseFragment extends Fragment {

    protected abstract int getMenuAttached();
    protected abstract int getFragmentLayout();
    protected abstract void configureDesign(View pView);

    private View mFragView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mFragView = inflater.inflate(getFragmentLayout(),container,false);
        this.configureDesign(mFragView);
        return mFragView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(getMenuAttached(),menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}
