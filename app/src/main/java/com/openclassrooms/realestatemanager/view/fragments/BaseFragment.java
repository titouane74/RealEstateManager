package com.openclassrooms.realestatemanager.view.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.viewbinding.ViewBinding;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.utils.REMHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Florence LE BOURNOT on 26/11/2020
 */
abstract class BaseFragment<T extends ViewBinding> extends Fragment {

    protected T mBinding;
    //    private int mIntNavHost;
    private Context mContext;

    protected abstract int getMenuAttached();

    protected abstract void configureDesign(T pBinding, NavController pNavController, boolean pIsTablet, boolean pIsTabletLandscape);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        NavController lNavController;

        Type lSuperClass = getClass().getGenericSuperclass();
        Class<?> aClass = (Class<?>) ((ParameterizedType) lSuperClass).getActualTypeArguments()[0];
        try {
            Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            mBinding = (T) method.invoke(null, getLayoutInflater(), container, false);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        mContext = mBinding.getRoot().getContext();

        boolean lIsTablet = mContext.getResources().getBoolean(R.bool.isTablet);
        boolean lIsTabletLandscape = REMHelper.isTabletLandscape(mContext, lIsTablet);

        //TODO deactivate cause not used for the moment
//        mIntNavHost = REMHelper.getNavHostId(mContext, lIsTablet);

        lNavController = Navigation.findNavController((Activity) container.getContext(), R.id.nav_host_fragment);

        this.configureDesign(mBinding, lNavController, lIsTablet, lIsTabletLandscape);

        return mBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        if (getMenuAttached() != 0) {
            inflater.inflate(getMenuAttached(), menu);
            super.onCreateOptionsMenu(menu, inflater);
        }
    }

}
