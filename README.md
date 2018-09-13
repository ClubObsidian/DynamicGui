# DynamicGui

[![Documentation Status](https://readthedocs.org/projects/dynamicguidocs/badge/?version=latest)](https://dynamicguidocs.readthedocs.io/en/latest/?badge=latest)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)

A rewrite of the plugin originally made for Club Obsidian. 
Designed to make writing menus for players to interact with easier.

## Features

* Multi server support in one jar
  * Bukkit - Fully supported
  * Sponge - Currently in development
  * Forge - Planned
* A number of functions to use to customize menus
  * Functions use latebinding
* Guis can be written in yaml, json or hocon
* Proxy support
* Custom replacers
  * Built-in and support for PlaceholderAPI
* Loading guis from a remote location
  * Currently supports loading from a webserver

## Use cases

Some possible uses for DynamicGui.

* Shop
* Hub menu
* Staff tools
* Help menu

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