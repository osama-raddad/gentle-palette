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

## Toolchain

- Gradle 9.6.1 (wrapper) with Android Gradle Plugin 9.2.1 — requires JDK 17+.
- `compileSdk 37`, `minSdk 21`, `targetSdk 37` (app only; the library does not set a targetSdk), Java 17 source/target compatibility.
- AndroidX (`androidx.appcompat` is the demo app's only external dependency).
- Dependency and plugin versions are centralized in the version catalog `gradle/libs.versions.toml`; repositories are declared in `settings.gradle` (`google()`, `mavenCentral()`).
- JitPack publishing is configured in `gentlepalette/build.gradle` via `maven-publish` with `singleVariant('release')`; verify it with `./gradlew :gentlepalette:publishToMavenLocal`.
- CI is GitHub Actions (`.github/workflows/ci.yml`), running `./gradlew build` on JDK 21.

## Build Commands

```bash
./gradlew build                              # full build incl. lint + release variants (what CI runs)
./gradlew :gentlepalette:assembleDebug       # build the library only
./gradlew :app:assembleDebug                 # build the demo app
./gradlew :gentlepalette:publishToMavenLocal # verify the JitPack/maven-publish setup
./gradlew clean
```

## Conventions

- Library code lives under `gentlepalette/src/main/java/com/osama/gentlepalette/`; both modules share the `com.osama.gentlepalette` Java package. The Gradle namespaces differ (`com.osama.gentlepalette` for the app, `com.osama.gentlepalette.lib` for the library) because two modules may not share a namespace — the library namespace only affects its generated `R`/manifest, not the public API package.
- The library intentionally has **zero dependencies** (advertised in the README badge) — keep it that way.
- Colors are passed around as `#RRGGBB` hex strings inside the library and as Android color ints at the API boundary; formatting back to hex uses `String.format("#%06X", 0xFFFFFF & color)`.
- Version is tracked in `app/build.gradle` (`versionName "0.1.3"`) and in the library's publishing block, matching the JitPack release tag documented in the README.
- Licensed under Apache 2.0 (`LICENSE.md`).
