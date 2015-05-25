package com.petrodevelopment.copdapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.petrodevelopment.copdapp.util.U;

import java.util.List;

public abstract class GenericAdapter<Data> extends BaseAdapter {
    protected List<Data> data;
    protected Context context;

    public GenericAdapter(List<Data> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        U.log(this, "getCount: " + data.size());
        return data.size();
    }

    @Override
    public Data getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(getCellResourceId(position), parent, false);
            convertView.setTag(createHolder(convertView));
        }
        update(convertView, position);
        return convertView;
    }

    public abstract void update(View view, int position);

    public abstract int getCellResourceId(int positon);

    /**
     * To be overridden only in views with more than ten cells, to improve performance.
     * The object returned by createHolder can be used within the update method with view.getTag()
     *
     * @param view
     * @return
     */
    public Object createHolder(View view){
        return null;
    };

}