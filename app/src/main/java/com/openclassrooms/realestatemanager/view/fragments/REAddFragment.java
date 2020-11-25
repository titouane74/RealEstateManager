package com.openclassrooms.realestatemanager.view.fragments;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.utils.REMHelper;
import com.openclassrooms.realestatemanager.viewmodel.REAddViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class REAddFragment extends Fragment implements POIDialogFragment.OnDialogPOIListener {

    private static final String TAG = "TAG_REAddFragment";

    //DESIGN
    private EditText mDateOnMarket;
    private Spinner mSpinType;
    private Spinner mSpinRooms;
    private Spinner mSpinBedrooms;
    private Spinner mSpinBathrooms;
    private TextView mPoiTV;
    private ChipGroup mPoiChipGroup;

    //LIFECYCLE
    private REAddViewModel mViewModel;
    private Context mContext;

    private Calendar mDateCal;

    public static REAddFragment newInstance() {
        return new REAddFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View lView = inflater.inflate(R.layout.fragment_scroll_re_add, container, false);
        mContext = lView.getContext();

        bindView(lView);

        configureSpinners();

        mDateOnMarket.setOnClickListener(v -> displayCalendarDialog ());
        mPoiTV.setOnClickListener(v -> displayPOIDialog());

        return lView;
    }

    private void displayPOIDialog() {
        DialogFragment lDialogPoi = new POIDialogFragment();
        lDialogPoi.show(getChildFragmentManager(),"POIDialogFragment");
    }

    private void configureSpinners() {
        mSpinType.setAdapter(REMHelper.paramSpinAdapter(mContext,R.array.type_spinner));
        mSpinRooms.setAdapter(REMHelper.paramSpinAdapter(mContext,R.array.rooms_spinner));
        mSpinBedrooms.setAdapter(REMHelper.paramSpinAdapter(mContext,R.array.rooms_spinner));
        mSpinBathrooms.setAdapter(REMHelper.paramSpinAdapter(mContext,R.array.rooms_spinner));
    }

    private void bindView(View pView) {
        mDateOnMarket = pView.findViewById(R.id.frag_add_tv_market_date);
        mSpinType = pView.findViewById(R.id.frag_re_add_spin_type);
        mSpinRooms = pView.findViewById(R.id.frag_re_add_spin_rooms);
        mSpinBedrooms = pView.findViewById(R.id.frag_re_add_spin_bedrooms);
        mSpinBathrooms = pView.findViewById(R.id.frag_re_add_spin_bathrooms);
        mPoiTV = pView.findViewById(R.id.frag_add_tv_poi);
        mPoiChipGroup = pView.findViewById(R.id.id_frag_re_add_chipgrp_poi);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(REAddViewModel.class);
        // TODO: Use the ViewModel
    }

    /**
     * OnClick du champs meeting_date_et : affiche la boîte de dialogue calendrier
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
                    mDateOnMarket.setText(lDate);
                },
                lCalendar.get(Calendar.YEAR),
                lCalendar.get(Calendar.MONTH),
                lCalendar.get(Calendar.DAY_OF_MONTH)
        );
        lDatePickerDialog.show();
    }


    @Override
    public void onPOIOkClicked(List<String> pList) {

        for(int lIndex=0; lIndex < pList.size();lIndex++) {
            Log.d(TAG, "onPOIOkClicked: " + pList.get(lIndex));
        }

        final Chip lChipPoi = new Chip(requireContext());

        for(int lIndex=0; lIndex < pList.size();lIndex++) {
            lChipPoi.setText(pList.get(lIndex));
            lChipPoi.setCloseIconVisible(true);
            lChipPoi.setOnCloseIconClickListener(v -> mPoiChipGroup.removeView(lChipPoi));

            mPoiChipGroup.addView(lChipPoi);
            mPoiChipGroup.setVisibility(View.VISIBLE);
        }

    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}