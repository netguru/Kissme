package com.netguru.multiplatformstorage

import android.content.Context
import androidx.test.core.app.ApplicationProvider

actual val storage: MultiplatformStorage
    get() {
        AndroidStorageProvider.appContext = ApplicationProvider.getApplicationContext<Context>()
        return MultiplatformStorage()
    }
