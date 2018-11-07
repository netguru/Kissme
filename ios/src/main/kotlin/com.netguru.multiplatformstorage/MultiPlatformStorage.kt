package com.netguru.multiplatformstorage

import kotlinx.cinterop.CValuesRef
import platform.CoreFoundation.*
import platform.Foundation.*
import platform.Security.*
import platform.darwin.OSStatus

class MultiPlatformStorage {

    private val SecClass = kSecClass as CFStringRef
    private val SecAttrService = kSecAttrService as CFStringRef
    private val SecAttrAccessGroup = kSecAttrAccessGroup as CFStringRef
    private val SecAttrGeneric = kSecAttrGeneric as CFStringRef
    private val SecAttrAccount = kSecAttrAccount as CFStringRef
    private val SecMatchLimit = kSecMatchLimit as CFStringRef
    private val SecReturnData = kSecReturnData as CFStringRef
    private val SecValueData = kSecValueData as CFStringRef
    private val SecAttrAccessible = kSecAttrAccessible as CFStringRef
    private var SecReturnAttributes = kSecReturnAttributes as CFStringRef

    var accessGroup: NSString? = null
        private set

    var serviceName: String = ""
    private set

    fun getAll(): Map<String, *> {

        val keychainQueryMap: MutableMap<CFStringRef, Any?> = mutableMapOf()

        keychainQueryMap[SecClass] = kSecClassGenericPassword
        keychainQueryMap[SecAttrService] = serviceName as CFStringRef
        keychainQueryMap[SecReturnAttributes] = kCFBooleanTrue
        keychainQueryMap[SecMatchLimit] = kSecMatchLimitAll

        accessGroup?.let {
            keychainQueryMap[SecAttrAccessGroup] = accessGroup
        }

        val result: CValuesRef<CFTypeRefVar>? = null
        val status = SecItemCopyMatching(keychainQueryMap as CFDictionaryRef, result)

        if (status != errSecSuccess) else {
            return emptyMap<String, Any>()
        }

        var values = mutableMapOf<String, Any>()
        val dictionaryResult = result as Array<NSDictionary>

        for (attribute in dictionaryResult) {
            val accountData = attribute.valueForKey(SecAttrAccount as String) as String
            val value = attribute.valueForKey(SecValueData as String) as NSData

            values[accountData] = (NSString.stringEncodingForData(data = value, encodingOptions = null, convertedString = null, usedLossyConversion = null )) as String
        }

        return values
    }

    fun getString(key: String, defaultValue: String?): String? {
        data(key = key as NSString)?.let {
            NSString.stringEncodingForData(data = it, encodingOptions = null, convertedString = null, usedLossyConversion = null)
        }
        return ""
    }

    fun putString(key: String, value: String) {
        val key = key as NSString
        val value = value as NSString

        value.dataUsingEncoding(NSUTF8StringEncoding)?.let {
            set(value = it, key = key)
        }
    }

    fun getInt(key: String, defaultValue: Int?): Int? {
        val key = key as NSString

        val numberValue = objectForKey(key = key) as? NSNumber
        numberValue?.let {
            return it.intValue
        }
        return null
    }

    fun putInt(key: String, value: Int) {
        set(value = NSNumber(value), key = key as NSString)
    }

    fun getLong(key: String, defaultValue: Long?): Long? {
        val key = key as NSString

        val numberValue = objectForKey(key = key) as? NSNumber
        numberValue?.let {
            return it.longValue
        }
        return null
    }

    fun putLong(key: String, value: Long) {
        set(value = NSNumber(long = value), key = key as NSString)
    }

    fun getFloat(key: String, defaultValue: Float?): Float? {
        val key = key as NSString

        val numberValue = objectForKey(key = key) as? NSNumber
        numberValue?.let {
            return it.floatValue
        }
        return null
    }

    fun putFloat(key: String, value: Float) {
        set(value = NSNumber(value), key = key as NSString)
    }

