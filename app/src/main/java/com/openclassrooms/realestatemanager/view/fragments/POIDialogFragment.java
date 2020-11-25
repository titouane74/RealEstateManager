package com.openclassrooms.realestatemanager.view.fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.openclassrooms.realestatemanager.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Florence LE BOURNOT on 25/11/2020
 */
public class POIDialogFragment extends DialogFragment {

    private OnDialogPOIListener mCallback;

    public interface OnDialogPOIListener  {
        void onPOIOkClicked(List<String> pList);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder lBuilder = new AlertDialog.Builder(getActivity());
        lBuilder.setTitle(getString(R.string.poi_title_list));

        List<String> lPoiSelected = new ArrayList<>();
        List<String> lPoiList = Arrays.asList(getResources().getStringArray(R.array.poi_spinner));

        final CharSequence[] charSequenceItems = lPoiList.toArray(new CharSequence[lPoiList.size()]);


        List<String> finalLPoiList = lPoiList;

        lBuilder.setMultiChoiceItems(charSequenceItems, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked) {
                    lPoiSelected.add(finalLPoiList.get(which));
                }
            }
        });

        lBuilder.setNegativeButton(R.string.default_btn_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {  }
        });
        lBuilder.setPositiveButton(R.string.default_btn_ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mCallback.onPOIOkClicked(lPoiSelected);
            }
        });
        return lBuilder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        // Verify that the host activity implements the callback interface
        try {
            // Instantiate the NoticeDialogListener so we can send events to the host
            mCallback = (OnDialogPOIListener) context;
        } catch (ClassCastException e) {
            // The activity doesn't implement the interface, throw exception
            throw new ClassCastException(e.toString()
                    + " must implement OnDialogPOIListener");
        }
    }
}
