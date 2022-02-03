package com.example.islamicapp.ui;

import static android.content.Context.MODE_PRIVATE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import com.example.islamicapp.R;
import com.example.islamicapp.Variables;
import com.example.islamicapp.common.Compass;
import com.example.islamicapp.common.GPSTracker;
import com.example.islamicapp.databinding.FragmentQiblaBinding;
import com.example.islamicapp.model.Azkar;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class QiblaFragment extends Fragment {

    private static final String TAG = "QiblaFinder";
    private Compass compass;
    private float currentAzimuth;
    SharedPreferences prefs;
    GPSTracker gps;

    FragmentQiblaBinding binding;
    //on request permission result
    ActivityResultLauncher<String> requestPermissionLauncher = requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // Permission is granted. Continue the action or workflow in your
                    // app.
                    SaveBoolean("permission_granted", true);
                    getBearing();
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
                            if (android.os.Build.VERSION.SDK_INT>=11){
                                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                                Toast.makeText(getContext(),getResources().getString(R.string.deny_perm_twice)
                                ,Toast.LENGTH_LONG).show();
                            }

                        }
                    })
                            .show();

                }
            });
    //-------------------------------------------------------------


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentQiblaBinding.inflate(getLayoutInflater());


        prefs = getActivity().getSharedPreferences("qibla", MODE_PRIVATE);
        gps = new GPSTracker(getContext());
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        binding.mainImageQibla.setVisibility(View.INVISIBLE);
        binding.mainImageQibla.setVisibility(View.GONE);

        // requestPermission();
        setupCompass();
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "start compass");
        if (compass != null) {
            compass.start();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (compass != null) {
            compass.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (compass != null) {
            compass.stop();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (compass != null) {
            compass.stop();
        }
    }

    private void setupCompass() {

        Boolean permission_granted = GetBoolean("permission_granted");
        if (permission_granted && ContextCompat.checkSelfPermission(
                getContext(), Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            getBearing();
        } else {
            binding.textUp.setText("");
            binding.textDown.setText("permission not granted");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                //getActivity().requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
            } else {
                getBearing();
            }

        }
        compass = new Compass(getContext());
        Compass.CompassListener cl = new Compass.CompassListener() {
            @Override
            public void onNewAzimuth(float azimuth) {
                adjustGambarDial(azimuth);
                adjustArrowQiblat(azimuth);
            }
        };
        compass.setListener(cl);
    }

    public void adjustGambarDial(float azimuth) {
        Animation an = new RotateAnimation(-currentAzimuth, -azimuth, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        currentAzimuth = (azimuth);
        an.setDuration(500);
        an.setRepeatCount(0);
        an.setFillAfter(true);
        binding.mainImageDial.startAnimation(an);
    }

    public void adjustArrowQiblat(float azimuth) {
        float QiblaDegree = GetFloat("QiblaDegree");
        Animation an = new RotateAnimation(-(currentAzimuth) + QiblaDegree, -azimuth, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        currentAzimuth = (azimuth);
        an.setDuration(500);
        an.setRepeatCount(0);
        an.setFillAfter(true);
        binding.mainImageQibla.startAnimation(an);
        if (QiblaDegree > 0) {
            binding.mainImageQibla.setVisibility(View.VISIBLE);
        } else {
            binding.mainImageQibla.setVisibility(View.INVISIBLE);
            binding.mainImageQibla.setVisibility(View.GONE);
        }
    }

    @SuppressLint("MissingPermission")
    public void getBearing() {
        // Get the location manager
        fetch_GPS();
    }

    public void fetch_GPS() {
        double result = 0;
        gps = new GPSTracker(getContext());
        if (gps.canGetLocation()) {
            double latitude = gps.getLatitude();
            double longitude = gps.getLongitude();
            binding.textDown.setText(getResources().getString(R.string.qibla_down_txt) + "\n" + "lat" + ": " + latitude + " long" + ": " + longitude);
            Log.e("TAG", "GPS is on");
            double lat_saya = gps.getLatitude();
            double lon_saya = gps.getLongitude();
            if (lat_saya < 0.001 && lon_saya < 0.001) {
                binding.mainImageQibla.setVisibility(View.INVISIBLE);
                binding.mainImageQibla.setVisibility(View.GONE);
                binding.textUp.setText("");
                binding.textUp.setVisibility(View.GONE);
                binding.textDown.setText(getResources().getString(R.string.location_ready));
            } else {
                double longitude2 = 39.826209; // Kaabah Position https://www.latlong.net/place/kaaba-mecca-saudi-arabia-12639.html
                double longitude1 = lon_saya;
                double latitude2 = Math.toRadians(21.422507); // Kaabah Position https://www.latlong.net/place/kaaba-mecca-saudi-arabia-12639.html
                double latitude1 = Math.toRadians(lat_saya);
                double longDiff = Math.toRadians(longitude2 - longitude1);
                double y = Math.sin(longDiff) * Math.cos(latitude2);
                double x = Math.cos(latitude1) * Math.sin(latitude2) - Math.sin(latitude1) * Math.cos(latitude2) * Math.cos(longDiff);
                result = (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
                float result2 = (float) result;
                SaveFloat("QiblaDegree", result2);
                binding.textUp.setText(getResources().getString(R.string.qibla_up_txt)+ result2 +getResources().getString(R.string.north));
                binding.textUp.setVisibility(View.VISIBLE);
                binding.mainImageQibla.setVisibility(View.VISIBLE);

            }
        } else {
            gps.showSettingsAlert();
            binding.mainImageQibla.setVisibility(View.INVISIBLE);
            binding.mainImageQibla.setVisibility(View.GONE);
            binding.textUp.setText("");
            binding.textUp.setVisibility(View.GONE);
            binding.textDown.setText(getResources().getString(R.string.enable_gps));
        }
    }

    public void SaveBoolean(String key, Boolean bb) {
        SharedPreferences.Editor edit = prefs.edit();
        edit.putBoolean(key, bb);
        edit.apply();
    }

    public Boolean GetBoolean(String key) {
        Boolean result = prefs.getBoolean(key, false);
        return result;
    }

    public void SaveFloat(String key, Float ff) {
        SharedPreferences.Editor edit = prefs.edit();
        edit.putFloat(key, ff);
        edit.apply();
    }

    public Float GetFloat(String key) {
        Float ff = prefs.getFloat(key, 0);
        return ff;
    }



}