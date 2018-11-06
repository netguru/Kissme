package com.netguru.multiplatformstorage

import kotlin.test.Test
import kotlin.test.*

expect val storage: MultiPlatformStorage

class MultiPlatformStorageTest : BaseTest() {

    @Test
    fun `should get proper string value when storage contains key`() {
        //given
        storage.putString(STRING_KEY, STRING)
        //when
        val data = storage.getString(STRING_KEY, "")
        //then
        assertEquals(STRING, data)
    }

    @Test
    fun `should return default string value when storage doesn't contain key`() {
        //given
        val defaultValue = "default"
        storage.putString(STRING_KEY, STRING)
        //when
        val data = storage.getString("key", defaultValue)
        //then
        assertEquals(defaultValue, data)
    }

    @Test
    fun `should get proper int value when storage contains key`() {
        //given
        storage.putInt(INT_KEY, INT)
        //when
        val data = storage.getInt(INT_KEY, 0)
        //then
        assertEquals(INT, data)
    }

    @Test
    fun `should return default int value when storage doesn't contain key`() {
        //given
        val defaultValue = 4
        storage.putInt(INT_KEY, INT)
        //when
        val data = storage.getInt("key", defaultValue)
        //then
        assertEquals(defaultValue, data)
    }

    @Test
    fun `should get proper long value when storage contains key`() {
        //given
        storage.putLong(LONG_KEY, LONG)
        //when
        val data = storage.getLong(LONG_KEY, 0)
        //then
        assertEquals(LONG, data)
    }

    @Test
    fun `should return default long value when storage doesn't contain key`() {
        //given
        val defaultValue = 7L
        storage.putLong(LONG_KEY, LONG)
        //when
        val data = storage.getLong("key", defaultValue)
        //then
        assertEquals(defaultValue, data)
    }

    @Test
    fun `should get proper float value when storage contains key`() {
        //given
        storage.putFloat(FLOAT_KEY, FLOAT)
        //when
        val data = storage.getFloat(FLOAT_KEY, 0F)
        //then
        assertEquals(FLOAT, data)
    }

    @Test
    fun `should return default float value when storage doesn't contain key`() {
        //given
        val defaultValue = 2F
        storage.putFloat(FLOAT_KEY, FLOAT)
        //when
        val data = storage.getFloat("key", defaultValue)
        //then
        assertEquals(defaultValue, data)
    }

    @Test
    fun `should get proper double value when storage contains key`() {
        //given
        storage.putDouble(DOUBLE_KEY, DOUBLE)
        //when
        val data = storage.getDouble(DOUBLE_KEY, 0.0)
        //then
        assertEquals(DOUBLE, data)
    }

    @Test
    fun `should return default double value when storage doesn't contain key`() {
        //given
        val defaultValue = 2.0
        storage.putDouble(DOUBLE_KEY, DOUBLE)
        //when
        val data = storage.getDouble("key", defaultValue)
        //then
        assertEquals(defaultValue, data)
    }

    @Test
    fun `should get proper boolean value when storage contains key`() {
        //given
        storage.putBoolean(BOOLEAN_KEY, BOOLEAN)
        //when
        val data = storage.getBoolean(BOOLEAN_KEY, false)
        //then
        assertEquals(BOOLEAN, data)
    }

    @Test
    fun `should return default boolean value when storage doesn't contain key`() {
        //given
        val defaultValue = false
        storage.putBoolean(BOOLEAN_KEY, BOOLEAN)
        //when
        val data = storage.getBoolean("key", defaultValue)
        //then
        assertEquals(defaultValue, data)
    }

    @Test
    fun `should contains call return true when storage contains key`() {
        //given
        storage.putInt(INT_KEY, INT)
        //when
        val value = storage.contains(INT_KEY)
        //then
        assertEquals(true, value)
    }

    @Test
    fun `should contains call return false when storage doesn't contain key`() {
        //given
        storage.putInt(INT_KEY, INT)
        //when
        val value = storage.contains(STRING_KEY)
        //then
        assertEquals(false, value)
    }

    @Test
    fun `should get all data from multiplatform storage`() {
        //given
        with(storage) {
            putString(STRING_KEY, STRING)
            putInt(INT_KEY, INT)
            putLong(LONG_KEY, LONG)
            putFloat(FLOAT_KEY, FLOAT)
            putDouble(DOUBLE_KEY, DOUBLE)
            putBoolean(BOOLEAN_KEY, BOOLEAN)
        }
        //when
        val dataMap = storage.getAll()
        //then
        assertTrue {
            dataMap.containsKey(STRING_KEY)
            dataMap.containsKey(INT_KEY)
            dataMap.containsKey(LONG_KEY)
            dataMap.containsKey(FLOAT_KEY)
            dataMap.containsKey(DOUBLE_KEY)
            dataMap.containsKey(BOOLEAN_KEY)
            dataMap.containsKey(BOOLEAN_KEY)
        }
    }

    companion object {
        private const val STRING = "some text"
        private const val STRING_KEY = "key:string"
        private const val INT = 4
        private const val INT_KEY = "key:int"
        private const val LONG = -12L
        private const val LONG_KEY = "key:long"
        private const val FLOAT = 32F
        private const val FLOAT_KEY = "key:float"
        private const val DOUBLE = 10.0
        private const val DOUBLE_KEY = "key:double"
        private const val BOOLEAN = true
        private const val BOOLEAN_KEY = "key:boolean"
    }
}
