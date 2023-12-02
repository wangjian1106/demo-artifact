package com.example.jps;

import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.model.JpsElement;
import org.jetbrains.jps.model.JpsElementCollection;
import org.jetbrains.jps.model.ex.JpsElementCollectionRole;
import org.jetbrains.jps.model.module.JpsModule;
import org.jetbrains.jps.model.serialization.facet.JpsFacetConfigurationSerializer;

import static com.example.jps.Constants.FACET_RELATIVE_PATH;
import static com.example.jps.Constants.FACET_RESOURCE_DIRECTORY;

public class MyJpsFacetConfigurationSerializer extends JpsFacetConfigurationSerializer<MyJpsModuleExtension> {

    private final JpsElementCollectionRole<MyJpsModuleExtension> collectionRole;

    public MyJpsFacetConfigurationSerializer() {
        super(MyJpsModuleExtensionImpl.ROLE, Constants.MY_FACET_ID, null);
        this.collectionRole = MyJpsModuleExtensionImpl.COLLECTION_ROLE;
    }

    @Override
    public MyJpsModuleExtension loadExtension(@NotNull Element facetConfigurationElement, String name, JpsElement parent, JpsModule module) {
        MyJpsModuleExtensionImpl extension = new MyJpsModuleExtensionImpl(name);
        String relativePath = facetConfigurationElement.getAttributeValue(FACET_RELATIVE_PATH);
        String resourceDirectory = facetConfigurationElement.getAttributeValue(FACET_RESOURCE_DIRECTORY);
        extension.addFacetConfiguration(relativePath, resourceDirectory);
        return extension;
    }

    @Override
    public MyJpsModuleExtension loadExtension(Element configurationElement, String facetName, JpsModule module, JpsElement parentFacet) {
        MyJpsModuleExtension e = this.loadExtension(configurationElement, facetName, parentFacet, module);
        return (module.getContainer().getOrSetChild(this.collectionRole)).addChild(e);
    }

    @Override
    public boolean hasExtension(JpsModule module) {
        JpsElementCollection<MyJpsModuleExtension> collection = module.getContainer().getChild(this.collectionRole);
        return collection != null && !collection.getElements().isEmpty();
    }
}
