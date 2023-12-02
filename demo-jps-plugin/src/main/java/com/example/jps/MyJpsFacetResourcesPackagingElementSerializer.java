package com.example.jps;

import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.jps.model.ex.JpsElementCollectionRole;
import org.jetbrains.jps.model.serialization.artifact.JpsPackagingElementSerializer;

import static com.example.jps.Constants.FACET_ID_ATTRIBUTE;
import static com.example.jps.Constants.MY_FACET_RESOURCE_ELEMENT_TYPE_ID;

public class MyJpsFacetResourcesPackagingElementSerializer extends JpsPackagingElementSerializer<MyJpsFacetResourcesPackagingElement> {

    public MyJpsFacetResourcesPackagingElementSerializer() {
        super(MY_FACET_RESOURCE_ELEMENT_TYPE_ID, MyJpsFacetResourcesPackagingElement.class);
    }

    public MyJpsFacetResourcesPackagingElement load(Element element) {
        String facetId = element.getAttributeValue(FACET_ID_ATTRIBUTE);
        return createFacetResourcesPackagingElement(createReference(facetId));
    }

    public @NotNull MyJpsExtensionReference createReference(@NotNull String facetId) {
        int indexOfFirstSlash = facetId.indexOf('/');
        int indexOfLastSlash = facetId.lastIndexOf('/');
        String moduleName = facetId.substring(0, indexOfFirstSlash);
        String facetTypeId = facetId.substring(indexOfFirstSlash + 1, indexOfLastSlash);
        String extensionName = facetId.substring(indexOfLastSlash + 1);
        return new MyJpsExtensionReferenceImpl(getExtensionRole(facetTypeId), moduleName, extensionName);
    }

    public @NotNull MyJpsFacetResourcesPackagingElement createFacetResourcesPackagingElement(@NotNull MyJpsExtensionReference reference) {
        return new MyJpsFacetResourcesPackagingElementImpl(reference);
    }

    private static JpsElementCollectionRole<? extends MyJpsModuleExtension> getExtensionRole(String id) {
        if (Constants.MY_FACET_ID.equals(id)) {
            return MyJpsModuleExtensionImpl.COLLECTION_ROLE;
        }
        throw new IllegalArgumentException("Unknown JavaEE module extension id:" + id);
    }
}