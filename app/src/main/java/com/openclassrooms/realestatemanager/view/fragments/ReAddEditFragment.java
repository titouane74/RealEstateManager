package com.openclassrooms.realestatemanager.view.fragments;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentReAddEditBinding;
import com.openclassrooms.realestatemanager.di.Injection;
import com.openclassrooms.realestatemanager.di.ReViewModelFactory;
import com.openclassrooms.realestatemanager.model.ReLocation;
import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.utils.REMHelper;
import com.openclassrooms.realestatemanager.view.adapters.AddEditPhotoAdapter;
import com.openclassrooms.realestatemanager.viewmodel.ReAddEditViewModel;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.openclassrooms.realestatemanager.view.adapters.ReListAdapter.IS_EDIT_KEY;
import static com.openclassrooms.realestatemanager.view.adapters.ReListAdapter.RE_ID_KEY;

public class ReAddEditFragment extends BaseFragment<FragmentReAddEditBinding> {

    private static final String TAG = "TAG_REAddFragment";

    private View mFragView;

    private FragmentReAddEditBinding mBinding;

    private AddEditPhotoAdapter mAdapter;
    private ReAddEditViewModel mViewModel;
    private Context mContext;
    private NavController mNavController;
    private Calendar mDateCal;
    private boolean mIsTabletLandscape;
    private boolean mIsEdit;

    private RealEstate mRealEstate = new RealEstate();
    private long mReId;
    private ReLocation mReLocation = new ReLocation();

    @Override
    protected int getMenuAttached() {
        return R.menu.menu_save;
    }

    @Override
    protected void configureDesign(FragmentReAddEditBinding pBinding, NavController pNavController, boolean pIsTablet, boolean pIsTabletLandscape) {
        mBinding = pBinding;
        mFragView = mBinding.getRoot();
        mContext = getContext();
        mNavController = pNavController;
        mIsTabletLandscape = pIsTabletLandscape;
        configureSpinners();
        initRecyclerView();
        mBinding.fragReAddEditEtMarketDate.setOnClickListener(v -> displayCalendarDialogMarket());
        mBinding.fragReAddEditEtSoldDate.setOnClickListener(v -> displayCalendarDialogSold());

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            mReId = getArguments().getInt(RE_ID_KEY);
            mIsEdit = getArguments().getBoolean(IS_EDIT_KEY);
            Log.d(TAG, "onViewCreated: AddEdit args in : " + mReId + " IsEdit : " + mIsEdit);
        } else {
            Log.d(TAG, "onViewCreated: AddEdit : args null");
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        if (pItem.getItemId() == R.id.menu_action_save) {
            prepareRealEstate();
            if (!mIsTabletLandscape) {
                mNavController.navigate(R.id.action_reAddFragment_to_reListFragment);
            }
            return true;
        }
        return super.onOptionsItemSelected(pItem);
    }

    private void prepareRealEstate() {

        boolean lIsSold = mBinding.fragReAddEditCbSold.isSelected();
        String lAgentFirstName = mBinding.fragReAddEditEtAgentFirstName.getText().toString();
        String lAgentLastName = mBinding.fragReAddEditEtAgentLastName.getText().toString();

        String lDescription = mBinding.fragReAddEditEtDescription.getText().toString();

        String lType = mBinding.fragReAddEditSpinType.getSelectedItem().toString();
        int lArea = Integer.parseInt(mBinding.fragReAddEditEtArea.getText().toString());
        int lPrice = Integer.parseInt(mBinding.fragReAddEditEtPrice.getText().toString());
        int lNbRooms = REMHelper.convertSpinnerValueToInt(mBinding.fragReAddEditSpinRooms.getSelectedItem().toString());
        int lNbBedRooms = REMHelper.convertSpinnerValueToInt(mBinding.fragReAddEditSpinBedrooms.getSelectedItem().toString());
        int lNbBathRooms = REMHelper.convertSpinnerValueToInt(mBinding.fragReAddEditSpinBathrooms.getSelectedItem().toString());

        managePoi();

        mRealEstate = new RealEstate(lType, lPrice, lArea, lNbRooms, lNbBedRooms, lNbBathRooms, lDescription, lIsSold,
                lAgentFirstName, lAgentLastName);
/*
        mRealEstate.setReIsSold(lIsSold);
        mRealEstate.setReAgentFirstName(lAgentFirstName);
        mRealEstate.setReAgentLastName(lAgentLastName);
        mRealEstate.setReDescription(lDescription);
        mRealEstate.setReType(lType);
        mRealEstate.setReArea(lArea);
        mRealEstate.setRePrice(lPrice);
        mRealEstate.setReNbRooms(lNbRooms);
        mRealEstate.setReNbBedrooms(lNbBedRooms);
        mRealEstate.setReNbBathrooms(lNbBathRooms);
*/

        if (!mIsEdit) {
            mViewModel.insertRealEstate(mRealEstate);
            mViewModel.selectMaxReId().observe(getViewLifecycleOwner(), pMaxReId -> {
                manageLocation(pMaxReId);
            });
        } else {
            mRealEstate.setReId(mReId);
            mViewModel.updateRealEstate(mRealEstate);
        }
    }

