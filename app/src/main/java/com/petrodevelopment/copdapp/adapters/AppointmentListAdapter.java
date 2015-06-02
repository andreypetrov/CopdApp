package com.petrodevelopment.copdapp.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.model.Appointment;
import com.petrodevelopment.copdapp.model.ClinicianType;
import com.petrodevelopment.copdapp.model.Provider;
import com.petrodevelopment.copdapp.util.U;

import java.util.Date;
import java.util.List;

public class AppointmentListAdapter extends GenericAdapter<Appointment> {
    private int cellMainMenuLayoutId;

    public AppointmentListAdapter(List<Appointment> data, Context context, int cellMainMenuLayoutId) {
        super(data, context);
        this.cellMainMenuLayoutId = cellMainMenuLayoutId;
    }

    @Override
    public void update(View view, int position) {
        Appointment appointment = getItem(position);
        Provider provider = appointment.getProvider(context);
        ClinicianType clinicianType = provider.getClinitianType(context);
        Date date = U.convertUnixStringToDate(appointment.date);

        View backgroundView=  view.findViewById(R.id.background_layout);
        backgroundView.setBackgroundColor(Color.parseColor(clinicianType.color));
        TextView doctorNameView = (TextView) view.findViewById(R.id.doctor_name);

        if(provider != null) doctorNameView.setText(appointment.getProvider(context).getNameAndTitle());
        TextView doctorTitleView = (TextView) view.findViewById(R.id.clinician_type);
        doctorTitleView.setText(clinicianType.name);
        TextView dateView = (TextView) view.findViewById(R.id.date);
        dateView.setText(U.convertToDateString(date));
        TextView timeView = (TextView) view.findViewById(R.id.time);
        timeView.setText(U.convertToTimeString(date));
        ImageView doctorImage = (ImageView) view.findViewById(R.id.doctor_image);
        doctorImage.setImageDrawable(U.getDrawableFromImageName(clinicianType.imageResourceName, context));
    }

    @Override
    public int getCellResourceId(int position) {
        return cellMainMenuLayoutId;
    }

}