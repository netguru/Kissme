package com.netguru.kissme

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import com.ironz.binaryprefs.BinaryPreferencesBuilder
import com.ironz.binaryprefs.Preferences
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.security.Key
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec

/**
 * Simple keys storage.
 * All keys are encrypted using [KeyStore] and stored in [Preferences].
 * Keys are generated automatically.
 */
internal class EncryptionKeysStorage {

    /**
     * Provides decrypted [ByteArray] key based on [encryptionKey]
     */
    internal fun getKey(encryptionKey: EncryptionKey, appContext: Context): ByteArray {
        val keyStore = KeyStore.getInstance(KEYSTORE_NAME)
        keyStore.load(null)

        val preferences = BinaryPreferencesBuilder(appContext)
            .name(PREFERENCES_NAME)
            .build()

        if (!keysExist(keyStore, preferences)) {
            generateNewKeys(keyStore, preferences)
        }

        return with(encryptionKey) {
            decrypt(decryptString(keyAlias, preferences), keyStore.getSecretKey(keystoreKeyAlias))
        }
    }

    private fun clearKeys(keyStore: KeyStore, preferences: Preferences) = EncryptionKey.values().forEach {
        keyStore.deleteEntry(it.keystoreKeyAlias)
        preferences.edit {
            remove(it.keyAlias)
        }
    }

    private fun keysExist(keyStore: KeyStore, preferences: Preferences) =
        EncryptionKey.values().fold(true) { status, key ->
            status && keyStore.containsAlias(key.keystoreKeyAlias) && preferences.contains(key.keyAlias)
        }

    private fun generateKeystoreKey(keyAlias: String, keyStore: KeyStore) = with(keyStore) {
        val aesKeyGenSpec =
            KeyGenParameterSpec.Builder(keyAlias, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .build()
        with(KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, KEYSTORE_NAME)) {
            init(aesKeyGenSpec)
            generateKey()
        }
    }

    private fun generateNewKeys(keyStore: KeyStore, preferences: Preferences) {
        clearKeys(keyStore, preferences)
        EncryptionKey.values().forEach {
            generateKeystoreKey(it.keystoreKeyAlias, keyStore)
            preferences.edit {
                putString(
                    it.keyAlias,
                    encodeToString(encryptKey(generateRandomKey(), keyStore.getSecretKey(it.keystoreKeyAlias)))
                )
            }
        }
    }

    private fun encodeToString(byteArray: ByteArray) = Base64.encodeToString(byteArray, Base64.DEFAULT)

    private fun decryptString(key: String, preferences: Preferences) =
        Base64.decode(preferences.getString(key, "")!!, Base64.DEFAULT)

    private fun generateRandomKey() = with(ByteArray(KEY_LENGTH)) {
        SecureRandom().nextBytes(this)
        this
    }

    private fun encryptKey(data: ByteArray, key: Key) = with(Cipher.getInstance(TRANSFORMATION)) {
        init(Cipher.ENCRYPT_MODE, key)
        generateIvKeyVector(doFinal(data), iv)
    }

    private fun decrypt(encryptedData: ByteArray, key: Key) = with(Cipher.getInstance(TRANSFORMATION)) {
        val (iv, encryptedKey) = extractIvAndEncryptedKey(encryptedData)
        val ivSpec = GCMParameterSpec(GCM_INITIAL_LENGTH, iv)
        init(Cipher.DECRYPT_MODE, key, ivSpec)
        doFinal(encryptedKey)
    }

    private fun extractIvAndEncryptedKey(encryptedData: ByteArray) = with(ByteBuffer.wrap(encryptedData)) {
        order(ByteOrder.BIG_ENDIAN)
        val ivLength = int
        val iv = ByteArray(ivLength)
        val encryptedKey = ByteArray(encryptedData.size - Integer.SIZE - ivLength)
        get(iv)
        get(encryptedKey)
        iv to encryptedKey
    }

    private fun generateIvKeyVector(encryptedKey: ByteArray, iv: ByteArray): ByteArray {
        val ivAndEncryptedKey = ByteArray(Integer.SIZE + iv.size + encryptedKey.size)
        with(ByteBuffer.wrap(ivAndEncryptedKey)) {
            order(ByteOrder.BIG_ENDIAN)
            putInt(iv.size)
            put(iv)
            put(encryptedKey)
        }

        return ivAndEncryptedKey
    }

    private fun KeyStore.getSecretKey(alias: String) = (getEntry(alias, null) as KeyStore.SecretKeyEntry).secretKey

    companion object {
        private const val TRANSFORMATION = "AES/GCM/NoPadding"
        private const val PREFERENCES_NAME = "keys_preferences"
        private const val KEYSTORE_NAME = "AndroidKeyStore"
        private const val KEY_LENGTH = 16
        private const val GCM_INITIAL_LENGTH = 128
    }
}

/**
 * Keys stored in [EncryptionKeysStorage].
 * Contains aliases for [Preferences] and [KeyStore].
 */
internal enum class EncryptionKey(internal val keyAlias: String, internal val keystoreKeyAlias: String) {
    VALUES_KEY(keyAlias = "values_key_alias", keystoreKeyAlias = "keystore_values_key_alias"),
    VALUES_IV(keyAlias = "values_iv_alias", keystoreKeyAlias = "keystore_values_iv_alias"),
    KEYS_KEY(keyAlias = "keys_key_alias", keystoreKeyAlias = "keystore_keys_key_alias");
}
