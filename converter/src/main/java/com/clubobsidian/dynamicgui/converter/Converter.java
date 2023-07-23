package com.clubobsidian.dynamicgui.converter;

import com.clubobsidian.wrappy.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public interface Converter {

    ConfigurationSection convert(@NotNull File file);

}