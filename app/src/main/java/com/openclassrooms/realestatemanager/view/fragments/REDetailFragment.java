package com.openclassrooms.realestatemanager.view.fragments;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.viewmodel.REDetailViewModel;

public class REDetailFragment extends Fragment {

    private static final String TAG = "REDetailFragment";
    private TextView mREPrice;
    private TextView mRENbRooms;
    private TextView mRENbBedrooms;
    private TextView mREnBBathrooms;
    private TextView mREType;
    private TextView mREDescription;
    private TextView mREArea;

    private REDetailViewModel mViewModel;

    public static REDetailFragment newInstance() {
        return new REDetailFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View lView =  inflater.inflate(R.layout.fragment_re_detail, container, false);
        bindView(lView);
        return lView;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            REDetailFragmentArgs lArgs = REDetailFragmentArgs.fromBundle(getArguments());

            RealEstate lRE = lArgs.getRealestate();
            Log.i(TAG, "onViewCreated: " + lRE.toString());
            displayRealEstate(lRE);
        }
    }

    @SuppressLint("SetTextI18n")
    private void displayRealEstate(RealEstate pRE) {
        mREPrice.setText(Integer.toString(pRE.getRePrice()));
        mRENbRooms.setText(Integer.toString(pRE.getReNbRooms()));
        mRENbBedrooms.setText(Integer.toString(pRE.getReNbBedrooms()));
        mREnBBathrooms.setText(Integer.toString(pRE.getReNbBathrooms()));
        mREType.setText(pRE.getReType());
        mREDescription.setText(pRE.getReDescription());
        mREArea.setText(Integer.toString(pRE.getReArea()));
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(REDetailViewModel.class);
        // TODO: Use the ViewModel
    }
    private void bindView(View pView) {
        mREPrice = pView.findViewById(R.id.frag_re_det_tv_price);
        mRENbRooms = pView.findViewById(R.id.frag_re_det_tv_nb_rooms);
        mRENbBedrooms = pView.findViewById(R.id.frag_re_det_tv_nb_bedrooms);
        mREnBBathrooms = pView.findViewById(R.id.frag_re_det_tv_nb_bathrooms);
        mREType = pView.findViewById(R.id.frag_re_det_tv_type);
        mREDescription = pView.findViewById(R.id.frag_re_det_tv_description);
        mREArea = pView.findViewById(R.id.frag_re_det_tv_area);
    }
}