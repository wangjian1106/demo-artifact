package com.example.demo.artifact.facet;

import com.google.common.collect.Lists;
import com.intellij.facet.pointers.FacetPointer;
import com.intellij.facet.pointers.FacetPointersManager;
import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.project.Project;
import com.intellij.packaging.artifacts.ArtifactType;
import com.intellij.packaging.elements.ComplexPackagingElement;
import com.intellij.packaging.elements.PackagingElement;
import com.intellij.packaging.elements.PackagingElementResolvingContext;
import com.intellij.packaging.impl.elements.DirectoryCopyPackagingElement;
import com.intellij.packaging.impl.elements.FacetBasedPackagingElement;
import com.intellij.packaging.ui.ArtifactEditorContext;
import com.intellij.packaging.ui.PackagingElementPresentation;
import com.intellij.util.xmlb.annotations.Attribute;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class MyComplexPackagingElement
        extends ComplexPackagingElement<MyComplexPackagingElement>
        implements FacetBasedPackagingElement {

    public static final String FACET_ATTRIBUTE = "facetId";

    private String path;
    private String facetId;
    private MyFacet myFacet;
    private FacetPointer<MyFacet> pointer;

    public MyComplexPackagingElement(Project project) {
        super(MyComplexPackagingElementType.getInstance());
        this.myProject = project;
    }

    public MyComplexPackagingElement(MyFacet myFacet) {
        super(MyComplexPackagingElementType.getInstance());
        this.myFacet = myFacet;
        this.path = myFacet.getPath();
        this.pointer = FacetPointersManager.getInstance(myFacet.getModule().getProject()).create(myFacet);
        this.facetId = pointer.getId();
    }

    @Override
    public @Nullable List<? extends PackagingElement<?>> getSubstitution(@NotNull PackagingElementResolvingContext context, @NotNull ArtifactType artifactType) {
        return Lists.newArrayList(new DirectoryCopyPackagingElement(path));
    }

    @Override
    public @NotNull PackagingElementPresentation createPresentation(@NotNull ArtifactEditorContext context) {
        return new MyComplexPackagingElementPresentation(myFacet, context, pointer, "resources");
    }

    @Attribute(FACET_ATTRIBUTE)
    public String getFacetId() {
        return this.facetId;
    }

    public void setFacetId(String facetId) {
        this.facetId = facetId;
    }

    @Override
    public boolean isEqualTo(@NotNull PackagingElement<?> element) {
        if (element instanceof MyComplexPackagingElement) {
            MyComplexPackagingElement other = (MyComplexPackagingElement)element;
            return Objects.equals(path, other.path) && Objects.equals(facetId, other.facetId);
        }
        return false;
    }

    @Override
    public @Nullable MyComplexPackagingElement getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull MyComplexPackagingElement state) {
        this.facetId = state.getFacetId();
        this.pointer = (this.facetId != null) ? FacetPointersManager.getInstance(this.myProject).create(this.facetId) : null;
        if (this.pointer != null) {
            this.myFacet = Objects.requireNonNull(this.pointer.getFacet());
            this.path = this.myFacet.getPath();
        }
    }

    @Nullable
    @Override
    public MyFacet findFacet(@NotNull PackagingElementResolvingContext context) {
        return ReadAction.compute(() -> this.pointer.findFacet(context.getModulesProvider(), context.getFacetsProvider()));
    }

    @Override
    public String toString() {
        return "my-facet-contents:" + this.pointer.getFacetName() + "(" + this.pointer.getModuleName() + ")";
    }
}
