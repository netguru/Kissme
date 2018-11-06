package com.netguru.multiplatformstorage

import android.content.SharedPreferences
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import org.junit.Test

class StorageTest {

    private val storage = MultiPlatformStorage()
    private val editor = mockk<SharedPreferences.Editor>(relaxUnitFun = true)
    private val preferences = mockk<SharedPreferences> { every { edit() } returns editor }

    init {
        mockkObject(SharedPreferencesProvider)
        every { SharedPreferencesProvider.preferences() } returns preferences
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
    fun `should get int from shared preferences when getting string`() {
        //given
        every { preferences.getInt(any(), any()) } returns 0
        //when
        storage.getInt("", 0)
        //then
        verify { preferences.getInt("", 0) }
    }

    @Test
    fun `should put int in shared preferences when putting string`() {
        //given
        every { editor.putInt(any(), any()) } returns editor
        //when
        storage.putInt("", 0)
        //then
        verify { editor.putInt("", 0) }
    }

    @Test
    fun `should get long from shared preferences when getting string`() {
        //given
        every { preferences.getLong(any(), any()) } returns 0
        //when
        storage.getLong("", 0)
        //then
        verify { preferences.getLong("", 0) }
    }

    @Test
    fun `should put long in shared preferences when putting string`() {
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
    fun `should get boolean from shared preferences when getting string`() {
        //given
        every { preferences.getBoolean(any(), any()) } returns true
        //when
        storage.getBoolean("", true)
        //then
        verify { preferences.getBoolean("", true) }
    }

    @Test
    fun `should put boolean in shared preferences when putting string`() {
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
}
