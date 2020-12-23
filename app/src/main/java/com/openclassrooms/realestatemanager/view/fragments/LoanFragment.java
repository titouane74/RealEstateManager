package com.openclassrooms.realestatemanager.view.fragments;

import android.content.Context;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentLoanBinding;
import com.openclassrooms.realestatemanager.model.MonthlyPayment;
import com.openclassrooms.realestatemanager.utils.REMHelper;


public class LoanFragment extends BaseFragment<FragmentLoanBinding> {

    private FragmentLoanBinding mBinding;
    private Context mContext;
    private String mPrice;
    private String mContribution;

    @Override
    protected void configureDesign(FragmentLoanBinding pBinding, boolean pIsTablet) {
        setHasOptionsMenu(true);
        mBinding = pBinding;
        mContext = mBinding.getRoot().getContext();

        mBinding.fragLoanEtPrice.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (!mBinding.fragLoanEtPrice.getText().toString().equals("")) {
                    mPrice = mBinding.fragLoanEtPrice.getText().toString();
                }
            }
        });
        mBinding.fragLoanEtContribution.setOnFocusChangeListener((v, hasFocus) -> {
            if (!hasFocus) {
                if (!mBinding.fragLoanEtContribution.getText().toString().equals("")) {
                    mContribution = mBinding.fragLoanEtContribution.getText().toString();
                }
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_confirm, menu);
        super.onCreateOptionsMenu(menu, inflater);
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

            lPayment.setMpAmountBorrowed(Integer.parseInt(mPrice));

            lPayment.setMpRate(Double.parseDouble(mBinding.fragLoanEtRate.getText().toString()));
            lPayment.setMpDuration(Integer.parseInt(mBinding.fragLoanEtDuration.getText().toString()));

            int lContribution =0;
            if (!mBinding.fragLoanEtContribution.getText().toString().equals("")) {
                lContribution = Integer.parseInt(mContribution);
            }
            lPayment.setMpContribution(lContribution);
            String lPay = REMHelper.formatNumberWithCommaAndCurrency(lPayment.calculateMonthlyPayment());

            mBinding.fragLoanTvMonthlyPaymentResult.setText(lPay);
        }
    }

    private boolean validInput() {
        String lField = "";
        if ((mBinding.fragLoanEtPrice.getText() == null) || (mBinding.fragLoanEtPrice.getText().toString().equals(""))) {
            lField = REMHelper.addValueAndSeparatorToString(lField, " , ", getString(R.string.txt_price));
        }
        if ((mBinding.fragLoanEtRate.getText() == null) || (mBinding.fragLoanEtRate.getText().toString().equals(""))) {
            lField = REMHelper.addValueAndSeparatorToString(lField, " , ", getString(R.string.loan_txt_err_field_rate));
        }
        if ((mBinding.fragLoanEtDuration.getText() == null) || (mBinding.fragLoanEtDuration.getText().toString().equals(""))) {
            lField = REMHelper.addValueAndSeparatorToString(lField, " , ", getString(R.string.loan_txt_err_field_duration));
        }
        if (lField.length() > 0) {
            Toast.makeText(mContext, getString(R.string.loan_txt_err_valid_input) + " " + lField, Toast.LENGTH_SHORT).show();
            return false;
        } else {
            return true;
        }
    }
}