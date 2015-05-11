package com.petrodevelopment.copdapp.adapters;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.model.Provider;

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
        TextView textView = (TextView) view.findViewById(R.id.first_name);
        textView.setText(provider.firstName);
    }

    @Override
    public int getCellResourceId() {
        return cellMainMenuLayoutId;
    }

}