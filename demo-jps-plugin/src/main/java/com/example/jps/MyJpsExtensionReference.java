package com.example.jps;

import org.jetbrains.jps.model.JpsElementReference;
import org.jetbrains.jps.model.ex.JpsElementCollectionRole;
import org.jetbrains.jps.model.module.JpsModuleReference;

public interface MyJpsExtensionReference extends JpsElementReference<MyJpsModuleExtension> {

    JpsModuleReference getParentReference();

    String getExtensionName();

    JpsElementCollectionRole<? extends MyJpsModuleExtension> getChildRole();
}
