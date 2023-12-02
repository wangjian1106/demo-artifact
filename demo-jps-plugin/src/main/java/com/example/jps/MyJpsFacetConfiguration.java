package com.example.jps;

import org.jetbrains.jps.model.JpsElement;

public interface MyJpsFacetConfiguration extends JpsElement {

    String getRelativePath();

    String getResourceDirectory();
}
