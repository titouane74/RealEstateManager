package com.openclassrooms.realestatemanager.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;

/**
 * Created by Florence LE BOURNOT on 29/11/2020
 */
public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> {

    @NonNull
    @Override
    public PhotoAdapter.PhotoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View lView = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_re_cardview_photo_item,
                parent, false);

        return new PhotoHolder(lView);
    }

    @Override
    public void onBindViewHolder(@NonNull PhotoAdapter.PhotoHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
            return 0;
    }

    static class PhotoHolder extends RecyclerView.ViewHolder {
        private final ImageView mPhoto;
        private final TextView mPhotDescription;

        public PhotoHolder(@NonNull View itemView) {
            super(itemView);
            mPhoto = itemView.findViewById(R.id.frag_re_add_edit_img_photo);
            mPhotDescription = itemView.findViewById(R.id.frag_re_add_edit_tv_photo);
        }
    }
}
