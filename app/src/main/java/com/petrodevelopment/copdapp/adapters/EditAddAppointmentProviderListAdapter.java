package com.petrodevelopment.copdapp.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.model.Provider;

import java.util.List;

public class EditAddAppointmentProviderListAdapter extends GenericAdapter<Provider> {
    private int cellMainMenuLayoutId;

    public EditAddAppointmentProviderListAdapter(List<Provider> data, Context context, int cellMainMenuLayoutId) {
        super(data, context);
        this.cellMainMenuLayoutId = cellMainMenuLayoutId;
    }

    //Only thing really changed so far is this - Tom - 18-05-2015
    @Override
    public void update(View view, int position) {
        Provider provider = getItem(position);
        TextView textView = (TextView) view.findViewById(R.id.doctor_name);
        textView.setText(provider.firstName + " " + provider.lastName);
    }

    @Override
    public int getCellResourceId(int position) {
        return cellMainMenuLayoutId;
    }

}