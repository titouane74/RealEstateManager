package com.openclassrooms.realestatemanager.view.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.openclassrooms.realestatemanager.BuildConfig;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentReDetailBinding;
import com.openclassrooms.realestatemanager.di.Injection;
import com.openclassrooms.realestatemanager.di.ReViewModelFactory;
import com.openclassrooms.realestatemanager.model.ReLocation;
import com.openclassrooms.realestatemanager.model.RePhoto;
import com.openclassrooms.realestatemanager.model.RePoi;
import com.openclassrooms.realestatemanager.model.RealEstateComplete;
import com.openclassrooms.realestatemanager.utils.REMHelper;
import com.openclassrooms.realestatemanager.view.adapters.DetailPhotoAdapter;
import com.openclassrooms.realestatemanager.viewmodel.ReDetailViewModel;

import java.text.ParseException;
import java.util.List;

import static com.openclassrooms.realestatemanager.view.adapters.ReListAdapter.IS_EDIT_KEY;
import static com.openclassrooms.realestatemanager.view.adapters.ReListAdapter.RE_ID_KEY;

public class ReDetailFragment extends BaseFragment<FragmentReDetailBinding> {

    private OnClickListener mCallback;

    /**
     * Interface permettant de gérer les callbacks vers la MainActivity
     */
    public interface OnClickListener {
        void navigateAddEdit(Bundle pBundle);
    }

    public static final String GOOGLE_STATIC_MAP_URL = "https://maps.googleapis.com/maps/api/staticmap";
    private static final String TAG = "REDetailFragment";
    private FragmentReDetailBinding mBinding;
    private DetailPhotoAdapter mAdapter;
    private ReDetailViewModel mViewModel;
    private Context mContext;
    private long mReId;
    private List<RePhoto> mPhotoList;

