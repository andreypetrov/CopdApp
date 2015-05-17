package com.petrodevelopment.copdapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.util.U;
import com.petrodevelopment.copdapp.viewmodel.AppointmentListVm;
import com.petrodevelopment.copdapp.viewmodel.AppointmentRecordCategoryVm;

import java.util.List;

/**
 * Every category has subcategories
 * Created by andrey on 15/05/2015.
 */
public class AppointmentRecordCategoriesAdapter extends GenericAdapter<AppointmentRecordCategoryVm> {
    public static final String TYPE_CATEGORY = "category";
    public static final String TYPE_SUBCATEGORY = "subcategory";

    private int categoryLayoutId;
    private int subcategoryLayoutId;

    public AppointmentRecordCategoriesAdapter(List<AppointmentRecordCategoryVm> data, Context context, int categoryLayoutId, int subcategoryLayoutId) {
        super(data, context);
        this.categoryLayoutId = categoryLayoutId;
        this.subcategoryLayoutId = subcategoryLayoutId;
    }

    @Override
    public void update(View view, int position) {
        updateListener(view, position);
        AppointmentRecordCategoryVm category = getItem(position);

        TextView nameView = (TextView) view.findViewById(R.id.name);
        nameView.setText(category.name);

        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        imageView.setImageResource(R.drawable.ic_alarm_add_grey600_48dp);
    }

    private void updateListener(View view, int position) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                U.log(this, "view clicked!");
            }
        });
    }

    @Override
    public int getCellResourceId(int position) {
        if (getItemViewType(position) == 0) return categoryLayoutId;
        else return subcategoryLayoutId;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (getItem(position).type.equals(TYPE_CATEGORY)) return 0;
        else return 1; //subcategory
    }
}
