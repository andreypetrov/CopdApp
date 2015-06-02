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
        if (position == 0) updateSelectProviderView(view);
        else {
            Provider provider = getItem(position-1);
            TextView textView = (TextView) view.findViewById(R.id.doctor_name);
            textView.setText(provider.title + " " + provider.firstName + " " + provider.lastName);
        }
    }

    private void updateSelectProviderView(View view) {
        TextView textView = (TextView) view.findViewById(R.id.doctor_name);
        textView.setText(context.getString(R.string.select_provider));
    }

    @Override
    public int getCellResourceId(int position) {
        return cellMainMenuLayoutId;
    }

}