    @Override
    protected void configureDesign(FragmentReDetailBinding pBinding, boolean pIsTablet) {
        setHasOptionsMenu(true);
        mBinding = pBinding;
        mContext = mBinding.getRoot().getContext();
        initRecyclerView();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_edit, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void initRecyclerView() {
        mAdapter = new DetailPhotoAdapter();
        mBinding.fragReDetRvPhoto.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mBinding.fragReDetRvPhoto.setAdapter(mAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem pItem) {
        if (pItem.getItemId() == R.id.action_menu_edit) {
            Bundle lBundle = new Bundle();
            lBundle.putLong(RE_ID_KEY, mReId);
            lBundle.putBoolean(IS_EDIT_KEY, true);
            navigateTo(lBundle);
            return true;
        }
        return super.onOptionsItemSelected(pItem);
    }

    private void navigateTo(Bundle pBundle) {
        mCallback = (OnClickListener) mContext;
        mCallback.navigateAddEdit(pBundle);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (getArguments() != null) {
            mReId = getArguments().getLong(RE_ID_KEY);
        }
    }
    private void configureViewModel() {
        ReViewModelFactory lFactory = Injection.reViewModelFactory(mContext);
        mViewModel = new ViewModelProvider(this, lFactory).get(ReDetailViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        configureViewModel();
        mViewModel.selectReComplete(mReId).observe(getViewLifecycleOwner(), pRealEstateComplete -> {
            try {
                displayReComplete(pRealEstateComplete);
            } catch (ParseException pE) {
                pE.printStackTrace();
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void displayReComplete(RealEstateComplete pRe) throws ParseException {
        if (pRe != null) {
            for (RePoi lPoi : pRe.getPoiList()) {
                if (lPoi.getPoiName().equals(getString(R.string.poi_restaurant))) {
                    mBinding.fragReDetPoiRestaurant.setChecked(true);
                } else if (lPoi.getPoiName().equals(getString(R.string.poi_subway))) {
                    mBinding.fragReDetPoiSubway.setChecked(true);
                } else if (lPoi.getPoiName().equals(getString(R.string.poi_school))) {
                    mBinding.fragReDetPoiSchool.setChecked(true);
                } else if (lPoi.getPoiName().equals(getString(R.string.poi_health))) {
                    mBinding.fragReDetPoiHealth.setChecked(true);
                } else if (lPoi.getPoiName().equals(getString(R.string.poi_food))) {
                    mBinding.fragReDetPoiFood.setChecked(true);
                } else if (lPoi.getPoiName().equals(getString(R.string.poi_bank))) {
                    mBinding.fragReDetPoiBank.setChecked(true);
                } else if (lPoi.getPoiName().equals(getString(R.string.poi_store))) {
                    mBinding.fragReDetPoiStore.setChecked(true);
                } else if (lPoi.getPoiName().equals(getString(R.string.poi_park))) {
                    mBinding.fragReDetPoiPark.setChecked(true);
                }

            }

            if (pRe.getRealEstate().isReIsSold()) {
                mBinding.fragReDetTvTxtSoldDate.setVisibility(View.VISIBLE);
                mBinding.fragReDetTvSoldDate.setVisibility(View.VISIBLE);
            } else {
                mBinding.fragReDetTvTxtSoldDate.setVisibility(View.INVISIBLE);
                mBinding.fragReDetTvSoldDate.setVisibility(View.INVISIBLE);
            }
            mBinding.fragReDetTvDescription.setText(pRe.getRealEstate().getReDescription());
            mBinding.fragReDetTvAgent.setText(pRe.getRealEstate().getReAgentFirstName() + " " + pRe.getRealEstate().getReAgentLastName());
            mBinding.fragReDetTvPrice.setText(REMHelper.formatNumberWithCommaAndCurrency(pRe.getRealEstate().getRePrice()));
            mBinding.fragReDetTvType.setText(pRe.getRealEstate().getReType());
            mBinding.fragReDetTvArea.setText(Integer.toString(pRe.getRealEstate().getReArea()));
            mBinding.fragReDetTvNbRooms.setText(Integer.toString(pRe.getRealEstate().getReNbRooms()));
            mBinding.fragReDetTvNbBedrooms.setText(Integer.toString(pRe.getRealEstate().getReNbBedrooms()));
            mBinding.fragReDetTvNbBathrooms.setText(Integer.toString(pRe.getRealEstate().getReNbBathrooms()));

            if(pRe.getReLocation() != null) {
                mBinding.fragReDetTvCompleteAddress.setText(pRe.getReLocation().toString());
            }

            mBinding.fragReDetTvMarketDate.setText(REMHelper.convertDateToString(pRe.getRealEstate().getReOnMarketDate()));
            mBinding.fragReDetTvSoldDate.setText(REMHelper.convertDateToString(pRe.getRealEstate().getReSaleDate()));

            mPhotoList = pRe.getRePhotoList();
            mAdapter.setPhotoList(mPhotoList);
            mAdapter.notifyDataSetChanged();

            //TODO TO REACTIVATE
            displayStaticMap(pRe.getReLocation());

        } else {
            Log.d(TAG, "displayReComplete: pRe is null");
        }
    }

    private void displayStaticMap(ReLocation pReLoc) {
        try {
            Uri.Builder uriStaticMap
                    = Uri.parse(GOOGLE_STATIC_MAP_URL).buildUpon();

            int lIntSize = (int) (getResources().getDimension(R.dimen.re_det_map_size) / getResources().getDisplayMetrics().density);
            String lSize = lIntSize + "x" + lIntSize;

            uriStaticMap
                    .appendQueryParameter("size", lSize)
                    .appendQueryParameter("scale", getString(R.string.static_map_scale))
                    .appendQueryParameter("zoom", getString(R.string.static_map_zoom))
                    .appendQueryParameter("key", BuildConfig.MAPS_API_KEY);
            String markers = getString(R.string.static_map_txt_markers_param)
                    + pReLoc.getLocLatitude() + "," + pReLoc.getLocLongitude();
            uriStaticMap.appendQueryParameter("markers", markers);

            Glide.with(this)
                    .load(uriStaticMap.build())
                    .apply(RequestOptions.centerCropTransform())
                    .into(mBinding.fragReDetStaticMap);
        } catch (Exception pE) {
            pE.printStackTrace();
        }
    }
}