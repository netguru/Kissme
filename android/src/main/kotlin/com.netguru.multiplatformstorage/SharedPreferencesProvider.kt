package com.netguru.multiplatformstorage

import android.annotation.SuppressLint
import android.content.Context
import android.preference.PreferenceManager

@SuppressLint("StaticFieldLeak")
object SharedPreferencesProvider {

    internal lateinit var appContext: Context

    internal fun preferences(name: String? = null) = if (name == null) {
        PreferenceManager.getDefaultSharedPreferences(appContext)
    } else {
        appContext.getSharedPreferences(name, Context.MODE_PRIVATE)
    }
}
