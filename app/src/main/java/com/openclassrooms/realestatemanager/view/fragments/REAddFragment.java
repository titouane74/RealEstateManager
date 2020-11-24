package com.openclassrooms.realestatemanager.view.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.viewmodel.REAddViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class REAddFragment extends Fragment {

    //DESIGN
    private EditText mDateOnMarket;

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

        mDateOnMarket.setOnClickListener(v -> displayCalendarDialog ());

        return lView;
    }

    private void bindView(View pView) {
        mDateOnMarket = pView.findViewById(R.id.frag_add_tv_market_date);
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
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        DateFormat lDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        mDateCal = Calendar.getInstance();

                        mDateCal.set(year,month,dayOfMonth);
                        String lDate = lDateFormat.format(mDateCal.getTime());
                        mDateOnMarket.setText(lDate);
                    }
                },
                lCalendar.get(Calendar.YEAR),
                lCalendar.get(Calendar.MONTH),
                lCalendar.get(Calendar.DAY_OF_MONTH)
        );
        lDatePickerDialog.show();
    }

}