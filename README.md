# Android Emergency SOS SDK

## Overview

Emergency SOS SDK for fast emergency response

## Installation

Add the Maven dependency once artifacts are published:

~~~kotlin
dependencies {
  implementation("io.github.amitgaikwad2837:android-emergency-sos-sdk:1.0.0")
}
~~~

## Integration Example

~~~kotlin
import com.sdk.sos.EmergencySOSSDK

class ExampleUsage {
    fun setup() {
        val sdk = EmergencySOSSDK()
        // Configure and call SDK APIs here based on your app flow.
    }
}
~~~

## Feature Highlights

- manual-sos
- shake-detection
- voice-trigger
- gps-collection
- emergency-contacts
- background-monitoring

## Android Permissions

- ACCESS_FINE_LOCATION
- RECORD_AUDIO
- INTERNET

## Development

~~~bash
./gradlew build
./gradlew test
~~~

## License

MIT