    private void manageLocation(int pMaxReId) {
        String lStreet = mBinding.fragReAddEditEtStreet.getText().toString();
        String lDistrict = mBinding.fragReAddEditEtDistrict.getText().toString();
        String lCity = mBinding.fragReAddEditEtCity.getText().toString();
        String lCounty = mBinding.fragReAddEditEtCounty.getText().toString();
        int lZipCode = Integer.parseInt(mBinding.fragReAddEditEtZipCode.getText().toString());
        String lCountry = mBinding.fragReAddEditSpinCountry.getSelectedItem().toString();

        mReLocation = new ReLocation(pMaxReId, lStreet, lDistrict, lCity, lCounty, lZipCode, lCountry);
        if (!mIsEdit) {
            mViewModel.insertReLocation(mReLocation);
        } else {
            //TODO
            //mReLocation.setLocId();
        }
    }

    private void managePoi() {
/*                <item>@string/poi_restaurant</item>
        <item>@string/poi_subway</item>
        <item>@string/poi_school</item>
        <item>@string/poi_park</item>
        <item>@string/poi_store</item>
        <item>@string/poi_bank</item>*/

    }

    private void configureSpinners() {
        mBinding.fragReAddEditSpinType.setAdapter(REMHelper.configureSpinAdapter(mContext, R.array.type_spinner));
        mBinding.fragReAddEditSpinRooms.setAdapter(REMHelper.configureSpinAdapter(mContext, R.array.rooms_spinner));
        mBinding.fragReAddEditSpinBedrooms.setAdapter(REMHelper.configureSpinAdapter(mContext, R.array.rooms_spinner));
        mBinding.fragReAddEditSpinBathrooms.setAdapter(REMHelper.configureSpinAdapter(mContext, R.array.rooms_spinner));
        mBinding.fragReAddEditSpinCountry.setAdapter(REMHelper.configureSpinAdapter(mContext, R.array.country_spinner));
    }

    private void initRecyclerView() {
        mAdapter = new AddEditPhotoAdapter();
        mBinding.fragReAddEditRvPhoto.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mBinding.fragReAddEditRvPhoto.setAdapter(mAdapter);
        initPhotoList();
    }

    private void initPhotoList() {
        List<String> lPhotoList = new ArrayList<>();
        lPhotoList.add("https://lemagdesanimaux.ouest-france.fr/images/dossiers/2019-06/cheval-073016.jpg");
        lPhotoList.add("https://www.30millionsdamis.fr/uploads/pics/conseils-erreurs-chat-1171.jpg");
        lPhotoList.add("https://cdn.futura-sciences.com/buildsv6/images/wide1920/a/0/f/a0fc73919d_50166390_chaton.jpg");
        lPhotoList.add("https://www.i-cad.fr/uploads/5bec27af5afec.jpeg");
        lPhotoList.add("https://www.dogteur.com/media/magpleasure/mpblog/list_thumbnail_file/e/a/cache/5/ece9a24a761836a70934a998c163f8c8/eaf7d56dbea1bb003bb0bb649c022bab.jpg");
        lPhotoList.add("https://lemagduchat.ouest-france.fr/images/dossiers/2018-11/chat-drole-113730.jpg");
        lPhotoList.add("https://cdn-s-www.ledauphine.com/images/5FC3042C-0F6B-4D19-87CF-01591980B2D3/NW_detail_M/title-1602592555.jpg");
        lPhotoList.add("https://www.ultrapremiumdirect.com/img/cms/blog/loulou-ronron-therapie.jpg");


        mAdapter.setPhotoList(lPhotoList);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        configureViewModel();
//        if (mIsEdit) {
            mViewModel.getRealEstate(mReId).observe(getViewLifecycleOwner(), pRealEstate -> {
                displayRealEstate(pRealEstate);
            });
//        }
    }

