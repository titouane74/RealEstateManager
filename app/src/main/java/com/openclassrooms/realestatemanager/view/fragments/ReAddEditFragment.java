package com.openclassrooms.realestatemanager.view.fragments;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.android.gms.maps.model.LatLng;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentReAddEditBinding;
import com.openclassrooms.realestatemanager.di.Injection;
import com.openclassrooms.realestatemanager.di.ReViewModelFactory;
import com.openclassrooms.realestatemanager.model.ReLocation;
import com.openclassrooms.realestatemanager.model.RePhoto;
import com.openclassrooms.realestatemanager.model.RePoi;
import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.model.RealEstateComplete;
import com.openclassrooms.realestatemanager.notification.NotifyWorker;
import com.openclassrooms.realestatemanager.utils.REMHelper;
import com.openclassrooms.realestatemanager.utils.REMHelperAddEdit;
import com.openclassrooms.realestatemanager.utils.Utils;
import com.openclassrooms.realestatemanager.view.adapters.AddEditPhotoAdapter;
import com.openclassrooms.realestatemanager.viewmodel.ReAddEditViewModel;


import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.openclassrooms.realestatemanager.view.adapters.ReListAdapter.IS_EDIT_KEY;
import static com.openclassrooms.realestatemanager.view.adapters.ReListAdapter.RE_ID_KEY;

public class ReAddEditFragment extends BaseFragment<FragmentReAddEditBinding> {

    private static final String TAG = "TAG_REAddFragment";

    private FragmentReAddEditBinding mBinding;

    private AddEditPhotoAdapter mAdapter;
    private ReAddEditViewModel mViewModel;
    private Context mContext;
    private NavController mNavController;
    private String mStringDateMarket;
    private String mStringDateSold;
    private boolean mIsTabletLandscape;
    private boolean mIsEdit;
    private boolean mIsLocationEmpty = true;
    private boolean mIsReEmpty = true;
    private boolean mIsPhotoEmpty = true;
    private boolean mIsPoiEmpty = true;
    private Date mDateMarket = null;
    private Date mDateSold = null;
    private boolean mIsMandatoryDataComplete = false;

    private long mReId;
    private RealEstate mRealEstate = new RealEstate();
    private ReLocation mReLocation = new ReLocation();
    private List<RePoi> mPoiList = new ArrayList<>();
    private List<RePhoto> mPhotoList = new ArrayList<>();
    private RealEstateComplete mReComp;
    private List<RePhoto> mInitialPhotoList = new ArrayList<>();

    @Override
    protected int getMenuAttached() {
        return R.menu.menu_save;
    }

    @Override
    protected void configureDesign(FragmentReAddEditBinding pBinding, NavController pNavController, boolean pIsTablet, boolean pIsTabletLandscape) {
        mBinding = pBinding;
        mContext = getContext();
        mNavController = pNavController;
        mIsTabletLandscape = pIsTabletLandscape;

        configureSpinners();
        initRecyclerView();

        mBinding.fragReAddEditEtMarketDate.setOnClickListener(v -> {
            displayCalendarDialog(R.id.frag_re_add_edit_et_market_date);
            mDateMarket = (mStringDateMarket != null) ? REMHelper.convertStringToDate(mStringDateMarket) : null;
        });
        mBinding.fragReAddEditEtSoldDate.setOnClickListener(v -> {
            displayCalendarDialog(R.id.frag_re_add_edit_et_sold_date);
            mDateSold = (mStringDateSold != null) ? REMHelper.convertStringToDate(mStringDateSold) : null;
            if ((mDateMarket != null) && (mDateSold != null) && (mDateSold.before(mDateMarket))) {
                Toast.makeText(mContext, getString(R.string.add_edit_txt_err_date_sold_before_date_market), Toast.LENGTH_SHORT).show();
            }
        });
        mBinding.fragReAddEditImgSelectPhoto.setOnClickListener(v -> addPhoto());
    }

    /**
     * Call the image picker to add or take photo
     */
    private void addPhoto() {
        ImagePicker.create(this)
                .start();
    }

