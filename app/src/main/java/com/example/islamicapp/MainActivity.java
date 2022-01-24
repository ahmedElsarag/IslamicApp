package com.example.islamicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.islamicapp.common.NavigationHost;
import com.example.islamicapp.ui.SurahFragment;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity implements NavigationHost {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.product_grid).setBackgroundResource(R.drawable.shr_product_grid_background_shape);
        setUpToolbar();

        MaterialButton btn = findViewById(R.id.pack_drop);

        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container, SurahFragment.class, null)
                .commit();

    }

    @Override
    public void navigateTo(Fragment fragment, boolean addToBackstack) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container,fragment);
        if (addToBackstack)
            transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setUpToolbar() {
        Toolbar toolbar = findViewById(R.id.app_bar);
            setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new NavigationIconClickListener(this, findViewById(R.id.product_grid)));
        }
}