package com.icecreamhappens.secureprefs

import android.content.ContentProvider
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri

/**
 * [ContentProvider] which provides Application [android.content.Context] for [AndroidStorageProvider].
 * Context will be provided automatically when Application starts.
 */
internal class StorageInitializer : ContentProvider() {

    override fun onCreate(): Boolean {
        AndroidStorageProvider.appContext = context!!.applicationContext
        return false
    }

    override fun query(uri: Uri, projection: Array<out String>?, selection: String?, selectionArgs: Array<out String>?, sortOrder: String?): Cursor? = null
    override fun getType(uri: Uri): String? = null
    override fun insert(uri: Uri, values: ContentValues?): Uri? = null
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int = 0
    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<out String>?): Int = 0
}
