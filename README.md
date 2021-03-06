# Gradle dependencies formatter
[![Build Status](https://travis-ci.org/platan/idea-gradle-dependencies-formatter.svg?branch=master)](https://travis-ci.org/platan/idea-gradle-dependencies-formatter)
[![Build Status (Windows)](https://img.shields.io/appveyor/ci/platan/idea-gradle-dependencies-formatter/master.svg?label=build%20(Windows))](https://ci.appveyor.com/project/platan/idea-gradle-dependencies-formatter)
[![Coverage Status](https://coveralls.io/repos/platan/idea-gradle-dependencies-formatter/badge.svg?branch=master&service=github)](https://coveralls.io/github/platan/idea-gradle-dependencies-formatter?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/1a2524f23e9b49538760cdce6368bf8e)](https://www.codacy.com/app/platan/idea-gradle-dependencies-formatter?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=platan/idea-gradle-dependencies-formatter&amp;utm_campaign=Badge_Grade)
[![codebeat badge](https://codebeat.co/badges/669c521f-7ecb-4e0a-a079-fa5c28ca17c7)](https://codebeat.co/projects/github-com-platan-idea-gradle-dependencies-formatter-master)
[![Version](https://img.shields.io/jetbrains/plugin/v/7937.svg?label=latest)](https://plugins.jetbrains.com/plugin/7937-gradle-dependencies-formatter)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/7937.svg)](https://plugins.jetbrains.com/plugin/7937-gradle-dependencies-formatter)

IntelliJ IDEA plugin for formatting Gradle dependencies. 

## Features

- Convert a string notation to a map notation
- Convert a map notation to a string notation
- Sort dependencies
- Paste a Maven dependency as a Gradle dependency (on the fly conversion)

## Installation

Install using the JetBrains Plugin Repository:  
`File` > `Settings` > `Plugins` > `Browse repositories...` > type `gradle dependencies formatter` in search form > `Install plugin`

## Usage

### Convert between a string notation and a map notation

Use `Show Intention Actions` action (`Alt + Enter` or ⌥⏎) and choose `Convert to map notation` or `Convert to string notation`.

![Convert a string notation to a map notation](https://raw.githubusercontent.com/platan/idea-gradle-dependencies-formatter/master/readme/convert.gif)

### Sort dependencies

In order to sort dependencies open a `.gradle` file and use `Sort Gradle dependencies` action from `Code` menu. 

![Sort dependencies](https://raw.githubusercontent.com/platan/idea-gradle-dependencies-formatter/master/readme/sort.gif)

### Paste a Maven dependency as a Gradle dependency

1. Copy a declaration of a Maven dependency in any editor.
2. Paste it into gradle file to dependencies section. Plugin will automatically convert it to a Gradle dependency.

![Paste a Maven dependency as a Gradle dependency](https://raw.githubusercontent.com/platan/idea-gradle-dependencies-formatter/master/readme/paste.gif)

Features:
- converts Maven dependency with `groupId` (required), `artifactId` (required), `version`, `classifier`, `optional` and `exclusions` fields
- handles scopes: `compile`, `provided`, `runtime`, `test`
- optional dependencies are coded using syntax defined by [Nebula Extra Configurations](https://github.com/nebula-plugins/gradle-extra-configurations-plugin/)
- can convert several dependencies at once

If you need to paste XML with maven dependency into gradle file without modification, please use `Paste Simple` action.

Note: In version 2016.3 IntelliJ IDEA added a similar feature. It converts a single maven dependency at once and handles only simple cases. This plugin overrides this built-in feature.

## Development

Build:  
`./gradlew build`

In order to run tests with specific IntelliJ IDEA version (e.g. 2016.3) use this command:  
`./gradlew -P ideaVersion=2016.3 test`

Run IntelliJ IDEA with the plugin intalled in:
`./gradlew runIde`

This project uses [gradle-intellij-plugin](https://github.com/JetBrains/gradle-intellij-plugin). Please refer to its documentation for information about a configuration. 

Note: Currently the project is not prepared to be imported as a IntelliJ Platform Plugin.

## Changelog

### 0.5.3 (2017-12-19)
- (bugfix) Unable to paste multiple Maven dependencies at once [#3](https://github.com/platan/idea-gradle-dependencies-formatter/issues/3)

### 0.5.2 (2017-04-09)
- (bugfix) fixed NPE in `MapNotationToStringNotationIntention#getElementPredicate`

### 0.5.1 (2017-02-12)
- (improvement) Run `MavenToGradleDependenciesCopyPasteProcessor` before built-in `CopyPasteProcessor`s

### 0.5.0 (2016-03-29)
- (feature) Sort dependencies

### 0.4.0 (2015-10-20)
- (feature) Convert a map notation to a string notation
- (bugfix) Convert string notation of a dependency with ext to a map notation

### 0.3.0 (2015-09-22)
- Convert a string notation to a map notation

### 0.2.0 (2015-09-08)
- Paste a Maven dependency as a Gradle dependency:
    - added support for elements `classifier` and `optional`
    - version is skipped for dependency without version

### 0.1.0 - initial release (2015-09-02)
- Paste a Maven dependency as a Gradle dependency

## License

This project is licensed under the MIT license.
