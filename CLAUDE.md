# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Commands

```bash
# Build all modules
./gradlew build

# Run all tests
./gradlew test

# Run tests for a single module
./gradlew :whdt-core:test

# Run a specific test class
./gradlew :whdt-core:test --tests "fully.qualified.ClassName"

# Publish to GitHub Packages (requires GPR_USER and GPR_KEY env vars)
./gradlew publish
```

Tests are written with [Kotest](https://kotest.io/) and run on JUnit Platform.

## Architecture

WHDT is a Kotlin framework for Human Digital Twins (HDTs) — digital representations of people. It builds on [WLDT](https://github.com/wldt) (White Label Digital Twin) to provide a ready-to-use HDT runtime with pluggable physical/digital interfaces.

### Module dependency graph

```
whdt-core  ←──  whdt-distributed  ←──┐
    ↑                                  whdt-wldt-plugin  ←──  whdt-examples
    ├──────────────────────────────────┘                           │
    └──────────────────────────────────────────────────────────────┘
whdt-dsl  (scaffolding, not yet implemented)
```

### whdt-core

Pure domain model with no external runtime dependencies. Central aggregate:

```kotlin
HumanDigitalTwin(
    hdtId: HdtId,
    models: List<Model>,                     // data models, each holding Properties
    physicalInterfaces: List<PhysicalInterface>,  // MQTT
    digitalInterfaces: List<DigitalInterface>,    // MQTT, HTTP
    storages: List<Storage>,                 // default: IN_MEMORY
    metadata: Map<String, String>,
)
```

Key patterns used throughout:
- `@JvmInline value class` wrappers for every ID and name (`HdtId`, `ModelId`, `PropertyName`, etc.) — enforce type-safety at call sites.
- IDs are derived, not stored: `"$hdtId:$name"`.
- Every type is `@Serializable` (kotlinx.serialization) with explicit `@SerialName` discriminators on sealed interfaces/data classes.

### whdt-distributed

Serialization and messaging layer. Does not contain runtime logic.

- `SerDe<T>` — interface for symmetric JSON encode/decode; `jsonSerDe<T>()` factory builds one from kotlinx-serialization.
- `Stub` — pre-built `SerDe` instances for the core domain types (e.g. `Stub.propertyJsonSerDe()`).
- `Message` — envelope for distributed HDT events: `HdtId`, `SenderId`, send/receive timestamps, and a `JsonElement` payload that callers unwrap with `message.unwrap<T>()`.
- `Namespace` — single source of truth for MQTT topic conventions:
  - `whdt/{hdtId}/property-update-request/{propertyName}`
  - `whdt/{hdtId}/property-update-notification/{propertyName}`

### whdt-wldt-plugin

Adapts the WHDT domain model to the WLDT execution runtime.

- `HumanDigitalTwinFactory.fromHumanDigitalTwin(hdt: HumanDigitalTwin): DigitalTwin` — converts a core HDT descriptor into a runnable WLDT `DigitalTwin` by wiring up physical/digital adapters (MQTT, HTTP) and storage.
- `WhdtShadowingFunction` — WLDT `ShadowingFunction` implementation that maps HDT model properties, events, and actions into WLDT digital twin state transactions.
- `WldtApp` — entry point; call `addStartAll(hdts)` to start a list of HDTs.

### whdt-dsl

Scaffolding for a planned ergonomic DSL that will let developers define HDTs in Kotlin without manually constructing the `HumanDigitalTwin` data class hierarchy. No sources yet.

## Publishing

Each publishable module reads its version from `<module>/version.txt`. The root `build.gradle.kts` also reads a `packageVersion` environment variable for the group-level version. Releases are automated via semantic-release (`scripts/release.mjs`, `package.json`).