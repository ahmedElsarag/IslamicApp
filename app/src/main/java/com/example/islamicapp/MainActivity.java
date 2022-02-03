package com.example.islamicapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.example.islamicapp.common.NavigationHost;
import com.example.islamicapp.common.NavigationIconClickListener;
import com.example.islamicapp.databinding.ActivityMainBinding;
import com.example.islamicapp.ui.AzkarFragment;
import com.example.islamicapp.ui.HomeFragment;
import com.example.islamicapp.ui.QiblaFragment;
import com.example.islamicapp.ui.SurahFragment;

import java.util.Locale;
import java.util.TooManyListenersException;

public class MainActivity extends AppCompatActivity implements NavigationHost, View.OnClickListener {

    AzkarFragment azkarFragment;
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.productGrid.setBackgroundResource(R.drawable.shr_product_grid_background_shape);
        setUpToolbar();
        azkarFragment = new AzkarFragment();

        binding.backDrop.azkar.setOnClickListener(this);
        binding.backDrop.quran.setOnClickListener(this);
        binding.backDrop.qibla.setOnClickListener(this);
        binding.backDrop.home.setOnClickListener(this);
        binding.backDrop.language.setOnClickListener(this);


        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.fragment_container, HomeFragment.class, null)
                .commit();

    }

    @Override
    public void navigateTo(Fragment fragment, boolean addToBackstack) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment);
        if (addToBackstack)
            transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setUpToolbar() {
        setSupportActionBar(binding.appBar);

        binding.appBar.setNavigationOnClickListener(new NavigationIconClickListener(this, findViewById(R.id.product_grid)));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            azkarFragment.myOnKeyDown(keyCode, MainActivity.this);
            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.quran:
                ((NavigationHost) (MainActivity.this)).navigateTo(new SurahFragment(), true);
                break;
            case R.id.azkar:
                ((NavigationHost) (MainActivity.this)).navigateTo(new AzkarFragment(), true);
                break;
            case R.id.qibla:
                ((NavigationHost) (MainActivity.this)).navigateTo(new QiblaFragment(), true);
                break;
            case R.id.home:
                ((NavigationHost) (MainActivity.this)).navigateTo(new HomeFragment(), true);
                break;
            case R.id.language:
                if (binding.backDrop.language.getText().equals("AR")) {
                    Toast.makeText(MainActivity.this, "clicked", Toast.LENGTH_LONG).show();
                    setLocale("ar");
                } else {
                    setLocale("en");
                }
                break;

        }
    }

    private void setLocale(String language) {
        //Initialize resources
        Resources resources = getResources();
        //Initialize metrics
        DisplayMetrics metrics = resources.getDisplayMetrics();
        //Initialize configuration
        Configuration configuration = resources.getConfiguration();
        if (language.equals("ar"))
            configuration.setLayoutDirection(new Locale("ar"));
        else
            configuration.setLayoutDirection(new Locale("en"));
        //Initialize locale
        configuration.locale = new Locale(language);
        //Update configuration
        resources.updateConfiguration(configuration, metrics);
        //Notify configuration
        onConfigurationChanged(configuration);
        Intent refresh = new Intent(this, MainActivity.class);
        finish();
        startActivity(refresh);
    }
}