# Sees

This is **Sees** - _Super Easy Event System_, a simple event system for Java. It can be used for any project that needs an easy-to-use event system.
It is designed to be simple and intuitive, allowing developers to create, call, and listen to events in just a few lines of code.

[![Latest](https://maven.pietr.space/api/badge/latest/releases/nl/devpieter/sees?name=Latest)](https://maven.pietr.space/#/releases/nl/devpieter/sees)
![Build Status](https://img.shields.io/github/actions/workflow/status/DevPieter/Sees/maven-publish.yml?branch=main&label=Build%20Status&logo=github)
![License](https://img.shields.io/github/license/DevPieter/Sees?color=blue&label=License&logo=github)
![Java Version](https://img.shields.io/badge/Java-21-blue?logo=java&logoColor=white)

- [Features](#features)
- [Installation](#installation)
  - [Gradle](#gradle)
  - [Maven](#maven)
- [Usage](#usage)

## Features

* Easy to use and understand
* Simple to implement
* Event creation, calling, and listening in a few lines of code
* Open source

## Installation

[![Latest Release](https://maven.pietr.space/api/badge/latest/releases/nl/devpieter/sees?name=Latest%20Release)](https://maven.pietr.space/#/releases/nl/devpieter/sees)
[![Latest Snapshot](https://maven.pietr.space/api/badge/latest/snapshots/nl/devpieter/sees?name=Latest%20Snapshot)](https://maven.pietr.space/#/snapshots/nl/devpieter/sees)

### Gradle

```gradle
repositories {
    maven {
        name "Pietr Space Releases"
        url "https://maven.pietr.space/releases"
    }
}

dependencies {
    implementation "nl.devpieter:sees:${version}"
}
```

### Maven

```xml
<repositories>
    <repository>
        <id>pietr-space-releases</id>
        <name>Pietr Space Releases</name>
        <url>https://maven.pietr.space/releases</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>nl.devpieter</groupId>
        <artifactId>sees</artifactId>
        <version>${version}</version>
    </dependency>
</dependencies>
```

## Usage

[//]: # (Link to wiki)
Examples and documentation can be found in the [wiki](https://github.com/DevPieter/Sees/wiki).