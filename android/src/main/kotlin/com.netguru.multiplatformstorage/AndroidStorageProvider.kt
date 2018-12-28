package com.netguru.multiplatformstorage

import android.annotation.SuppressLint
import android.content.Context
import android.preference.PreferenceManager
import com.ironz.binaryprefs.BinaryPreferencesBuilder
import com.ironz.binaryprefs.Preferences


/**
 * [com.ironz.binaryprefs.Preferences] provider.
 * Provides proper instance of [com.ironz.binaryprefs.Preferences] based on [name] parameter
 */
@SuppressLint("StaticFieldLeak")
internal object AndroidStorageProvider {

    /**
     * Application [Context] provided by [StorageInitializer],
     * needed for proper [com.ironz.binaryprefs.Preferences] initialization
     */
    internal lateinit var appContext: Context

    /**
     * Whether [name] parameter is specified, it returns named [com.ironz.binaryprefs.Preferences] instance.
     * In other cases, it uses default instance.
     */
    internal fun preferences(name: String? = null): Preferences {

        val sharedPrefs = if (name == null) {
            PreferenceManager.getDefaultSharedPreferences(appContext)
        } else {
            appContext.getSharedPreferences(name, Context.MODE_PRIVATE)
        }

        val builder =
            BinaryPreferencesBuilder(appContext).migrateFrom(sharedPrefs)
                .apply {
                    name(name)
                }

        return builder.build()
    }
}