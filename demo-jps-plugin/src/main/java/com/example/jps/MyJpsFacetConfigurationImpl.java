package com.example.jps;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.model.JpsElementChildRole;
import org.jetbrains.jps.model.ex.JpsElementBase;
import org.jetbrains.jps.model.ex.JpsElementChildRoleBase;
import org.jetbrains.jps.model.ex.JpsElementCollectionRole;

public class MyJpsFacetConfigurationImpl extends JpsElementBase<MyJpsFacetConfigurationImpl> implements MyJpsFacetConfiguration {

    private static final JpsElementChildRole<MyJpsFacetConfiguration> ROLE = JpsElementChildRoleBase.create("my facet configuration");
    public static final JpsElementCollectionRole<MyJpsFacetConfiguration> COLLECTION_ROLE = JpsElementCollectionRole.create(ROLE);

    private final String relativePath;
    private final String resourceDirectory;

    public MyJpsFacetConfigurationImpl(String relativePath, String resourceDirectory) {
        this.relativePath = relativePath;
        this.resourceDirectory = resourceDirectory;
    }

    private MyJpsFacetConfigurationImpl(MyJpsFacetConfiguration myJpsFacetConfiguration) {
        this.relativePath = myJpsFacetConfiguration.getRelativePath();
        this.resourceDirectory = myJpsFacetConfiguration.getResourceDirectory();
    }

    @Override
    public String getRelativePath() {
        return relativePath;
    }

    @Override
    public String getResourceDirectory() {
        return resourceDirectory;
    }

    @Override
    public @NotNull MyJpsFacetConfigurationImpl createCopy() {
        return new MyJpsFacetConfigurationImpl(this);
    }

    @Override
    public void applyChanges(@NotNull MyJpsFacetConfigurationImpl modified) {
    }
}
