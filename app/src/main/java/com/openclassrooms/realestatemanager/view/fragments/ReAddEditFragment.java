package com.openclassrooms.realestatemanager.view.fragments;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentReAddEditBinding;
import com.openclassrooms.realestatemanager.di.Injection;
import com.openclassrooms.realestatemanager.di.ReViewModelFactory;
import com.openclassrooms.realestatemanager.model.ReLocation;
import com.openclassrooms.realestatemanager.model.RePhoto;
import com.openclassrooms.realestatemanager.model.RePoi;
import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.model.RealEstateComplete;
import com.openclassrooms.realestatemanager.utils.REMHelper;
import com.openclassrooms.realestatemanager.utils.REMHelperAddEdit;
import com.openclassrooms.realestatemanager.view.adapters.AddEditPhotoAdapter;
import com.openclassrooms.realestatemanager.viewmodel.ReAddEditViewModel;

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

    private View mFragView;

    private FragmentReAddEditBinding mBinding;

    private AddEditPhotoAdapter mAdapter;
    private ReAddEditViewModel mViewModel;
    private Context mContext;
    private NavController mNavController;
    private String mStringDateMarket;
    private String mStringDateSold;
    private Calendar mDateCal;
    private boolean mIsTabletLandscape;
    private boolean mIsEdit;
    private boolean mIsLocationEmpty = true;
    private boolean mIsReEmpty =true;
    private boolean mIsPhotoEmpty = true;
    private boolean mIsPoiEmpty =  true;

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
        mFragView = mBinding.getRoot();
        mContext = getContext();
        mNavController = pNavController;
        mIsTabletLandscape = pIsTabletLandscape;
        configureSpinners();
        initRecyclerView();
        mBinding.fragReAddEditEtMarketDate.setOnClickListener(v -> displayCalendarDialogMarket());
        mBinding.fragReAddEditEtSoldDate.setOnClickListener(v -> displayCalendarDialogSold());
