package com.openclassrooms.realestatemanager.view.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.databinding.FragmentReMapsBinding;
import com.openclassrooms.realestatemanager.di.Injection;
import com.openclassrooms.realestatemanager.di.ReViewModelFactory;
import com.openclassrooms.realestatemanager.model.RealEstateComplete;
import com.openclassrooms.realestatemanager.utils.PermissionUtils;
import com.openclassrooms.realestatemanager.viewmodel.MapViewModel;

import java.util.List;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static androidx.core.content.ContextCompat.checkSelfPermission;
import static com.openclassrooms.realestatemanager.AppRem.sApi;
import static com.openclassrooms.realestatemanager.view.adapters.ReListAdapter.RE_ID_KEY;

public class MapsFragment extends BaseFragment<FragmentReMapsBinding> implements LocationListener {

    private OnMarkerClick mCallback;

    public interface OnMarkerClick {
        void navigateDetail(Bundle pBundle);
    }

    private static final int PERMISSION_REQUEST_CODE = 1;

    public static final String  TAG = "TAG_MAP";
    private int mZoom;
    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationClient;
    private SupportMapFragment mMapFragment;
    private Context mContext;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @SuppressLint("MissingPermission")
        @Override
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;

            if (checkPermissions()) {
                mMap.setMyLocationEnabled(true);
            }

            mMap.setOnMarkerClickListener(pMarker -> {
                displayRealEstateDetail(pMarker);
                return false;
            });
        }
    };

    public MapsFragment() {    }

    @Override
    protected void configureDesign(FragmentReMapsBinding pBinding, boolean pIsTablet) {
        mContext = getContext();

        mZoom = Integer.parseInt(getString(R.string.map_zoom));
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext());

        if (!checkPermissions()) {
            if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                // Display a dialog with rationale.
                PermissionUtils.RationaleDialog.newInstance(PERMISSION_REQUEST_CODE, true)
                        .show(requireActivity().getSupportFragmentManager(), "dialog");
            } else {
                // Location permission has not been granted yet, request it.
                requestPermissions(new String[]{ACCESS_FINE_LOCATION},
                        PERMISSION_REQUEST_CODE);
            }
        } else {
            getCurrentLocation();
        }
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

        lMapViewModel.selectAllReCompleteMandatoryDataComplete().observe(getViewLifecycleOwner(),
                MapsFragment.this::setMapMarkers);
    }

    /**
     * Set the markers on the map
     * @param pReCompList : list object : real estate list
     */
    public void setMapMarkers(List<RealEstateComplete> pReCompList) {
        if (mMap != null) {
            mMap.clear();
            for (RealEstateComplete lReComp : pReCompList) {
                String lName = lReComp.getRealEstate().getReType();
                lName += "\n " + lReComp.getRealEstate().getReArea();
                lName += "\n " + lReComp.getRealEstate().getRePrice();

                if (lReComp.getReLocation()!= null) {
                    LatLng latLng = new LatLng(lReComp.getReLocation().getLocLatitude(),
                            lReComp.getReLocation().getLocLongitude());
                    Marker lMarker = mMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(lName));
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

        RealEstateComplete lReComp = (RealEstateComplete) pMarker.getTag();
        if (lReComp != null) {
            Bundle lBundle = new Bundle();
            lBundle.putLong(RE_ID_KEY, lReComp.getRealEstate().getReId());
            mCallback = (OnMarkerClick) mContext;
            mCallback.navigateDetail(lBundle);
        }
    }

    /**
     * Get the current location
     */
    @SuppressLint("MissingPermission")
    public void getCurrentLocation() {
        Task<Location> task = mFusedLocationClient.getLastLocation();
        task.addOnSuccessListener(location -> {
            if (location != null) {
                saveLocation(location);
            }
        });

        LocationManager lLocationManager = (LocationManager) this.requireContext().getSystemService(Context.LOCATION_SERVICE);
        if (lLocationManager != null) {
            lLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 50, this);
        }
    }

    /**
     * Save the location and configure the view model and move the camera to the location
     * @param pLocation : object : location
     */
    public void saveLocation(Location pLocation) {
        double lLongitude = pLocation.getLongitude();
        double lLatitude = pLocation.getLatitude();
        LatLng lLatLng = new LatLng(lLatitude, lLongitude);

        sApi.saveLocationInSharedPreferences(pLocation);
        configureViewModel();
        setCameraOnCurrentLocation(lLatLng, mZoom);
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

    /**
     * Called when the location has changed.
     *
     * <p> There are no restrictions on the use of the supplied Location object.
     *
     * @param location The new location, as a Location object.
     */

    @Override
    public void onLocationChanged(Location location) {
        saveLocation(location);
    }


    /**
     * This callback will never be invoked and providers can be considers as always in the
     * {@link Location Provider#AVAILABLE} state.
     *
     * @deprecated This callback will never be invoked.
     */
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    /**
     * Called when the provider is enabled by the user.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     */
    @Override
    public void onProviderEnabled(String provider) {
    }

    /**
     * Called when the provider is disabled by the user. If requestLocationUpdates
     * is called on an already disabled provider, this method is called
     * immediately.
     *
     * @param provider the name of the location provider associated with this
     *                 update.
     */
    @Override
    public void onProviderDisabled(String provider) {
    }

    /**
     * Manage the permissions access for the location and the camera
     */
    private boolean checkPermissions() {
        int lPermissionLocation = checkSelfPermission(requireActivity(), ACCESS_FINE_LOCATION);

        return lPermissionLocation == PackageManager.PERMISSION_GRANTED;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            //IF PERMISSION GRANTED
            //grantResults[0] -> Permission for the location
            if (grantResults.length > 0) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                    getCurrentLocation();
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                        if (shouldShowRequestPermissionRationale(ACCESS_FINE_LOCATION)) {
                            showMessageOKCancel(requireContext().getString(R.string.permission_required_toast),
                                    (dialog, which) -> requestPermissions(new String[]{ACCESS_FINE_LOCATION},
                                            PERMISSION_REQUEST_CODE));
                        }
                    }
                }
            }
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(requireContext())
                .setMessage(message)
                .setPositiveButton(R.string.default_btn_ok, okListener)
                .setNegativeButton(R.string.default_btn_cancel, null)
                .create()
                .show();

    }
}