    private void configureViewModel() {
        ReViewModelFactory lFactory = Injection.reViewModelFactory(mContext);
        mViewModel = new ViewModelProvider(this, lFactory).get(ReAddEditViewModel.class);
    }


    /**
     * Display calendar dialog box
     */
    private void displayCalendarDialogMarket() {
        Calendar lCalendar = Calendar.getInstance();

        DatePickerDialog lDatePickerDialog = new DatePickerDialog(
                mContext,
                (view, year, month, dayOfMonth) -> {
                    @SuppressLint("SimpleDateFormat") DateFormat lDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    mDateCal = Calendar.getInstance();

                    mDateCal.set(year, month, dayOfMonth);
                    String lDate = lDateFormat.format(mDateCal.getTime());
                    mBinding.fragReAddEditEtMarketDate.setText(lDate);
                },
                lCalendar.get(Calendar.YEAR),
                lCalendar.get(Calendar.MONTH),
                lCalendar.get(Calendar.DAY_OF_MONTH)
        );
        lDatePickerDialog.show();
    }

    private void displayCalendarDialogSold() {
        Calendar lCalendar = Calendar.getInstance();

        DatePickerDialog lDatePickerDialog = new DatePickerDialog(
                mContext,
                (view, year, month, dayOfMonth) -> {
                    @SuppressLint("SimpleDateFormat") DateFormat lDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    mDateCal = Calendar.getInstance();

                    mDateCal.set(year, month, dayOfMonth);
                    String lDate = lDateFormat.format(mDateCal.getTime());
                    mBinding.fragReAddEditEtSoldDate.setText(lDate);
                },
                lCalendar.get(Calendar.YEAR),
                lCalendar.get(Calendar.MONTH),
                lCalendar.get(Calendar.DAY_OF_MONTH)
        );
        lDatePickerDialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void displayRealEstate(RealEstate pRe) {
        int lPosInAdapter = 0;
        String lNbRooms = "0";

        if (pRe != null) {
            mBinding.fragReAddEditEtPrice.setText(Integer.toString(pRe.getRePrice()));
            mBinding.fragReAddEditCbSold.setSelected(pRe.isReIsSold());

            ArrayAdapter<CharSequence> lAdapter = REMHelper.configureSpinAdapter(mContext, R.array.type_spinner);
            lPosInAdapter = lAdapter.getPosition(pRe.getReType());
            mBinding.fragReAddEditSpinType.setSelection(lPosInAdapter);

            lPosInAdapter = REMHelper.getPositionInRoomSpinner(mContext, R.array.rooms_spinner, pRe.getReNbRooms());
            mBinding.fragReAddEditSpinRooms.setSelection(lPosInAdapter);

            lNbRooms = REMHelper.convertSpinRoomToString(pRe.getReNbBedrooms());
            lPosInAdapter = REMHelper.getPositionInSpinner(mContext, R.array.rooms_spinner, lNbRooms);
            mBinding.fragReAddEditSpinBedrooms.setSelection(lPosInAdapter);
            lNbRooms = REMHelper.convertSpinRoomToString(pRe.getReNbBathrooms());
            lPosInAdapter = REMHelper.getPositionInSpinner(mContext, R.array.rooms_spinner, lNbRooms);
            mBinding.fragReAddEditSpinBathrooms.setSelection(lPosInAdapter);

            mBinding.fragReAddEditEtDescription.setText(pRe.getReDescription());
            mBinding.fragReAddEditEtArea.setText(Integer.toString(pRe.getReArea()));


        }
    }
}