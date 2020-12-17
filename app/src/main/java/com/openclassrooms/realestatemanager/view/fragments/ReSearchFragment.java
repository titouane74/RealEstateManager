package com.openclassrooms.realestatemanager.view.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.sqlite.db.SimpleSQLiteQuery;

import com.google.gson.Gson;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentReSearchBinding;
import com.openclassrooms.realestatemanager.di.Injection;
import com.openclassrooms.realestatemanager.di.ReViewModelFactory;
import com.openclassrooms.realestatemanager.model.DataSearch;
import com.openclassrooms.realestatemanager.model.RePoi;
import com.openclassrooms.realestatemanager.model.RealEstateComplete;
import com.openclassrooms.realestatemanager.utils.DateConverter;
import com.openclassrooms.realestatemanager.utils.REMHelper;
import com.openclassrooms.realestatemanager.viewmodel.ReSearchViewModel;

import org.json.JSONArray;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.openclassrooms.realestatemanager.view.activities.MainActivity.sApi;
import static com.openclassrooms.realestatemanager.view.adapters.ReListAdapter.IS_EDIT_KEY;
import static com.openclassrooms.realestatemanager.view.adapters.ReListAdapter.RE_ID_KEY;

public class ReSearchFragment extends BaseFragment<FragmentReSearchBinding> {

    private OnSearchResult mCallback;

    public interface OnSearchResult {
        void onSearchResult(List<RealEstateComplete> pReCompList);
    }

    private static final String TAG = "TAG_ReSearchFragment";
    private View mFragView;
    private Context mContext;
    private NavController mNavController;
    private boolean mIsTabletLandscape;
    private String mStringDateMarketFrom;
    private String mStringDateMarketTo;
    private String mStringDateSoldFrom;
    private String mStringDateSoldTo;
    private Date mDateMarketFrom = null;
    private Date mDateSoldFrom = null;
    private Date mDateMarketTo = null;
    private Date mDateSoldTo = null;
    private DataSearch mDataSearch = new DataSearch();
    private List<DataSearch> mDsList = new ArrayList<>();

    private ReSearchViewModel mViewModel;

    @Override
    protected int getMenuAttached() {
        return R.menu.menu_confirm;
    }

