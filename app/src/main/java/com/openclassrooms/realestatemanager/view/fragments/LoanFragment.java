package com.openclassrooms.realestatemanager.view.fragments;

import android.content.Context;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentLoanBinding;
import com.openclassrooms.realestatemanager.model.MonthlyPayment;


public class LoanFragment extends BaseFragment<FragmentLoanBinding> {

    private FragmentLoanBinding mBinding;

    @Override
    protected int getMenuAttached() { return R.menu.menu_confirm; }

    @Override
    protected int getFragmentLayout() { return R.layout.fragment_loan; }

    @Override
    protected void configureDesign(FragmentLoanBinding pBinding) {
        mBinding = pBinding;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        if (pItem.getItemId() == R.id.menu_action_confirm) {
            calculateMonthlyPayment();
            return true;
        }
        return super.onOptionsItemSelected(pItem);
    }

    private void calculateMonthlyPayment() {
        MonthlyPayment lPayment = new MonthlyPayment();
        lPayment.setMpAmountBorrowed(Integer.parseInt(mBinding.fragLoanEtPrice.getText().toString()));
        lPayment.setMpContribution(Integer.parseInt(mBinding.fragLoanEtContribution.getText().toString()));
        lPayment.setMpRate(Double.parseDouble(mBinding.fragLoanEtRate.getText().toString()));
        lPayment.setMpDuration(Integer.parseInt(mBinding.fragLoanEtDuration.getText().toString()));

        mBinding.fragLoanTvMonthlyPaymentResult.setText(String.valueOf(lPayment.calculateMonthlyPayment()));
    }
}