    fun getBoolean(key: String, defaultValue: Boolean?): Boolean? {
        val key = key as NSString

        val numberValue = objectForKey(key = key) as? NSNumber
        numberValue?.let {
            return it.boolValue
        }
        return null
    }

    fun putBoolean(key: String, value: Boolean) {
        set(value = value, key = key as NSString)
    }

    fun contains(key: String): Boolean {
        val key = key as NSString
        return data(key = key)?.let { true } ?: false
    }

    fun remove(key: String) {
        removeObject(key = key as NSString)
    }

    fun clear() {
        deleteKeychainSecClass(secClass = kSecClassGenericPassword)
        deleteKeychainSecClass(secClass = kSecClassInternetPassword)
        deleteKeychainSecClass(secClass = kSecClassCertificate)
        deleteKeychainSecClass(secClass = kSecClassKey)
        deleteKeychainSecClass(secClass = kSecClassIdentity) // Identity items
    }

    private fun deleteKeychainSecClass(secClass: CFStringRef?): Boolean {
        val query = mutableMapOf<CFStringRef,CFStringRef>()
        val status: OSStatus = SecItemDelete(query as CFDictionaryRef)

        return status == errSecSuccess
    }

    private fun removeObject(key: NSString): Boolean {
        val keychainQueryMap = setupKeychainQueryDictionary(forKey = key)
        val status: OSStatus = SecItemDelete(query = keychainQueryMap as CFDictionaryRef)

        return status == errSecSuccess
    }

    private fun set(value: NSCodingProtocol, key: NSString): Boolean {
        val data = NSKeyedArchiver.archivedDataWithRootObject(value)
        return set(value = data, key = key)
    }

    private fun set(value: Boolean, key: NSString): Boolean {
        return set(value = NSNumber(value), key = key)
    }

    private fun set(value: NSString, key: NSString): Boolean {
        value.dataUsingEncoding(NSUTF8StringEncoding)?.let {
            set(value = it, key = key)
        }
        return false
    }

    private fun set(value: NSData, key: NSString): Boolean {
        val keychainQueryMap = setupKeychainQueryDictionary(forKey = key)

        keychainQueryMap[SecValueData] = value
        keychainQueryMap[SecAttrAccessible] = kSecAttrAccessibleWhenUnlocked

        val status: OSStatus = SecItemAdd(keychainQueryMap as CFDictionaryRef, null)

        if (status == errSecSuccess) {
            return true
        } else if (status == errSecDuplicateItem) {
            print("Duplicate = need to update data")
        } else {
            return false
        }
        return false
    }

    private fun data(key: NSString): NSData? {
        val keychainQueryMap = setupKeychainQueryDictionary(forKey = key)

        keychainQueryMap[SecMatchLimit] = kSecMatchLimitOne
        keychainQueryMap[SecReturnData] = kCFBooleanTrue

        val result: CValuesRef<CFTypeRefVar>? = null
        val status = SecItemCopyMatching(keychainQueryMap as CFDictionaryRef, result)

        return if (status == 0) result as? NSData else null
    }

    private fun objectForKey(key: NSString): NSCodingProtocol? = data(key = key)?.let {
        NSKeyedUnarchiver.unarchiveObjectWithData(data = it) as? NSCodingProtocol
    }

    private fun setupKeychainQueryDictionary(forKey: NSString): MutableMap<CFStringRef, Any?> {
        val keychainQueryMap: MutableMap<CFStringRef, Any?> = mutableMapOf()

        keychainQueryMap[this.SecClass] = kSecClass
        keychainQueryMap[this.SecAttrService] = kSecAttrService

        accessGroup?.let {
            keychainQueryMap[this.SecAttrAccessGroup] = it
        }

        val encodedIdentifier = forKey.dataUsingEncoding(NSUTF8StringEncoding)

        keychainQueryMap[this.SecAttrGeneric] = encodedIdentifier
        keychainQueryMap[this.SecAttrAccount] = encodedIdentifier

        return keychainQueryMap
    }
}
