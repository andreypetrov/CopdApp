package com.petrodevelopment.copdapp.gallery;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.petrodevelopment.copdapp.MainApplication;
import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.activities.BaseActivity;
import com.petrodevelopment.copdapp.model.AppointmentRecord;
import com.petrodevelopment.copdapp.util.OnSwipeTouchListener;
import com.squareup.picasso.Picasso;

/**
 * Created by user on 15-05-29.
 */
public class GalleryActivity  extends BaseActivity {
    private int imageIndex;
    private String appointmentId;
    private AppointmentRecord appointmentRecord;
    private ImageView imageView;
    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        setContentView(R.layout.activity_gallery);


        initModel();
        updateUi();
    }


    public void initViews() {
        imageView = (ImageView) findViewById(R.id.image_view);
        imageView.setOnTouchListener(new OnSwipeTouchListener(GalleryActivity.this) {
            @Override
            public void onSwipeLeft() {
                Toast.makeText(GalleryActivity.this, "left", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeRight() {
                Toast.makeText(GalleryActivity.this, "right", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeTop() {
                Toast.makeText(GalleryActivity.this, "top", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSwipeBottom() {
                Toast.makeText(GalleryActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void initModel() {
        appointmentId = getIntent().getStringExtra(MainApplication.APPOINTMENT_ID_EXTRA);
        imageIndex = getIntent().getIntExtra(MainApplication.IMAGE_INDEX_EXTRA, 0);
        appointmentRecord = ((MainApplication)getApplication()).getAppointment(appointmentId).severity;//TODO make this dynamic
    }



    private void updateUi() { //TODO pass as parameter the type of animation for the image update - left slide, right slide or none
        String imageUrl = appointmentRecord.imageUrls.get(imageIndex);
        Picasso.with(this).load(imageUrl).into(imageView);

    }


    public void onBackSlide(View view) {
        if (imageIndex > 0) {
            imageIndex--;
            updateUi();
        }
    }

    public void onForwardSlide(View view) {
        if (imageIndex < appointmentRecord.imageUrls.size() - 1){
            imageIndex++;
            updateUi();
        }
    }
}
