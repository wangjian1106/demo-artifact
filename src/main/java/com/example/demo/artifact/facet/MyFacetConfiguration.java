package com.example.demo.artifact.facet;

import com.intellij.facet.FacetConfiguration;
import com.intellij.facet.ui.FacetEditorContext;
import com.intellij.facet.ui.FacetEditorTab;
import com.intellij.facet.ui.FacetValidatorsManager;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class MyFacetConfiguration implements FacetConfiguration {

    @Override
    public FacetEditorTab[] createEditorTabs(FacetEditorContext editorContext, FacetValidatorsManager validatorsManager) {
        return new FacetEditorTab[] { new MyFacetConfigurationEditorTab() };
    }

    private static class MyFacetConfigurationEditorTab extends FacetEditorTab {

        @Override
        public @NotNull JComponent createComponent() {
            JBTextField textField = new JBTextField();
            textField.setEditable(false);
            textField.setText("MyFacet");
            return FormBuilder.createFormBuilder()
                    .addLabeledComponent("Name", textField)
                    .addComponentFillVertically(new JPanel(), 0)
                    .getPanel();
        }

        @Override
        public boolean isModified() {
            return false;
        }

        @Override
        public String getDisplayName() {
            return "My Facet Configurations";
        }
    }
}