    @Override
    protected void configureDesign(FragmentReSearchBinding pBinding, NavController pNavController, boolean pIsTablet, boolean pIsTabletLandscape) {
        mBinding = pBinding;
        mFragView = mBinding.getRoot();
        mContext = getContext();
        mNavController = pNavController;
        mIsTabletLandscape = pIsTabletLandscape;
//        mCallback = (OnSearchResult) mContext;
        configureSpinners();
        configureViewModel();
        mBinding.fragReSearchEtMarketDateFrom.setOnClickListener(v -> {
            displayCalendarDialog(R.id.frag_re_search_et_market_date_from);
            mDateMarketFrom = (mStringDateMarketFrom != null) ? REMHelper.convertStringToDate(mStringDateMarketFrom) : null;
        });
        mBinding.fragReSearchEtMarketDateTo.setOnClickListener(v -> {
            displayCalendarDialog(R.id.frag_re_search_et_market_date_to);
            mDateMarketTo = (mStringDateMarketTo != null) ? REMHelper.convertStringToDate(mStringDateMarketTo) : null;
        });
        mBinding.fragReSearchEtSoldDateFrom.setOnClickListener(v -> {
            displayCalendarDialog(R.id.frag_re_search_et_sold_date_from);
            mDateSoldFrom = (mStringDateSoldFrom != null) ? REMHelper.convertStringToDate(mStringDateSoldFrom) : null;
            if ((mDateMarketFrom != null) && (mDateSoldFrom != null) && (mDateSoldFrom.before(mDateMarketFrom))) {
                Toast.makeText(mContext, getString(R.string.add_edit_txt_err_date_sold_before_date_market), Toast.LENGTH_SHORT).show();
            }
        });
        mBinding.fragReSearchEtSoldDateTo.setOnClickListener(v -> {
            displayCalendarDialog(R.id.frag_re_search_et_sold_date_to);
            mDateSoldTo = (mStringDateSoldTo != null) ? REMHelper.convertStringToDate(mStringDateSoldTo) : null;
            if ((mDateMarketTo != null) && (mDateSoldTo != null) && (mDateSoldTo.before(mDateMarketTo))) {
                Toast.makeText(mContext, getString(R.string.add_edit_txt_err_date_sold_before_date_market), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        Log.d(TAG, "onOptionsItemSelected: ");
        if (pItem.getItemId() == R.id.menu_action_confirm) {
            Toast.makeText(getContext(), getString(R.string.default_txt_confirm), Toast.LENGTH_SHORT).show();
            buildQuery();
            return true;
        }
        return super.onOptionsItemSelected(pItem);
    }

    private void configureSpinners() {
        mBinding.fragReSearchSpinType.setAdapter(REMHelper.configureSpinAdapter(mContext, R.array.type_spinner));
        mBinding.fragReSearchSpinRooms.setAdapter(REMHelper.configureSpinAdapter(mContext, R.array.rooms_spinner));
        mBinding.fragReSearchSpinBedrooms.setAdapter(REMHelper.configureSpinAdapter(mContext, R.array.rooms_spinner));
        mBinding.fragReSearchSpinBathrooms.setAdapter(REMHelper.configureSpinAdapter(mContext, R.array.rooms_spinner));
        mBinding.fragReSearchSpinCountry.setAdapter(REMHelper.configureSpinAdapter(mContext, R.array.country_spinner));
        mBinding.fragReSearchSpinNbPhoto.setAdapter(REMHelper.configureSpinAdapter(mContext, R.array.photo_spinner));
    }
/*    private void displayCalendarDialogMarket() {
        Calendar lCalendar = Calendar.getInstance();
        final String[] mDate = new String[1];

        DatePickerDialog lDatePickerDialog = new DatePickerDialog(
                mContext,
                (view, year, month, dayOfMonth) -> {
                    @SuppressLint("SimpleDateFormat") DateFormat lDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar lDateCal = Calendar.getInstance();

                    lDateCal.set(year,month,dayOfMonth);
                    String lDate = lDateFormat.format(lDateCal.getTime());
                    mBinding.fragReSearchEtMarketDate.setText(lDate);
                },
                lCalendar.get(Calendar.YEAR),
                lCalendar.get(Calendar.MONTH),
                lCalendar.get(Calendar.DAY_OF_MONTH)
        );
        lDatePickerDialog.show();
    }
    private void displayCalendarDialogSold() {
        Calendar lCalendar = Calendar.getInstance();
        final String[] mDate = new String[1];

        DatePickerDialog lDatePickerDialog = new DatePickerDialog(
                mContext,
                (view, year, month, dayOfMonth) -> {
                    @SuppressLint("SimpleDateFormat") DateFormat lDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Calendar lDateCal = Calendar.getInstance();

                    lDateCal.set(year,month,dayOfMonth);
                    String lDate = lDateFormat.format(lDateCal.getTime());
                    mBinding.fragReSearchEtSoldDate.setText(lDate);
                },
                lCalendar.get(Calendar.YEAR),
                lCalendar.get(Calendar.MONTH),
                lCalendar.get(Calendar.DAY_OF_MONTH)
        );
        lDatePickerDialog.show();
    }*/

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

                    if (pId == R.id.frag_re_search_et_market_date_from) {
                        mStringDateMarketFrom = lDate;
                        mBinding.fragReSearchEtMarketDateFrom.setText(lDate);
                    } else if (pId == R.id.frag_re_search_et_market_date_to) {
                        mStringDateMarketTo = lDate;
                        mBinding.fragReSearchEtMarketDateTo.setText(lDate);
                    } else if (pId == R.id.frag_re_search_et_sold_date_from) {
                        mStringDateSoldFrom = lDate;
                        mBinding.fragReSearchEtSoldDateFrom.setText(lDate);
                    } else if (pId == R.id.frag_re_search_et_sold_date_to) {
                        mStringDateSoldTo = lDate;
                        mBinding.fragReSearchEtSoldDateTo.setText(lDate);
                    }
                },
                lCalendar.get(Calendar.YEAR),
                lCalendar.get(Calendar.MONTH),
                lCalendar.get(Calendar.DAY_OF_MONTH)
        );
        lDatePickerDialog.show();
    }

    /**
     * Configure the view model
     */
    private void configureViewModel() {
        ReViewModelFactory lFactory = Injection.reViewModelFactory(mContext);
        mViewModel = new ViewModelProvider(this, lFactory).get(ReSearchViewModel.class);
    }


    private void buildQuery() {

        int lIndexArgs = prepareConditions() + 1;

        if (lIndexArgs == 0) {
            Toast.makeText(mContext, getString(R.string.search_txt_err_select_one_criterion), Toast.LENGTH_SHORT).show();
        } else {

            String lArg = "";
            boolean lIsPhoto = false;

            //Initialize query string
            String lStrQuery = "";
            String lStrFromClause = "";
            String lStrWhereClause = "";
            String lStrCondition = "";

            //Input the basic select with basic clause from
            lStrQuery += "SELECT DISTINCT(reId), reType, rePrice, locCity, reIsMandatoryDataComplete, reIsSold ";
            lStrFromClause += " FROM realestate INNER JOIN location ON realestate.reId = location.locreid ";

            if (isPoiSelected()) {
                lStrFromClause += " LEFT JOIN poi ON realestate.reId = poi.poireid ";
            }
            //If number of photo > 0 add table photo in ClauseFrom
/*
            if (!mBinding.fragReSearchSpinNbPhoto.getSelectedItem().equals("0")) {
                lStrFromClause += " LEFT JOIN photo ON realestate.reId = photo.phreid ";
                lIsPhoto = true;
            }
*/

            lStrQuery += lStrFromClause;

            //Add conditions to clause Where
            lStrWhereClause += " WHERE";

            //Initialize list of arguments
            String[] lArgsList = new String[lIndexArgs];
            boolean lIsPoiConditionAdded = false;
            //pour chaque DataSearch => ajout condition et argument
            for (DataSearch lDs : mDsList) {
//                if ((lStrCondition.length() != 0) && (!lDs.getDsWhereClause().contains("AND"))) {
                if (lStrCondition.length() != 0) {     //a condition has been added
                    if (!lDs.getDsWhereClause().contains("AND")) {   //the whereclause don't contains AND
                        if (!lDs.getDsWhereClause().contains("poi")) {    // the whereclause don't contains poi
                            lStrCondition += " AND ";
                        } else {    // contains poi
                            if (!lIsPoiConditionAdded) {   // first poi condition not added
                                lStrCondition += " AND ";  //
                            }
                        }
                    }
                }
                if (lDs.getDsWhereClause().indexOf("poi") > 0) {
                    if (!lIsPoiConditionAdded) {
                        lStrCondition += lDs.getDsWhereClause();
                        lIsPoiConditionAdded = true;
                    }
                    lArgsList[lDs.getDsArg1Index()] = lDs.getDsArg1();
                } else {
                    lStrCondition += lDs.getDsWhereClause();
                    lArgsList[lDs.getDsArg1Index()] = lDs.getDsArg1();
                    if (lDs.getDsArg2() != null) {
                        lStrCondition += lDs.getDsWhereClause();
                        lArgsList[lDs.getDsArg2Index()] = lDs.getDsArg2();
                    }
                }

            }

//            if (lIsPhoto) {
//                if (lIndexArgs != 1) {
            lStrQuery += lStrWhereClause;
//                }
//            } else {
//                lStrQuery += lStrWhereClause;
//            }

            lStrQuery += lStrCondition;
            lStrQuery += ";";

/*
            lStrQuery = "SELECT DISTINCT(reId), reType, rePrice, locCity, reIsMandatoryDataComplete, reIsSold" +
            " FROM realestate INNER JOIN location ON realestate.reId = location.locreid" +
                    " INNER JOIN photo ON realestate.reId = photo.phreid" +
                    " GROUP BY photo.phreid HAVING COUNT(phreid) >= ? ;";
            lArgsList[0] = "0";
*/
            executeQuery(lStrQuery, lArgsList);
        }

    }

    private boolean isPoiSelected() {
        //Poi
        //Bank Food Health Restaurant School Store Subway Park
        int lPoiSelected = 0;
        lPoiSelected += (mBinding.fragReSearchPoiBank.isChecked()) ? 1 : 0;
        lPoiSelected += (mBinding.fragReSearchPoiFood.isChecked()) ? 1 : 0;
        lPoiSelected += (mBinding.fragReSearchPoiHealth.isChecked()) ? 1 : 0;
        lPoiSelected += (mBinding.fragReSearchPoiRestaurant.isChecked()) ? 1 : 0;
        lPoiSelected += (mBinding.fragReSearchPoiSchool.isChecked()) ? 1 : 0;
        lPoiSelected += (mBinding.fragReSearchPoiStore.isChecked()) ? 1 : 0;
        lPoiSelected += (mBinding.fragReSearchPoiSubway.isChecked()) ? 1 : 0;
        lPoiSelected += (mBinding.fragReSearchPoiPark.isChecked()) ? 1 : 0;
        return lPoiSelected > 0;
    }

    private int prepareConditions() {
        int lIndexArgs = -1;
        String lArg = "";

        //Agent last name
        if ((mBinding.fragReSearchEtAgentLastName != null) &&
                (!mBinding.fragReSearchEtAgentLastName.getText().toString().equals(""))) {
            lIndexArgs++;
            mDsList.add(new DataSearch(" reAgentLastName LIKE ? ",
                    lIndexArgs, mBinding.fragReSearchEtAgentLastName.getText().toString()));
        }

        //Data incomplete
        if ((mBinding.fragReSearchCbIncomplete != null) &&
                (mBinding.fragReSearchCbIncomplete.isChecked())) {
            lIndexArgs++;
            mDsList.add(new DataSearch(" reIsMandatoryDataComplete = ? ", lIndexArgs, "0"));
        }

        //DATES
        lIndexArgs = manageDates(lIndexArgs);

        //Type
        if (mBinding.fragReSearchSpinType.getSelectedItem().toString() != getString(R.string.spinners_select)) {
            lIndexArgs++;
            mDsList.add(new DataSearch(" reType LIKE ? ",
                    lIndexArgs, mBinding.fragReSearchSpinType.getSelectedItem().toString()));
        }

        String lIntMin = "";
        String lIntMax = "";
        //Area
        if (!mBinding.fragReSearchEtAreaMin.getText().toString().equals("")) {
            lIntMin = mBinding.fragReSearchEtAreaMin.getText().toString();
        }
        if (!mBinding.fragReSearchEtAreaMax.getText().toString().equals("")) {
            lIntMax = mBinding.fragReSearchEtAreaMax.getText().toString();
        }
        if (!lIntMin.equals("") || !lIntMax.equals("")) {
            lIndexArgs = manageConditionWith2Arguments(lIndexArgs, lIntMin, lIntMax, "reArea");
            lIntMin = "";
            lIntMax = "";
        }

        //Price
        if (!mBinding.fragReSearchEtPriceMin.getText().toString().equals("")) {
            lIntMin = mBinding.fragReSearchEtPriceMin.getText().toString();
        }
        if (!mBinding.fragReSearchEtPriceMax.getText().toString().equals("")) {
            lIntMax = mBinding.fragReSearchEtPriceMax.getText().toString();
        }
        if (!lIntMin.equals("") || !lIntMax.equals("")) {
            lIndexArgs = manageConditionWith2Arguments(lIndexArgs, lIntMin, lIntMax, "rePrice");
        }

        //Rooms
        lIndexArgs = manageRooms(lIndexArgs);

        //Location
        lIndexArgs = manageLocation(lIndexArgs);

        //Poi
        //Bank Food Health Restaurant School Store Subway Park
        lIndexArgs = managePoi(lIndexArgs);
/*        if (mBinding.fragReSearchPoiBank.isChecked()) {
            lIndexArgs++;
            mDsList.add(addPoiArg(" poiName In ( ? ) ", lIndexArgs, getString(R.string.poi_bank)));
        }
        if (mBinding.fragReSearchPoiFood.isChecked()) {
            lIndexArgs++;
            mDsList.add(addPoiArg(" poiName In ( ? ) ", lIndexArgs, getString(R.string.poi_food)));
        }
        if (mBinding.fragReSearchPoiHealth.isChecked()) {
            lIndexArgs++;
            mDsList.add(addPoiArg(" poiName In ( ? ) ", lIndexArgs, getString(R.string.poi_health)));
        }
        if (mBinding.fragReSearchPoiRestaurant.isChecked()) {
            lIndexArgs++;
            mDsList.add(addPoiArg(" poiName In ( ? ) ", lIndexArgs, getString(R.string.poi_restaurant)));
        }
        if (mBinding.fragReSearchPoiSchool.isChecked()) {
            lIndexArgs++;
            mDsList.add(addPoiArg(" poiName In ( ? ) ", lIndexArgs, getString(R.string.poi_school)));
        }
        if (mBinding.fragReSearchPoiStore.isChecked()) {
            lIndexArgs++;
            mDsList.add(addPoiArg(" poiName In ( ? ) ", lIndexArgs, getString(R.string.poi_store)));
        }
        if (mBinding.fragReSearchPoiSubway.isChecked()) {
            lIndexArgs++;
            mDsList.add(addPoiArg(" poiName In ( ? ) ", lIndexArgs, getString(R.string.poi_subway)));
        }
        if (mBinding.fragReSearchPoiPark.isChecked()) {
            lIndexArgs++;
            mDsList.add(addPoiArg(" poiName In ( ? ) ", lIndexArgs, getString(R.string.poi_park)));
        }*/

        //Photos
        if (!mBinding.fragReSearchSpinNbPhoto.getSelectedItem().equals("0")) {
            lIndexArgs++;
            lArg = String.valueOf(REMHelper.convertSpinnerValueToInt(mBinding.fragReSearchSpinNbPhoto.getSelectedItem().toString()));
//            mDsList.add(new DataSearch(" GROUP BY photo.phreid HAVING COUNT(phreid) >= ? ", lIndexArgs, lArg));
            mDsList.add(new DataSearch(" reNbPhotos >= ? ", lIndexArgs, lArg));
        }

        return lIndexArgs;
    }

    private int manageDates(int pIndexArgs) {
        String lDateFrom = "";
        String lDateTo = "";

        //On market date
        if ((mBinding.fragReSearchEtMarketDateFrom != null) &&
                (!mBinding.fragReSearchEtMarketDateFrom.getText().toString().equals(""))) {
            lDateFrom = mBinding.fragReSearchEtMarketDateFrom.getText().toString();
            lDateFrom = String.valueOf(DateConverter.fromDate(REMHelper.convertStringToDate(lDateFrom)));
        }

        if ((mBinding.fragReSearchEtMarketDateTo != null) &&
                (!mBinding.fragReSearchEtMarketDateTo.getText().toString().equals(""))) {
            lDateTo = mBinding.fragReSearchEtMarketDateTo.getText().toString();
            lDateTo = String.valueOf(DateConverter.fromDate(REMHelper.convertStringToDate(lDateTo)));
        }
        if (!lDateFrom.equals("") || !lDateTo.equals("")) {
            pIndexArgs = manageConditionWith2Arguments(pIndexArgs, lDateFrom, lDateTo, "reOnMarketDate");
            lDateFrom = "";
            lDateTo = "";
        }

        //Sold on date
        if (mBinding.fragReSearchCbSold.isChecked()) {
            pIndexArgs++;
            mDsList.add(addBooleanArg(" reIsSold = ? ", pIndexArgs));
        }

        if ((mBinding.fragReSearchEtSoldDateFrom != null) &&
                (!mBinding.fragReSearchEtSoldDateFrom.getText().toString().equals(""))) {
            lDateFrom = mBinding.fragReSearchEtSoldDateFrom.getText().toString();
            lDateFrom = String.valueOf(DateConverter.fromDate(REMHelper.convertStringToDate(lDateFrom)));
        }

        if ((mBinding.fragReSearchEtSoldDateTo != null) &&
                (!mBinding.fragReSearchEtSoldDateTo.getText().toString().equals(""))) {
            lDateTo = mBinding.fragReSearchEtSoldDateTo.getText().toString();
            lDateTo = String.valueOf(DateConverter.fromDate(REMHelper.convertStringToDate(lDateTo)));
        }
        if (!lDateFrom.equals("") || !lDateTo.equals("")) {
            pIndexArgs = manageConditionWith2Arguments(pIndexArgs, lDateFrom, lDateTo, "reSaleDate");
        }
        return pIndexArgs;
    }

    private int manageRooms(int pIndexArgs) {
        String lArg = "";

        if (!mBinding.fragReSearchSpinRooms.getSelectedItem().equals("0")) {
            pIndexArgs++;
            lArg = String.valueOf(REMHelper.convertSpinnerValueToInt(mBinding.fragReSearchSpinRooms.getSelectedItem().toString()));
            mDsList.add(new DataSearch(" reNbRooms >= ? ", pIndexArgs, lArg));
        }
        if (!mBinding.fragReSearchSpinBedrooms.getSelectedItem().equals("0")) {
            pIndexArgs++;
            lArg = String.valueOf(REMHelper.convertSpinnerValueToInt(mBinding.fragReSearchSpinBedrooms.getSelectedItem().toString()));
            mDsList.add(new DataSearch(" reNbBedRooms >= ? ", pIndexArgs, lArg));
        }
        if (!mBinding.fragReSearchSpinBathrooms.getSelectedItem().equals("0")) {
            pIndexArgs++;
            lArg = String.valueOf(REMHelper.convertSpinnerValueToInt(mBinding.fragReSearchSpinBathrooms.getSelectedItem().toString()));
            mDsList.add(new DataSearch(" reNbBathRooms >= ? ", pIndexArgs, lArg));
        }
        return pIndexArgs;
    }

    private int manageLocation(int pIndexArgs) {
        if (!mBinding.fragReSearchEtCity.getText().toString().equals("")) {
            pIndexArgs++;
            mDsList.add(new DataSearch(" locCity LIKE ? ", pIndexArgs, mBinding.fragReSearchEtCity.getText().toString()));
        }
        if (mBinding.fragReSearchSpinCountry.getSelectedItem().toString() != getString(R.string.spinners_select)) {
            pIndexArgs++;
            mDsList.add(new DataSearch(" locCountry LIKE ? ", pIndexArgs, mBinding.fragReSearchSpinCountry.getSelectedItem().toString()));
        }
        return pIndexArgs;
    }

    private int manageConditionWith2Arguments(int pIndexArgs, String pArgMin, String pArgMax, String pField) {

        if ((!pArgMin.equals("")) && (pArgMax.equals(""))) {
            pIndexArgs++;
            mDsList.add(new DataSearch(" " + pField + " >= ? ", pIndexArgs, pArgMin));
        } else if ((pArgMin.equals("")) && (!pArgMax.equals(""))) {
            pIndexArgs++;
            mDsList.add(new DataSearch(" " + pField + " <= ? ", pIndexArgs, pArgMax));
        } else if (!(pArgMin.equals("")) && (!pArgMax.equals(""))) {
            pIndexArgs++;
            mDsList.add(new DataSearch(" " + pField + " BETWEEN ? ", pIndexArgs, pArgMin));
            pIndexArgs++;
            mDsList.add(new DataSearch(" AND ? ", pIndexArgs, pArgMax));
        }

        return pIndexArgs;
    }

    private int manageDateCondition(int pIndexArgs, String pDateFrom, String pDateTo, String pField) {

        if ((!pDateFrom.equals("")) && (pDateTo.equals(""))) {
            pIndexArgs++;
            mDsList.add(new DataSearch(" " + pField + " >= ? ", pIndexArgs,
                    String.valueOf(DateConverter.fromDate(REMHelper.convertStringToDate(pDateFrom)))));
        } else if ((pDateFrom.equals("")) && (!pDateTo.equals(""))) {
            pIndexArgs++;
            mDsList.add(new DataSearch(" " + pField + " <= ? ", pIndexArgs,
                    String.valueOf(DateConverter.fromDate(REMHelper.convertStringToDate(pDateTo)))));
        } else if ((pDateFrom.equals("")) && (pDateTo.equals(""))) {
            pIndexArgs++;
            mDsList.add(new DataSearch(" " + pField + " BETWEEN ? ", pIndexArgs,
                    String.valueOf(DateConverter.fromDate(REMHelper.convertStringToDate(pDateFrom)))));
            pIndexArgs++;
            mDsList.add(new DataSearch(" AND ? ", pIndexArgs,
                    String.valueOf(DateConverter.fromDate(REMHelper.convertStringToDate(pDateTo)))));
        }

        return pIndexArgs;
    }

    private DataSearch addPoiArg(String pCondition, int pIndexArg, String pArg) {
        return new DataSearch(pCondition, pIndexArg, pArg);
    }

    private int managePoi(int pIndexArgs) {
//        String lPoiCondition = " realestate.reId IN (SELECT DISTINCT(poireid) FROM poi  WHERE poiName IN ( ";
        String lPoiCondition = " poiName IN ( ";
        List<RePoi> lPoiList = new ArrayList<>();
        String lCondition = "";

        if (mBinding.fragReSearchPoiBank.isChecked()) {
            pIndexArgs++;
            lCondition = REMHelper.addValueAndSeparatorToString(lCondition, ",", " ? ");
            lPoiList.add(new RePoi(pIndexArgs, getString(R.string.poi_bank)));
        }
        if (mBinding.fragReSearchPoiFood.isChecked()) {
            pIndexArgs++;
            lCondition = REMHelper.addValueAndSeparatorToString(lCondition, ",", " ? ");
            lPoiList.add(new RePoi(pIndexArgs, getString(R.string.poi_food)));
        }
        if (mBinding.fragReSearchPoiHealth.isChecked()) {
            pIndexArgs++;
            lCondition = REMHelper.addValueAndSeparatorToString(lCondition, ",", " ? ");
            lPoiList.add(new RePoi(pIndexArgs, getString(R.string.poi_health)));
        }
        if (mBinding.fragReSearchPoiRestaurant.isChecked()) {
            pIndexArgs++;
            lCondition = REMHelper.addValueAndSeparatorToString(lCondition, ",", " ? ");
            lPoiList.add(new RePoi(pIndexArgs, getString(R.string.poi_restaurant)));
        }
        if (mBinding.fragReSearchPoiSchool.isChecked()) {
            pIndexArgs++;
            lCondition = REMHelper.addValueAndSeparatorToString(lCondition, ",", " ? ");
            lPoiList.add(new RePoi(pIndexArgs, getString(R.string.poi_school)));
        }
        if (mBinding.fragReSearchPoiStore.isChecked()) {
            pIndexArgs++;
            lCondition = REMHelper.addValueAndSeparatorToString(lCondition, ",", " ? ");
            lPoiList.add(new RePoi(pIndexArgs, getString(R.string.poi_store)));
        }
        if (mBinding.fragReSearchPoiSubway.isChecked()) {
            pIndexArgs++;
            lCondition = REMHelper.addValueAndSeparatorToString(lCondition, ",", " ? ");
            lPoiList.add(new RePoi(pIndexArgs, getString(R.string.poi_subway)));
        }
        if (mBinding.fragReSearchPoiPark.isChecked()) {
            pIndexArgs++;
            lCondition = REMHelper.addValueAndSeparatorToString(lCondition, ",", " ? ");
            lPoiList.add(new RePoi(pIndexArgs, getString(R.string.poi_park)));
        }
        lPoiCondition += lCondition;
//        lPoiCondition += " )) ";
        lPoiCondition += " ) ";

        for (RePoi lPoi : lPoiList) {
            mDsList.add(new DataSearch(lPoiCondition, (int) lPoi.getPoiReId(), lPoi.getPoiName()));
        }
        return pIndexArgs;
    }

    private DataSearch addBooleanArg(String pCondition, int pIndexArg) {
        return new DataSearch(pCondition, pIndexArg, "1");
    }

    private void executeQuery(String pStrQuery, String[] pArgs) {
        SimpleSQLiteQuery lQuery = new SimpleSQLiteQuery(pStrQuery, pArgs);
        mViewModel.selectSearch(lQuery).observe(getViewLifecycleOwner(), pReCompList -> {
            Log.d(TAG, "buildQuery: " + pReCompList.size());
            for (RealEstateComplete lReComp : pReCompList) {
                Log.d(TAG, "buildQuery: " + lReComp.getRealEstate().getReDescription()
                        + " ; " + lReComp.getRealEstate().getRePrice());
            }
            if (pReCompList.size() == 0) {
                Toast.makeText(mContext, getString(R.string.search_txt_err_no_data_found), Toast.LENGTH_SHORT).show();
            } else {
                sApi.setSearchResult(pReCompList);
                mNavController.navigate(R.id.action_reSearchFragment_to_reListFragment);
            }
//            mCallback = (OnSearchResult) mContext;
// mCallbach sans onAttach => error null object reference
//            mCallback.onSearchResult(pReCompList);

        });
    }

//    @Override
//    public void onAttach(@NonNull Context context) {
//        super.onAttach(context);
//        try {
//            mCallback = (OnSearchResult) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException("Error in retrieving data. Please try again");
//        }
//    }
}