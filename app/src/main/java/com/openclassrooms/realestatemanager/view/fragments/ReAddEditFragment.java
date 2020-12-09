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
import com.openclassrooms.realestatemanager.model.RePoi;
import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.model.RealEstateComplete;
import com.openclassrooms.realestatemanager.utils.REMHelper;
import com.openclassrooms.realestatemanager.view.adapters.AddEditPhotoAdapter;
import com.openclassrooms.realestatemanager.viewmodel.ReAddEditViewModel;

import java.sql.Date;
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

    private long mReId;
    private RealEstate mRealEstate = new RealEstate();
    private ReLocation mReLocation = new ReLocation();
    private RePoi mRePoi = new RePoi();
    private List<RePoi> mPoiList = new ArrayList<>();

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
        //mBinding.fragReAddEditEtMarketDate.setOnClickListener(v -> displayCalendarDialogMarket());
        mBinding.fragReAddEditEtSoldDate.setOnClickListener(v -> displayCalendarDialogSold());
        mBinding.fragReAddEditEtMarketDate.setOnClickListener(v -> displayCalendarDialogMarket());

/*        CompoundButton.OnCheckedChangeListener checkedListener = new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                switch(buttonView.getId()){
                    case R.id.frag_re_add_edit_poi_restaurant:
                        if (isChecked) {
                            mPoiList.add(new RePoi(pMaxReId, getString(R.string.poi_restaurant)););
                        }
                        break;
                }
            }
        };*/

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            mReId = getArguments().getLong(RE_ID_KEY);
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
            return true;
        }
        return super.onOptionsItemSelected(pItem);
    }

    private void prepareRealEstate() {

        boolean lIsSold = mBinding.fragReAddEditCbSold.isChecked();
        String lAgentFirstName = mBinding.fragReAddEditEtAgentFirstName.getText().toString();
        String lAgentLastName = mBinding.fragReAddEditEtAgentLastName.getText().toString();

        String lDescription = mBinding.fragReAddEditEtDescription.getText().toString();

        String lType = mBinding.fragReAddEditSpinType.getSelectedItem().toString();
        int lArea = Integer.parseInt(mBinding.fragReAddEditEtArea.getText().toString());
        int lPrice = Integer.parseInt(mBinding.fragReAddEditEtPrice.getText().toString());
        int lNbRooms = REMHelper.convertSpinnerValueToInt(mBinding.fragReAddEditSpinRooms.getSelectedItem().toString());
        int lNbBedRooms = REMHelper.convertSpinnerValueToInt(mBinding.fragReAddEditSpinBedrooms.getSelectedItem().toString());
        int lNbBathRooms = REMHelper.convertSpinnerValueToInt(mBinding.fragReAddEditSpinBathrooms.getSelectedItem().toString());

        DateFormat lDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar mDateCal = Calendar.getInstance();
        mDateCal.set(2020,12,9);
        String lStringDate = lDateFormat.format(mDateCal.getTime());

        Date date = Date.valueOf(lStringDate);

        mRealEstate = new RealEstate(lType, lPrice, lArea, lNbRooms, lNbBedRooms, lNbBathRooms, lDescription, lIsSold,
                lAgentFirstName, lAgentLastName, date, date);

        if (!mIsEdit) {
            mViewModel.insertRealEstate(mRealEstate);
            mViewModel.selectMaxReId().observe(getViewLifecycleOwner(), pMaxReId -> {
                Log.d(TAG, "prepareRealEstate: " + pMaxReId);
                manageLocation(pMaxReId);
                managePoi(pMaxReId);
                if (!mIsTabletLandscape) {
                    mNavController.navigate(R.id.action_reAddFragment_to_reListFragment);
                }
            });
        } else {
            mRealEstate.setReId(mReId);
            mViewModel.updateRealEstate(mRealEstate);
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
            mPoiList.add( new RePoi(pMaxReId, getString(R.string.poi_park)));
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
        if (mIsEdit) {
/*
        mViewModel.getRealEstate(mReId).observe(getViewLifecycleOwner(), pRealEstate -> {
            displayRealEstate(pRealEstate);
        });
*/
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
}