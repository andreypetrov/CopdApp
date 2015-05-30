package com.petrodevelopment.copdapp.gallery;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;

import com.petrodevelopment.copdapp.MainApplication;
import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.activities.BaseActivity;
import com.petrodevelopment.copdapp.model.AppointmentRecord;
import com.petrodevelopment.copdapp.util.Animation;
import com.petrodevelopment.copdapp.util.OnSwipeTouchListener;
import com.petrodevelopment.copdapp.util.U;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by user on 15-05-29.
 */
public class GalleryActivity extends BaseActivity {
    private int imageIndex;
    private String appointmentId;
    private AppointmentRecord appointmentRecord;
    private ImageView imageView;


    public enum AnimationDirection {
        BACKWARD,
        FORWARD,
        NONE
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        initViews();
        initModel();
        initToolbar();
        updateUi(AnimationDirection.NONE);
    }

    @Override
    public void initModel() {
        appointmentId = getIntent().getStringExtra(MainApplication.APPOINTMENT_ID_EXTRA);
        imageIndex = getIntent().getIntExtra(MainApplication.IMAGE_INDEX_EXTRA, 0);
        appointmentRecord = getModelFacade().getAppointment(appointmentId).appointmentRecords.get(0);//TODO make this dynamic

    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //toolbar.setTitle(appointmentRecord.name);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        U.log(this, "appointmentRecord.name: " + appointmentRecord.getAppointmentRecordCategory(this).name);
        getSupportActionBar().setTitle(appointmentRecord.getAppointmentRecordCategory(this).name);
    }

    public void initViews() {
        imageView = (ImageView) findViewById(R.id.image_view);
        imageView.setOnTouchListener(createOnSwipeTouchListener());
    }

    private OnSwipeTouchListener createOnSwipeTouchListener () {
       return new OnSwipeTouchListener(GalleryActivity.this) {
            @Override
            public void onSwipeLeft() {
                stepForward();
            }

            @Override
            public void onSwipeRight() {
                stepBackward();
            }

            @Override
            public void onSwipeTop() {
            }

            @Override
            public void onSwipeBottom() {
            }
        };
    }



    private void updateUi(final AnimationDirection animationDirection) { //TODO pass as parameter the type of animation for the image update - left slide, right slide or none
        String imageUrl = appointmentRecord.imageUrls.get(imageIndex);
        Picasso.with(this).load(imageUrl).into(imageView);
    }


    private void swap(ImageView frontImage, ImageView backImage) {
        ImageView temp = frontImage;
        frontImage = backImage;
        backImage = temp;
    }


    public void stepBackward() {
        U.log(this, "back slide");
        if (imageIndex > 0) {
            imageIndex--;
            updateUi(AnimationDirection.BACKWARD);
        }
    }

    public void stepForward() {
        U.log(this, "forward slide");
        if (imageIndex < appointmentRecord.imageUrls.size() - 1) {
            imageIndex++;
            updateUi(AnimationDirection.FORWARD);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.gallery_menu, menu);
        return true;
    }

}
