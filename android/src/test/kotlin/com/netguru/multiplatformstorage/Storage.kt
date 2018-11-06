package com.netguru.multiplatformstorage

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import java.lang.ref.WeakReference

actual val storage: MultiPlatformStorage
    get() {
        SharedPreferencesProvider._context = WeakReference(ApplicationProvider.getApplicationContext<Context>())
        return MultiPlatformStorage()
    }
