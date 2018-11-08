package com.netguru.multiplatformstorage

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
@RunWith(AndroidJUnit4::class)
actual open class BaseTest actual constructor() {

    /**
     * Needed for proper Roboelectric initialization
     */
    @Test
    fun emptyTest() = Unit
}
