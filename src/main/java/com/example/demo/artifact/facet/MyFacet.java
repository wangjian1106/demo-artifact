package com.example.demo.artifact.facet;

import com.google.common.collect.Lists;
import com.intellij.facet.Facet;
import com.intellij.facet.FacetType;
import com.intellij.facet.FacetTypeId;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.packaging.elements.PackagingElement;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Paths;
import java.util.List;

public class MyFacet extends Facet<MyFacetConfiguration> {

    public static final FacetTypeId<MyFacet> ID = new FacetTypeId<>("my-facet");

    public MyFacet(@NotNull FacetType facetType, @NotNull Module module, @NotNull String name,
                   @NotNull MyFacetConfiguration configuration, Facet underlyingFacet) {
        super(facetType, module, name, configuration, underlyingFacet);
        configuration.setFacetRelativePath(this.getRelativePath());
        configuration.setFacetResourceDirectory(this.getResourceDirectory());
    }

    public String getRelativePath() {
        return "lib";
    }

    public String getResourceDirectory() {
        return Paths.get(ModuleUtil.getModuleDirPath(getModule()), "lib").toString();
    }

    public List<? extends PackagingElement<?>> getFacetResourcesPackagingElements() {
        return Lists.newArrayList(new MyComplexPackagingElement(this));
    }
}
