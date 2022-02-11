package com.example.islamicapp.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.islamicapp.R;
import com.example.islamicapp.adapter.SurahAdapter;
import com.example.islamicapp.common.GPSTracker;
import com.example.islamicapp.databinding.FragmentHomeBinding;
import com.example.islamicapp.viewmodel.PrayerTimeViewModel;
import com.example.islamicapp.viewmodel.SurahViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class HomeFragment extends Fragment {

    GPSTracker gps;
    PrayerTimeViewModel prayerTimeViewModel;
    FragmentHomeBinding binding;
    String day;

    ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                } else {
                    new MaterialAlertDialogBuilder(getActivity())
                            .setTitle(getResources().getString(R.string.perm_dialog_title))
                            .setMessage(getResources().getString(R.string.perm_dialog_body))
                            .setNegativeButton(getResources().getString(R.string.negative_btn), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            }).setPositiveButton(getResources().getString(R.string.positive_btn), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // This is Case 3. Request for permission here
                            //if user denied permission twice android will block request permession thi from android11
                            if (android.os.Build.VERSION.SDK_INT >= 11) {
                                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                                Toast.makeText(getContext(), getResources().getString(R.string.deny_perm_twice)
                                        , Toast.LENGTH_LONG).show();
                            }

                        }
                    })
                            .show();

                }
            });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(getLayoutInflater());


        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (ContextCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            fetch_GPS();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
            } else {
                fetch_GPS();
            }

        }
    }

    public void fetch_GPS() {
        gps = new GPSTracker(getContext());
        if (gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();

            Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH) + 1;
            day = "0" + c.get(Calendar.DAY_OF_MONTH);

            Log.d("TAG", "address: " + latitude);
            getData(String.valueOf(latitude), String.valueOf(longitude), 5, month < 10 ? "0" + month : "" + month, 2022);
            Log.d("TAG", "GPS is on");
            Log.d("TAG", "fetch_GPS: " + latitude + " " + longitude);
            Log.d("TAG", "fetch_GPS: " + year + " " + month + " " + day);
        } else {
            gps.showSettingsAlert();
        }
    }

    private void getData(String lat, String lng, int method, String month, int year) {
        prayerTimeViewModel = new ViewModelProvider(this).get(PrayerTimeViewModel.class);
        prayerTimeViewModel.getPrayerTime(lat, lng, method, month, year).observe(getActivity(), prayerTimeResponse -> {
            Log.d("TAG", "getData: " + prayerTimeResponse.getData().get(0).getDate().getReadable());
            for (int i = 0; i < prayerTimeResponse.getData().size(); ++i) {
                if (day.equals(prayerTimeResponse.getData().get(i).getDate().getGregorian().getDay())) {

                    String fagr = prayerTimeResponse.getData().get(i).getTimings().getFajr().substring(0, 5);
                    String dhohr = prayerTimeResponse.getData().get(i).getTimings().getDhuhr().substring(0, 5);
                    String asr = prayerTimeResponse.getData().get(i).getTimings().getAsr().substring(0, 5);
                    String maghrb = prayerTimeResponse.getData().get(i).getTimings().getMaghrib().substring(0, 5);
                    String isha = prayerTimeResponse.getData().get(i).getTimings().getIsha().substring(0, 5);

                    binding.fagr.setText(fagr);
                    binding.dhohr.setText(dhohr);
                    binding.aser.setText(asr);
                    binding.maghreb.setText(maghrb);
                    binding.isha.setText(isha);

                    binding.gregorianDate.setText(prayerTimeResponse.getData().get(i).getDate().getReadable());

                }
            }

        });
    }
}