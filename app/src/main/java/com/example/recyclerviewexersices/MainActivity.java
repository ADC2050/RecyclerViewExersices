package com.example.recyclerviewexersices;// MainActivity.java

import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements BottomNavigationFragment.OnNavigationItemSelectedListener {

    androidx.appcompat.widget.Toolbar toolbar;

    public static ArrayList<Person> personList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load the initial fragment
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }

        intitViews();
    }

    private void intitViews() {
        toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
    }

    public void onNavigationItemSelected(int itemId) {
        if (itemId == R.id.nav_home) {
            loadFragment(new HomeFragment());
        } else if (itemId == R.id.nav_list) {
            loadFragment(new StudentListFragment());
        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment_container, fragment)
                .commit();
    }
}