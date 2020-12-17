package com.openclassrooms.realestatemanager.view.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.di.Injection;
import com.openclassrooms.realestatemanager.di.ReViewModelFactory;
import com.openclassrooms.realestatemanager.model.RealEstateComplete;
import com.openclassrooms.realestatemanager.viewmodel.MapViewModel;

import java.util.List;

public class MapsFragment extends Fragment {

    private static final int PERMISSION_REQUEST_CODE = 1;

    public static final String  TAG = "TAG_MAP";
    private int mZoom;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private SupportMapFragment mMapFragment;
    private Context mContext;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            mMap.setOnMarkerClickListener(pMarker -> {
                displayRealEstateDetail(pMarker);
                return false;
            });
        }
    };
    public MapsFragment() {    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View lView = inflater.inflate(R.layout.fragment_re_maps, container, false);

        mZoom = Integer.parseInt(getString(R.string.map_zoom));
        mContext = lView.getContext();
        configureViewModel();
        double lLat = Double.parseDouble(getString(R.string.default_latitude_position));
        double lLng = Double.parseDouble(getString(R.string.default_longitude_position));
        LatLng lLatLng = new LatLng(lLat,lLng);
        setCameraOnCurrentLocation(lLatLng, mZoom);
        return lView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.re_map);
        if (mMapFragment != null) {
            mMapFragment.getMapAsync(callback);
        }
    }

    public void configureViewModel() {

        ReViewModelFactory lFactory = Injection.reViewModelFactory(mContext);
        MapViewModel lMapViewModel = new ViewModelProvider(requireActivity(),lFactory).get(MapViewModel.class);

        lMapViewModel.selectAllReCompleteMap().observe(getViewLifecycleOwner(),
                pReCompList -> MapsFragment.this.setMapMarkers(pReCompList));
    }

    /**
     * Set the markers on the map
     * @param pReCompList : list object : real estate list
     */
    public void setMapMarkers(List<RealEstateComplete> pReCompList) {
        BitmapDescriptor lIcon;
        if (mMap != null) {
            mMap.clear();
            for (RealEstateComplete lReComp : pReCompList) {
                String lName = lReComp.getRealEstate().getReType();
                lName += "\n " + lReComp.getRealEstate().getReArea();
                lName += "\n " + lReComp.getRealEstate().getRePrice();

                lIcon = BitmapDescriptorFactory.fromResource(R.drawable.ic_location);

                if (lReComp.getReLocation()!= null) {
                    LatLng latLng = new LatLng(lReComp.getReLocation().getLocLatitude(),
                            lReComp.getReLocation().getLocLongitude());
                    Marker lMarker = mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(lName));
//                            .icon(R.drawable.ic_location));
                    lMarker.setTag(lReComp);
                }
            }
        }
    }

    /**
     * Open the real estate detail when click on the marker
     * @param pMarker : object : marker
     */
    private void displayRealEstateDetail(Marker pMarker) {
        Context lContext = requireContext();
        RealEstateComplete lReComp = (RealEstateComplete) pMarker.getTag();
        if (lReComp != null) {
            //TODO
//            Intent lIntentRestoDetail = new Intent(lContext, RestaurantDetailActivity.class);
//            lIntentRestoDetail.putExtra(RESTO_PLACE_ID, lRestaurant.getRestoPlaceId());
//            lContext.startActivity(lIntentRestoDetail);
        }
    }
    /**
     * Set camera to the location
     * @param latLng : object : latLng : new location
     * @param zoom : int : intensity of the zoom
     */
    public void setCameraOnCurrentLocation(LatLng latLng, int zoom) {
        try {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
        } catch (Exception pE) {
            Log.e(TAG, "setCameraOnCurrentLocation: " + pE.getMessage());
        }
    }
}