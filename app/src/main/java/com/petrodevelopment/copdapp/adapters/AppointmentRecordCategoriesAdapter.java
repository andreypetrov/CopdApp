package com.petrodevelopment.copdapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.model.Appointment;
import com.petrodevelopment.copdapp.model.AppointmentRecord;
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
    private Appointment appointment;

    public AppointmentRecordCategoriesAdapter(List<AppointmentRecordCategoryVm> data, Appointment appointment, Context context, int categoryLayoutId, int subcategoryLayoutId) {
        super(data, context);
        this.appointment = appointment;
        this.categoryLayoutId = categoryLayoutId;
        this.subcategoryLayoutId = subcategoryLayoutId;
    }

    @Override
    public void update(View view, int position) {
        if (getItemViewType(position) == 1) {
//            updateListener(view, position);
//            updateAppointmentData(view, position);
        }
        AppointmentRecordCategoryVm category = getItem(position);

        TextView nameView = (TextView) view.findViewById(R.id.name);
        nameView.setText(category.name);

        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        imageView.setImageResource(context.getResources().getIdentifier(category.image , "drawable", context.getPackageName()));
    }


    /**
     * Depending on the position of the current item in the list, get the corresponding view to show
     * @param position
     * @return
     */
    public AppointmentRecord getFromIndex(int position) {
        switch (position) {
            case 1:
                return appointment.severity;
            case 2:
                return appointment.assessment;
            case 4:
                return appointment.medications;
            case 5:
                return appointment.tests;
            case 6:
                return appointment.lifeStyleChanges;
            case 7:
                return appointment.futureReferrals;
            default:
                return null;
        }
    }

    private void updateAppointmentData(View view, int position) {
        AppointmentRecord record = getFromIndex(position);
        final View bodyView = view.findViewById(R.id.cell_body);
        updateText(bodyView, record);
        updateImages(bodyView, record);
    }

    private void updateText(View view, AppointmentRecord record) {
        if (record!= null && record.note != null) {
            TextView textView = (TextView) view.findViewById(R.id.note);
            textView.setText(record.note);
        };
    }

    /**
     * For every appointment record update the images gallery
     * @param view
     * @param record
     */
    private void updateImages(View view, final AppointmentRecord record) {
        if (record != null && record.imageUrls != null) {
            GridView gridView = (GridView) view.findViewById(R.id.gallery);
            ImageAdapter imageAdapter = new ImageAdapter(record.imageUrls, context, R.layout.cell_grid_gallery);
            gridView.setAdapter(imageAdapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    Toast.makeText(context, "" + position + ", record note: " + record.note,
                            Toast.LENGTH_SHORT).show();
                }
            });
        }
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
