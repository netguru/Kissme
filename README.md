
# KotlinMultiplatformStorage
KotlinMultiplatformStorage is an open-source Kotlin MultiPlatform key-value storage library. It allows you to store key-value data in common module without any additional boilerplate code.

Currently library supports following platforms:
- android
- ios_arm64
- ios_x64

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
                implementation 'com.netguru.kotlinmultiplatformstorage:kotlinmultiplatformstorage-common:0.1.0'
            }
        }
        androidMain {
            dependencies {
                implementation 'com.netguru.kotlinmultiplatformstorage:kotlinmultiplatformstorage-android:0.1.0'
            }
        }

        iosMain {
            dependencies {
                implementation 'com.netguru.kotlinmultiplatformstorage:kotlinmultiplatformstorage-ios:0.1.0'
            }
        }
    }
```

Remember to enable `GRADLE_METADATA` in `settings.gradle`:
```groovy
enableFeaturePreview('GRADLE_METADATA')
```
## Usage
Just instantiate `MultiPlatformStorage` in common module class and enjoy! It's as simple as that!
You don't have to initialize it by yourself. You don't have to pass `Context`. Everything is done automatically.
```kotlin
val storage = MultiPlatformStorage(name = "some_storage")
```
`name` parameter is optional, and you can omit it if you want to use default storage.
`MultiPlatformStorage` allows you to store and persist multiple data types:
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

You can get all keys stored in `MultiPlatformStorage` by calling:
```kotlin
storage.getAll()
```

You can check if `MultiPlatformStorage` contains selected `key` by calling:
```kotlin
storage.contains(key = "someKey")
```

You can also remove selected key from storage:
```kotlin
storage.remove(key = "someKey")
```

Last, but not least, you can remove all data stored in `MultiPlatformStorage`:
```kotlin
storage.clear()
```

## About
`KotlinMultiPlatformStorage` allows to store key-value pairs in platform-specific way.

### Android
Android implementation uses `SharedPreferences` interface. Library allows you to choose between storing
values in named or default preferences provided by `PreferenceManager`.

`SharedPreference` instance is initialized lazily when calling one of the available functions
using application `Context` which is provided automatically by `ContentProvider`.

### iOS

As we all know `UserDefaults` isn't best place to store app secrets, that's why iOS implementation is using native iOS Keychain. Security Enclave is a hardware-based key manager that's isolated from processor. It allows you to store, delete, fetch passwords and accounts. 

`Keychain` is simple wrapper build upon `Security` interface to store, save, and fetch not only passwords, but also accounts.

## Running sample app
Sample app uses Maven Local for resolving `KotlinMultiPlatformStorage` dependencies. Before running sample app you need to:
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
