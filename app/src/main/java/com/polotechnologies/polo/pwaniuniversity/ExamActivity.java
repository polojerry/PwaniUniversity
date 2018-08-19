package com.polotechnologies.polo.pwaniuniversity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.polotechnologies.polo.pwaniuniversity.Fragments.StudentExamResult;
import com.polotechnologies.polo.pwaniuniversity.Fragments.StudentExamTranscript;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ExamActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static String admissionNumber;
    public static String passportUrl;

    public static FragmentTransaction fragmentTransaction;
    public static Fragment startFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        Toolbar toolbar = (Toolbar) findViewById(R.id.exam_toolbar);
        setSupportActionBar(toolbar);

        startFragment = new StudentExamResult();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.examContentMain, startFragment);
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.exam_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Fetching admissionNumber from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences(Config.SHARED_PREF_NAME, Context.MODE_PRIVATE);
        admissionNumber = sharedPreferences.getString(Config.ADMISSION_SHARED_PREF, "Not Available");
        passportUrl = sharedPreferences.getString(Config.IMAGE_URL_SHARED_PREF, "Not Available");

        //Declaring Navigation
        NavigationView navigationView = (NavigationView) findViewById(R.id.exam_nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Fetching the Header
        View navigationHeader = navigationView.getHeaderView(0);

        //Declaring the header views
        CircleImageView passport =
                (CircleImageView) navigationHeader.findViewById(R.id.image_nav_exam_profile_pic);
        TextView admission =
                (TextView) navigationHeader.findViewById(R.id.text_nav_exam_reg_number);

        //Assigning the header views
        Picasso.get()
                .load(passportUrl)
                .into(passport);

        admission.setText(admissionNumber);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.exam_drawer_layout);
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
            case R.id.nav_exam_result:
                if (!startFragment.isVisible()) {
                    fragment = new StudentExamResult();
                }
            break;
            case R.id.nav_exam_transcript:
                fragment = new StudentExamTranscript();
            break;    
        }

        if (fragment != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.examContentMain, fragment);
            fragmentTransaction.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.exam_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
