package com.netguru.kissme

import org.jetbrains.annotations.TestOnly

/**
 * Simple [EncryptionKeysStorage] provider.
 * Provides proper [EncryptionKeysStorage] instance based on [mock] property.
 */
internal object EncryptionKeysStorageProvider {

    private var mock: EncryptionKeysStorage? = null

    private val value by lazy { EncryptionKeysStorage() }

    /**
     * Provides [EncryptionKeysStorage] instance
     */
    internal fun provide() = mock ?: value

    /**
     * Sets mocked [EncryptionKeysStorage] instance.
     * It should be called from testing code only!
     */
    @TestOnly
    internal fun setMockValue(mock: EncryptionKeysStorage) {
        this.mock = mock
    }
}
