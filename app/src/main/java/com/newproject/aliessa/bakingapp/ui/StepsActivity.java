package com.newproject.aliessa.bakingapp.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.newproject.aliessa.bakingapp.R;


public class StepsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_steps);
        if (savedInstanceState == null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (MainActivity.tablet_on) {
                StepsActivityFragment stepsActivityFragment= new StepsActivityFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.steps_fragmint_layout, stepsActivityFragment)
                        .commit();

                DetailsActivityFragment detailsActivityFragment = new DetailsActivityFragment();
                fragmentManager.beginTransaction()
                        .replace(R.id.steps_details_layout, detailsActivityFragment)
                        .commit();
            } else {
                StepsActivityFragment stepsActivityFragment =new StepsActivityFragment();
                fragmentManager.beginTransaction()
                        .add(R.id.steps_fragmint_layout, stepsActivityFragment)
                        .commit();
            }
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
               onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
