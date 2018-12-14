package com.netguru.multiplatformstorage

import android.annotation.SuppressLint
import android.content.Context
import com.ironz.binaryprefs.BinaryPreferencesBuilder
import com.ironz.binaryprefs.Preferences
import com.ironz.binaryprefs.encryption.AesValueEncryption
import com.ironz.binaryprefs.encryption.XorKeyEncryption

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
        // TODO keep all the keys in Keystore
        val builder = BinaryPreferencesBuilder(appContext)
            .keyEncryption(XorKeyEncryption("16 bytes secret key".toByteArray()))
            .valueEncryption(
                AesValueEncryption(
                    "16 bytes secret key".toByteArray(),
                    "16 bytes initial vector".toByteArray()
                )
            )

        name?.let {
            builder.name(it)
        }

        return builder.build()
    }
}