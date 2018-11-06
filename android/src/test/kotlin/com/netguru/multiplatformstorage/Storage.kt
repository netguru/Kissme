package com.netguru.multiplatformstorage

import android.content.Context
import androidx.test.core.app.ApplicationProvider

actual val storage: MultiPlatformStorage
    get() {
        SharedPreferencesProvider.appContext = ApplicationProvider.getApplicationContext<Context>()
        return MultiPlatformStorage()
    }
