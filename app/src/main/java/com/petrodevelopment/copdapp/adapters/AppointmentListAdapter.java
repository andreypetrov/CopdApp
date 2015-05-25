package com.petrodevelopment.copdapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.model.Provider;
import com.petrodevelopment.copdapp.util.U;
import com.petrodevelopment.copdapp.viewmodel.AppointmentListVm;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AppointmentListAdapter extends GenericAdapter<AppointmentListVm> {
    private int cellMainMenuLayoutId;

    public AppointmentListAdapter(List<AppointmentListVm> data, Context context, int cellMainMenuLayoutId) {
        super(data, context);
        this.cellMainMenuLayoutId = cellMainMenuLayoutId;
    }

    @Override
    public void update(View view, int position) {
        AppointmentListVm appointmentListVm = getItem(position);

        View backgroundView=  view.findViewById(R.id.background_layout);
        backgroundView.setBackgroundColor(Color.parseColor(appointmentListVm.backgroundColor));
        TextView doctorNameView = (TextView) view.findViewById(R.id.doctor_name);
        doctorNameView.setText(appointmentListVm.doctorName);
        TextView doctorTitleView = (TextView) view.findViewById(R.id.doctor_title);
        doctorTitleView.setText(appointmentListVm.doctorTitle);
        TextView dateView = (TextView) view.findViewById(R.id.date);
        dateView.setText(appointmentListVm.date);
        TextView timeView = (TextView) view.findViewById(R.id.time);
        timeView.setText(appointmentListVm.time);

        ImageView doctorImage = (ImageView) view.findViewById(R.id.doctor_image);
        doctorImage.setImageDrawable(U.getDrawableFromImageName(appointmentListVm.doctorImageUrl, context));

        //Picasso.with(context).load(appointmentListVm.doctorImageUrl).into(doctorImage);

    }

    @Override
    public int getCellResourceId(int position) {
        return cellMainMenuLayoutId;
    }

}