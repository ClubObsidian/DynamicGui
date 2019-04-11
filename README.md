# DynamicGui


[![Bounty](https://img.shields.io/bountysource/team/ClubObsidian/activity.svg)](https://github.com/ClubObsidian/DynamicGui/issues?q=is%3Aopen+is%3Aissue+label%3Abounty)
[![CLA assistant](https://cla-assistant.io/readme/badge/ClubObsidian/DynamicGui)](https://cla-assistant.io/ClubObsidian/DynamicGui) 
[![Build Status](https://travis-ci.org/ClubObsidian/DynamicGui.svg?branch=master)](https://travis-ci.org/ClubObsidian/DynamicGui)
[![Documentation Status](https://readthedocs.org/projects/dynamicguidocs/badge/?version=latest)](https://dynamicguidocs.readthedocs.io/en/latest/?badge=latest)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)


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
* Loading guis from a remote location
  * Currently supports loading from a webserver
* Different gui types
  * beacon
  * brewing
  * chest
  * crafting
  * dispenser
  * dropper
  * furnace
  * hopper
  * workbench


## Use cases

Just a few uses for DynamicGui.

* Crate rewards
* Help menu
* Hub menu
* Kit preview
* Player information
* Quests
* Shop
* Staff tools

## Getting Started

### Gui Examples

You can find [gui examples here](https://github.com/ClubObsidian/DynamicGuiExamples).

### Gui Documentation

You find find [gui documentation here](https://dynamicguidocs.readthedocs.io/en/latest/).

## Contributing

There are a few ways you can contribute to DynamicGui. The first is filling issue reports, which is a great help.
The second way you can contribute is by submitting code. We accept most contributions which do not break
backwards compatability. DynamicGui should not have any hard dependencies on plugins on build-time or run-time.
Please use reflection if you want or need a plugin dependency. If you are writing against a plugin dependency a 
registry should be created so that other plugin dependencies can be optionally used in the future. Before your code will
be accepted please sign our [CLA](https://cla-assistant.io/ClubObsidian/DynamicGui).


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
