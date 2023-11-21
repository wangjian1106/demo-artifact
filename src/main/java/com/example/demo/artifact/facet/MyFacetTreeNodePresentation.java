package com.example.demo.artifact.facet;

import com.intellij.facet.pointers.FacetPointer;
import com.intellij.ide.projectView.PresentationData;
import com.intellij.packaging.ui.ArtifactEditorContext;
import com.intellij.packaging.ui.TreeNodePresentation;
import com.intellij.ui.SimpleTextAttributes;
import org.jetbrains.annotations.NotNull;

public class MyFacetTreeNodePresentation extends TreeNodePresentation {
    private final MyFacet myFacet;
    private final ArtifactEditorContext context;
    private final FacetPointer<MyFacet> myFacetPointer;
    private final String kind;

    public MyFacetTreeNodePresentation(@NotNull ArtifactEditorContext context,
                                       @NotNull FacetPointer<MyFacet> facetPointer,
                                       @NotNull String kind) {
        this.context = context;
        this.myFacetPointer = facetPointer;
        this.myFacet = facetPointer.findFacet(context.getModulesProvider(), context.getFacetsProvider());
        this.kind = kind;
    }

    @Override
    public String getPresentableName() {
        return this.myFacetPointer.getFacetName(this.context.getModulesProvider(), this.context.getFacetsProvider());
    }

    @Override
    public boolean canNavigateToSource() {
        return (this.myFacet != null);
    }

    @Override
    public void navigateToSource() {
        this.context.selectFacet(this.myFacet);
    }

    @Override
    public void render(@NotNull PresentationData presentationData,
                       SimpleTextAttributes mainAttributes,
                       SimpleTextAttributes commentAttributes) {
        String moduleName = this.myFacetPointer.getModuleName(this.context.getModifiableModuleModel());
        String text = String.format("%s module: %s facet %s", moduleName, getPresentableName(), this.kind);
        if (this.myFacet == null) {
            presentationData.addText(text, SimpleTextAttributes.ERROR_ATTRIBUTES);
        } else {
            presentationData.setIcon(this.myFacet.getType().getIcon());
            presentationData.addText(text, mainAttributes);
        }
    }

    @Override
    public int getWeight() {
        return 10;
    }
}
