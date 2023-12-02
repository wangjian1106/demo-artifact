package com.example.jps;

import org.jetbrains.jps.model.JpsNamedElement;
import org.jetbrains.jps.model.module.JpsModule;

import java.util.List;

public interface MyJpsModuleExtension extends JpsNamedElement {

    List<MyJpsFacetConfiguration> getFacetConfiguration();

    JpsModule getModule();
}
