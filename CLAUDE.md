# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

Gentle Palette is a small Android library that "rounds" any color to its nearest match in a predefined color palette, using Euclidean distance in RGB space. It is published via JitPack as `com.github.osama-raddad:gentle-palette`.

Two Gradle modules:

- `gentlepalette/` — the library itself (Android library module, zero dependencies). The entire public API is two files:
  - `GentlePalette.java` — `GentlePalette.generate(int colorInt, String[] baseColors, OnColorRoundingDone callback)` converts the input color to RGB, computes the Euclidean distance to each palette color, and invokes the callback with the nearest one as a color int.
  - `Palettes.java` — an interface holding the predefined palettes as `String[]` hex constants: `FLAT_COLORS`, `MATERIAL_COLORS`, `FLUENT_COLORS`, `METRO_COLORS`, `SOCIAL_COLORS`.
- `app/` — a demo application (`MainActivity`) that lets the user type a hex color and shows it rounded against `Palettes.MATERIAL_COLORS`.

There are no unit or instrumentation tests in either module.

## Build Commands

```bash
./gradlew compileDebugSources --stacktrace   # what CI runs (see .travis.yml)
./gradlew :gentlepalette:assembleDebug       # build the library only
./gradlew :app:assembleDebug                 # build the demo app
./gradlew clean                              # deletes the root build dir (custom task in root build.gradle)
```

## Legacy Toolchain — Important

This project uses a 2017-era Android toolchain. Expect build failures on modern environments unless these constraints are respected:

- Gradle wrapper 4.1 with Android Gradle Plugin 3.0.1 — requires **JDK 8** (CI uses `oraclejdk8`).
- `compileSdkVersion 27`, `minSdkVersion 15`, `targetSdkVersion 27`, Java 8 source/target compatibility.
- Uses the old **Android Support libraries** (`com.android.support:*:27.0.2`), not AndroidX.
- Repositories include **jcenter()**, which has been sunset — dependency resolution may fail without migrating repositories or working from caches.
- The demo app uses the deprecated `compile` configuration for the module dependency (`compile project(':gentlepalette')`).

Do not upgrade the toolchain, migrate to AndroidX, or change SDK versions as a side effect of another task — treat that as its own explicitly requested change.

## Conventions

- Library code lives under `gentlepalette/src/main/java/com/osama/gentlepalette/`; both modules share the `com.osama.gentlepalette` package.
- The library intentionally has **zero dependencies** (advertised in the README badge) — keep it that way.
- Colors are passed around as `#RRGGBB` hex strings inside the library and as Android color ints at the API boundary; formatting back to hex uses `String.format("#%06X", 0xFFFFFF & color)`.
- Version is tracked in `app/build.gradle` (`versionName "0.1.3"`), matching the JitPack release tag documented in the README.
- Licensed under Apache 2.0 (`LICENSE.md`).
