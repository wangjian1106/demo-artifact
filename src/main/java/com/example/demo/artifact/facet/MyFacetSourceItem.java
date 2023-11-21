package com.example.demo.artifact.facet;

import com.intellij.facet.pointers.FacetPointer;
import com.intellij.facet.pointers.FacetPointersManager;
import com.intellij.ide.projectView.PresentationData;
import com.intellij.openapi.roots.ui.configuration.artifacts.sourceItems.DelegatedSourceItemPresentation;
import com.intellij.packaging.elements.PackagingElement;
import com.intellij.packaging.ui.ArtifactEditorContext;
import com.intellij.packaging.ui.PackagingSourceItem;
import com.intellij.packaging.ui.SourceItemPresentation;
import com.intellij.ui.SimpleTextAttributes;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

public class MyFacetSourceItem extends PackagingSourceItem {

    private final MyFacet myFacet;

    public MyFacetSourceItem(@NotNull MyFacet facet) {
        this.myFacet = facet;
    }

    @NotNull
    @Override
    public SourceItemPresentation createPresentation(@NotNull ArtifactEditorContext context) {
        FacetPointer<MyFacet> pointer = FacetPointersManager.getInstance(context.getProject()).create(this.myFacet);
        return new DelegatedSourceItemPresentation(new MyFacetTreeNodePresentation(context, pointer, "content")) {
            public void render(@NotNull PresentationData presentationData,
                               SimpleTextAttributes mainAttributes,
                               SimpleTextAttributes commentAttributes) {
                presentationData.setIcon(MyFacetSourceItem.this.myFacet.getType().getIcon());
                presentationData.addText(String.format("%s facet contents", myFacet.getName()), mainAttributes);
            }

            public int getWeight() {
                return 5;
            }
        };
    }

    @NotNull
    @Override
    public List<? extends PackagingElement<?>> createElements(@NotNull ArtifactEditorContext context) {
        return Collections.singletonList(new MyComplexPackagingElement(this.myFacet));
    }

    @Override
    public boolean equals(Object obj) {
        return (obj instanceof MyFacetSourceItem && this.myFacet.equals(((MyFacetSourceItem)obj).myFacet));
    }

    @Override
    public int hashCode() {
        return this.myFacet.hashCode();
    }
}
