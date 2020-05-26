package abbesolo.com.go4Lunch.settings.activity.fragments;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import abbesolo.com.go4Lunch.R;

//
// Created by Hounsa Romuald on 2020-03-17.
// Copyright (c) 2020 abbesolo.com.go4Lunch.settings.activity.fragments. All rights reserved.
//
public class SettingsHeaders extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource (R.xml.settings_headers, rootKey);
    }
}
