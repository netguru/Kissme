package com.icecreamhappens.secureprefs

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties.PURPOSE_ENCRYPT
import android.security.keystore.KeyProperties.PURPOSE_DECRYPT
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey


/**
 * [com.ironz.binaryprefs.Preferences] provider.
 * Provides proper instance of [com.ironz.binaryprefs.Preferences] based on [name] parameter
 */
@SuppressLint("StaticFieldLeak")
internal object AndroidStorageProvider {

    internal const val DEFAULT_PREFERENCES_NAME = "default"

    /**
     * Application [Context] provided by ContentProvider [StorageInitializer]
     */
    internal lateinit var appContext: Context

    /**
     * Whether [name] parameter is specified, it returns named [com.ironz.binaryprefs.Preferences] instance.
     * In other cases, it uses default instance.
     */
    internal fun preferences(name: String? = null): SharedPreferences {

        val masterKey = MasterKey.Builder(appContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            appContext,
            name ?: DEFAULT_PREFERENCES_NAME,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

}
