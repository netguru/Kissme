package com.icecreamhappens.secureprefs

import android.content.Context
import androidx.test.core.app.ApplicationProvider

actual val storage: SecurePrefs
    get() {
        AndroidStorageProvider.appContext = ApplicationProvider.getApplicationContext<Context>()
        return SecurePrefs()
    }
