# Upgrade Plan: barberpro (20260731012317)

- **Generated**: 2026-07-30 00:00 UTC
- **HEAD Branch**: main
- **HEAD Commit ID**: N/A

## Available Tools

**JDKs**
- JDK 21.0.2: C:\Program Files\Java\jdk-21\bin (current project JDK, used by all steps)

**Build Tools**
- Maven Wrapper: 3.9.16 (current, compatible with Java 21)

## Guidelines

> Note: You can add any specific guidelines or constraints for the upgrade process here if needed, bullet points are preferred.

## Options

- Working branch: appmod/java-upgrade-20260731012317
- Run tests before and after the upgrade: true

## Upgrade Goals

- Java 21 (latest LTS runtime)

## Technology Stack

| Technology/Dependency | Current | Min Compatible | Why Incompatible |
| --------------------- | ------- | -------------- | ---------------- |
| Java | 21 | 21 | User requested latest LTS runtime |
| Spring Boot | 4.1.0 | 4.1.0 | Already aligned with Java 21 |
| Maven Wrapper | 3.9.16 | 3.9.0 | Compatible with Java 21 and Spring Boot 4 |

## Derived Upgrades

- None required; the project already targets Java 21 and uses a compatible Maven wrapper.

## Impact Analysis

### Dependency Changes

- No dependency or plugin version changes required for this upgrade.

### Source Code Changes

- No source code changes required for the Java 21 runtime upgrade.

### Configuration Changes

- No configuration changes required.

### CI/CD Changes

- No CI/CD changes required as no runtime version drift is present in the build configuration.

### Risks & Warnings

- Existing uncommitted source files are present in the working tree; they are not part of the Java runtime upgrade and should remain unchanged unless required by baseline test failures.
- The upgrade path is effectively a verification of current Java 21 compatibility rather than a version bump.

## Upgrade Steps

- Step 1: Setup Environment
  - **Rationale**: Confirm Java 21 and Maven wrapper availability before verification.
  - **Changes to Make**: None; environment sanity check only.
  - **Verification**: `./mvnw.cmd -q -version && java -version`

- Step 2: Baseline & Validation
  - **Rationale**: Verify that the project already targeting Java 21 compiles and passes tests on the requested runtime.
  - **Changes to Make**: None; validation only.
  - **Verification**: `./mvnw.cmd -q clean test`
