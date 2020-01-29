package com.netguru.kissme

/**
 * Key-value data storage.
 * Allows storing values with several types using [String] as a key.
 *
 * Storage mechanism is defined using a platform-specific implementation.
 * For more information check platform-specific module.
 */
expect class Kissme(name: String? = null) {

    /**
     * Retrieve all values from the storage.
     */
    fun getAll(): Map<String, *>

    /**
     * Retrieve a String value stored at [key]. If storage doesn't contain [key] - returns [defaultValue].
     */
    fun getString(key: String, defaultValue: String?): String?

    /**
     * Put a String [value] stored at [key] to the storage.
     */
    fun putString(key: String, value: String)

    /**
     * Retrieve an Int value stored at [key]. If storage doesn't contain [key] - returns [defaultValue].
     */
    fun getInt(key: String, defaultValue: Int): Int

    /**
     * Put an Int [value] stored at [key] to the storage.
     */
    fun putInt(key: String, value: Int)

    /**
     * Retrieve a Long value stored at [key]. If storage doesn't contain [key] - returns [defaultValue].
     */
    fun getLong(key: String, defaultValue: Long): Long

    /**
     * Put a Long [value] stored at [key] to the storage.
     */
    fun putLong(key: String, value: Long)

    /**
     * Retrieve a Float value stored at [key]. If storage doesn't contain [key] - returns [defaultValue].
     */
    fun getFloat(key: String, defaultValue: Float): Float

    /**
     * Put a Float [value] stored at [key] to the storage.
     */
    fun putFloat(key: String, value: Float)

    /**
     * Retrieve a Double value stored at [key]. If storage doesn't contain [key] - returns [defaultValue].
     */
    fun getDouble(key: String, defaultValue: Double): Double

    /**
     * Put a Double [value] stored at [key] to the storage.
     */
    fun putDouble(key: String, value: Double)

    /**
     * Retrieve a Boolean value stored at [key]. If storage doesn't contain [key] - returns [defaultValue].
     */
    fun getBoolean(key: String, defaultValue: Boolean): Boolean

    /**
     * Put a Boolean [value] stored at [key] to the storage.
     */
    fun putBoolean(key: String, value: Boolean)

    /**
     * Checks whether the storage contains a value with specified [key].
     */
    fun contains(key: String): Boolean

    /**
     * Removes value with specified [key] from the storage
     */
    fun remove(key: String)

    /**
     * Removes all values from the storage
     */
    fun clear()
}
