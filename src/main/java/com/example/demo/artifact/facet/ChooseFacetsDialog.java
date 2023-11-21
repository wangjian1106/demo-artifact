package com.example.demo.artifact.facet;

import com.intellij.ide.util.ChooseElementsDialog;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.List;

public class ChooseFacetsDialog extends ChooseElementsDialog<MyFacet> {

    public ChooseFacetsDialog(@NotNull Project project, List<MyFacet> myFacets, String title, String description) {
        super(project, myFacets, title, description);
    }

    @Override
    protected String getItemText(MyFacet item) {
        return item.getName();
    }

    @Override
    protected Icon getItemIcon(MyFacet item) {
        return MyFacetType.getInstance().getIcon();
    }
}
