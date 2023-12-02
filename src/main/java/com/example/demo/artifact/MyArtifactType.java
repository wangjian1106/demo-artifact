package com.example.demo.artifact;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.module.Module;
import com.intellij.packaging.artifacts.ArtifactType;
import com.intellij.packaging.elements.CompositePackagingElement;
import com.intellij.packaging.elements.PackagingElementFactory;
import com.intellij.packaging.elements.PackagingElementOutputKind;
import com.intellij.packaging.elements.PackagingElementResolvingContext;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Collections;
import java.util.List;

public class MyArtifactType extends ArtifactType {

    public static MyArtifactType getInstance() {
        return EP_NAME.findExtension(MyArtifactType.class);
    }

    public MyArtifactType() {
        super("MyArtifact", () -> "MyArtifact");
    }

    @Override
    public @NotNull Icon getIcon() {
        return AllIcons.Nodes.Artifact;
    }

    @Override
    public @NotNull String getDefaultPathFor(@NotNull PackagingElementOutputKind kind) {
        return "/";
    }

    @Override
    public @NotNull CompositePackagingElement<?> createRootElement(@NotNull String artifactName) {
        return PackagingElementFactory.getInstance().createArtifactRootElement();
    }

    @NotNull
    @Override
    public List<MyFromModulesArtifactTemplate> getNewArtifactTemplates(@NotNull PackagingElementResolvingContext context) {
        return Collections.singletonList(new MyFromModulesArtifactTemplate(context, this));
    }

    public String getArtifactName(List<Module> modules) {
        if (modules.size() == 1) {
            return modules.get(0).getName();
        }
        StringBuilder name = new StringBuilder();
        for (Module module : modules) {
            name.append(module.getName()).append(",");
        }
        name.delete(name.length() - 1, 1);
        return name.toString();
    }
}
