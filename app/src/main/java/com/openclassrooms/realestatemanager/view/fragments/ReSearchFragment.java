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
import com.openclassrooms.realestatemanager.model.RealEstateComplete;
import com.openclassrooms.realestatemanager.utils.REMHelper;
import com.openclassrooms.realestatemanager.viewmodel.ReSearchViewModel;

import org.json.JSONArray;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
    private String mStringDateMarket;
    private String mStringDateSold;
    private Date mDateMarket = null;
    private Date mDateSold = null;

    private ReSearchViewModel mViewModel;

    @Override
    protected int getMenuAttached() { return R.menu.menu_confirm; }

    @Override
    protected void configureDesign(FragmentReSearchBinding pBinding, NavController pNavController, boolean pIsTablet, boolean pIsTabletLandscape) {
        mBinding = pBinding;
        mFragView = mBinding.getRoot();
        mContext = getContext();
        mNavController = pNavController;
        mIsTabletLandscape = pIsTabletLandscape;
        configureSpinners();
        configureViewModel();
        mBinding.fragReSearchEtMarketDate.setOnClickListener(v -> {
            //displayCalendarDialogMarket ()
            displayCalendarDialog(R.id.frag_re_search_et_market_date);
            mDateMarket = (mStringDateMarket != null) ? REMHelper.convertStringToDate(mStringDateMarket) : null;
        });
        mBinding.fragReSearchEtSoldDate.setOnClickListener(v-> {
            //displayCalendarDialogSold()
            displayCalendarDialog(R.id.frag_re_search_et_sold_date);
            mDateSold = (mStringDateSold != null) ? REMHelper.convertStringToDate(mStringDateSold) : null;
            if ((mDateMarket != null) && (mDateSold != null) && (mDateSold.before(mDateMarket))) {
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
        mBinding.fragReSearchSpinType.setAdapter(REMHelper.configureSpinAdapter(mContext,R.array.type_spinner));
        mBinding.fragReSearchSpinRooms.setAdapter(REMHelper.configureSpinAdapter(mContext,R.array.rooms_spinner));
        mBinding.fragReSearchSpinBedrooms.setAdapter(REMHelper.configureSpinAdapter(mContext,R.array.rooms_spinner));
        mBinding.fragReSearchSpinBathrooms.setAdapter(REMHelper.configureSpinAdapter(mContext,R.array.rooms_spinner));
        mBinding.fragReSearchSpinCountry.setAdapter(REMHelper.configureSpinAdapter(mContext,R.array.country_spinner));
        mBinding.fragReSearchSpinNbPhoto.setAdapter(REMHelper.configureSpinAdapter(mContext,R.array.photo_spinner));
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

                    if (pId == R.id.frag_re_search_et_market_date) {
                        mStringDateMarket = lDate;
                        mBinding.fragReSearchEtMarketDate.setText(lDate);
                    } else if (pId == R.id.frag_re_search_et_sold_date) {
                        mStringDateSold = lDate;
                        mBinding.fragReSearchEtSoldDate.setText(lDate);
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
        String lStrQuery = "";
        List<Object> lArgs = new ArrayList<>();

        boolean lContainsCondition = false;

        lStrQuery += "SELECT * FROM realestate";

        lStrQuery += " WHERE";
        lStrQuery += " reType = ?";
        lStrQuery += " AND reDescription = ?";
//        lStrQuery += " reIsMandatoryDataComplete = ?";
        lContainsCondition=true;

        lStrQuery += ";";

        String[] args = {"Apartment","apart apart"};
//        String[] args = {"true"};

//        ReDatabase  db = ReDatabase.getInstance(mContext);
//        Cursor lCursor = db.query(lStrQuery,args);
//        Log.d(TAG, "buildQuery: " + lCursor.getCount());

        SimpleSQLiteQuery lQuery = new SimpleSQLiteQuery(lStrQuery, args);
        mViewModel.selectSearch(lQuery).observe(getViewLifecycleOwner(), pReCompList -> {
            Log.d(TAG, "buildQuery: " + pReCompList.size());
            for(RealEstateComplete lReComp : pReCompList) {
                Log.d(TAG, "buildQuery: " + lReComp.getRealEstate().getReDescription());
            }

//            mCallback = (OnSearchResult) mContext;
            mNavController.navigate(R.id.action_reSearchFragment_to_reListFragment);

//            mCallback.onSearchResult(pReCompList);

        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnSearchResult) context;
        } catch (ClassCastException e) {
            throw new ClassCastException("Error in retrieving data. Please try again");
        }
    }
}