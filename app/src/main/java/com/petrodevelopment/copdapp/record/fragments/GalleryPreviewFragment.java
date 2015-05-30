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
import com.petrodevelopment.copdapp.model.AppointmentRecord;

/**
 * Created by user on 15-05-14.
 */
public class GalleryPreviewFragment extends BaseFragment {

    private OnGalleryClickListener onGalleryClickListener;
    private GridView gridView;
    private ImageAdapter imageAdapter;

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
    public void initImages(AppointmentRecord record) {
        initImages(getView(), record, getActivity());
    }

    /**
     * For every appointment record update the images gallery
     * @param view
     * @param record
     * @param context
     */
    private void initImages(View view, final AppointmentRecord record, final Context context) {
        if (record != null && record.imageUrls != null) {
            gridView = (GridView) view.findViewById(R.id.gallery);
            imageAdapter = new ImageAdapter(record.imageUrls, context, R.layout.cell_grid_gallery);
            gridView.setAdapter(imageAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    //TODO add click ui effects here potentially
                    if (onGalleryClickListener != null) onGalleryClickListener.onImageClick(position);
                }
            });
        }
    }



    public void update() {
        imageAdapter.notifyDataSetChanged();
    }



    public void setOnGalleryClickListener(OnGalleryClickListener onGalleryClickListener) {
        this.onGalleryClickListener = onGalleryClickListener;
    }
}
