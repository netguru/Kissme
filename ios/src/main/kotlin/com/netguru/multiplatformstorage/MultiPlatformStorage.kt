package com.netguru.multiplatformstorage
import com.netguru.keychainWrapper.*

actual class MultiPlatformStorage actual constructor(name: String?) {

    actual fun getAll(): Map<String, *> {
        val ok = Keychain.accountsForService(serviceName="ok12")
        print(ok)
        return mapOf<String, String>()
    }

    actual fun getString(key: String, defaultValue: String?): String? {
        return ""
    }

    actual fun putString(key: String, value: String) {
        Keychain.setPassword(password="ok", forService= "ok12", account= "ok123")

//        Keychain.setPassword(password = "ok", forService= "ok12", account= "ok123")
//        val accounts = Keychain.accountsForService(serviceName= "ok12")
//        print(accounts)
    }

    actual fun getInt(key: String, defaultValue: Int): Int {
        return 0
    }

    actual fun putInt(key: String, value: Int) {
        print("ok2")
    }

    actual fun getLong(key: String, defaultValue: Long): Long {
        return 0
    }

    actual fun putLong(key: String, value: Long) {
        print("ok3")
    }

    actual fun getFloat(key: String, defaultValue: Float): Float {
        return 1.5.toFloat()
    }

    actual fun putFloat(key: String, value: Float) {
        print("ok4")
    }

    actual fun getDouble(key: String, defaultValue: Double): Double {
        return 0.0
    }

    actual fun putDouble(key: String, value: Double) {
        print("ok5")
    }

    actual fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        return false
    }

    actual fun putBoolean(key: String, value: Boolean) {
        print("ok6")

    }

    actual fun contains(key: String): Boolean {
        return true
    }

    actual fun remove(key: String) {
        print("ok7")

    }

    actual fun clear() {
        print("ok8")

    }
}
