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

public class ReAddEditFragment extends BaseFragment<FragmentReAddEditBinding>  implements AddEditPhotoAdapter.OnRecyclerViewListener{

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

    private long mReId;
    private RealEstate mRealEstate = new RealEstate();
    private ReLocation mReLocation = new ReLocation();
    private List<RePoi> mPoiList = new ArrayList<>();
    private List<RePhoto> mPhotoList = new ArrayList<>();

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
        mBinding.fragReAddEditImgSelectPhoto.setOnClickListener(v-> addPhoto());

    }

    private void addPhoto() {
        ImagePicker.create(this)
                .start();
    }

    @Override
    public void onActivityResult(int requestCode, final int resultCode, Intent data) {
        if (ImagePicker.shouldHandle(requestCode, resultCode, data)) {
            // Get a list of picked images
            List<Image> images = ImagePicker.getImages(data);
            for(Image lImg : images) {
                Log.d(TAG, "onActivityResult: " + lImg.getName() + " " + lImg.getPath() + " " + lImg.getId() + " " + lImg.describeContents());
                mPhotoList.add(new RePhoto( lImg.getName(), lImg.getPath(),lImg.getId()));
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

        String lType = mBinding.fragReAddEditSpinType.getSelectedItem().toString();
        if (lType == getString(R.string.spinners_select)) {
            lType ="";
        }
        int lArea = Integer.parseInt(mBinding.fragReAddEditEtArea.getText().toString());
        int lPrice = Integer.parseInt(mBinding.fragReAddEditEtPrice.getText().toString());
        int lNbRooms = REMHelper.convertSpinnerValueToInt(mBinding.fragReAddEditSpinRooms.getSelectedItem().toString());
        int lNbBedRooms = REMHelper.convertSpinnerValueToInt(mBinding.fragReAddEditSpinBedrooms.getSelectedItem().toString());
        int lNbBathRooms = REMHelper.convertSpinnerValueToInt(mBinding.fragReAddEditSpinBathrooms.getSelectedItem().toString());

        if (mStringDateMarket != null) {
            lDateMarket = REMHelper.formatStringToDate(mStringDateMarket);
        }
        if (mStringDateSold != null) {
            lDateSold = REMHelper.formatStringToDate(mStringDateSold);
        }

        mRealEstate = new RealEstate(lType, lPrice, lArea, lNbRooms, lNbBedRooms, lNbBathRooms, lDescription, lIsSold,
                lAgentFirstName, lAgentLastName, lDateSold, lDateMarket);

        saveRealEstate();
    }

    private void saveRealEstate() {
        if (!mIsEdit) {
            mViewModel.insertRealEstate(mRealEstate);
            mViewModel.selectMaxReId().observe(getViewLifecycleOwner(), pMaxReId -> {
                manageLocation(pMaxReId);
                managePoi(pMaxReId);
                managePhoto(pMaxReId);
                if (!mIsTabletLandscape) {
                    mNavController.navigate(R.id.action_reAddFragment_to_reListFragment);
                }
            });
        } else {
            mRealEstate.setReId(mReId);
            mViewModel.updateRealEstate(mRealEstate);
        }
    }

    private void managePhoto(long pMaxReId) {
        for (RePhoto lPhoto : mPhotoList) {
            lPhoto.setPhReId(pMaxReId);
            mViewModel.insertRePhoto(lPhoto);
        }
    }
    private void manageLocation(long pMaxReId) {
        boolean lIsValid = true;
        String lStreet = mBinding.fragReAddEditEtStreet.getText().toString();
        String lDistrict = mBinding.fragReAddEditEtDistrict.getText().toString();
        String lCity = mBinding.fragReAddEditEtCity.getText().toString();
        String lCounty = mBinding.fragReAddEditEtCounty.getText().toString();
/*        Pattern lPattern = Pattern.compile("#[0-9]#");
        Matcher lMatcher = lPattern.matcher(lStringZipCode);
        lIsValid = lMatcher.matches();*/

        String lZipCode = mBinding.fragReAddEditEtZipCode.getText().toString();

        String lCountry = mBinding.fragReAddEditSpinCountry.getSelectedItem().toString();
        if (lCountry == getString(R.string.spinners_select)) {
            lCountry = "";
        }

        if (lIsValid) {
            mReLocation = new ReLocation(pMaxReId, lStreet, lDistrict, lCity, lCounty, lZipCode, lCountry);
            if (!mIsEdit) {
                mViewModel.insertReLocation(mReLocation);
            } else {
                //TODO
                //mReLocation.setLocId();
            }
        }
    }

    private void managePoi(long pMaxReId) {

        if (mBinding.fragReAddEditPoiRestaurant.isChecked()) {
            mPoiList.add(new RePoi(pMaxReId, getString(R.string.poi_restaurant)));
        }
        if (mBinding.fragReAddEditPoiSubway.isChecked()) {
            mPoiList.add(new RePoi(pMaxReId, getString(R.string.poi_subway)));
        }
        if (mBinding.fragReAddEditPoiSchool.isChecked()) {
            mPoiList.add(new RePoi(pMaxReId, getString(R.string.poi_school)));
        }
        if (mBinding.fragReAddEditPoiPark.isChecked()) {
            mPoiList.add(new RePoi(pMaxReId, getString(R.string.poi_park)));
        }
        if (mBinding.fragReAddEditPoiStore.isChecked()) {
            mPoiList.add(new RePoi(pMaxReId, getString(R.string.poi_store)));
        }
        if (mBinding.fragReAddEditPoiBank.isChecked()) {
            mPoiList.add(new RePoi(pMaxReId, getString(R.string.poi_bank)));
        }
        if (mBinding.fragReAddEditPoiFood.isChecked()) {
            mPoiList.add(new RePoi(pMaxReId, getString(R.string.poi_food)));
        }
        if (mBinding.fragReAddEditPoiHealth.isChecked()) {
            mPoiList.add(new RePoi(pMaxReId, getString(R.string.poi_health)));
        }
        Log.d(TAG, "managePoi: size " + mPoiList.size());
        if ((!mIsEdit) && (mPoiList.size() > 0)) {
            for (RePoi lPoi : mPoiList) {
                Log.d(TAG, "managePoi: " + lPoi.getPoiName());
                mViewModel.insertRePoi(lPoi);
            }
//        } else if (mPoiList.size() > 0) {
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
    private void displayReComplete(RealEstateComplete pRe) {
        int lPosInAdapter = 0;
        String lNbRooms = "0";

        if (pRe != null) {

            for (RePoi lPoi : pRe.getPoiList()) {
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
            mBinding.fragReAddEditEtPrice.setText(Integer.toString(pRe.getRealEstate().getRePrice()));
            mBinding.fragReAddEditCbSold.setChecked(pRe.getRealEstate().isReIsSold());

            ArrayAdapter<CharSequence> lAdapter = REMHelper.configureSpinAdapter(mContext, R.array.type_spinner);
            lPosInAdapter = lAdapter.getPosition(pRe.getRealEstate().getReType());
            mBinding.fragReAddEditSpinType.setSelection(lPosInAdapter);

            lPosInAdapter = REMHelper.getPositionInRoomSpinner(mContext, R.array.rooms_spinner, pRe.getRealEstate().getReNbRooms());
            mBinding.fragReAddEditSpinRooms.setSelection(lPosInAdapter);

            lNbRooms = REMHelper.convertSpinRoomToString(pRe.getRealEstate().getReNbBedrooms());
            lPosInAdapter = REMHelper.getPositionInSpinner(mContext, R.array.rooms_spinner, lNbRooms);
            mBinding.fragReAddEditSpinBedrooms.setSelection(lPosInAdapter);
            lNbRooms = REMHelper.convertSpinRoomToString(pRe.getRealEstate().getReNbBathrooms());
            lPosInAdapter = REMHelper.getPositionInSpinner(mContext, R.array.rooms_spinner, lNbRooms);
            mBinding.fragReAddEditSpinBathrooms.setSelection(lPosInAdapter);

            mBinding.fragReAddEditEtDescription.setText(pRe.getRealEstate().getReDescription());
            mBinding.fragReAddEditEtArea.setText(Integer.toString(pRe.getRealEstate().getReArea()));

            mBinding.fragReAddEditEtAgentLastName.setText(pRe.getRealEstate().getReAgentLastName());
            mBinding.fragReAddEditEtAgentFirstName.setText(pRe.getRealEstate().getReAgentFirstName());

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

    @Override
    public void listToSave(List<RePhoto> pPhotoList) {
        mPhotoList = pPhotoList;
    }
}