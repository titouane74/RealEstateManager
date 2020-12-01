package com.openclassrooms.realestatemanager.view.fragments;

import android.content.Context;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentLoanBinding;


public class LoanFragment extends BaseFragment<FragmentLoanBinding> {

    private static final String TAG = "TAG_LoanFragment";

    private FragmentLoanBinding mBinding;
    private View mFragView;
    private Context mContext;

    //TODO plantage si menuAttached return 0
    @Override
    protected int getMenuAttached() { return R.menu.menu_confirm; }

    @Override
    protected int getFragmentLayout() { return R.layout.fragment_loan; }

    @Override
    protected void configureDesign(FragmentLoanBinding pBinding) {
        mBinding = pBinding;
        mFragView = mBinding.getRoot();
        mContext = getContext();
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
        Toast.makeText(getContext(), getString(R.string.txt_monthly_payment), Toast.LENGTH_SHORT).show();
        Log.d(TAG, "calculateMonthlyPayment: ");
    }
}