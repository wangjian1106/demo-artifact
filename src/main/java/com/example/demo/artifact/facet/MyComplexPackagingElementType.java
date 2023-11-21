package com.example.demo.artifact.facet;

import com.google.common.collect.Lists;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.NlsActions;
import com.intellij.packaging.artifacts.Artifact;
import com.intellij.packaging.elements.ComplexPackagingElementType;
import com.intellij.packaging.elements.CompositePackagingElement;
import com.intellij.packaging.elements.PackagingElement;
import com.intellij.packaging.elements.PackagingElementType;
import com.intellij.packaging.ui.ArtifactEditorContext;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class MyComplexPackagingElementType extends ComplexPackagingElementType<MyComplexPackagingElement> {

    public static MyComplexPackagingElementType getInstance() {
        return PackagingElementType.getInstance(MyComplexPackagingElementType.class);
    }

    public static final String TYPE_ID = "my-facet-contents";

    public MyComplexPackagingElementType() {
        super(TYPE_ID, () -> "My Facet Contents");
    }

    @Override
    public boolean canCreate(@NotNull ArtifactEditorContext context, @NotNull Artifact artifact) {
        Module[] modules = context.getModulesProvider().getModules();
        List<MyFacet> myFacets = MyFacetSourceItemsProvider.getMyFacets(context, modules);
        return !myFacets.isEmpty();
    }

    @Override
    public @NotNull List<? extends PackagingElement<?>> chooseAndCreate(@NotNull ArtifactEditorContext context,
                                                                        @NotNull Artifact artifact,
                                                                        @NotNull CompositePackagingElement<?> parent) {
        Collection<MyFacet> facets = MyFacetSourceItemsProvider.getMyFacets(context, context.getModulesProvider().getModules());

        ChooseFacetsDialog dialog = new ChooseFacetsDialog(context.getProject(), Lists.newArrayList(facets), "Choose My Facet", "");
        dialog.show();
        List<MyComplexPackagingElement> elements = Lists.newArrayList();
        if (dialog.isOK()) {
            for (MyFacet facet : dialog.getChosenElements()) {
                elements.add(new MyComplexPackagingElement(facet));
            }
        }
        return elements;
    }

    @Override
    public @NotNull MyComplexPackagingElement createEmpty(@NotNull Project project) {
        return new MyComplexPackagingElement(project);
    }

    @Override
    public @NlsActions.ActionText String getShowContentActionText() {
        return "Show Content of the Facet";
    }
}
