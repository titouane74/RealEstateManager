package com.openclassrooms.realestatemanager.view.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.utils.REMHelper;
import com.openclassrooms.realestatemanager.viewmodel.REAddViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class REEditFragment extends BaseFragment {

    private static final String TAG = "TAG_REEditFragment";

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

    public static REEditFragment newInstance() {
        return new REEditFragment();
    }

    @Override
    protected int getMenuAttached() { return R.menu.menu_save; }

    @Override
    protected int getFragmentLayout() { return R.layout.fragment_re_edit; }

    @Override
    protected void configureDesign(View pView) {
        mContext = getContext();
        mFragView = pView;
        bindView(mFragView);

        configureSpinners();

        mDateOnMarket.setOnClickListener(v -> displayCalendarDialog ());
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
        mCbBank = pView.findViewById(R.id.frag_re_add_poi_bank);
        mCbFood = pView.findViewById(R.id.frag_re_add_poi_food);
        mCbHealth = pView.findViewById(R.id.frag_re_add_poi_health);
        mCbRestaurant = pView.findViewById(R.id.frag_re_add_poi_restaurant);
        mCbSchool = pView.findViewById(R.id.frag_re_add_poi_school);
        mCbStore = pView.findViewById(R.id.frag_re_add_poi_store);
        mCbSubway = pView.findViewById(R.id.frag_re_add_poi_subway);
        mCbPark = pView.findViewById(R.id.frag_re_add_poi_park);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(REAddViewModel.class);
        // TODO: Use the ViewModel
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        if (pItem.getItemId() == R.id.menu_action_save) {
            Toast.makeText(getContext(), "SAVE", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(pItem);
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }
}