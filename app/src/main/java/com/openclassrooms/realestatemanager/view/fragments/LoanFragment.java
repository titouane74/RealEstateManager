package com.openclassrooms.realestatemanager.view.fragments;

import android.content.Context;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentLoanBinding;
import com.openclassrooms.realestatemanager.model.MonthlyPayment;
import com.openclassrooms.realestatemanager.utils.REMHelper;


public class LoanFragment extends BaseFragment<FragmentLoanBinding> {

    private FragmentLoanBinding mBinding;
    private Context mContext;

    @Override
    protected int getMenuAttached() {
        return R.menu.menu_confirm;
    }

    @Override
    protected int getFragmentLayout() {
        return R.layout.fragment_loan;
    }

    @Override
    protected void configureDesign(FragmentLoanBinding pBinding) {
        mBinding = pBinding;
        View lFragView = mBinding.getRoot();
        mContext = lFragView.getContext();

        mBinding.fragLoanEtPrice.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (!mBinding.fragLoanEtPrice.getText().toString().equals("")) {
                    mBinding.fragLoanEtPrice.setText(REMHelper.formatStringNumberWithCommaAndCurrency(mBinding.fragLoanEtPrice.getText().toString()));
                }
            }
        });
        mBinding.fragLoanEtContribution.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (!mBinding.fragLoanEtContribution.getText().toString().equals("")) {
                    mBinding.fragLoanEtContribution.setText(REMHelper.formatStringNumberWithCommaAndCurrency(mBinding.fragLoanEtContribution.getText().toString()));
                }
            }
        });
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

        if (validInput()) {
            MonthlyPayment lPayment = new MonthlyPayment();

            int lPrice = REMHelper.formatStringNumberWithCommaAndCurrencyToInt(mBinding.fragLoanEtPrice.getText().toString());
            lPayment.setMpAmountBorrowed(lPrice);

            lPayment.setMpRate(Double.parseDouble(mBinding.fragLoanEtRate.getText().toString()));
            lPayment.setMpDuration(Integer.parseInt(mBinding.fragLoanEtDuration.getText().toString()));

            int lContribution =0;
            if (!mBinding.fragLoanEtContribution.getText().toString().equals("")) {
                lContribution = REMHelper.formatStringNumberWithCommaAndCurrencyToInt(mBinding.fragLoanEtContribution.getText().toString());
            }
            lPayment.setMpContribution(lContribution);
            String lPay = REMHelper.formatNumberWithCommaAndCurrency(lPayment.calculateMonthlyPayment());

            mBinding.fragLoanTvMonthlyPaymentResult.setText(lPay);
        }
    }

    private boolean validInput() {
        String lField = "";
        if ((mBinding.fragLoanEtPrice.getText() == null) || (mBinding.fragLoanEtPrice.getText().toString().equals(""))) {
            lField = REMHelper.addValueAndSeparatorToString(lField, ",", getString(R.string.txt_price));
        }
        if ((mBinding.fragLoanEtRate.getText() == null) || (mBinding.fragLoanEtRate.getText().toString().equals(""))) {
            lField = REMHelper.addValueAndSeparatorToString(lField, ",", getString(R.string.loan_txt_err_field_rate));
        }
        if ((mBinding.fragLoanEtDuration.getText() == null) || (mBinding.fragLoanEtDuration.getText().toString().equals(""))) {
            lField = REMHelper.addValueAndSeparatorToString(lField, ",", getString(R.string.loan_txt_err_field_duration));
        }
        if (lField.length() > 0) {
            Toast.makeText(mContext, getString(R.string.loan_txt_err_valid_input) + " " + lField, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}