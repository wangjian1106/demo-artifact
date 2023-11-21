package com.example.demo.artifact;

import com.example.demo.artifact.facet.MyFacet;
import com.google.common.collect.Lists;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.module.ModuleUtil;
import com.intellij.openapi.roots.OrderEnumerator;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.roots.ui.configuration.ChooseModulesDialog;
import com.intellij.packaging.artifacts.ArtifactTemplate;
import com.intellij.packaging.artifacts.ModifiableArtifact;
import com.intellij.packaging.elements.CompositePackagingElement;
import com.intellij.packaging.elements.PackagingElementResolvingContext;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;

public class MyFromModulesArtifactTemplate extends ArtifactTemplate {

    protected final PackagingElementResolvingContext context;

    protected final MyArtifactType artifactType;

    public MyFromModulesArtifactTemplate(PackagingElementResolvingContext context, MyArtifactType artifactType) {
        this.context = context;
        this.artifactType = artifactType;
    }

    @Override
    public NewArtifactConfiguration createArtifact() {
        Module[] modules = context.getModulesProvider().getModules();
        ChooseModulesDialog dialog = new ChooseModulesDialog(context.getProject(),
                Lists.newArrayList(modules),
                "Choose Modules",
                "Selected modules will be included in the created artifact with all dependencies");
        dialog.setSingleSelectionMode();
        if (!dialog.showAndGet() || dialog.getChosenElements().isEmpty()) {
            return null;
        }
        assert dialog.getChosenElements().size() == 1;
        return doCreateArtifact(dialog.getChosenElements());
    }

    @NotNull
    public NewArtifactConfiguration doCreateArtifact(final List<Module> modules) {
        String artifactName = this.artifactType.getArtifactName(modules);
        CompositePackagingElement<?> root = this.artifactType.createRootElement(artifactName);

        OrderEnumerator orderEnumerator = ProjectRootManager.getInstance(context.getProject())
                .orderEntries(modules)
                .productionOnly();
        OrderEnumerator enumerator = orderEnumerator.using(context.getModulesProvider())
                .withoutSdk()
                .runtimeOnly()
                .recursively();
        enumerator.forEach(entry -> {
            if (entry instanceof com.intellij.openapi.roots.ModuleSourceOrderEntry) {
                Collection<MyFacet> myFacets = this.context.getFacetsProvider().getFacetsByType(entry.getOwnerModule(), MyFacet.ID);
                for (MyFacet myFacet : myFacets) {
                    root.addOrFindChildren(myFacet.getFacetResourcesPackagingElements());
                }
            }
            return true;
        });

        return new NewArtifactConfiguration(root, artifactName, artifactType);
    }

    @Override
    public void setUpArtifact(@NotNull ModifiableArtifact artifact, @NotNull NewArtifactConfiguration configuration) {
        Module module = getModule(artifact);
        if (module == null) {
            return;
        }
        setUpArtifact(artifact, module);
    }

    protected void setUpArtifact(@NotNull ModifiableArtifact artifact, @NotNull Module module) {
        String moduleDirPath = ModuleUtil.getModuleDirPath(module);
        String deploymentDescriptorPath = Paths.get(moduleDirPath, "target").toString();
        artifact.setOutputPath(deploymentDescriptorPath);
    }

    @Override
    public @Nls String getPresentableName() {
        return "From module";
    }

    @Nullable
    protected Module getModule(ModifiableArtifact artifact) {
        String name = artifact.getName();
        int index = name.lastIndexOf(':');
        if (index >= 0) {
            name = name.substring(0, index);
        }
        return context.getModulesProvider().getModule(name);
    }
}
