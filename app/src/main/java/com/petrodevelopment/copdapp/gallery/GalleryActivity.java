package com.petrodevelopment.copdapp.gallery;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        initViews();
        initModel();
        initToolbar();
        updateUi();
    }
    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(appointmentRecord.name);
    }

    public void initViews() {
        imageView = (ImageView) findViewById(R.id.image_view);
        imageView.setOnTouchListener(new OnSwipeTouchListener(GalleryActivity.this) {
            @Override
            public void onSwipeLeft() {
                onBackSlide();
            }

            @Override
            public void onSwipeRight() {
                onForwardSlide();
            }

            @Override
            public void onSwipeTop() {
            }

            @Override
            public void onSwipeBottom() {
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


    public void onBackSlide() {
        if (imageIndex > 0) {
            imageIndex--;
            updateUi();
        }
    }

    public void onForwardSlide() {
        if (imageIndex < appointmentRecord.imageUrls.size() - 1){
            imageIndex++;
            updateUi();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gallery_menu, menu);
        return true;
    }
}
