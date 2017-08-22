package com.newproject.aliessa.bakingapp.ui;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.newproject.aliessa.bakingapp.R;

public class MainActivity extends AppCompatActivity {
//Recipe
    public static boolean tablet_on = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState== null){
            if (findViewById(R.id.main_fragmint_tablet)!=null){
                MainActivityFragment activityfragment = new MainActivityFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.main_fragmint_tablet,activityfragment).commit();
            }else {
                tablet_on=false;
                MainActivityFragment activityfragment = new MainActivityFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().add(R.id.main_fragmint_layout,activityfragment).commit();


            }
        }


    }
}
