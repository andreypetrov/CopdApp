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

public class ProviderListAdapter extends GenericAdapter<Provider> {
    private int cellMainMenuLayoutId;

    public ProviderListAdapter(List<Provider> data, Context context, int cellMainMenuLayoutId) {
        super(data, context);
        this.cellMainMenuLayoutId = cellMainMenuLayoutId;
    }

    @Override
    public void update(View view, int position) {
        Provider provider = getItem(position);

        TextView doctorNameView = (TextView) view.findViewById(R.id.doctor_name);
        doctorNameView.setText(provider.firstName + " " + provider.lastName);
        TextView doctorTitleView = (TextView) view.findViewById(R.id.doctor_title);
        doctorTitleView.setText(provider.getClinitianType(context).name);
        TextView dateView = (TextView) view.findViewById(R.id.email);
        dateView.setText(provider.email);

        ImageView doctorImage = (ImageView) view.findViewById(R.id.doctor_image);
        Picasso.with(context).load(provider.photoUrl).into(doctorImage);
    }


    @Override
    public int getCellResourceId(int position) {
        return cellMainMenuLayoutId;
    }

}