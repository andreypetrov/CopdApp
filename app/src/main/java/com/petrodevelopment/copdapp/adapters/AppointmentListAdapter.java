package com.petrodevelopment.copdapp.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.model.Provider;
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

        TextView doctorNameView = (TextView) view.findViewById(R.id.doctor_name);
        doctorNameView.setText(appointmentListVm.doctorName);
        TextView doctorTitleView = (TextView) view.findViewById(R.id.doctor_title);
        doctorTitleView.setText(appointmentListVm.doctorTitle);
        TextView dateView = (TextView) view.findViewById(R.id.date);
        dateView.setText(appointmentListVm.date);

        ImageView doctorImage = (ImageView) view.findViewById(R.id.doctor_image);
        Picasso.with(context).load(appointmentListVm.doctorImageUrl).into(doctorImage);
    }

    @Override
    public int getCellResourceId() {
        return cellMainMenuLayoutId;
    }

}