package com.example.demo.artifact.facet;

import com.intellij.facet.pointers.FacetPointer;
import com.intellij.ide.projectView.PresentationData;
import com.intellij.packaging.ui.ArtifactEditorContext;
import com.intellij.packaging.ui.PackagingElementPresentation;
import com.intellij.ui.SimpleTextAttributes;
import org.jetbrains.annotations.NotNull;

public class MyComplexPackagingElementPresentation extends PackagingElementPresentation {
    private final MyFacet myFacet;
    private final ArtifactEditorContext context;
    private final FacetPointer<MyFacet> myFacetPointer;
    private final String kind;

    public MyComplexPackagingElementPresentation(MyFacet myFacet, ArtifactEditorContext context, FacetPointer<MyFacet> myFacetPointer, String kind) {
        this.myFacet = myFacet;
        this.context = context;
        this.myFacetPointer = myFacetPointer;
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
    public void render(@NotNull PresentationData presentationData, SimpleTextAttributes mainAttributes, SimpleTextAttributes commentAttributes) {
        String text = String.format("%s module: %s facet %s",
                this.myFacetPointer.getModuleName(this.context.getModifiableModuleModel()), getPresentableName(), this.kind);
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
