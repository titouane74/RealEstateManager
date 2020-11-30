package com.openclassrooms.realestatemanager.view.fragments;

import android.content.Context;

import android.util.Log;
import android.view.View;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentLoanBinding;


public class LoanFragment extends BaseFragment<FragmentLoanBinding> {

    private static final String TAG = "TAG_LoanFragment";

    private FragmentLoanBinding mBinding;
    private View mFragView;
    private Context mContext;

    //TODO plantage si menuAttached return 0
    @Override
    protected int getMenuAttached() { return R.menu.menu_search; }

    @Override
    protected int getFragmentLayout() { return R.layout.fragment_loan; }

    @Override
    protected void configureDesign(FragmentLoanBinding pBinding) {
        mBinding = pBinding;
        mFragView = mBinding.getRoot();
        mContext = getContext();
        mBinding.fragLoanBtnCalculate.setOnClickListener(v -> calculateMonthlyPayment());
    }

    private void calculateMonthlyPayment() {
        Log.d(TAG, "calculateMonthlyPayment: ");
    }
}