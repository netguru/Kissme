package com.icecreamhappens.secureprefs

import com.netguru.keychainWrapper.*
import platform.Foundation.*

actual class SecurePrefs actual constructor(name: String?) {

    private val serviceName: String = name ?: "defaultservice"

    actual fun getAll(): Map<String, *> {
        val allPasswords = Keychain.getAllPasswordForService(serviceName) ?: emptyMap<String, String>()
        return allPasswords
            .filter { it.key is String }
            .map {
                it.key as String to it.value
            }
            .toMap()
    }

    actual fun getString(key: String, defaultValue: String?): String? {
        return Keychain.passwordForService(serviceName, key) ?: defaultValue
    }

    actual fun putString(key: String, value: String) {
        Keychain.setPassword(value, serviceName, key)
    }

    actual fun getInt(key: String, defaultValue: Int): Int {
        return Keychain.passwordForService(serviceName, key)?.toInt() ?: defaultValue
    }

    actual fun putInt(key: String, value: Int) {
        Keychain.setPassword(value.toString(), serviceName, key)
    }

    actual fun getLong(key: String, defaultValue: Long): Long {
        return Keychain.passwordForService(serviceName, key)?.toLong() ?: defaultValue
    }

    actual fun putLong(key: String, value: Long) {
        Keychain.setPassword(value.toString(), serviceName, key)
    }

    actual fun getFloat(key: String, defaultValue: Float): Float {
        return Keychain.passwordForService(serviceName, key)?.toFloat() ?: defaultValue
    }

    actual fun putFloat(key: String, value: Float) {
        Keychain.setPassword(value.toString(), serviceName, key)
    }

    actual fun getDouble(key: String, defaultValue: Double): Double {
        return Keychain.passwordForService(serviceName, key)?.toDouble() ?: defaultValue
    }

    actual fun putDouble(key: String, value: Double) {
        Keychain.setPassword(value.toString(), serviceName, key)
    }

    actual fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return Keychain.passwordForService(serviceName, key)?.toBoolean() ?: defaultValue
    }

    actual fun putBoolean(key: String, value: Boolean) {
        Keychain.setPassword(value.toString(), serviceName, key)
    }

    actual fun contains(key: String): Boolean {
        return Keychain.containsForService(serviceName, key)
    }

    actual fun remove(key: String) {
        Keychain.deletePasswordForService(serviceName, key)
    }

    actual fun clear() {
        Keychain.clear()
    }
}
