package com.netguru.multiplatformstorage

import android.content.SharedPreferences

actual class MultiPlatformStorage actual constructor(name: String?) {

    private val preferences: SharedPreferences by lazy {
        SharedPreferencesProvider.preferences(name)
    }

    actual fun getAll(): Map<String, *> = preferences.all

    actual fun getString(key: String, defaultValue: String?): String? = preferences.getString(key, defaultValue)

    actual fun putString(key: String, value: String) = preferences.edit { putString(key, value) }

    actual fun getInt(key: String, defaultValue: Int): Int = preferences.getInt(key, defaultValue)

    actual fun putInt(key: String, value: Int) = preferences.edit { putInt(key, value) }

    actual fun getLong(key: String, defaultValue: Long): Long = preferences.getLong(key, defaultValue)

    actual fun putLong(key: String, value: Long) = preferences.edit { putLong(key, value) }

    actual fun getFloat(key: String, defaultValue: Float): Float = preferences.getFloat(key, defaultValue)

    actual fun putFloat(key: String, value: Float) = preferences.edit { putFloat(key, value) }

    actual fun getBoolean(key: String, defaultValue: Boolean): Boolean = preferences.getBoolean(key, defaultValue)

    actual fun putBoolean(key: String, value: Boolean) = preferences.edit { putBoolean(key, value) }

    actual fun contains(key: String): Boolean = preferences.contains(key)
}

private inline fun SharedPreferences.edit(action: SharedPreferences.Editor.() -> Unit) = with(edit()) {
    action()
    apply()
}
