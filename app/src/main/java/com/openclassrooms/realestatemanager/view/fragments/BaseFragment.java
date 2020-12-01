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
import androidx.viewbinding.ViewBinding;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created by Florence LE BOURNOT on 26/11/2020
 */
abstract class BaseFragment<T extends ViewBinding> extends Fragment {

    protected T mBinding;

    protected abstract int getMenuAttached();

    protected abstract int getFragmentLayout();

    protected abstract void configureDesign(T pBinding);

    private View mFragView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

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
        this.configureDesign(mBinding);

        return mBinding.getRoot();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(getMenuAttached(), menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

}
