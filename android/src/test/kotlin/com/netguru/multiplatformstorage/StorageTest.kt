package com.netguru.multiplatformstorage

import com.ironz.binaryprefs.Preferences
import com.ironz.binaryprefs.PreferencesEditor
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import org.junit.Test

class StorageTest {

    private val storage = MultiplatformStorage()
    private val editor = mockk<PreferencesEditor>(relaxUnitFun = true)
    private val preferences = mockk<Preferences> { every { edit() } returns editor }

    init {
        mockkObject(AndroidStorageProvider)
        every { AndroidStorageProvider.preferences() } returns preferences
    }

    @Test
    fun `should get all data from shared preferences when getting all`() {
        //given
        every { preferences.all } returns emptyMap<String, String>()
        //when
        storage.getAll()
        //then
        verify { preferences.all }
    }

    @Test
    fun `should get string from shared preferences when getting string`() {
        //given
        every { preferences.getString(any(), any()) } returns ""
        //when
        storage.getString("", "")
        //then
        verify { preferences.getString("", "") }
    }

    @Test
    fun `should put string in shared preferences when putting string`() {
        //given
        every { editor.putString(any(), any()) } returns editor
        //when
        storage.putString("", "")
        //then
        verify { editor.putString("", "") }
    }

    @Test
    fun `should get int from shared preferences when getting int`() {
        //given
        every { preferences.getInt(any(), any()) } returns 0
        //when
        storage.getInt("", 0)
        //then
        verify { preferences.getInt("", 0) }
    }

    @Test
    fun `should put int in shared preferences when putting int`() {
        //given
        every { editor.putInt(any(), any()) } returns editor
        //when
        storage.putInt("", 0)
        //then
        verify { editor.putInt("", 0) }
    }

    @Test
    fun `should get long from shared preferences when getting long`() {
        //given
        every { preferences.getLong(any(), any()) } returns 0
        //when
        storage.getLong("", 0)
        //then
        verify { preferences.getLong("", 0) }
    }

    @Test
    fun `should put long in shared preferences when putting long`() {
        //given
        every { editor.putLong(any(), any()) } returns editor
        //when
        storage.putLong("", 0)
        //then
        verify { editor.putLong("", 0) }
    }

    @Test
    fun `should get float from shared preferences when getting string`() {
        //given
        every { preferences.getFloat(any(), any()) } returns 0f
        //when
        storage.getFloat("", 0f)
        //then
        verify { preferences.getFloat("", 0f) }
    }

    @Test
    fun `should put float in shared preferences when putting string`() {
        //given
        every { editor.putFloat(any(), any()) } returns editor
        //when
        storage.putFloat("", 0f)
        //then
        verify { editor.putFloat("", 0f) }
    }

    @Test
    fun `should get long from shared preferences when getting double`() {
        //given
        every { preferences.getLong(any(), any()) } returns 0L
        //when
        storage.getDouble("", 0.0)
        //then
        verify { preferences.getLong("", 0L) }
    }

    @Test
    fun `should put long in shared preferences when putting double`() {
        //given
        val value = 2.0
        every { editor.putLong(any(), any()) } returns editor
        //when
        storage.putDouble("", value)
        //then
        verify { editor.putLong("", value.toRawBits()) }
    }

    @Test
    fun `should get boolean from shared preferences when getting boolean`() {
        //given
        every { preferences.getBoolean(any(), any()) } returns true
        //when
        storage.getBoolean("", true)
        //then
        verify { preferences.getBoolean("", true) }
    }

    @Test
    fun `should put boolean in shared preferences when putting boolean`() {
        //given
        every { editor.putBoolean(any(), any()) } returns editor
        //when
        storage.putBoolean("", true)
        //then
        verify { editor.putBoolean("", true) }
    }

    @Test
    fun `should call shared preferences contains when checking if storage contains key`() {
        //given
        every { preferences.contains(any()) } returns true
        //when
        storage.contains("")
        //then
        verify { preferences.contains("") }
    }

    @Test
    fun `should remove key from shared preferences contains when removing key`() {
        //given
        every { editor.remove(any()) } returns editor
        //when
        storage.remove("")
        //then
        verify { editor.remove("") }
    }

    @Test
    fun `should clear shared preferences contains when clear called`() {
        //given
        every { editor.clear() } returns editor
        //when
        storage.clear()
        //then
        verify { editor.clear() }
    }
}
