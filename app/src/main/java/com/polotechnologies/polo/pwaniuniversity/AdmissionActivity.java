package com.polotechnologies.polo.pwaniuniversity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.polotechnologies.polo.pwaniuniversity.Fragments.StudentID;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdmissionActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String admissionNumber;
    public static String passportUrl;

    public static FragmentTransaction fragmentTransaction;
    public static Fragment startFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission);
        Toolbar toolbar = (Toolbar) findViewById(R.id.admission_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_admission_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        startFragment = new StudentID();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.admissionContentMain, startFragment);
        fragmentTransaction.commit();

        //Fetching admissionNumber from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        admissionNumber = sharedPreferences.getString(Config.ADMISSION_SHARED_PREF, "Not Available");
        passportUrl = sharedPreferences.getString(Config.IMAGE_URL_SHARED_PREF, "Not Available");

        //Declaring the Navigation
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_admission_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Fetching the Header
        View navigationHeader = navigationView.getHeaderView(0);

        //Declaring the header views
        CircleImageView passport =
                (CircleImageView) navigationHeader.findViewById(R.id.image_nav_admission_profile_pic);
        TextView admission =
                (TextView) navigationHeader.findViewById(R.id.text_nav_admission_reg_number);

        //Assigning the header views
        Picasso.get()
                .load(passportUrl)
                .into(passport);

        admission.setText(admissionNumber);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_admission_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;

        switch (id) {

            case R.id.nav_admission_id_card:
                if (!startFragment.isVisible()) {
                    fragment = new StudentID();
                }
                break;
            case R.id.nav_admission_student_passport:

                break;

            case R.id.nav_admission_high_school_certificate:

                break;

            case R.id.nav_admission_leaving_certificate:

                break;

            case R.id.nav_admission_national_id_birth_certificate:

                break;

            case R.id.nav_admission_signed_acceptance_letter:

                break;
        }

        if (fragment != null) {
            fragmentTransaction.replace(R.id.admissionContentMain, fragment);
            fragmentTransaction.commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_admission_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
