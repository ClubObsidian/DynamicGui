package com.clubobsidian.dynamicgui.converter;

import com.clubobsidian.wrappy.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.Objects;

public abstract class Converter {

    private final File directory;

    public Converter(@NotNull File baseDirectory) {
        this.directory = new File(Objects.requireNonNull(baseDirectory), this.name());
    }

    public File getDirectory() {
        return this.directory;
    }

    public abstract String name();

    public abstract ConfigurationSection convert(@NotNull File file);

}