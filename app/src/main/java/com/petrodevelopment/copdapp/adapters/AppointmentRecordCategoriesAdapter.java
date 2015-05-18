package com.petrodevelopment.copdapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
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
        if (getItemViewType(position) == 1) updateListener(view, position);
        AppointmentRecordCategoryVm category = getItem(position);

        TextView nameView = (TextView) view.findViewById(R.id.name);
        nameView.setText(category.name);

        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        imageView.setImageResource(context.getResources().getIdentifier(category.image , "drawable", context.getPackageName()));
    }

    private void updateListener(View view, int position) {

        View headerView = view.findViewById(R.id.cell_header);
        final View bodyView = view.findViewById(R.id.cell_body);
        final EditText noteView=  (EditText) bodyView.findViewById(R.id.note);

        headerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (bodyView.getVisibility() == View.GONE) {
                    bodyView.setVisibility(View.VISIBLE);
//                    noteView.requestFocus();
//                    noteView.setSelection(noteView.getText().length());
//                    inputMethodManager.showSoftInput(noteView, InputMethodManager.SHOW_IMPLICIT);
                }
                else {
                    bodyView.setVisibility(View.GONE);
                    inputMethodManager.hideSoftInputFromWindow(noteView.getWindowToken(), 0);
                }
                
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
