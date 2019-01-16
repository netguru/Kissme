
# Kissme: Kotlin Secure Storage Multiplatform

[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Kissme-brightgreen.svg?style=flat-square)](https://android-arsenal.com/details/1/7452)

**Kissme** is an open-source library providing encrypted key-value storage.

It can be integrated seamlessly in Kotlin projects built with **Kotlin Multiplatform**, **Kotlin/Native**, and **Kotlin Android** plugins.

**Kissme** allows storing key-value data in common code modules without any additional boilerplate code.

Currently library supports the following platforms:
- Android (API `>=` 23)
- iOS (`ios_arm64` and `ios_x64` targets)

## Download
To use this library in your project, add Netguru bintray url to the repositories block:
```groovy
repositories {
    maven {  url 'https://dl.bintray.com/netguru/maven/' }
}
```

Then add following dependencies to the common module build.gradle:
```groovy
    sourceSets {
        commonMain {
            dependencies {
                implementation 'com.netguru.kissme:kissme-common:0.2.2'     
            }
        }
        androidMain {
            dependencies {
                implementation 'com.netguru.kissme:kissme-android:0.2.2'
            }
        }
        iosMain {
            dependencies {
                implementation 'com.netguru.kissme:kissme-ios:0.2.2'
            }
        }
    }
```

Remember to enable `GRADLE_METADATA` in `settings.gradle`:
```groovy
enableFeaturePreview('GRADLE_METADATA')
```
## Usage
Just start with creating an instance of `Kissme` class somewhere in your common module and enjoy! It's as simple as that.
You don't have to initialize it by yourself nor to pass Android `Context`. Everything is done automatically.
```kotlin
val storage = Kissme(name = "my_secret_storage")
```
The `name` parameter is optional. You can omit it if you want to use default storage.
`Kissme` allows you to store and persist multiple data types:
- String
- Int
- Long
- Float
- Double
- Boolean 

If you want to store something, just call:
```kotlin
storage.putString(key = "someKey", value = "value")
```

If you want to get stored value - use:
```kotlin
storage.getString(key = "someKey", defaultValue = "default")
```

All `get()` functions will return `defaultValue` parameter if storage doesn't contain selected `key`.

You can get all keys stored in `Kissme` storage by calling:
```kotlin
storage.getAll()
```

You can check if `Kissme` storage contains selected `key` by calling:
```kotlin
storage.contains(key = "someKey")
```

You can also remove selected key from storage:
```kotlin
storage.remove(key = "someKey")
```

Last, but not least, you can remove all data stored in `Kissme` storage:
```kotlin
storage.clear()
```

## About
`Kissme` allows to store key-value pairs in platform-specific way securely.

### Android
Android implementation uses [binaryprefs](https://github.com/yandextaxitech/binaryprefs) library under the hood in order to provide a robust key-value storage mechanism. 
The keys and values are encrypted using XOR and AES encryption accordingly. The data encryption and encryption keys storing generating mechanisms are fully automated and is applied to the stored data by default. All the encryption keys are stored in the Android `KeyStore`.  

In order to acquire the application `Context` instance required for data storing operations the library registers an internal 
 `ContentProvider`.

### iOS
The iOS implementation is using native iOS `Keychain`. The Secure Enclave is a hardware-based key manager that's isolated from processor. It allows you to store, delete, fetch passwords and accounts. 
`Keychain` is simple wrapper build upon `Security` interface to store, save, and fetch not only passwords, but also accounts.

## Running sample app
Sample app uses Maven Local for resolving `Kissme` dependencies. Before running sample app you need to:
1. Build the library - `./gradlew build`
2. Publish dependencies to Maven Local - `./gradlew publishToMavenLocal`
3. Run selected library. If you want to run iOS app - you need to properly configure Xcode.
 Please check: https://kotlinlang.org/docs/tutorials/native/mpp-ios-android.html#setting-up-xcode before running iOS sample app.

## License

Copyright 2018 Netguru

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
