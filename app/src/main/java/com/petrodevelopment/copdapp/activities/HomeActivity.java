package com.petrodevelopment.copdapp.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.petrodevelopment.copdapp.MainApplication;
import com.petrodevelopment.copdapp.NavigationDrawerFragment;
import com.petrodevelopment.copdapp.R;
import com.petrodevelopment.copdapp.fragments.AppointmentsFragment;
import com.petrodevelopment.copdapp.fragments.CaregiversFragment;
import com.petrodevelopment.copdapp.fragments.MedicationsFragment;
import com.petrodevelopment.copdapp.fragments.ProviderFragment;
import com.petrodevelopment.copdapp.fragments.SectionFragment;
import com.petrodevelopment.copdapp.record.RecordActivity;
import com.petrodevelopment.copdapp.record.RecordAppointmentActivity;
import com.petrodevelopment.copdapp.util.U;


public class HomeActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, SectionFragment.SectionFragmentParent {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private SearchView mSearchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initDrawer();
//        initSpinner();
        mTitle = getTitle();
        startRecordAppointmentActivity("0");
//        startAddAppointmentActivity();
//        Retrofit.initialize();
//        startRecordActivity();
    }

//    private void initSpinner() {
//        Spinner spinner = (Spinner) findViewById(R.id.sort_by_spinner);
//// Create an ArrayAdapter using the string array and a default spinner layout
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.planets_array, R.layout.dropdown_textview);
//// Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//// Apply the adapter to the spinner
//
//        spinner.setAdapter(adapter);
//    }

    public void startRecordAppointmentActivity(String appointmentId) {
        Intent intent = new Intent(this, RecordAppointmentActivity.class);
        startActivity(intent);
    }

    public void startRecordActivity (String appointmentId){
        Intent intent = new Intent(this, RecordActivity.class);
        startActivity(intent);
    }

    /*
     *  Added by Tom 22/05/2015, to access AddEditAppointmentActivity
     */
    public void startAddAppointmentActivity(String appointmentId) {

        Intent intent = new Intent(this, AddEditAppointmentActivity.class);
        intent.putExtra(MainApplication.APPOINTMENT_ID_EXTRA, appointmentId);
        startActivity(intent);
    }

    /**
     * Setup drawer
     */
    private void initDrawer() {

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    /**
     * Update the main content by replacing fragments
     * @param position
     */
    @Override
    public void onNavigationDrawerItemSelected(int position) {
        if (position == 4) startRecordAppointmentActivity("test");
        else {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, createSectionFragment(position))
                    .commit();
        }
    }

    private Fragment createSectionFragment(int position) {
        switch (position) {
            case 0:
                return AppointmentsFragment.newInstance(0, getString(R.string.title_appointments_fragment));
            case 1:
                return ProviderFragment.newInstance(1, getString(R.string.title_providers_fragment));
            case 2:
                return MedicationsFragment.newInstance(2, getString(R.string.title_medications_fragment));
            default:
                return CaregiversFragment.newInstance(3, getString(R.string.title_caregivers_fragment));
        }
    }

    @Override
    public void onSectionFragmentAttached(int sectionNumber, String title) {
        mTitle = title;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        //actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.home_menu, menu);
            setupSearch(menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    private void setupSearch(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchItem.getActionView();
        //TODO make this dependent on the current fragment and delegate search to fragments in the text change callback
        mSearchView.setQueryHint(getString(R.string.search_appointments));
        mSearchView.findViewById(R.id.search_plate).setBackgroundColor(Color.TRANSPARENT);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                U.log(this, "query text submit: " + s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                U.log(this, "query text change: " + s);
                return false;
            }
        });
        mSearchView.setSubmitButtonEnabled(false);

    }
}
