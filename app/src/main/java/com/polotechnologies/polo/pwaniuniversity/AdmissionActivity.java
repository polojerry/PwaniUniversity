package com.polotechnologies.polo.pwaniuniversity;

import android.content.Intent;
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

import com.polotechnologies.polo.pwaniuniversity.Fragments.StudentID;

public class AdmissionActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_admission_view);
        navigationView.setNavigationItemSelectedListener(this);
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

        switch (id){
            case R.id.nav_admission_dashboard:
                Intent startDashboard = new Intent(AdmissionActivity.this, MainActivity.class);
                startActivity(startDashboard);
                finish();
                break;

            case R.id.nav_admission_id_card:
                fragment= new StudentID();
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

        if(fragment != null){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.admissionContentMain,fragment);
            fragmentTransaction.commit();
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_admission_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
