package abbesolo.com.go4Lunch.settings.activity.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreferenceCompat;

import java.util.Objects;

import abbesolo.com.go4Lunch.R;

//
// Created by Hounsa Romuald on 2020-03-17.
// Copyright (c) 2020 abbesolo.com.go4Lunch.settings.activity.fragments. All rights reserved.
//
public class NotificationsFragment extends PreferenceFragmentCompat {

    //FIELDS
    private static final String PREF_NOTIFICATION_KEY = "notification_firebase";

    private SharedPreferences.OnSharedPreferenceChangeListener mOnSharedPreferenceChangeListener;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource (R.xml.notifications_settings, rootKey);

        mOnSharedPreferenceChangeListener = (sharedPreferences, key) -> {
            if (key.equals (PREF_NOTIFICATION_KEY)) {
                SwitchPreferenceCompat switchPreference = findPreference (key);
                Objects.requireNonNull (switchPreference)
                        .setSwitchTextOn ("Notifications ok");
            }
        };

    }

    @Override
    public void onResume() {
        super.onResume ();

        getPreferenceScreen ()
                .getSharedPreferences ()
                .registerOnSharedPreferenceChangeListener (mOnSharedPreferenceChangeListener);

        SwitchPreferenceCompat switchPreferenceCompat = findPreference (PREF_NOTIFICATION_KEY);
        Objects.requireNonNull (switchPreferenceCompat).setSwitchTextOn ("Notifications ok");
    }

    @Override
    public void onPause() {
        super.onPause ();
        getPreferenceScreen ()
                .getSharedPreferences ()
                .unregisterOnSharedPreferenceChangeListener (mOnSharedPreferenceChangeListener);
    }
}
