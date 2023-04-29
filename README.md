<div align="center">
<h1>DynamicGui</h1>

[![build](https://github.com/ClubObsidian/DynamicGui/actions/workflows/build.yml/badge.svg)](https://github.com/ClubObsidian/DynamicGui/actions/workflows/build.yml)
[![](https://jitpack.io/v/ClubObsidian/DynamicGui.svg)](https://jitpack.io/#ClubObsidian/DynamicGui)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

A plugin designed to make writing menus for Minecraft easy. Menus can be written in the configuration language of your choice: yaml, hocon, json or xml.
</div>

## Features

* A number of functions to use to customize menus
  * Asynchronous functions
  * Functions use latebinding
  * Addons can be written for more functions
  * Use conditions with replacers using [EvalEx](https://github.com/uklimaschewski/EvalEx)
* Guis can be written in yaml, json, xml or hocon
* Proxy support
  * Bungeecord
* Custom replacers
  * Built-in replacer support
  * Support for [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/)
  * User defined macros
    * Per Gui, Slot and Global
    * Supports macro chaining
* Loading guis from a remote location
  * Currently supports loading from a webserver
  * Can also load from websites like Github using url parameters
* Different gui types
  * Beacon
  * Brewing stand
  * Chest
  * Crafting Table
  * Dispenser
  * Dropper
  * Furnace
  * Hopper
  * Workbench

## Future features

* Support for sponge

## Use cases

Just a few uses for DynamicGui.

* Crafting recipes
* Crate rewards
* Help menu
* Hub menu
* Kit preview
* Player information
* Player statistics
* Quests
* Shop
* Staff tools

## Downloads

You can get [development builds from Github actions](https://github.com/ClubObsidian/DynamicGui/actions/workflows/build.yml) but you will need a Github account.

## Getting Started

### Gui Documentation

You find find [gui documentation here](https://dynamicgui.github.io/documentation/).

## Contributing

Before contributing please read our [documentation on contributing.](CONTRIBUTING.md)


## Dependency

Gradle
```groovy
repositories {
  maven { 
    url 'https://jitpack.io' 
  }
}

dependencies {
  compileOnly 'com.github.clubobsidian.dynamicgui:api:6.1.0'
}
```
Maven
```xml
<repositories>
	<repository>
		<id>jitpack.io</id>
		<url>https://jitpack.io</url>
	</repository>
</repositories>
<dependency>
	<groupId>com.github.ClubObsidian.DynamicGui</groupId>
	<artifactId>api</artifactId>
	<version>6.1.0</version>
</dependency>
```

## Development

### Eclipse

1. Git clone the project
2. Generate eclipse files with `gradlew eclipse`
3. Import project
4. Right click on the project
5. Add gradle nature

### Intellij

1. Git clone the project
2. Generate intellij files with `gradlew idea`
3. Import project

### Building

`gradlew build`
