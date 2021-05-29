<div align="center">
<h1>DynamicGui</h1>

[![Jenkins](https://img.shields.io/jenkins/build?jobUrl=https%3A%2F%2Fci.ravenlab.dev%2Fjob%2FDynamicGui%2F&logo=data:image/png;base64,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)](https://ci.ravenlab.dev/job/DynamicGui/)
[![CLA assistant](https://cla-assistant.io/readme/badge/ClubObsidian/DynamicGui)](https://cla-assistant.io/ClubObsidian/DynamicGui) 
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/5c517edfdbf946d3a9ac4187160eab0f)](https://www.codacy.com/app/virustotalop/DynamicGui?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=ClubObsidian/DynamicGui&amp;utm_campaign=Badge_Grade)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

</div>

A rewrite of the plugin originally created at Club Obsidian.

Designed to make writing menus easy.


## Features

* Multi server support in one jar
  * Bukkit - Fully supported
  * Sponge - In alpha
    * Things may break
* A number of functions to use to customize menus
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
  * Punishments

## Downloads

You can get [development builds from our jenkins.](https://ci.ravenlab.dev/job/DynamicGui/)

## Getting Started

### Gui Examples

You can find [gui examples here](https://github.com/ClubObsidian/DynamicGuiExamples).

### Gui Documentation

You find find [gui documentation here](https://dynamicgui.github.io/documentation/).

## Contributing

Before contributing please read our [documentation on contributing.](CONTRIBUTING.md)


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

`gradlew shadowJar`
