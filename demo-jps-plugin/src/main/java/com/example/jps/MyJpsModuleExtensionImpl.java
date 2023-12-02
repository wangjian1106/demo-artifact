package com.example.jps;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.model.JpsElementChildRole;
import org.jetbrains.jps.model.ex.JpsElementChildRoleBase;
import org.jetbrains.jps.model.ex.JpsElementCollectionRole;
import org.jetbrains.jps.model.ex.JpsNamedCompositeElementBase;
import org.jetbrains.jps.model.module.JpsModule;

import java.util.List;

public class MyJpsModuleExtensionImpl extends JpsNamedCompositeElementBase<MyJpsModuleExtensionImpl> implements MyJpsModuleExtension {
    public static final JpsElementChildRole<MyJpsModuleExtension> ROLE = JpsElementChildRoleBase.create("my module");
    public static final JpsElementCollectionRole<MyJpsModuleExtension> COLLECTION_ROLE = JpsElementCollectionRole.create(ROLE);

    public MyJpsModuleExtensionImpl(@NotNull String name) {
        super(name);
        this.myContainer.setChild(MyJpsFacetConfigurationImpl.COLLECTION_ROLE);
    }

    private MyJpsModuleExtensionImpl(MyJpsModuleExtensionImpl original) {
        super(original);
    }

    public void addFacetConfiguration(String relativePath, String resourceDirectory) {
        MyJpsFacetConfigurationImpl myJpsFacetConfiguration = new MyJpsFacetConfigurationImpl(relativePath, resourceDirectory);
        this.myContainer.getChild(MyJpsFacetConfigurationImpl.COLLECTION_ROLE).addChild(myJpsFacetConfiguration);
    }

    @Override
    public List<MyJpsFacetConfiguration> getFacetConfiguration() {
        return this.myContainer.getChild(MyJpsFacetConfigurationImpl.COLLECTION_ROLE).getElements();
    }

    @Override
    public JpsModule getModule() {
        return (JpsModule)this.myParent.getParent();
    }

    @Override
    public @NotNull MyJpsModuleExtensionImpl createCopy() {
        return new MyJpsModuleExtensionImpl(this);
    }

}
