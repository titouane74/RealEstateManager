package com.openclassrooms.realestatemanager.view.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentReAddEditBinding;
import com.openclassrooms.realestatemanager.utils.REMHelper;
import com.openclassrooms.realestatemanager.viewmodel.ReAddEditViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ReAddEditFragment extends BaseFragment<FragmentReAddEditBinding>{

    private static final String TAG = "TAG_REAddFragment";

    private View mFragView;

    private FragmentReAddEditBinding mBinding;

    private ReAddEditViewModel mViewModel;
    private Context mContext;

    private Calendar mDateCal;

    @Override
    protected int getMenuAttached() {
        return R.menu.menu_save;
    }

    @Override
    protected int getFragmentLayout() { return
            R.layout.fragment_re_add_edit;
    }

    @Override
    protected void configureDesign(FragmentReAddEditBinding pBinding) {
        mBinding = pBinding;
        mFragView = mBinding.getRoot();
        mContext = getContext();
        configureSpinners();

        mBinding.fragAddEditCardvDateOnMarket.setOnClickListener(v -> displayCalendarDialog ());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        if (pItem.getItemId() == R.id.menu_action_save) {
            Toast.makeText(getContext(), "SAVE", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(pItem);
    }

    private void configureSpinners() {
        mBinding.fragReAddSpinType.setAdapter(REMHelper.paramSpinAdapter(mContext,R.array.type_spinner));
        mBinding.fragReAddSpinRooms.setAdapter(REMHelper.paramSpinAdapter(mContext,R.array.rooms_spinner));
        mBinding.fragReAddSpinBedrooms.setAdapter(REMHelper.paramSpinAdapter(mContext,R.array.rooms_spinner));
        mBinding.fragReAddSpinBathrooms.setAdapter(REMHelper.paramSpinAdapter(mContext,R.array.rooms_spinner));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ReAddEditViewModel.class);
        // TODO: Use the ViewModel
    }

    /**
     * Display calendar dialog box
     */
    private void displayCalendarDialog () {
        Calendar lCalendar = Calendar.getInstance();

        DatePickerDialog lDatePickerDialog = new DatePickerDialog(
                mContext,
                (view, year, month, dayOfMonth) -> {
                    @SuppressLint("SimpleDateFormat") DateFormat lDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    mDateCal = Calendar.getInstance();

                    mDateCal.set(year,month,dayOfMonth);
                    String lDate = lDateFormat.format(mDateCal.getTime());
                    mBinding.fragAddEditEtMarketDate.setText(lDate);
                },
                lCalendar.get(Calendar.YEAR),
                lCalendar.get(Calendar.MONTH),
                lCalendar.get(Calendar.DAY_OF_MONTH)
        );
        lDatePickerDialog.show();
    }
}