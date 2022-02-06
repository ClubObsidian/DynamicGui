# Contributing

First off thank you for considering contributing to DynamicGui! Below are a few different ways you can contribute.

## Issues

If you run into a bug please report it, **please note if this is a bug that could result in item duplication or some other security risk please report it to `virustotal#0001` on the DynamicGui Discord server.** If the bug is not a security risk or could do damage to servers that are using the plugin please file a bug report at https://github.com/ClubObsidian/DynamicGui/issues

## Code

We gladly accept most contributions which do not break backwards compatability. For the most part we try to avoid hard or soft dependencies on plugins on build-time or run-time. Please use reflection if you want or need a plugin dependency. If you are writing against a plugin dependency a [registry](https://github.com/ClubObsidian/DynamicGui/tree/master/src/main/java/com/clubobsidian/dynamicgui/registry) should be created so that similar plugins can add compability.

## Documentation

If you are updating documentation please put `[ci skip]` in your commit so that your commit gets skipped by github actions, you can [read more about that here.](https://github.blog/changelog/2021-02-08-github-actions-skip-pull-request-and-push-workflows-with-skip-ci/)


## Features

* We are always looking new features, if you think you have a good idea create an issue about it and we will see if we will implement it!