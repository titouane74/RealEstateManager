package com.openclassrooms.realestatemanager.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.openclassrooms.realestatemanager.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Florence LE BOURNOT on 26/11/2020
 */
abstract class BaseFragment<T extends ViewBinding> extends Fragment {

    protected T mBinding;

    protected abstract void configureDesign(T pBinding, boolean pIsTablet);

    @SuppressWarnings("unchecked")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Type lSuperClass = getClass().getGenericSuperclass();
        Class<?> aClass = (Class<?>) ((ParameterizedType) lSuperClass).getActualTypeArguments()[0];
        try {
            Method method = aClass.getDeclaredMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
            mBinding = (T) method.invoke(null, getLayoutInflater(), container, false);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        Context lContext = mBinding.getRoot().getContext();

        boolean lIsTablet = lContext.getResources().getBoolean(R.bool.isTablet);

        this.configureDesign(mBinding, lIsTablet);

        return mBinding.getRoot();
    }

}
