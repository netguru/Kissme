package com.netguru.multiplatformstorage

actual class MultiPlatformStorage actual constructor(name: String?) {

    actual fun getAll(): Map<String, *> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun getString(key: String, defaultValue: String?): String? {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun putString(key: String, value: String) {}

    actual fun getInt(key: String, defaultValue: Int): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun putInt(key: String, value: Int) {}

    actual fun getLong(key: String, defaultValue: Long): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun putLong(key: String, value: Long) {}

    actual fun getFloat(key: String, defaultValue: Float): Float {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun putFloat(key: String, value: Float) {}

    actual fun getDouble(key: String, defaultValue: Double): Double {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun putDouble(key: String, value: Double) {}

    actual fun getBoolean(key: String, defaultValue: Boolean): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun putBoolean(key: String, value: Boolean) {}

    actual fun contains(key: String): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    actual fun remove(key: String) {}

    actual fun clear() {}
}
