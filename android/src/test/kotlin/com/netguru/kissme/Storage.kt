package com.netguru.kissme

import android.content.Context
import androidx.test.core.app.ApplicationProvider

actual val storage: Kissme
    get() {
        AndroidStorageProvider.appContext = ApplicationProvider.getApplicationContext<Context>()
        return Kissme()
    }