    /**
     * Manage the result of the selection of photos in the the image picker
     *
     * @param requestCode : int : request code
     * @param resultCode  : int : result code
     * @param data        : Intent : data
     */
    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            List<Image> images = ImagePicker.getImages(data);
            for (Image lImg : images) {
                mPhotoList.add(new RePhoto(lImg.getName(), lImg.getPath(), lImg.getId()));
            }
            if(!mIsEdit) mInitialPhotoList = mPhotoList;
            mAdapter.setPhotoList(mPhotoList);
            mAdapter.notifyDataSetChanged();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            mReId = getArguments().getLong(RE_ID_KEY);
            mIsEdit = getArguments().getBoolean(IS_EDIT_KEY);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        if (pItem.getItemId() == R.id.menu_action_save) {
            if (Utils.isInternetAvailable(mContext)) {
                manageRealEstate();
                return true;
            } else {
                Toast.makeText(mContext, getString(R.string.default_txt_no_internet_no_backup), Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(pItem);
    }

    /**
     * Manage the real estate information
     */
    private void manageRealEstate() {

        String lAgentFirstName = mBinding.fragReAddEditEtAgentFirstName.getText().toString();
        boolean lIsValidAgentFirstName = REMHelperAddEdit.controlValidityWithRegex(lAgentFirstName, getString(R.string.regex_agent));
        String lAgentLastName = mBinding.fragReAddEditEtAgentLastName.getText().toString();
        boolean lIsValidAgentLastName = REMHelperAddEdit.controlValidityWithRegex(lAgentLastName, getString(R.string.regex_agent));

        String lDescription = mBinding.fragReAddEditEtDescription.getText().toString();

        String lType = mBinding.fragReAddEditSpinType.getSelectedItem().toString().equals(getString(R.string.spinners_select))
                ? "" : mBinding.fragReAddEditSpinType.getSelectedItem().toString();

        int lArea = (!mBinding.fragReAddEditEtArea.getText().toString().equals("")) ? Integer.parseInt(mBinding.fragReAddEditEtArea.getText().toString()) : 0;
        int lPrice = (!mBinding.fragReAddEditEtPrice.getText().toString().equals("")) ? Integer.parseInt(mBinding.fragReAddEditEtPrice.getText().toString()) : 0;

        int lNbRooms = REMHelper.convertSpinnerValueToInt(mBinding.fragReAddEditSpinRooms.getSelectedItem().toString());
        int lNbBedRooms = REMHelper.convertSpinnerValueToInt(mBinding.fragReAddEditSpinBedrooms.getSelectedItem().toString());
        int lNbBathRooms = REMHelper.convertSpinnerValueToInt(mBinding.fragReAddEditSpinBathrooms.getSelectedItem().toString());

        if (mStringDateMarket != null) {
            mDateMarket = REMHelper.convertStringToDate(mStringDateMarket);
        } else if ((mBinding.fragReAddEditEtMarketDate.getText() != null) && (!mBinding.fragReAddEditEtMarketDate.getText().toString().equals(""))) {
            mDateMarket = REMHelper.convertStringToDate(mBinding.fragReAddEditEtMarketDate.getText().toString());
        }

        boolean lIsSold = mBinding.fragReAddEditCbSold.isChecked();
        if (mStringDateSold != null) {
            mDateSold = REMHelper.convertStringToDate(mStringDateSold);
        } else if ((mBinding.fragReAddEditEtSoldDate.getText() != null) && (!mBinding.fragReAddEditEtSoldDate.getText().toString().equals(""))) {
            mDateSold = REMHelper.convertStringToDate(mBinding.fragReAddEditEtSoldDate.getText().toString());
        }
        if (mDateSold != null && !lIsSold) {
            lIsSold = true;
            mBinding.fragReAddEditCbSold.setChecked(true);
        }

        mIsPhotoEmpty = mInitialPhotoList.size() == 0;
        mIsPoiEmpty = REMHelperAddEdit.isPoiEmpty(mBinding);

        if (!lIsValidAgentFirstName || !lIsValidAgentLastName) {
            Toast.makeText(mContext, getString(R.string.add_edit_txt_err_agent_not_valid) + " "
                    + getString(R.string.default_txt_data_not_saved), Toast.LENGTH_SHORT).show();
        } else if (lIsSold && mDateSold == null) {
            Toast.makeText(mContext, getString(R.string.add_edit_txt_err_date_sold_empty) + " "
                    + getString(R.string.default_txt_data_not_saved), Toast.LENGTH_SHORT).show();
        } else if (mDateMarket != null || mDateSold != null || lIsSold || !lAgentFirstName.equals("") || !lAgentLastName.equals("")
                || !lDescription.equals("") || !lType.equals("") || !mIsPhotoEmpty || !mIsPoiEmpty) {
            mRealEstate = new RealEstate(lType, lPrice, lArea, lNbRooms, lNbBedRooms, lNbBathRooms, lDescription, lIsSold,
                    lAgentFirstName, lAgentLastName, mDateSold, mDateMarket, mIsMandatoryDataComplete, mInitialPhotoList.size());
            mIsReEmpty = false;

            if (manageLocation()) {
                saveRealEstate();
            }
        } else {
            Toast.makeText(mContext, getString(R.string.add_edit_txt_err_no_data), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Manage the location data
     *
     * @return : boolean : return true if the information can be saved
     */
    private boolean manageLocation() {
        String lErrorData = "";
        boolean lIsValidStreet;
        boolean lIsValidCity;
        boolean lIsValidZipCode;

        String lDistrict = mBinding.fragReAddEditEtDistrict.getText().toString();
        String lCounty = mBinding.fragReAddEditEtCounty.getText().toString();
        String lCountry = mBinding.fragReAddEditSpinCountry.getSelectedItem().toString().equals(getString(R.string.spinners_select))
                ? "" : mBinding.fragReAddEditSpinCountry.getSelectedItem().toString();

        String lStreet = mBinding.fragReAddEditEtStreet.getText().toString();
        lIsValidStreet = REMHelperAddEdit.controlValidityWithRegex(lStreet, getString(R.string.regex_street));

        String lCity = mBinding.fragReAddEditEtCity.getText().toString();
        lIsValidCity = REMHelperAddEdit.controlValidityWithRegex(lCity, getString(R.string.regex_city));

        String lZipCode = mBinding.fragReAddEditEtZipCode.getText().toString();
        lIsValidZipCode = REMHelperAddEdit.controlValidityWithRegex(lZipCode, getString(R.string.regex_zip_code));

        lErrorData = !lIsValidStreet ? REMHelper.addValueAndSeparatorToString(lErrorData, ", ", getString(R.string.txt_street)) : lErrorData;
        lErrorData = !lIsValidZipCode ? REMHelper.addValueAndSeparatorToString(lErrorData, ", ", getString(R.string.txt_zip_code)) : lErrorData;
        lErrorData = !lIsValidCity ? REMHelper.addValueAndSeparatorToString(lErrorData, ", ", getString(R.string.txt_city)) : lErrorData;

        if (lErrorData.length() > 0) {
            Toast.makeText(mContext, "Error on data : " + lErrorData + " "
                    + getString(R.string.default_txt_data_not_saved), Toast.LENGTH_SHORT).show();
            return false;
        } else {
            if (!lStreet.equals("") || !lZipCode.equals("") || !lCity.equals("") || !lDistrict.equals("")
                    || lCounty.equals("") || !lCountry.equals("")) {
                mReLocation = new ReLocation(lStreet, lDistrict, lCity, lCounty, lZipCode, lCountry,0,0);
                manageGeolocalisation();
            }
            return true;
        }
    }

    private void manageGeolocalisation() {
        if (!mReLocation.getLocStreet().isEmpty() && !mReLocation.getLocZipCode().isEmpty() && !mReLocation.getLocCity().isEmpty()) {
            String lAddress = mReLocation.getLocStreet() + " " + mReLocation.getLocZipCode() + " " + mReLocation.getLocCity();
            LatLng lLatLng = getLocationFromAddress(lAddress);
            if (lLatLng != null) {
                mReLocation.setLocLatitude(lLatLng.latitude);
                mReLocation.setLocLongitude(lLatLng.longitude);
                mIsLocationEmpty = false;
            } else {
                mIsLocationEmpty = true;
            }
        }
    }
    /**
     * Save the real estate information
     */
    private void saveRealEstate() {

        if (mIsReEmpty && mIsLocationEmpty && mIsPhotoEmpty && mIsPoiEmpty) {
            Toast.makeText(mContext, getString(R.string.add_edit_txt_err_no_data), Toast.LENGTH_SHORT).show();
        } else {
            mRealEstate.setReIsMandatoryDataComplete(REMHelperAddEdit.getIsMandatoryDataComplete(mRealEstate, mReLocation, mIsPhotoEmpty));
            mIsMandatoryDataComplete = mRealEstate.isReIsMandatoryDataComplete();
            if (!mIsEdit) {
                mViewModel.insertRealEstate(mRealEstate);
                mViewModel.selectMaxReId().observe(getViewLifecycleOwner(), pMaxReId -> {
                    mReId = pMaxReId;
                    mReLocation.setLocReId(pMaxReId);
                    mViewModel.insertReLocation(mReLocation);
                    savePoiAndPhotoInformations(pMaxReId);
                });

            } else {
                mRealEstate.setReId(mReId);
                mViewModel.updateRealEstate(mRealEstate);

                mReLocation.setLocId(mReComp.getReLocation().getLocId());
                mReLocation.setLocReId(mReId);
                mViewModel.updateReLocation(mReLocation);
                savePoiAndPhotoInformations(mReId);
            }
        }
    }
    /**
     * Call the management of the poi and photo information
     *
     * @param pReId : long : id of the real estate
     */
    private void savePoiAndPhotoInformations(long pReId) {
        managePoi(pReId);
        manageAddEditPhoto(pReId);
    }

    private void sendNotification() {
        NotificationManagerCompat lNotificationManager = NotificationManagerCompat.from(mContext);

        if (lNotificationManager.areNotificationsEnabled()) {
            NotifyWorker.createNotification(mContext, mIsMandatoryDataComplete);
            Log.d(TAG, "sendNotification: create notification");
        } else {
            Log.d(TAG, "sendNotification: dont create notification");
        }
    }

    /**
     * Manage the information of the poi
     * @param pReId : long : id of the real estate
     */
    private void managePoi(long pReId) {

        mPoiList = REMHelperAddEdit.setPoiList(mReComp, mPoiList, pReId, mIsEdit, getString(R.string.poi_restaurant),
                mBinding.fragReAddEditPoiRestaurant.isChecked(), mViewModel);
        mPoiList = REMHelperAddEdit.setPoiList(mReComp, mPoiList, pReId, mIsEdit, getString(R.string.poi_subway),
                mBinding.fragReAddEditPoiSubway.isChecked(), mViewModel);
        mPoiList = REMHelperAddEdit.setPoiList(mReComp, mPoiList, pReId, mIsEdit, getString(R.string.poi_school),
                mBinding.fragReAddEditPoiSchool.isChecked(), mViewModel);
        mPoiList = REMHelperAddEdit.setPoiList(mReComp, mPoiList, pReId, mIsEdit, getString(R.string.poi_park),
                mBinding.fragReAddEditPoiPark.isChecked(), mViewModel);
        mPoiList = REMHelperAddEdit.setPoiList(mReComp, mPoiList, pReId, mIsEdit, getString(R.string.poi_store),
                mBinding.fragReAddEditPoiStore.isChecked(), mViewModel);
        mPoiList = REMHelperAddEdit.setPoiList(mReComp, mPoiList, pReId, mIsEdit, getString(R.string.poi_bank),
                mBinding.fragReAddEditPoiBank.isChecked(), mViewModel);
        mPoiList = REMHelperAddEdit.setPoiList(mReComp, mPoiList, pReId, mIsEdit, getString(R.string.poi_food),
                mBinding.fragReAddEditPoiFood.isChecked(), mViewModel);
        mPoiList = REMHelperAddEdit.setPoiList(mReComp, mPoiList, pReId, mIsEdit, getString(R.string.poi_health),
                mBinding.fragReAddEditPoiHealth.isChecked(), mViewModel);

        if (mPoiList.size() > 0) {
            for (RePoi lPoi : mPoiList) {
                mViewModel.insertRePoi(lPoi);
            }
        }
    }

    /**
     * Manage the add / edit information of the photos
     * @param pReId : long : id of hte real estate
     */
    private void manageAddEditPhoto(long pReId) {
        if (mIsEdit) {
            mViewModel.selectRePhoto(pReId).observe(getViewLifecycleOwner(), pPhotoList -> {
                mInitialPhotoList = pPhotoList;
                Log.d(TAG, "manageAddEditPhoto: nbphotos: " + mInitialPhotoList.size());
                managePhoto(pReId);
            });
        } else {
            managePhoto(pReId);
        }
    }

    /**
     * Add, update or delete photo. Update the number of photo in the real estate. return to the list
     * @param pReId : long : id of the real estate
     */
    private void managePhoto(long pReId) {
        int lNbPhoto = REMHelperAddEdit.setPhotoList(mInitialPhotoList, mPhotoList, pReId, mIsEdit, mViewModel);
        if(mIsEdit) {
            mRealEstate.setReNbPhotos(lNbPhoto);
            mViewModel.updateRealEstate(mRealEstate);
        }
        sendNotification();
        navigateToList();
    }
    /**
     * Return to the fragment list when not in tablet landscape mode
     */
    private void navigateToList() {
        if (!mIsTabletLandscape) {
            mNavController.navigate(R.id.action_reAddFragment_to_reListFragment);
        }
    }

    /**
     * Configure the different spinner of the screen
     */
    private void configureSpinners() {
        mBinding.fragReAddEditSpinType.setAdapter(REMHelper.configureSpinAdapter(mContext, R.array.type_spinner));
        mBinding.fragReAddEditSpinRooms.setAdapter(REMHelper.configureSpinAdapter(mContext, R.array.rooms_spinner));
        mBinding.fragReAddEditSpinBedrooms.setAdapter(REMHelper.configureSpinAdapter(mContext, R.array.rooms_spinner));
        mBinding.fragReAddEditSpinBathrooms.setAdapter(REMHelper.configureSpinAdapter(mContext, R.array.rooms_spinner));
        mBinding.fragReAddEditSpinCountry.setAdapter(REMHelper.configureSpinAdapter(mContext, R.array.country_spinner));
    }

    /**
     * Initialize the recycler view
     */
    private void initRecyclerView() {

        mAdapter = new AddEditPhotoAdapter();
        mBinding.fragReAddEditRvPhoto.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mBinding.fragReAddEditRvPhoto.setAdapter(mAdapter);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        configureViewModel();
        if (mIsEdit) {
            mViewModel.selectReComplete(mReId).observe(getViewLifecycleOwner(), pReComp -> {
                mReComp = pReComp;
                displayReComplete(pReComp);
            });
        }
    }

    /**
     * Configure the view model
     */
    private void configureViewModel() {
        ReViewModelFactory lFactory = Injection.reViewModelFactory(mContext);
        mViewModel = new ViewModelProvider(this, lFactory).get(ReAddEditViewModel.class);
    }

    /**
     * Display the data of the real estate in the edit case
     *
     * @param pReComp : object : RealEstateComplete : all the information of the real estate
     */
    @SuppressLint("SetTextI18n")
    private void displayReComplete(RealEstateComplete pReComp) {
        int lPosInAdapter;
        String lNbRooms;

        if (pReComp != null) {

            for (RePoi lPoi : pReComp.getPoiList()) {
                if (lPoi.getPoiName().equals(getString(R.string.poi_restaurant))) {
                    mBinding.fragReAddEditPoiRestaurant.setChecked(true);
                } else if (lPoi.getPoiName().equals(getString(R.string.poi_subway))) {
                    mBinding.fragReAddEditPoiSubway.setChecked(true);
                } else if (lPoi.getPoiName().equals(getString(R.string.poi_school))) {
                    mBinding.fragReAddEditPoiSchool.setChecked(true);
                } else if (lPoi.getPoiName().equals(getString(R.string.poi_health))) {
                    mBinding.fragReAddEditPoiHealth.setChecked(true);
                } else if (lPoi.getPoiName().equals(getString(R.string.poi_food))) {
                    mBinding.fragReAddEditPoiFood.setChecked(true);
                } else if (lPoi.getPoiName().equals(getString(R.string.poi_bank))) {
                    mBinding.fragReAddEditPoiBank.setChecked(true);
                } else if (lPoi.getPoiName().equals(getString(R.string.poi_store))) {
                    mBinding.fragReAddEditPoiStore.setChecked(true);
                } else if (lPoi.getPoiName().equals(getString(R.string.poi_park))) {
                    mBinding.fragReAddEditPoiPark.setChecked(true);
                }
            }

            String lPrice = (pReComp.getRealEstate().getRePrice() == 0) ? null : Integer.toString(pReComp.getRealEstate().getRePrice());
            mBinding.fragReAddEditEtPrice.setText(lPrice);
            mBinding.fragReAddEditCbSold.setChecked(pReComp.getRealEstate().isReIsSold());

            ArrayAdapter<CharSequence> lAdapter = REMHelper.configureSpinAdapter(mContext, R.array.type_spinner);
            lPosInAdapter = lAdapter.getPosition(pReComp.getRealEstate().getReType());
            mBinding.fragReAddEditSpinType.setSelection(lPosInAdapter);

            lPosInAdapter = REMHelper.getPositionInRoomSpinner(mContext, R.array.rooms_spinner, pReComp.getRealEstate().getReNbRooms());
            mBinding.fragReAddEditSpinRooms.setSelection(lPosInAdapter);

            lNbRooms = REMHelper.convertSpinRoomToString(pReComp.getRealEstate().getReNbBedrooms());
            lPosInAdapter = REMHelper.getPositionInSpinner(mContext, R.array.rooms_spinner, lNbRooms);
            mBinding.fragReAddEditSpinBedrooms.setSelection(lPosInAdapter);
            lNbRooms = REMHelper.convertSpinRoomToString(pReComp.getRealEstate().getReNbBathrooms());
            lPosInAdapter = REMHelper.getPositionInSpinner(mContext, R.array.rooms_spinner, lNbRooms);
            mBinding.fragReAddEditSpinBathrooms.setSelection(lPosInAdapter);

            mBinding.fragReAddEditEtDescription.setText(pReComp.getRealEstate().getReDescription());

            String lArea = (pReComp.getRealEstate().getReArea() == 0) ? null : Integer.toString(pReComp.getRealEstate().getReArea());
            mBinding.fragReAddEditEtArea.setText(lArea);

            mBinding.fragReAddEditEtAgentLastName.setText(pReComp.getRealEstate().getReAgentLastName());
            mBinding.fragReAddEditEtAgentFirstName.setText(pReComp.getRealEstate().getReAgentFirstName());

            mBinding.fragReAddEditEtMarketDate.setText(REMHelper.convertDateToString(pReComp.getRealEstate().getReOnMarketDate()));
            mBinding.fragReAddEditEtSoldDate.setText(REMHelper.convertDateToString(pReComp.getRealEstate().getReSaleDate()));

            mPhotoList = pReComp.getRePhotoList();
            mAdapter.setPhotoList(mPhotoList);
            mAdapter.notifyDataSetChanged();

            mBinding.fragReAddEditEtStreet.setText(pReComp.getReLocation().getLocStreet());
            mBinding.fragReAddEditEtDistrict.setText(pReComp.getReLocation().getLocDistrict());
            mBinding.fragReAddEditEtCity.setText(pReComp.getReLocation().getLocCity());
            mBinding.fragReAddEditEtCounty.setText(pReComp.getReLocation().getLocCounty());
            mBinding.fragReAddEditEtZipCode.setText(pReComp.getReLocation().getLocZipCode());
            mBinding.fragReAddEditSpinCountry.setSelection(REMHelper.getPositionInSpinner(mContext, R.array.country_spinner, pReComp.getReLocation().getLocCountry()));

        }
    }

    /**
     * Display a calendar to implement the on market date or the sold date
     *
     * @param pId : int : id of the field which is concerned by the implementation
     */
    private void displayCalendarDialog(int pId) {
        Calendar lCalendar = Calendar.getInstance();

        DatePickerDialog lDatePickerDialog = new DatePickerDialog(
                mContext,
                (view, year, month, dayOfMonth) -> {
                    @SuppressLint("SimpleDateFormat") DateFormat lDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar lDateCal;
                    lDateCal = Calendar.getInstance();

                    lDateCal.set(year, month, dayOfMonth);
                    String lDate = lDateFormat.format(lDateCal.getTime());

                    if (pId == R.id.frag_re_add_edit_et_market_date) {
                        mStringDateMarket = lDate;
                        mBinding.fragReAddEditEtMarketDate.setText(lDate);
                    } else if (pId == R.id.frag_re_add_edit_et_sold_date) {
                        mStringDateSold = lDate;
                        mBinding.fragReAddEditEtSoldDate.setText(lDate);
                    }
                },
                lCalendar.get(Calendar.YEAR),
                lCalendar.get(Calendar.MONTH),
                lCalendar.get(Calendar.DAY_OF_MONTH)
        );
        lDatePickerDialog.show();
    }

    public LatLng getLocationFromAddress(String pAddress) {
        Geocoder lCoder = new Geocoder(mContext);
        List<Address> lAddressList;
        LatLng lLatLng = null;

        try {
            lAddressList = lCoder.getFromLocationName(pAddress, 5);
            if (lAddressList == null) {
                return null;
            }
            if (lAddressList.size() > 0) {
                Address lLocation = lAddressList.get(0);
                lLatLng = new LatLng(lLocation.getLatitude(), lLocation.getLongitude());
            } else {
                return null;
            }
        } catch (IOException pE) {
            pE.printStackTrace();
        }
        return lLatLng;
    }
}