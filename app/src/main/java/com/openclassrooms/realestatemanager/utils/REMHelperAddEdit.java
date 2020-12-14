package com.openclassrooms.realestatemanager.utils;

import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;
import com.openclassrooms.realestatemanager.databinding.FragmentReAddEditBinding;
import com.openclassrooms.realestatemanager.model.ReLocation;
import com.openclassrooms.realestatemanager.model.RePhoto;
import com.openclassrooms.realestatemanager.model.RePoi;
import com.openclassrooms.realestatemanager.model.RealEstate;
import com.openclassrooms.realestatemanager.model.RealEstateComplete;
import com.openclassrooms.realestatemanager.viewmodel.ReAddEditViewModel;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Florence LE BOURNOT on 13/12/2020
 */
public class REMHelperAddEdit {

    /**
     * Control if all the poi are unchecked
     * @param pBinding : FragmentReAddEditBinding : fragment where are the data
     * @return : boolean : true if all ar unchecked, false if one is checked
     */
    public static boolean isPoiEmpty(FragmentReAddEditBinding pBinding) {

        return (!pBinding.fragReAddEditPoiRestaurant.isChecked() &&
                !pBinding.fragReAddEditPoiSubway.isChecked() &&
                !pBinding.fragReAddEditPoiSchool.isChecked() &&
                !pBinding.fragReAddEditPoiPark.isChecked() &&
                !pBinding.fragReAddEditPoiStore.isChecked() &&
                !pBinding.fragReAddEditPoiBank.isChecked() &&
                !pBinding.fragReAddEditPoiFood.isChecked() &&
                !pBinding.fragReAddEditPoiHealth.isChecked());
    }

    /**
     * Manage the poi to add in the list and delete in the database the ones which are unselected
     * @param pReComp : object : RealEstateComplete with all the real estate information
     * @param pPoiList : list of object : list of the poi which are selected
     * @param pReId : long : id of the real estate
     * @param pIsEdit : boolean : indicator if it's an add or edit
     * @param pPoi : string : the label of the poi which is controlled
     * @param pIsChecked : boolean : indicator if the poi is check or not
     * @param pViewModel : view model : ReAddEditViewModel
     * @return : list og object : list of the poi which are selected
     */
    public static List<RePoi> setPoiList(RealEstateComplete pReComp, List<RePoi> pPoiList, long pReId,
                                         boolean pIsEdit, String pPoi, boolean pIsChecked, ReAddEditViewModel pViewModel) {
        RePoi lPoi;

        if (pIsEdit) { //update
            lPoi = findInPoiList(pReComp.getPoiList(), pPoi);
            if ((lPoi != null) && !pIsChecked) {  // in list and  no more selected => delete
                pViewModel.deleteRePoi(lPoi);
            } else if ((lPoi == null) && pIsChecked) {  // not in list and selected => insert
                pPoiList.add(new RePoi(pReId, pPoi));
            }
        } else if (pIsChecked) { //insert
            pPoiList.add(new RePoi(pReId, pPoi));
        }
        return pPoiList;
    }

    /**
     * Search if the poi is in the list of poi for the real estate in the database
     * @param pPoiList : list of object : list of the poi in the database for the real estate
     * @param pPoi : string : poi to search
     * @return : object : RePoi : return the data of the poi
     */
    public static RePoi findInPoiList(List<RePoi> pPoiList, String pPoi) {
        for (RePoi lPoi : pPoiList) {
            if (lPoi.getPoiName().equals(pPoi)) {
                return lPoi;
            }
        }
        return null;
    }


    public static void setPhotoList(List<RePhoto> pReComp, List<RePhoto> pPhotoList, long pReId,
                                    boolean pIsEdit, ReAddEditViewModel pViewModel) {
        RePhoto lReCompPh;

        if (pIsEdit) {   //update
            //If the photo in database are no longer in the new list => delete
            for (RePhoto lPhoto : pReComp) {
                if (!isFindInNewPhotoList(pPhotoList, lPhoto)) {
                    pViewModel.deleteRePhoto(lPhoto);
                }
            }
        }

        for (RePhoto lPhoto : pPhotoList) {
            if (pIsEdit) { // update
                lReCompPh = findInPhotoList(pReComp, lPhoto);
                if (lReCompPh != null) {   // photo is in the database
                    if (!lReCompPh.equals(lPhoto)) {   // photos are not equals => update
                        lPhoto.setPhId(lReCompPh.getPhId());
                        pViewModel.updateRePhoto(lPhoto);
                    }
                } else {  // photo is not in the database => insert
                    lPhoto.setPhReId(pReId);
                    pViewModel.insertRePhoto(lPhoto);
                }
            } else {  // insert
                lPhoto.setPhReId(pReId);
                pViewModel.insertRePhoto(lPhoto);
            }
        }
    }

    /**
     * Search if the photo is in the list of photo for the real estate in the database
     * @param pReComp : list of object : list of the photo in the database for the real estate
     * @param pPhoto : object : RePhoto : photo to search
     * @return : object : RePhoto : return the data of the photo
     */
    public static RePhoto findInPhotoList(List<RePhoto> pReComp, RePhoto pPhoto) {
        for (RePhoto lPhoto : pReComp) {
            if (lPhoto.getPhImgId() == pPhoto.getPhImgId()) {
                return lPhoto;
            }
        }
        return null;
    }

    /**
     * Search if the real estate photo in the database is in the list of photo selected
     * @param pPhotoList : list of object : list of the photo selected
     * @param pPhoto : object : RePhoto : real estate photo in database to search
     * @return : object : boolean : return if the photo is found or not
     */
    public static boolean isFindInNewPhotoList(List<RePhoto> pPhotoList, RePhoto pPhoto) {
        for (RePhoto lPhoto : pPhotoList) {
            if (lPhoto.getPhImgId() == pPhoto.getPhImgId()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Control the validity of values with a regex
     * @param pValue : string : value to control
     * @param pRegex : string : regex to use for the control
     * @return : boolean : return true if the value matches to the regex otherwise return false
     */
    public static boolean controlValidityWithRegex(String pValue, String pRegex) {
        Matcher lMatcher;
        Pattern lPattern;

        if (!pValue.equals("")) {
            lPattern = Pattern.compile(pRegex);
            lMatcher = lPattern.matcher(pValue);
            return lMatcher.matches();
        } else {
            return true;
        }
    }

    /**
     * Get the indicator if all the mandatory fields are complete
     * @return : boolean : return true if all the mandatory data are complete otherwise false.
     * Return false by default
     */
    public static boolean getIsMandatoryDataComplete(RealEstate pRe, ReLocation pReLoc, boolean pIsPhotoEmpty) {
        if ((!pIsPhotoEmpty)
                && (pRe.getReOnMarketDate() != null)
                && (!pRe.getReAgentLastName().equals(""))
                && (!pReLoc.getLocStreet().equals(""))
                && (!pReLoc.getLocCity().equals(""))
                && (!pReLoc.getLocZipCode().equals(""))
                && (!pRe.getReType().equals(""))
                && (pRe.getReArea() != 0)
                && (pRe.getRePrice() != 0)
                && (!pRe.getReDescription().equals(""))
        ) {
            return true;
        }
        return false;
    }
}
