package com.openclassrooms.realestatemanager.view.fragments;

import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.utils.REMHelper;
import com.openclassrooms.realestatemanager.viewmodel.REAddViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

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
    private CheckBox mCbBank;
    private CheckBox mCbFood;
    private CheckBox mCbHealth;
    private CheckBox mCbRestaurant;
    private CheckBox mCbSchool;
    private CheckBox mCbStore;
    private CheckBox mCbSubway;
    private CheckBox mCbPark;
    private Button mCancel;
    private Button mSave;
    private View mFragView;
    private Toolbar mToolbar;

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
        mFragView = inflater.inflate(R.layout.fragment_scroll_re_add, container, false);
        mContext = mFragView.getContext();

        bindView(mFragView);

        configureSpinners();

        mDateOnMarket.setOnClickListener(v -> displayCalendarDialog ());
//        mPoiTV.setOnClickListener(v -> displayPOIDialog());
//        mCancel.setOnClickListener(v-> closeFragment());

        return mFragView;
    }

    private void closeFragment() {
        Navigation.findNavController(mFragView).navigate(R.id.action_nav_re_add_to_nav_re_list);
    }

/*
    private void displayPOIDialog() {
        DialogFragment lDialogPoi = new POIDialogFragment();
        lDialogPoi.show(getChildFragmentManager(),"POIDialogFragment");
    }
*/

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
//        mPoiChipGroup = pView.findViewById(R.id.id_frag_re_add_chipgrp_poi);
        mCbBank = pView.findViewById(R.id.frag_re_add_poi_bank);
        mCbFood = pView.findViewById(R.id.frag_re_add_poi_food);
        mCbHealth = pView.findViewById(R.id.frag_re_add_poi_health);
        mCbRestaurant = pView.findViewById(R.id.frag_re_add_poi_restaurant);
        mCbSchool = pView.findViewById(R.id.frag_re_add_poi_school);
        mCbStore = pView.findViewById(R.id.frag_re_add_poi_store);
        mCbSubway = pView.findViewById(R.id.frag_re_add_poi_subway);
        mCbPark = pView.findViewById(R.id.frag_re_add_poi_park);
        mCancel = pView.findViewById(R.id.btn_cancel);
        mSave = pView.findViewById(R.id.btn_save);
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