package com.clubobsidian.dynamicgui.converter.deluxe;

import com.clubobsidian.dynamicgui.converter.Converter;
import com.clubobsidian.wrappy.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class DeluxeConverter extends Converter {

    public DeluxeConverter(@NotNull File baseDirectory) {
        super(baseDirectory);
    }

    @Override
    public String name() {
        return "deluxe";
    }

    @Override
    public ConfigurationSection convert(@NotNull File file) {
        return null;
    }
}