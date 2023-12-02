package com.example.demo.artifact.facet;

import com.intellij.facet.FacetConfiguration;
import com.intellij.facet.ui.FacetEditorContext;
import com.intellij.facet.ui.FacetEditorTab;
import com.intellij.facet.ui.FacetValidatorsManager;
import com.intellij.openapi.util.InvalidDataException;
import com.intellij.openapi.util.WriteExternalException;
import com.intellij.ui.components.JBTextField;
import com.intellij.util.ui.FormBuilder;
import com.intellij.util.xmlb.annotations.Attribute;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

@SuppressWarnings("deprecation")
public class MyFacetConfiguration implements FacetConfiguration {


    private static final String FACET_RELATIVE_PATH = "facetRelativePath";
    private static final String FACET_RESOURCE_DIRECTORY = "facetResourceDirectory";

    @Attribute(FACET_RELATIVE_PATH)
    private String facetRelativePath;
    @Attribute(FACET_RESOURCE_DIRECTORY)
    private String facetResourceDirectory;

    public String getFacetRelativePath() {
        return facetRelativePath;
    }

    public void setFacetRelativePath(String facetRelativePath) {
        this.facetRelativePath = facetRelativePath;
    }

    public String getFacetResourceDirectory() {
        return facetResourceDirectory;
    }

    public void setFacetResourceDirectory(String facetResourceDirectory) {
        this.facetResourceDirectory = facetResourceDirectory;
    }

    @Override
    public void readExternal(Element element) throws InvalidDataException {
        this.facetRelativePath = element.getAttributeValue(FACET_RELATIVE_PATH);
        this.facetResourceDirectory = element.getAttributeValue(FACET_RESOURCE_DIRECTORY);
    }

    @Override
    public void writeExternal(Element element) throws WriteExternalException {
        element.setAttribute(FACET_RELATIVE_PATH, facetRelativePath);
        element.setAttribute(FACET_RESOURCE_DIRECTORY, facetResourceDirectory);
    }

    @Override
    public FacetEditorTab[] createEditorTabs(FacetEditorContext editorContext, FacetValidatorsManager validatorsManager) {
        return new FacetEditorTab[] { new MyFacetConfigurationEditorTab(facetRelativePath, facetResourceDirectory) };
    }

    private static class MyFacetConfigurationEditorTab extends FacetEditorTab {

        private final String facetRelativePath;
        private final String facetResourceDirectory;

        private MyFacetConfigurationEditorTab(String facetRelativePath, String facetResourceDirectory) {
            this.facetRelativePath = facetRelativePath;
            this.facetResourceDirectory = facetResourceDirectory;
        }

        @Override
        public @NotNull JComponent createComponent() {
            JBTextField nameTextField = new JBTextField();
            nameTextField.setEditable(false);
            nameTextField.setText("MyFacet");
            JBTextField facetRelativePathTextField = new JBTextField();
            facetRelativePathTextField.setEditable(false);
            facetRelativePathTextField.setText(facetRelativePath);
            JBTextField facetResourceDirectoryTextField = new JBTextField();
            facetResourceDirectoryTextField.setEditable(false);
            facetResourceDirectoryTextField.setText(facetResourceDirectory);
            return FormBuilder.createFormBuilder()
                    .addLabeledComponent("Name", nameTextField)
                    .addLabeledComponent("MyFacetRelativePath", facetRelativePathTextField)
                    .addLabeledComponent("MyFacetResourceDirectory", facetResourceDirectoryTextField)
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
