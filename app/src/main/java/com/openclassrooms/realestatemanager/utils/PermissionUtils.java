package com.openclassrooms.realestatemanager.utils;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;

import com.openclassrooms.realestatemanager.R;

import java.util.Objects;

public class PermissionUtils {

    /**
     * A dialog that explains the use of the location permission and requests the necessary
     * permission.
     * <p>
     * The activity should implement
     * {@link ActivityCompat.OnRequestPermissionsResultCallback}
     * to handle permit or denial of this permission request.
     */
    public static class RationaleDialog extends DialogFragment {

        private static final String ARGUMENT_PERMISSION_REQUEST_CODE = "requestCode";

        private static final String ARGUMENT_FINISH_ACTIVITY = "finish";

        private boolean finishActivity = false;

        /**
         * Creates a new instance of a dialog displaying the rationale for the use of the location
         * permission.
         * <p>
         * The permission is requested after clicking 'ok'.
         *
         * @param requestCode    Id of the request that is used to request the permission. It is
         *                       returned to the
         *                       {@link ActivityCompat.OnRequestPermissionsResultCallback}.
         * @param finishActivity Whether the calling Activity should be finished if the dialog is
         *                       cancelled.
         */
        public static RationaleDialog newInstance(int requestCode, boolean finishActivity) {
            Bundle arguments = new Bundle();
            arguments.putInt(ARGUMENT_PERMISSION_REQUEST_CODE, requestCode);
            arguments.putBoolean(ARGUMENT_FINISH_ACTIVITY, finishActivity);
            RationaleDialog dialog = new RationaleDialog();
            dialog.setArguments(arguments);
            return dialog;
        }

        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Bundle arguments = getArguments();
            final int requestCode = Objects.requireNonNull(arguments).getInt(ARGUMENT_PERMISSION_REQUEST_CODE);
            finishActivity = arguments.getBoolean(ARGUMENT_FINISH_ACTIVITY);

            return new AlertDialog.Builder(getActivity())
                    .setMessage(R.string.permission_rationale_location)
                    .setPositiveButton(android.R.string.ok, (dialog, which) -> {
                        // After click on Ok, request the permission.
                        ActivityCompat.requestPermissions(requireActivity(),
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                requestCode);
                        // Do not finish the Activity while requesting permission.
                        finishActivity = false;
                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .create();
        }

        @Override
        public void onDismiss(@NonNull DialogInterface dialog) {
            super.onDismiss(dialog);
            if (finishActivity) {
                Toast.makeText(getActivity(),
                        R.string.permission_required_toast,
                        Toast.LENGTH_SHORT)
                        .show();
                requireActivity().finish();
            }
        }
    }

}
