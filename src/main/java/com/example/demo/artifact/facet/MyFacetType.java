package com.example.demo.artifact.facet;

import com.intellij.facet.Facet;
import com.intellij.facet.FacetType;
import com.intellij.openapi.module.JavaModuleType;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleType;
import com.intellij.util.PlatformIcons;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class MyFacetType extends FacetType<MyFacet, MyFacetConfiguration> {

    private static MyFacetType instance;

    public static MyFacetType getInstance() {
        if (instance == null) {
            instance = FacetType.findInstance(MyFacetType.class);
        }
        return instance;
    }

    public MyFacetType() {
        super(MyFacet.ID, MyFacet.ID.toString(), "MyFacet");
    }

    @Override
    public MyFacetConfiguration createDefaultConfiguration() {
        return new MyFacetConfiguration();
    }

    @Override
    public MyFacet createFacet(@NotNull Module module, String name,
                               @NotNull MyFacetConfiguration configuration, @Nullable Facet underlyingFacet) {
        return new MyFacet(this, module, name, configuration, underlyingFacet);
    }

    @Override
    public boolean isSuitableModuleType(ModuleType moduleType) {
        return moduleType instanceof JavaModuleType;
    }

    @Override
    public @NotNull Icon getIcon() {
        return PlatformIcons.LIBRARY_ICON;
    }
}
