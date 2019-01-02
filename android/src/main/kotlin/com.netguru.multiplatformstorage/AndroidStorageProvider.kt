package com.netguru.multiplatformstorage

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import com.ironz.binaryprefs.BinaryPreferencesBuilder
import com.ironz.binaryprefs.Preferences
import com.ironz.binaryprefs.encryption.AesValueEncryption
import com.ironz.binaryprefs.encryption.XorKeyEncryption
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.security.Key
import java.security.KeyStore
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.spec.GCMParameterSpec


/**
 * [com.ironz.binaryprefs.Preferences] provider.
 * Provides proper instance of [com.ironz.binaryprefs.Preferences] based on [name] parameter
 */
@SuppressLint("StaticFieldLeak")
internal object AndroidStorageProvider {

    private const val DEFAULT_PREFERENCES_NAME = "default"
    private const val TRANSFORMATION = "AES/GCM/NoPadding"
    private const val PREFERENCES_NAME = "keys_preferences"
    private const val KEYSTORE_VALUES_KEY_ALIAS = "keystore_values_key_alias"
    private const val KEYSTORE_VALUES_IV_ALIAS = "keystore_values_iv_alias"
    private const val KEYSTORE_KEYS_KEY_ALIAS = "keystore_keys_key_alias"
    private const val VALUES_KEY_ALIAS = "values_key_alias"
    private const val VALUES_IV_ALIAS = "values_iv_alias"
    private const val KEYS_KEY_ALIAS = "keys_key_alias"
    private const val KEYSTORE_NAME = "AndroidKeyStore"

    private const val KEY_LENGTH = 16
    private const val GCM_INITIAL_LENGTH = 128

    /**
     * Application [Context] provided by [StorageInitializer],
     * needed for proper [com.ironz.binaryprefs.Preferences] initialization
     */
    internal lateinit var appContext: Context

    /**
     * Whether [name] parameter is specified, it returns named [com.ironz.binaryprefs.Preferences] instance.
     * In other cases, it uses default instance.
     */
    internal fun preferences(name: String? = null): Preferences {
        // Generate a new encryption key with initialization vector.
        val keyStore = KeyStore.getInstance(KEYSTORE_NAME)
        keyStore.load(null)

        val preferences = appContext.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

        if (!keysExist(keyStore, preferences)) {
            generateNewKeys(keyStore, preferences)
        }

        val valuesKey =
            decrypt(decryptString(VALUES_KEY_ALIAS, preferences), keyStore.getSecretKey(KEYSTORE_VALUES_KEY_ALIAS))
        val valuesIv =
            decrypt(decryptString(VALUES_IV_ALIAS, preferences), keyStore.getSecretKey(KEYSTORE_VALUES_IV_ALIAS))
        val keysKey =
            decrypt(decryptString(KEYS_KEY_ALIAS, preferences), keyStore.getSecretKey(KEYSTORE_KEYS_KEY_ALIAS))

        return BinaryPreferencesBuilder(appContext)
            .migrateFrom(getSharedPreferences(name))
            .keyEncryption(XorKeyEncryption(keysKey))
            .valueEncryption(AesValueEncryption(valuesKey, valuesIv))
            .name(name ?: DEFAULT_PREFERENCES_NAME)
            .build()
    }

    private fun clearKeys(keyStore: KeyStore, sharedPreferences: SharedPreferences) {
        with(keyStore) {
            deleteEntry(KEYSTORE_VALUES_KEY_ALIAS)
            deleteEntry(KEYSTORE_VALUES_IV_ALIAS)
            deleteEntry(KEYSTORE_KEYS_KEY_ALIAS)
        }
        sharedPreferences.edit {
            remove(VALUES_KEY_ALIAS)
            remove(VALUES_IV_ALIAS)
            remove(KEYS_KEY_ALIAS)
        }
    }

    private fun keysExist(keyStore: KeyStore, preferences: SharedPreferences) =
        keyStore.containsAlias(KEYSTORE_VALUES_KEY_ALIAS) && keyStore.containsAlias(KEYSTORE_KEYS_KEY_ALIAS)
                && keyStore.containsAlias(KEYSTORE_VALUES_IV_ALIAS) && preferences.contains(VALUES_IV_ALIAS)
                && preferences.contains(VALUES_KEY_ALIAS) && preferences.contains(KEYS_KEY_ALIAS)

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

    private fun generateNewKeys(keyStore: KeyStore, preferences: SharedPreferences) {
        clearKeys(keyStore, preferences)
        generateKeystoreKey(KEYSTORE_VALUES_KEY_ALIAS, keyStore)
        generateKeystoreKey(KEYSTORE_VALUES_IV_ALIAS, keyStore)
        generateKeystoreKey(KEYSTORE_KEYS_KEY_ALIAS, keyStore)
        preferences.edit {
            putString(
                VALUES_KEY_ALIAS,
                encodeToString(encryptKey(generateRandomKey(), keyStore.getSecretKey(KEYSTORE_VALUES_KEY_ALIAS)))
            )
            putString(
                VALUES_IV_ALIAS,
                encodeToString(encryptKey(generateRandomKey(), keyStore.getSecretKey(KEYSTORE_VALUES_IV_ALIAS)))
            )
            putString(
                KEYS_KEY_ALIAS,
                encodeToString(encryptKey(generateRandomKey(), keyStore.getSecretKey(KEYSTORE_KEYS_KEY_ALIAS)))
            )
        }
    }

    private fun encodeToString(byteArray: ByteArray) = Base64.encodeToString(byteArray, Base64.DEFAULT)

    private fun decryptString(key: String, preferences: SharedPreferences) =
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

    private fun getSharedPreferences(name: String?) = if (name == null) {
        PreferenceManager.getDefaultSharedPreferences(appContext)
    } else {
        appContext.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    private fun KeyStore.getSecretKey(alias: String) = (getEntry(alias, null) as KeyStore.SecretKeyEntry).secretKey

    private inline fun SharedPreferences.edit(action: SharedPreferences.Editor.() -> Unit) {
        val editor = edit()
        editor.action()
        editor.apply()
    }
}
