package com.example.jps;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.model.artifact.elements.JpsComplexPackagingElement;

public interface MyJpsFacetResourcesPackagingElement extends JpsComplexPackagingElement {
    @NotNull MyJpsExtensionReference getExtensionReference();
}
