package com.netguru.multiplatformstorage

import android.annotation.SuppressLint
import android.content.Context
import android.preference.PreferenceManager

/**
 * [android.content.SharedPreferences] provider.
 * Provides proper instance of [android.content.SharedPreferences] based on [name] parameter
 */
@SuppressLint("StaticFieldLeak")
internal object SharedPreferencesProvider {

    /**
     * Application [Context] provided by [SharedPreferencesInitializer],
     * needed for proper [android.content.SharedPreferences] initialization
     */
    internal lateinit var appContext: Context

    /**
     * Whether [name] parameter is specified, it returns named [android.content.SharedPreferences] instance.
     * In other cases, it uses default instance returned by [PreferenceManager.getDefaultSharedPreferences].
     */
    internal fun preferences(name: String? = null) = if (name == null) {
        PreferenceManager.getDefaultSharedPreferences(appContext)
    } else {
        appContext.getSharedPreferences(name, Context.MODE_PRIVATE)
    }
}
