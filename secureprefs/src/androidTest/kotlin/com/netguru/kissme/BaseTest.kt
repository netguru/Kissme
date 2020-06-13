package com.icecreamhappens.secureprefs

import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.annotation.Config

@Config(manifest = Config.NONE)
@RunWith(AndroidJUnit4::class)
actual open class BaseTest actual constructor() {

    @Before
    fun setUp() {
        EncryptionKeysStorageProvider.setMockValue(mockk {
            every { getKey(any(), any()) } returns "16BytesSecretKey".toByteArray()
        })
    }

    /**
     * Needed for proper Roboelectric initialization
     */
    @Test
    fun emptyTest() = Unit
}
