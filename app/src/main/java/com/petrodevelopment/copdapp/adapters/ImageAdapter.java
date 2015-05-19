package com.petrodevelopment.copdapp.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by andrey on 18/05/2015.
 */
public class ImageAdapter extends GenericAdapter<String>{
    private int cellMainMenuLayoutId;

    public ImageAdapter(List<String> data, Context context, int cellMainMenuLayoutId) {
        super(data, context);
        this.cellMainMenuLayoutId = cellMainMenuLayoutId;
    }

    @Override
    public void update(View view, int position) {
        ImageView imageView = (ImageView) view;
        Picasso.with(context).load(getItem(position)).into(imageView);
    }

    @Override
    public int getCellResourceId(int positon) {
        return cellMainMenuLayoutId;
    }
}
