package com.example.demo.artifact.facet;

import com.google.common.collect.Lists;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.roots.ui.configuration.artifacts.sourceItems.ModuleSourceItemGroup;
import com.intellij.packaging.artifacts.Artifact;
import com.intellij.packaging.impl.artifacts.ArtifactUtil;
import com.intellij.packaging.ui.ArtifactEditorContext;
import com.intellij.packaging.ui.PackagingSourceItem;
import com.intellij.packaging.ui.PackagingSourceItemsProvider;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyFacetSourceItemsProvider extends PackagingSourceItemsProvider {

    @NotNull
    public Collection<? extends PackagingSourceItem> getSourceItems(@NotNull ArtifactEditorContext editorContext,
                                                                    @NotNull Artifact artifact,
                                                                    PackagingSourceItem parent) {
        List<PackagingSourceItem> items = Lists.newArrayList();
        if (parent instanceof ModuleSourceItemGroup) {
            for (MyFacet facet : getNotAddedFacets(editorContext, artifact, ((ModuleSourceItemGroup)parent).getModule())) {
                items.add(new MyFacetSourceItem(facet));
            }
        }
        return items;
    }

    public static Collection<MyFacet> getNotAddedFacets(ArtifactEditorContext editorContext, Artifact artifact, Module... modules) {
        Set<MyFacet> facets = new HashSet<>(getMyFacets(editorContext, modules));
        ArtifactUtil.processPackagingElements(artifact, MyComplexPackagingElementType.getInstance(), element -> {
            facets.remove(element.findFacet(editorContext));
            return true;
        }, editorContext, true);
        return facets;
    }

    public static List<MyFacet> getMyFacets(ArtifactEditorContext editorContext, Module[] modules) {
        List<MyFacet> facets = Lists.newArrayList();
        for (Module module : modules) {
            facets.addAll(editorContext.getFacetsProvider().getFacetsByType(module, MyFacet.ID));
        }
        return facets;
    }
}
