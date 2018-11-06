package com.netguru.multiplatformstorage

import android.content.Context
import android.preference.PreferenceManager
import java.lang.ref.WeakReference

object SharedPreferencesProvider {

    internal var _context: WeakReference<Context>? = null

    private val context: Context
        get() = _context!!.get() ?: throw IllegalStateException("Context should be initialized")


    internal fun preferences(name: String? = null) = if (name == null) {
        PreferenceManager.getDefaultSharedPreferences(context)
    } else {
        context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }
}
