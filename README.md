> Mirror Policy: This repository is an automated mirror of the monorepo https://github.com/amitgaikwad2837/SDK.
>
> Do not push changes directly here. All changes must be made in the SDK monorepo and synced by workflow.
> Pull requests opened in this repo are for review visibility only and may be overwritten by the next sync.
# Android Emergency SOS SDK

## 📦 Registry & Repository

- **Maven Central**: [io.github.amitgaikwad2837:android-emergency-sos-sdk](https://central.sonatype.com/artifact/io.github.amitgaikwad2837/android-emergency-sos-sdk)
- **GitHub**: [amitgaikwad2837/android-emergency-sos-sdk](https://github.com/amitgaikwad2837/android-emergency-sos-sdk)

## Overview

Emergency SOS framework for rapid emergency response and support for vulnerable individuals. Features manual SOS triggers, shake detection, voice triggers, automatic location sharing, and emergency contact management.

## Installation

Add the Maven dependency:

~~~kotlin
dependencies {
  implementation("io.github.amitgaikwad2837:android-emergency-sos-sdk:0.0.9")
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

