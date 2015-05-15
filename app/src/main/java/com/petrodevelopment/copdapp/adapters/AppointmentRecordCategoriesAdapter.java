package com.petrodevelopment.copdapp.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.petrodevelopment.copdapp.viewmodel.AppointmentRecordCategoryVm;

import java.util.List;

/**
 * Every category has subcategories
 * Created by andrey on 15/05/2015.
 */
public class AppointmentRecordCategoriesAdapter extends BaseAdapter {
    private List<AppointmentRecordCategoryVm> mCategories;
    private Context mContext;

    public AppointmentRecordCategoriesAdapter (Context context, List<AppointmentRecordCategoryVm> categories) {
        mCategories = categories;
        mContext = context;
    }

    @Override
    public int getCount() {
        int count = 0;
        for (AppointmentRecordCategoryVm category : mCategories) {
            count++;
            for (AppointmentRecordCategoryVm subcategory : category.subcategories) {
                count++;
            }
        }
        return count;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }
}
