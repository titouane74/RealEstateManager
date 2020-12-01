package com.openclassrooms.realestatemanager.view.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentReAddEditBinding;
import com.openclassrooms.realestatemanager.databinding.FragmentReSearchBinding;
import com.openclassrooms.realestatemanager.utils.REMHelper;
import com.openclassrooms.realestatemanager.view.adapters.AddEditPhotoAdapter;
import com.openclassrooms.realestatemanager.viewmodel.ReAddEditViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReSearchFragment extends BaseFragment<FragmentReSearchBinding> {

    private static final String TAG = "TAG_ReSearchFragment";
    private View mFragView;
    private Context mContext;


    @Override
    protected int getMenuAttached() { return R.menu.menu_confirm; }

    @Override
    protected int getFragmentLayout() { return R.layout.fragment_re_search; }

    @Override
    protected void configureDesign(FragmentReSearchBinding pBinding) {
        mBinding = pBinding;
        mFragView = mBinding.getRoot();
        mContext = getContext();
        configureSpinners();

        mBinding.fragReSearchEtMarketDate.setOnClickListener(v -> displayCalendarDialogMarket ());
        mBinding.fragReSearchEtSoldDate.setOnClickListener(v-> displayCalendarDialogSold());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        if (pItem.getItemId() == R.id.menu_action_confirm) {
            Toast.makeText(getContext(), getString(R.string.default_txt_confirm), Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(pItem);
    }

    private void configureSpinners() {
        mBinding.fragReSearchSpinType.setAdapter(REMHelper.paramSpinAdapter(mContext,R.array.type_spinner));
        mBinding.fragReSearchSpinRooms.setAdapter(REMHelper.paramSpinAdapter(mContext,R.array.rooms_spinner));
        mBinding.fragReSearchSpinBedrooms.setAdapter(REMHelper.paramSpinAdapter(mContext,R.array.rooms_spinner));
        mBinding.fragReSearchSpinBathrooms.setAdapter(REMHelper.paramSpinAdapter(mContext,R.array.rooms_spinner));
        mBinding.fragReSearchSpinCountry.setAdapter(REMHelper.paramSpinAdapter(mContext,R.array.country_spinner));
        mBinding.fragReSearchSpinNbPhoto.setAdapter(REMHelper.paramSpinAdapter(mContext,R.array.photo_spinner));
    }


    private void displayCalendarDialogMarket() {
        Calendar lCalendar = Calendar.getInstance();
        final String[] mDate = new String[1];

        DatePickerDialog lDatePickerDialog = new DatePickerDialog(
                mContext,
                (view, year, month, dayOfMonth) -> {
                    @SuppressLint("SimpleDateFormat") DateFormat lDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar lDateCal = Calendar.getInstance();

                    lDateCal.set(year,month,dayOfMonth);
                    String lDate = lDateFormat.format(lDateCal.getTime());
                    mBinding.fragReSearchEtMarketDate.setText(lDate);
                },
                lCalendar.get(Calendar.YEAR),
                lCalendar.get(Calendar.MONTH),
                lCalendar.get(Calendar.DAY_OF_MONTH)
        );
        lDatePickerDialog.show();
    }
    private void displayCalendarDialogSold() {
        Calendar lCalendar = Calendar.getInstance();
        final String[] mDate = new String[1];

        DatePickerDialog lDatePickerDialog = new DatePickerDialog(
                mContext,
                (view, year, month, dayOfMonth) -> {
                    @SuppressLint("SimpleDateFormat") DateFormat lDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar lDateCal = Calendar.getInstance();

                    lDateCal.set(year,month,dayOfMonth);
                    String lDate = lDateFormat.format(lDateCal.getTime());
                    mBinding.fragReSearchEtSoldDate.setText(lDate);
                },
                lCalendar.get(Calendar.YEAR),
                lCalendar.get(Calendar.MONTH),
                lCalendar.get(Calendar.DAY_OF_MONTH)
        );
        lDatePickerDialog.show();
    }

}