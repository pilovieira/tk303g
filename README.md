# Tracker TK303G

![Platform](https://img.shields.io/badge/platform-Android-3DDC84?logo=android&logoColor=white)
![Min SDK](https://img.shields.io/badge/minSdk-21-blue)
![Target SDK](https://img.shields.io/badge/targetSdk-37-blue)
![Version](https://img.shields.io/badge/version-4.1.0-orange)

Android app to control **Coban TK303G** vehicle trackers over SMS, with no server or backend of its own: commands are built on the device and sent directly via SMS to the tracker's configured number.

## Features

- Sends the core Coban protocol commands: get location, lock/unlock vehicle, check status, monitor/tracker mode, GPRS/SMS mode, auto track
- Configure password, APN, server IP/port and authorized numbers
- Receives and logs the callbacks (SMS replies) sent back by the tracker
- Built-in tutorial walking through the initial setup
- Portuguese (Brazil) and English support

## Tech stack

- Java, Android Views (no Compose)
- [OrmLite](https://ormlite.com/) for local persistence
- AndroidX (AppCompat, Material Components)
- Google Mobile Ads (AdMob)
- Gradle / Android Gradle Plugin

## Requirements

- Android Studio or the command line with the [Android SDK](https://developer.android.com/studio) set up
- JDK 17
- A Coban TK303G (or compatible) tracker with an SMS-enabled SIM card

## Building

```bash
git clone git@github.com:pilovieira/tk303g.git
cd tk303g
./gradlew assembleDebug
```

The generated APK is placed in `app/build/outputs/apk/debug/`.

To install directly on a connected device/emulator:

```bash
./gradlew installDebug
```

## Project structure

```
app/src/main/java/br/com/pilovieira/tk303g/
├── business/   # TK303G protocol command building
├── comm/       # SMS sending and receiving
├── location/   # Location parsing and history
├── log/        # Server/tracker log screen and persistence
├── persist/    # Preferences and data access (OrmLite)
├── utils/      # Utilities (language, ads, browser)
└── view/       # Activities and Fragments (UI)
```

## Disclaimer

This app is just a command sender for the tracker's protocol. Use it at your own responsibility.

## Contact

Questions or suggestions: appsfuncionais@gmail.com
