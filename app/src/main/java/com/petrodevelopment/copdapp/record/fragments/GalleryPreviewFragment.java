package com.petrodevelopment.copdapp.record.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.adapters.ImageAdapter;
import com.petrodevelopment.copdapp.fragments.BaseFragment;
import com.petrodevelopment.copdapp.fragments.SectionFragment;
import com.petrodevelopment.copdapp.model.AppointmentRecord;

/**
 * Created by user on 15-05-14.
 */
public class GalleryPreviewFragment extends BaseFragment {

    private OnGalleryClickListener onGalleryClickListener;


    public interface OnGalleryClickListener {
        void onImageClick(int position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_gallery_preview, container, false);
        return rootView;
    }


    /**
     * For every appointment record update the images gallery
     * @param record
     */
    public void updateImages(AppointmentRecord record) {
        updateImages(getView(), record, getActivity());
    }

    /**
     * For every appointment record update the images gallery
     * @param view
     * @param record
     * @param context
     */
    private void updateImages(View view, final AppointmentRecord record, final Context context) {
        if (record != null && record.imageUrls != null) {
            GridView gridView = (GridView) view.findViewById(R.id.gallery);
            ImageAdapter imageAdapter = new ImageAdapter(record.imageUrls, context, R.layout.cell_grid_gallery);
            gridView.setAdapter(imageAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    Toast.makeText(context, "" + position + ", record note: " + record.note,
                            Toast.LENGTH_SHORT).show();
                    if (onGalleryClickListener != null) onGalleryClickListener.onImageClick(position);
                }
            });
        }
    }




    public void setOnGalleryClickListener(OnGalleryClickListener onGalleryClickListener) {
        this.onGalleryClickListener = onGalleryClickListener;
    }
}