//        mBinding.fragReAddEditEtMarketDate.setOnClickListener(v -> displayCalendarDialog(mFragView));
//        mBinding.fragReAddEditEtSoldDate.setOnClickListener(v -> displayCalendarDialog(mFragView));
        mBinding.fragReAddEditImgSelectPhoto.setOnClickListener(v -> addPhoto());

    }

    private void addPhoto() {
        ImagePicker.create(this)
                .start();
    }

    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            List<Image> images = ImagePicker.getImages(data);
            for (Image lImg : images) {
                mPhotoList.add(new RePhoto(lImg.getName(), lImg.getPath(), lImg.getId()));
            }
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
            prepareRealEstate();
            return true;
        }
        return super.onOptionsItemSelected(pItem);
    }

    private void prepareRealEstate() {
        Date lDateMarket = null;
        Date lDateSold = null;

        boolean lIsSold = mBinding.fragReAddEditCbSold.isChecked();
        String lAgentFirstName = mBinding.fragReAddEditEtAgentFirstName.getText().toString();
        String lAgentLastName = mBinding.fragReAddEditEtAgentLastName.getText().toString();

        String lDescription = mBinding.fragReAddEditEtDescription.getText().toString();

        String lType = mBinding.fragReAddEditSpinType.getSelectedItem().toString().equals(getString(R.string.spinners_select))
                ? "" : mBinding.fragReAddEditSpinType.getSelectedItem().toString();

        int lArea = (!mBinding.fragReAddEditEtArea.getText().toString().equals("")) ? Integer.parseInt(mBinding.fragReAddEditEtArea.getText().toString()) : 0;
        int lPrice = (!mBinding.fragReAddEditEtPrice.getText().toString().equals("")) ? Integer.parseInt(mBinding.fragReAddEditEtPrice.getText().toString()) : 0;

        int lNbRooms = REMHelper.convertSpinnerValueToInt(mBinding.fragReAddEditSpinRooms.getSelectedItem().toString());
        int lNbBedRooms = REMHelper.convertSpinnerValueToInt(mBinding.fragReAddEditSpinBedrooms.getSelectedItem().toString());
        int lNbBathRooms = REMHelper.convertSpinnerValueToInt(mBinding.fragReAddEditSpinBathrooms.getSelectedItem().toString());

        if (mStringDateMarket != null) {
            lDateMarket = REMHelper.convertStringToDate(mStringDateMarket);
        } else if (mBinding.fragReAddEditEtMarketDate.getText() != null) {
            lDateMarket = REMHelper.convertStringToDate(mBinding.fragReAddEditEtMarketDate.getText().toString());
        }

        if (mStringDateSold != null) {
            lDateSold = REMHelper.convertStringToDate(mStringDateSold);
        } else if (mBinding.fragReAddEditEtSoldDate.getText() != null) {
            lDateSold = REMHelper.convertStringToDate(mBinding.fragReAddEditEtSoldDate.getText().toString());
        }

        if(lDateMarket!=null || lDateSold!=null || lIsSold || !lAgentFirstName.equals("") || !lAgentLastName.equals("")
            || !lDescription.equals("") || !lType.equals("")) {
            mRealEstate = new RealEstate(lType, lPrice, lArea, lNbRooms, lNbBedRooms, lNbBathRooms, lDescription, lIsSold,
                    lAgentFirstName, lAgentLastName, lDateSold, lDateMarket);

            mIsReEmpty=false;

            if (manageLocation()) {
                mIsPhotoEmpty = mInitialPhotoList.size() != 0;
                mIsPoiEmpty = REMHelperAddEdit.isPoiEmpty(mBinding);
                saveRealEstate();
            }
        }
    }

    private void saveRealEstate() {

        if (mIsReEmpty && mIsLocationEmpty && mIsPhotoEmpty && mIsPoiEmpty) {
            Toast.makeText(mContext, getString(R.string.default_txt_err_no_data), Toast.LENGTH_SHORT).show();
        } else {
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

    private void savePoiAndPhotoInformations(long pReId) {
        managePoi(pReId);
        managePhoto(pReId);
    }

    private void managePhoto(long pReId) {
        if (mIsEdit) {
            mViewModel.selectRePhoto(pReId).observe(getViewLifecycleOwner(), pPhotoList -> {
                mInitialPhotoList = pPhotoList;
                REMHelperAddEdit.setPhotoList(mInitialPhotoList, mPhotoList, pReId, mIsEdit, mViewModel);
                navigateToList();
            });
        } else {
            REMHelperAddEdit.setPhotoList(mInitialPhotoList, mPhotoList, pReId, mIsEdit, mViewModel);
            navigateToList();
        }
    }

    private void navigateToList() {
        if (!mIsTabletLandscape) {
            mNavController.navigate(R.id.action_reAddFragment_to_reListFragment);
        }
    }

    private boolean manageLocation() {
        String lErrorData = "";
        boolean lIsValidStreet ;
        boolean lIsValidCity;
        boolean lIsValidZipCode ;

        String lDistrict = mBinding.fragReAddEditEtDistrict.getText().toString();
        String lCounty = mBinding.fragReAddEditEtCounty.getText().toString();
        String lCountry = mBinding.fragReAddEditSpinCountry.getSelectedItem().toString().equals(getString(R.string.spinners_select))
                ? "" : mBinding.fragReAddEditSpinCountry.getSelectedItem().toString();

        String lStreet = mBinding.fragReAddEditEtStreet.getText().toString();
        lIsValidStreet = REMHelperAddEdit.controlValidityWithRegex(lStreet,getString(R.string.regex_street));

        String lCity = mBinding.fragReAddEditEtCity.getText().toString();
        lIsValidCity = REMHelperAddEdit.controlValidityWithRegex(lCity,getString(R.string.regex_city));

        String lZipCode = mBinding.fragReAddEditEtZipCode.getText().toString();
        lIsValidZipCode = REMHelperAddEdit.controlValidityWithRegex(lZipCode,getString(R.string.regex_zip_code));

        lErrorData = !lIsValidStreet ? REMHelper.addValueAndSeparatorToString(lErrorData, ", " , getString(R.string.txt_street)) : lErrorData;
        lErrorData = !lIsValidZipCode ? REMHelper.addValueAndSeparatorToString(lErrorData, ", " , getString(R.string.txt_zip_code)) : lErrorData;
        lErrorData = !lIsValidCity ? REMHelper.addValueAndSeparatorToString(lErrorData, ", " , getString(R.string.txt_city)) : lErrorData;

        if (lErrorData.length()>0) {
            Toast.makeText(mContext, "Error on data : " + lErrorData, Toast.LENGTH_SHORT).show();
            return false;
        } else  {
            if (!lStreet.equals("") || !lZipCode.equals("") || !lCity.equals("") || !lDistrict.equals("")
                    || lCounty.equals("") || !lCountry.equals("")) {
                mReLocation = new ReLocation(lStreet, lDistrict, lCity, lCounty, lZipCode, lCountry);
                mIsLocationEmpty=false;
            }
            return true;
        }
    }

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
                    Log.d(TAG, "displayCalendarDialogMarket: dayOfMonth" + dayOfMonth);
                    String lDate = lDateFormat.format(mDateCal.getTime());
                    Log.d(TAG, "displayCalendarDialogMarket: lDate " + lDate);
                    mStringDateMarket = lDate;
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
                    mStringDateSold = lDate;
                    mBinding.fragReAddEditEtSoldDate.setText(lDate);
                },
                lCalendar.get(Calendar.YEAR),
                lCalendar.get(Calendar.MONTH),
                lCalendar.get(Calendar.DAY_OF_MONTH)
        );
        lDatePickerDialog.show();
    }

    @SuppressLint("SetTextI18n")
    private void displayReComplete(RealEstateComplete pReComp) {
        int lPosInAdapter = 0;
        String lNbRooms = "0";

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

            mBinding.fragReAddEditEtPrice.setText(Integer.toString(pReComp.getRealEstate().getRePrice()));
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
            mBinding.fragReAddEditEtArea.setText(Integer.toString(pReComp.getRealEstate().getReArea()));

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

    private void displayCalendarDialog(View pView) {
        final int lId = pView.getId();
        Log.d(TAG, "displayCalendarDialog: " + pView.getId());
        Calendar lCalendar = Calendar.getInstance();

        DatePickerDialog lDatePickerDialog = new DatePickerDialog(
                mContext,
                (view, year, month, dayOfMonth) -> {
                    @SuppressLint("SimpleDateFormat") DateFormat lDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar lDateCal;
                    lDateCal = Calendar.getInstance();

                    lDateCal.set(year, month, dayOfMonth);
                    String lDate = lDateFormat.format(lDateCal.getTime());

                    if (lId == R.id.frag_re_add_edit_et_market_date) {
                        mStringDateMarket = lDate;
                        mBinding.fragReAddEditEtMarketDate.setText(lDate);
                    } else if (lId == R.id.frag_re_add_edit_et_sold_date) {
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
}